package goa.systems.commons.sql;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import goa.systems.commons.io.InputOutput;

public class Migrator {

	private static final Logger logger = LoggerFactory.getLogger(Migrator.class);

	private DataSource ds;

	public Migrator(DataSource ds) {
		this.ds = ds;
	}

	public String[] getDefinedDatabaseVersions() {

		InputStream versionstream = Migrator.class.getResourceAsStream("/sql/versions");
		if (versionstream != null) {
			String sql = InputOutput.readString(versionstream);
			return sql.split("\\n");
		} else {
			logger.error("Can not find versions file.");
			return new String[] {};
		}
	}

	public String getDatabaseVersion() {
		String version = "0.0.0";
		if (ds != null) {
			try (Connection c = ds.getConnection(); Statement s = c.createStatement();) {
				if (s != null) {
					String catalog = c.getCatalog();
					ResultSet rs = s.executeQuery(String.format(
							"SELECT EXISTS ( SELECT * FROM information_schema.tables WHERE table_schema = '%s' AND table_name = 'settings')",
							catalog));
					/* If the settings table exists ... */
					if (rs.next() && rs.getInt(1) == 1) {
						rs.close();

						rs = s.executeQuery("SELECT `value` FROM `settings` WHERE `key` = 'version'");
						if (rs.next()) {
							version = rs.getString("value");
						}
					} else {
						rs.close();
					}
				}
			} catch (SQLException | NullPointerException e) {
				logger.error("Error evaluating database version.", e);
			}
		}
		return version;
	}

	public String[] getCommands(String version) {
		logger.info("Running database checks.");
		InputStream versionstream = Migrator.class.getResourceAsStream(String.format("/sql/%s.sql", version));
		if (versionstream != null) {
			String sql = InputOutput.readString(versionstream);
			return sql.split("\\n--\\n|\\n--(.*)--\\n");
		} else {
			logger.warn("Can not find input stream.");
			return new String[] {};
		}
	}

	public void applyCommandsForVersion(String version) {
		String[] commands = getCommands(version);
		logger.info("Migrating to version {}", version);
		try (Connection c = this.ds.getConnection(); Statement s = c.createStatement();) {
			for (String command : commands) {
				s.addBatch(command);
			}
			s.executeBatch();
		} catch (SQLException e) {
			logger.error("Error executing statement.", e);
		}
	}

	public void migrate() {
		String databaseversion = getDatabaseVersion();
		String applicationversion = getApplicationVersion();
		String[] versions = getDefinedDatabaseVersions();
		if (applicationversion.equals(databaseversion)) {
			logger.info("Database is on the current version and no migrations are required.");
		} else {
			boolean applymigration = false;
			for (String version : versions) {
				if (version.equals(databaseversion)) {
					applymigration = true;
				}
				if (applymigration) {
					applyCommandsForVersion(version);
				}
				if (version.equals(applicationversion)) {
					applymigration = false;
				}
			}
		}
	}

	private String getApplicationVersion() {
		return InputOutput.readString(Migrator.class.getResourceAsStream("/static/version"));
	}
}

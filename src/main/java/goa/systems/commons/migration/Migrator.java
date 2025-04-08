package goa.systems.commons.migration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import goa.systems.commons.io.InputOutput;

public class Migrator {

	private static final Logger logger = LoggerFactory.getLogger(Migrator.class);

	/**
	 * Migrates the database to the current application version.
	 * 
	 * @param datasource         DataSource to work with.
	 * @param resbase            Resource base in the class path where to find the
	 *                           SQL scripts.
	 * @param applicationversion Current application version.
	 * @param versions           Defined list of application versions.
	 * @throws MigratorException
	 */
	public void migrate(DataSource datasource, String resbase, String applicationversion, String[] versions)
			throws MigratorException {
		String databaseversion = determineDatabaseVersion(datasource);
		List<String> migrations = generateMigrations(applicationversion, versions, databaseversion);
		for (String m : migrations) {
			String sql = InputOutput
					.readString(Migrator.class.getResourceAsStream(String.format("%s/%s.sql", resbase, m)));
			String[] sqlcmds = sql.split("\\n\\s*--.*\\n");
			try (Connection c = datasource.getConnection(); Statement s = c.createStatement();) {
				for (String sqlcmd : sqlcmds) {
					s.addBatch(sqlcmd);
					logger.debug("Adding {} to batch.", sqlcmd);
				}
				int[] updatecount = s.executeBatch();
				logger.info("SQL batch update count: {}.", updatecount.length);
			} catch (SQLException e) {
				logger.error("Error running migration", e);
			}
		}
	}

	/**
	 * Determine database version based on settings table.
	 * 
	 * @param datasource DataSource to work with.
	 * @return The current database version or null in case of error.
	 */
	public String determineDatabaseVersion(DataSource datasource) {

		String version = null;
		try (Connection c = datasource.getConnection();
				Statement stm = c.createStatement();
				ResultSet rs = stm.executeQuery("SELECT `value` FROM `settings` WHERE key = 'databaseversion'")) {
			if (rs.next()) {
				version = rs.getString(1);
			}
		} catch (SQLException e) {
			logger.error("Settings table most likely does not exist. Assuming empty database.");
		}
		return version;
	}

	/**
	 * Generates a list of versions to be applied during the migration process.
	 * 
	 * @param applicationversion Application version string.
	 * @param versions           List of versions.
	 * @param databaseversion    Database version string.
	 * @throws MigratorException In case of error.
	 */
	public List<String> generateMigrations(String applicationversion, String[] versions, String databaseversion)
			throws MigratorException {

		List<String> steps = new ArrayList<>();

		if (applicationversion == null || applicationversion.isEmpty() || versions == null || versions.length == 0) {
			throw new MigratorException("Provided data is not valid.");
		}

		int applicationindex = getIndex(applicationversion, versions);
		int databaseindex = databaseversion == null ? 0 : getIndex(databaseversion, versions);

		if (applicationindex == -1 || databaseindex == -1) {
			throw new MigratorException(
					"Application version index or database version index not found in list of versions.");
		}

		if (databaseindex > applicationindex) {
			throw new MigratorException("Database is newer than application.");
		}

		int distance = applicationindex - databaseindex;

		if (distance == 0) {
			logger.info("Database is on the same version as application. No migration needed.");
		} else {
			logger.info("Database is {} {} behind application. Starting migration.", distance,
					distance == 1 ? "version" : "versions");
			int startindex = databaseversion == null ? 0 : databaseindex + 1;
			Collections.addAll(steps, Arrays.copyOfRange(versions, startindex, applicationindex + 1));
		}

		return steps;
	}

	/**
	 * Returns the index of the given version in the list of versions or -1 if it
	 * was not found or if an error occured.
	 * 
	 * @param version  Version to look for.
	 * @param versions List of versions to search.
	 * @return Index of version if found or -1 in case of error or if not found.
	 */
	public int getIndex(String version, String[] versions) {
		if (version == null || version.isEmpty() || versions == null || versions.length == 0) {
			return -1;
		}
		int i = 0;
		int j = -1;
		boolean found = false;
		while (!found && i < versions.length) {
			if (version.equals(versions[i])) {
				j = i;
				found = true;
			}
			i++;
		}
		return j;
	}
}

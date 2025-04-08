package goa.systems.commons.migration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.SQLiteDataSource;

import goa.systems.commons.io.InputOutput;

class MigratorTests {

	private static final Logger logger = LoggerFactory.getLogger(Migrator.class);

	@Test
	void testIndex() {

		Migrator m = new Migrator();
		String[] versions = new String[] { "0.0.0", "0.0.1", "0.0.2", "0.1.0", "0.1.1", "0.5.6-test" };
		assertEquals(0, m.getIndex("0.0.0", versions));
		assertEquals(1, m.getIndex("0.0.1", versions));
		assertEquals(-1, m.getIndex("0.0.0", null));
		assertEquals(-1, m.getIndex(null, versions));
		assertEquals(-1, m.getIndex(null, null));
		assertEquals(-1, m.getIndex("0.0.3", versions));
		assertEquals(5, m.getIndex("0.5.6-test", versions));
	}

	@Test
	void testStepGenerator() {

		Migrator m = new Migrator();
		String[] versions = new String[] { "0.0.0", "0.0.1", "0.0.2", "0.1.0", "0.1.1", "0.5.6-test" };

		assertDoesNotThrow(() -> {
			List<String> steps = m.generateMigrations("0.0.2", versions, "0.0.1");
			assertEquals(1, steps.size());
		});

		assertDoesNotThrow(() -> {
			List<String> steps = m.generateMigrations("0.5.6-test", versions, "0.0.0");
			assertEquals(5, steps.size());
		});

		assertThrows(MigratorException.class, () -> {
			m.generateMigrations("0.0.1", versions, "0.0.2");
		});

		assertThrows(MigratorException.class, () -> {
			m.generateMigrations("", versions, "0.0.2");
		});

		assertThrows(MigratorException.class, () -> {
			m.generateMigrations("0.0.1", versions, "");
		});

		assertThrows(MigratorException.class, () -> {
			m.generateMigrations("", versions, "");
		});

		assertThrows(MigratorException.class, () -> {
			m.generateMigrations(null, versions, "0.0.2");
		});

		assertThrows(MigratorException.class, () -> {
			m.generateMigrations(null, versions, null);
		});

		assertThrows(MigratorException.class, () -> {
			m.generateMigrations("0.0.1", versions, null);
		});
	}

	@Test
	void testVersionDetermination() {
		File sqlitedb = new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
		var sqliteurl = String.format("jdbc:sqlite:%s", sqlitedb.getAbsolutePath());
		Migrator m = new Migrator();
		assertDoesNotThrow(() -> {
			logger.info("Creating database in {}.", sqlitedb.getAbsolutePath());
			SQLiteDataSource sqlds = new SQLiteDataSource();
			sqlds.setUrl(sqliteurl);
			assertEquals("0.0.0", m.determineDatabaseVersion(sqlds));
			sqlitedb.delete();
		});
	}

	@Test
	void testMigration() {
		File sqlitedb = new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
		var sqliteurl = String.format("jdbc:sqlite:%s", sqlitedb.getAbsolutePath());
		Migrator m = new Migrator();
		assertDoesNotThrow(() -> {
			logger.info("Creating database in {}.", sqlitedb.getAbsolutePath());
			SQLiteDataSource sqlds = new SQLiteDataSource();
			sqlds.setUrl(sqliteurl);
			String base = "/migratortests";
			String[] versions = InputOutput
					.readString(MigratorTests.class.getResourceAsStream(String.format("%s/versions", base)))
					.split("\\n");
			m.migrate(sqlds, base, "0.0.3", versions);

			Connection con = sqlds.getConnection();
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT \"number\", \"description\" FROM b WHERE \"id\" = 1");
			rs.next();
			assertEquals("12345", rs.getString(1));
			assertEquals("article", rs.getString(2));
			rs.close();
			s.close();
			con.close();

			sqlitedb.delete();
		});
	}
}

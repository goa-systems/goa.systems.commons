package goa.systems.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test class for input output logic.
 * 
 * @author ago
 * @since 10.09.2020
 * @version 0.0.1
 * 
 */
class InputOutputTest {

	private static final Logger logger = LoggerFactory.getLogger(InputOutputTest.class);

	/**
	 * Tests full reading of file content.
	 */
	@Test
	void ioTest1() {
		String line1 = InputOutput.read(InputOutputTest.class.getResourceAsStream("/logback-test.xml"),
				StandardCharsets.UTF_8);
		assertEquals(813, line1.length());
	}

	/**
	 * Tests partial reading of file content. Input stream has to be closed
	 * manually.
	 */
	@Test
	void ioTest2() {
		InputStream is = InputOutputTest.class.getResourceAsStream("/logback-test.xml");
		String line1 = InputOutput.read(is, StandardCharsets.UTF_8, 55);
		logger.debug("Line 1 of logback config: {}", line1);
		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", line1);
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				logger.error("IOException occured: Can not close input stream.", e);
			}
		}
	}

	/**
	 * Tests partial reading of file content. More bytes are requested than there
	 * are available in the file. This should not lead to an exception. Instead the
	 * maximum number of available chars are returned.
	 */
	@Test
	void ioTest3() {
		InputStream is = InputOutputTest.class.getResourceAsStream("/iotest3.txt");
		String line1 = InputOutput.read(is, StandardCharsets.UTF_8, 55);
		logger.debug("Line 1 of logback config: {}", line1);
		assertEquals(14, line1.length());
		assertEquals("iotest3content", line1);
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				logger.error("IOException occured: Can not close input stream.", e);
			}
		}
	}

	/**
	 * Tests loading of different encoded files. Content is always the same. It
	 * contains German mutated vowels.
	 */
	@Test
	void ioTest4() {

		/* Files to be tested. */
		String[] files = { "utf8.txt", "iso88591.txt", "utf16.txt" };

		/* Charsets to be tested. */
		Charset[] chss = { StandardCharsets.UTF_8, StandardCharsets.ISO_8859_1, StandardCharsets.UTF_16 };

		/* Test logic. */
		for (int i = 0; i < chss.length; i++) {
			InputStream is = InputOutputTest.class.getResourceAsStream("/iotest4/" + files[i]);
			String line = InputOutput.read(is, chss[i], 55);
			logger.debug("Line 1 of logback config with encoding {}: {}", chss[i], line);
			assertEquals(19, line.length());
			assertEquals("iotest4content:öäüß", line);
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("IOException occured: Can not close input stream.", e);
				}
			}
		}
	}
}

package goa.systems.commons.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides basic input/output capabilities and reduces duplicate
 * implementations of reading and writing operations.
 * 
 * @author Andreas Gottardi
 * @version 0.0.1
 * @since 09.10.2020
 *
 */
public class InputOutput {

	private static final Logger logger = LoggerFactory.getLogger(InputOutput.class);

	private static final String IS_IS_NULL = "Inputstream is null, returning empty string.";

	private InputOutput() {
	}

	/**
	 * Reads the whole content from a input stream to a string. The input stream is
	 * closed after reading. Character set is UTF-8. If another character set is
	 * required, use the method
	 * 
	 * <pre>
	 * public static String read(InputStream inputstream, Charset cs);
	 * </pre>
	 * 
	 * @param inputstream The input stream to read from.
	 * @return A string containing the data.
	 */
	public static String readString(InputStream inputstream) {
		return readString(inputstream, StandardCharsets.UTF_8);
	}

	/**
	 * Reads the whole content from a input stream to a string. The input stream is
	 * closed after reading.
	 * 
	 * @param inputstream The input stream to read from.
	 * @param cs          The character set to read.
	 * @return A string containing the data.
	 */
	public static String readString(InputStream inputstream, Charset cs) {
		if (inputstream != null) {
			StringBuilder sb = new StringBuilder();
			int read;
			char[] buffer = new char[1024];
			try (InputStreamReader isr = new InputStreamReader(inputstream, cs);) {
				read = isr.read(buffer);
				while (read != -1) {
					sb.append(buffer, 0, read);
					read = isr.read(buffer);
				}
			} catch (IOException e) {
				logger.error("IOException occured while reading the whole stream.", e);
			}
			return sb.toString();
		} else {
			logger.error(IS_IS_NULL);
			return "";
		}
	}

	/**
	 * Reads the given length of characters from a stream. Does not close the stream
	 * after reading and returns the content as string. Character set is UTF-8. If
	 * another character set is required, use the method
	 * 
	 * <pre>
	 * public static String read(InputStream inputstream, Charset cs, int length);
	 * </pre>
	 * 
	 * @param inputstream The input stream to read from.
	 * @param length      The maximum number of characters to read. If there are
	 *                    less chars in the stream, the available number of
	 *                    characters will be returned.
	 * @return The string containing maximum the number of characters specified.
	 */
	public static String readString(InputStream inputstream, int length) {
		return readString(inputstream, length);
	}

	/**
	 * Reads the given length of characters from a stream. Does not close the stream
	 * after reading and returns the content as string.
	 * 
	 * @param inputstream The input stream to read from.
	 * @param cs          The character set to read.
	 * @param length      The maximum number of characters to read. If there are
	 *                    less chars in the stream, the available number of
	 *                    characters will be returned.
	 * @return The string containing maximum the number of characters specified.
	 */
	public static String readString(InputStream inputstream, Charset cs, int length) {
		if (inputstream != null) {
			StringBuilder sb = new StringBuilder();
			int read;
			char[] buffer = new char[1024];
			int charsread = 0;
			try {
				InputStreamReader isr = new InputStreamReader(inputstream, cs);
				read = isr.read(buffer, 0, length);
				while (charsread < length && read != -1) {
					charsread += read;
					sb.append(buffer, 0, read);
					read = isr.read(buffer);
				}
			} catch (IOException e) {
				logger.error("IOException occured while reading a certain amount of bytes.", e);
			}
			return sb.toString();
		} else {
			logger.error(IS_IS_NULL);
			return "";
		}
	}

	/**
	 * Reads the given input stream into a ByteArrayOutputStream for further
	 * handling.
	 * 
	 * @param inputstream
	 * @return new ByteArrayOutputStream or null.
	 */
	public static ByteArrayOutputStream readByteArrayOutputStream(InputStream inputstream) {

		if (inputstream != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int read;
			byte[] buffer = new byte[1024];
			try {
				read = inputstream.read(buffer);
				while (read != -1) {
					baos.write(buffer, 0, read);
					read = inputstream.read(buffer);
				}
			} catch (IOException e) {
				logger.error("IOException occured while reading the whole stream.", e);
			}
			return baos;
		} else {
			logger.error(IS_IS_NULL);
			return null;
		}
	}
}

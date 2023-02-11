package goa.systems.commons.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
	 * @param inputstream Inputstream to read from
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

	/**
	 * Writes a string to a file with default encoding of UTF-8.
	 * 
	 * @param str String to write.
	 * @param f   File to write to.
	 */
	public static void writeString(String str, File f) {
		try (FileOutputStream fos = new FileOutputStream(f)) {
			writeString(str, fos);
		} catch (IOException e) {
			logger.error("Error writing to file", e);
		}
	}

	/**
	 * Writes a string to a file with a given encoding.
	 * 
	 * @param str String to write.
	 * @param f   File to write to.
	 * @param cs  Encoding to use.
	 */
	public static void writeString(String str, File f, Charset cs) {
		try (FileOutputStream fos = new FileOutputStream(f)) {
			writeString(str, fos, cs);
		} catch (IOException e) {
			logger.error("Error writing to file", e);
		}
	}

	/**
	 * Writes a string to a specified output stream with a default encoding of
	 * UTF-8.
	 * 
	 * @param str String to write.
	 * @param os  Output stream to write to.
	 */
	public static void writeString(String str, OutputStream os) {
		writeString(str, os, StandardCharsets.UTF_8);
	}

	/**
	 * Writes a string to a specified output stream.
	 * 
	 * @param str String to write
	 * @param os  Stream to write to
	 * @param cs  Desired encoding
	 */
	public static void writeString(String str, OutputStream os, Charset cs) {
		try {
			os.write(str.getBytes(cs));
		} catch (IOException e) {
			logger.error("Error writing String to outputstream.", e);
		}
	}

	/**
	 * Copies from the given input stream to the given output stream.
	 * 
	 * This method does not close the streams. They have to be closed by caller if
	 * necessary.
	 * 
	 * @param is Stream to read from
	 * @param os Stream to write to
	 */
	public static void copy(InputStream is, OutputStream os) {

		if (is == null || os == null) {
			return;
		}
		try {
			byte[] buffer = new byte[1024];
			int read = is.read(buffer);
			while (read != -1) {
				os.write(buffer, 0, read);
				read = is.read(buffer);
			}
		} catch (IOException e) {
			logger.error("Error copying streams.", e);
		}

	}
}

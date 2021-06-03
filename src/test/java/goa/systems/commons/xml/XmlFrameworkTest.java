package goa.systems.commons.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import goa.systems.commons.io.InputOutput;

class XmlFrameworkTest {

	private static final Logger logger = LoggerFactory.getLogger(XmlFrameworkTest.class);

	@Test
	void xmlfwTest1() {
		try {
			Document d = XmlFramework.getDocumentBuilder()
					.parse(XmlFrameworkTest.class.getResourceAsStream("/xmltest/test1.xml"));
			NodeList nl = d.getElementsByTagName("root");

			DocumentType dt = d.getDoctype();

			assertNotNull(dt);

			assertEquals(1, nl.getLength());
			assertEquals("root", nl.item(0).getNodeName());

		} catch (SAXException | IOException | ParserConfigurationException e) {
			logger.error("Exception occured.", e);
		}
	}

	@Test
	void xmlfwTest2() {
		try {
			Document d = XmlFramework.getDocumentBuilder()
					.parse(XmlFrameworkTest.class.getResourceAsStream("/xmltest/test1.xml"));
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			Transformer tf = XmlFramework.getTransformer();
			tf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			tf.transform(new DOMSource(d), result);
			String xml = sw.toString().replace("\r", "");
			assertTrue(xml.contains("\n"));
			String[] lines = xml.split("\\n");
			assertEquals(3, lines.length);
			assertTrue(lines[1].startsWith("    "));
		} catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
			logger.error("Exception occured.", e);
		}
	}

	@Test
	void xmlfwTest3() {
		try {
			InputStream is = XmlFrameworkTest.class.getResourceAsStream("/xmltest/test1.xml");
			String content = InputOutput.read(is);
			Document document = XmlFramework.getDocumentFromString(content);
			NodeList nl = document.getChildNodes();
			assertEquals("root", nl.item(1).getNodeName());
			assertEquals("child", nl.item(1).getFirstChild().getNodeName());
		} catch (SAXException | IOException | ParserConfigurationException e) {
			logger.error("Exception occured.", e);
		}
	}
}

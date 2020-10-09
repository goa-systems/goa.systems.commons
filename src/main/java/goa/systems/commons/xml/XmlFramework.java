package goa.systems.commons.xml;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

/**
 * This class provides generators for DocumentBuilder(Factory)s and
 * Transformer(Factory)s. Security features are applied automatically and tools
 * like <a href="https://www.sonarlint.org">SonarLint</a> do not throw warnings.
 * 
 * @author Andreas Gottardi
 * @version 0.0.1
 * @since 09.10.2020
 *
 */
public class XmlFramework {

	private XmlFramework() {
	}

	/**
	 * Creates a TransformerFactory with security features implemented.
	 * 
	 * @return TransformerFactory
	 * @throws TransformerConfigurationException Thrown if FEATURE_SECURE_PROCESSING
	 *                                           can't be set.
	 */
	public static synchronized TransformerFactory getTransformerFactory() throws TransformerConfigurationException {
		TransformerFactory factory = TransformerFactory
				.newInstance("com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl", null);
		factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
		factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		return factory;
	}

	/**
	 * Creates a Transformer with security features implemented.
	 * 
	 * @return Transformer
	 * @throws TransformerConfigurationException Thrown if FEATURE_SECURE_PROCESSING
	 *                                           can't be set.
	 */
	public static synchronized Transformer getTransformer() throws TransformerConfigurationException {
		return getTransformerFactory().newTransformer();
	}

	/**
	 * Creates a DocumentBuilderFactory with security features implemented.
	 * 
	 * @return DocumentBuilderFactory
	 * @throws ParserConfigurationException Thrown if FEATURE_SECURE_PROCESSING
	 *                                      can't be set.
	 */
	public static synchronized DocumentBuilderFactory getDocumentBuilderFactory() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory
				.newInstance("com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl", null);
		factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		return factory;
	}

	/**
	 * Creates a DocumentBuilder with security features implemented.
	 * 
	 * @return DocumentBuilder
	 * @throws ParserConfigurationException Thrown if FEATURE_SECURE_PROCESSING
	 *                                      can't be set.
	 */
	public static synchronized DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
		return getDocumentBuilderFactory().newDocumentBuilder();
	}
}

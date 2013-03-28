package ru.spbstu.telematics.web;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.eclipse.jetty.http.HttpStatus;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MessageServlet extends HttpServlet {
	private static final String ROOT = "/home/lukash/dev/2013-spring/foowebapp/src/main/java";

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.addHeader("Content-Type", "text/html");
		String name = req.getParameter("name");
		if (name == null) {
			resp.setStatus(HttpStatus.NOT_FOUND_404);
			return;
		}

		InputStream is = new FileInputStream(ROOT + "/" + name);
		ServletOutputStream os = resp.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);
		domParser(is, dos);
		is.close();
		
		is = new FileInputStream(ROOT + "/" + name);
		os = resp.getOutputStream();
		dos = new DataOutputStream(os);
		saxParser(is, dos);
		is.close();
		
		is = new FileInputStream(ROOT + "/" + name);
		os = resp.getOutputStream();
		dos = new DataOutputStream(os);
		xpathParser(is, dos);
		is.close();
	}

	private void xpathParser(InputStream is, DataOutputStream dos) {
		try {
			XPath xp = XPathFactory.newInstance().newXPath();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			db = factory.newDocumentBuilder();
			
			Document doc = db.parse(is);
			XPathExpression v = xp.compile("/messages/message/user");
			NodeList result = (NodeList) v.evaluate(doc, XPathConstants.NODESET);
			
			for (int i = 0; i < result.getLength(); i++) {
				writeTag(dos, result.item(i).getParentNode());				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saxParser(InputStream is, final DataOutputStream dos) {
		try {
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			parser.parse(is, new DefaultHandler() {

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					try {
						dos.writeUTF("<br>sax start:" + uri + ":" + localName + ":"  + qName);
						for (int i = 0; i< attributes.getLength(); i ++) {
							dos.writeUTF("<br><i>\t" + attributes.getLocalName(i) + "=" + attributes.getValue(i) + "</i>");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					try {
						dos.writeUTF("<br>sax end:" + uri + ":" + localName + ":"  + qName + "\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void domParser(InputStream is, DataOutputStream dos) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = factory.newDocumentBuilder();
			Document doc = db.parse(is);
			
			writeTag(dos, doc);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeTag(DataOutputStream dos, Node doc)
			throws IOException {
		NodeList children = doc.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i) instanceof Element) {
				Element el = (Element) children.item(i);
				String tagName = el.getTagName();
				dos.writeUTF(tagName + " ");
				NamedNodeMap attributes = el.getAttributes();
				for (int j = 0; j < attributes.getLength(); j++) {
					Node attr = attributes.item(j);
					dos.writeUTF(attr.getNodeName() + "=" + attr.getNodeValue() + " ");
				}
				dos.writeUTF("<br>");
				
				if (el.getChildNodes().getLength() > 0) {
					writeTag(dos, el);
				}
			}
		}
	}

}

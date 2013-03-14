package ru.spbstu.telematics.web;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jetty.http.HttpStatus;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

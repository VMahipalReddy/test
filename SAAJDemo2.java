package com.triniti.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.soap.SOAPBinding;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Document;

import com.sun.xml.messaging.saaj.util.Base64;

public class SAAJDemo2 {

	public static void main(String[] args) throws Exception {
		
		String inputString = "<soapenv:Envelope xmlns:cat=\"http://xmlns.oracle.com/apps/scm/productCatalogManagement/advancedItems/flex/egoItemEff/itemSupplier/categories/\" xmlns:cat1=\"http://xmlns.oracle.com/apps/scm/productCatalogManagement/advancedItems/flex/egoItemEff/itemRevision/categories/\" xmlns:cat2=\"http://xmlns.oracle.com/apps/scm/productCatalogManagement/advancedItems/flex/egoItemEff/item/categories/\" xmlns:item=\"http://xmlns.oracle.com/apps/scm/productModel/items/itemServiceV2/\" xmlns:item1=\"http://xmlns.oracle.com/apps/scm/productModel/items/flex/itemRevision/\" xmlns:item2=\"http://xmlns.oracle.com/apps/scm/productModel/items/flex/item/\" xmlns:item3=\"http://xmlns.oracle.com/apps/scm/productModel/items/flex/itemGdf/\" xmlns:mod=\"http://xmlns.oracle.com/apps/flex/fnd/applcore/attachments/model/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:typ=\"http://xmlns.oracle.com/apps/scm/productModel/items/itemServiceV2/types/\">\r\n" + 
				"  <soapenv:Body>\r\n" + 
				"     <typ:createItem>\r\n" + 
				"        <typ:item>\r\n" + 
				"           <item:OrganizationCode>000</item:OrganizationCode>\r\n" + 
				"           <item:ItemClass>Root Item Class</item:ItemClass>\r\n" + 
				"           <item:Template>Finished Goods</item:Template>\r\n" + 
				"           <item:ItemNumber>Test14012019_05</item:ItemNumber>\r\n" + 
				"           <item:ItemDescription>Test14012019_05</item:ItemDescription>\r\n" + 
				"        </typ:item>\r\n" + 
				"     </typ:createItem>\r\n" + 
				"  </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>";
		
		SOAPConnectionFactory connectionfactory = SOAPConnectionFactory.newInstance();
		SOAPConnection connection = connectionfactory.createConnection();
		java.net.URL endpoint = new URL("https://ucf5-zeop-fa-ext.oracledemos.com/fscmService/ItemServiceV2?WSDL");
		/*
		 * URLConnection url = new
		 * URL("https://ucf5-zeop-fa-ext.oracledemos.com/fscmService/ItemServiceV2?WSDL"
		 * ).openConnection(); url.addRequestProperty("Username", "scm_impl");
		 * url.addRequestProperty("Password", "DayH33Ya7");
		 */
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage message = messageFactory.createMessage();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
	    Document document = builder.parse(new ByteArrayInputStream(inputString.getBytes()));
	    message.getSOAPPart().setContent(new DOMSource(document));
	    
	    String loginPassword = "scm_impl:DayH33Ya7";
	    message.getMimeHeaders().addHeader("Authorization", "Basic " + new  String(Base64.encode(loginPassword.getBytes())));
		
		SOAPMessage soapResponse = connection.call(message, endpoint);
		MimeHeaders SH = soapResponse.getMimeHeaders();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapResponse.writeTo(out);
        String strMsg = new String(out.toByteArray(),Charset.forName("utf-8"));
        System.out.println(strMsg);
        connection.close();
	}

	private static void printDoc(Document document) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("ISO-8859-1");
		StringWriter writer = new StringWriter();
		XMLWriter xmlwriter = new XMLWriter(writer, format);
		try {
			xmlwriter.write(document);
			System.out.println(writer.getBuffer().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

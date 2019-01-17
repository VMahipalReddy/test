package com.triniti.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.w3c.dom.Document;

public class SAAJDemo {

	public static void main(String[] args) throws Exception {
		String inputString = "<soapenv:Envelope xmlns:cat=\"http://xmlns.oracle.com/apps/scm/productCatalogManagement/advancedItems/flex/egoItemEff/itemSupplier/categories/\" xmlns:cat1=\"http://xmlns.oracle.com/apps/scm/productCatalogManagement/advancedItems/flex/egoItemEff/itemRevision/categories/\" xmlns:cat2=\"http://xmlns.oracle.com/apps/scm/productCatalogManagement/advancedItems/flex/egoItemEff/item/categories/\" xmlns:item=\"http://xmlns.oracle.com/apps/scm/productModel/items/itemServiceV2/\" xmlns:item1=\"http://xmlns.oracle.com/apps/scm/productModel/items/flex/itemRevision/\" xmlns:item2=\"http://xmlns.oracle.com/apps/scm/productModel/items/flex/item/\" xmlns:item3=\"http://xmlns.oracle.com/apps/scm/productModel/items/flex/itemGdf/\" xmlns:mod=\"http://xmlns.oracle.com/apps/flex/fnd/applcore/attachments/model/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:typ=\"http://xmlns.oracle.com/apps/scm/productModel/items/itemServiceV2/types/\">\r\n" + 
				"  <soapenv:Body>\r\n" + 
				"     <typ:createItem>\r\n" + 
				"        <typ:item>\r\n" + 
				"           <item:OrganizationCode>000</item:OrganizationCode>\r\n" + 
				"           <item:ItemClass>Root Item Class</item:ItemClass>\r\n" + 
				"           <item:Template>Finished Goods</item:Template>\r\n" + 
				"           <item:ItemNumber>Test16012019_04</item:ItemNumber>\r\n" + 
				"           <item:ItemDescription>Test16012019_04</item:ItemDescription>\r\n" + 
				"        </typ:item>\r\n" + 
				"     </typ:createItem>\r\n" + 
				"  </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>";
		
		QName serviceQName = new QName("djlksdlkjk");
	    QName portQName = new QName("dkld");
	    Service service = Service.create(serviceQName);
	    service.addPort(portQName, SOAPBinding.SOAP11HTTP_BINDING, "https://ucf3-zeuo-fa-ext.oracledemos.com/fscmService/ItemServiceV2?WSDL");
	    Dispatch<SOAPMessage> dispatcher = service.createDispatch(portQName, SOAPMessage.class, Service.Mode.MESSAGE);
	    Map<String, Object> rc = dispatcher.getRequestContext();
	    rc.put(BindingProvider.USERNAME_PROPERTY, "scm_impl");
	    rc.put(BindingProvider.PASSWORD_PROPERTY, "E44hC142q");
	    rc.put(BindingProvider.SOAPACTION_USE_PROPERTY, Boolean.TRUE);
	    
	    MessageFactory messageFactory = ((SOAPBinding)dispatcher.getBinding()).getMessageFactory();
	    SOAPMessage message = messageFactory.createMessage();
	    //SOAPBody body = message.getSOAPBody();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
	    Document document = builder.parse(new ByteArrayInputStream(inputString.getBytes()));
	    message.getSOAPPart().setContent(new DOMSource(document));
	    
		SOAPMessage response = (SOAPMessage) dispatcher.invoke(message);
		System.out.println("response: " + response);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		response.writeTo(out);
		String responseText = new String(out.toByteArray());
		System.out.println("Message: " + responseText);
	}

}

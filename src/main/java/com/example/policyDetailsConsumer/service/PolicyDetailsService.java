package com.example.policyDetailsConsumer.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

@Component
public class PolicyDetailsService {
    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${springjms.anotherQueue}")
    private String queue;

    public void sendMessage(String message) throws ParserConfigurationException, IOException, SAXException {
        String policyNumber = getValueFromXml(message, "policyNumber");
        if (!isPolicyNumberValid(policyNumber)) {
            message = "Policy number should have at least 8 digits";
        }
        jmsTemplate.convertAndSend(queue, message);
        System.out.println("Message sent-->" + message);
    }

    private String getValueFromXml(String message, String tag) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource src = new InputSource();
        src.setCharacterStream(new StringReader(message));
        Document doc = builder.parse(src);
        String value = doc.getElementsByTagName(tag).item(0).getTextContent();
        return value;
    }

    private boolean isPolicyNumberValid(String policyNumber) {
        return policyNumber.length() > 8;
    }
}

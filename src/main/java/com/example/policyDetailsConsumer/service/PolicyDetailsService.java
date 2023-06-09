package com.example.policyDetailsConsumer.service;

import com.example.policyDetailsConsumer.utility.Constants;
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

    @Value("${springjms.consumerQueue}")
    private String queue;

    public void sendMessage(String policyDetailsXml) throws ParserConfigurationException, IOException, SAXException {

        String policyNumber = getValueFromXml(policyDetailsXml, "policyNumber");
        if (!isPolicyNumberValid(policyNumber)) {
            jmsTemplate.convertAndSend(queue, Constants.INVALID_POLICY_NUMBER_MESSAGE);
            System.out.println("Message sent-->" + Constants.INVALID_POLICY_NUMBER_MESSAGE);
        } else {
            jmsTemplate.convertAndSend(queue, policyDetailsXml);
            System.out.println("Message sent-->" + policyDetailsXml);
        }
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
        return policyNumber.length() >= 8;
    }
}

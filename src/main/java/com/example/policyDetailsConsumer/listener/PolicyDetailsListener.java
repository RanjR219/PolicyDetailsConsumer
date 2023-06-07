package com.example.policyDetailsConsumer.listener;
import com.example.policyDetailsConsumer.service.PolicyDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

@Component
public class PolicyDetailsListener {
    @Autowired
    private PolicyDetailsService policyDetailsService;

    public PolicyDetailsListener(PolicyDetailsService policyDetailsService) {
        this.policyDetailsService = policyDetailsService;
    }

    @JmsListener(destination = "${springjms.myQueue}")
    public void receive(String message) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        System.out.println("Message Received---->" + message);
        policyDetailsService.sendMessage(message);
    }
}

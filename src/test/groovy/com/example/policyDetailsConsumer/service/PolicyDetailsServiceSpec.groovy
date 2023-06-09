package com.example.policyDetailsConsumer.service

import org.springframework.jms.core.JmsTemplate
import spock.lang.Specification

class PolicyDetailsServiceSpec extends Specification {

    private PolicyDetailsService policyDetailsService
    private JmsTemplate jmsTemplate

    def setup() {
        jmsTemplate = Mock(JmsTemplate)
        policyDetailsService = new PolicyDetailsService()
        policyDetailsService.jmsTemplate = jmsTemplate
    }

    def "Test sendMessage method"() {
        given:
        def validMessage = "<policyNumber>123456789</policyNumber>"
        def invalidMessage = "<policyNumber>1234</policyNumber>"

        when:
        policyDetailsService.sendMessage(validMessage)

        then:
        1 * jmsTemplate.convertAndSend(_, validMessage)

        when:
        policyDetailsService.sendMessage(invalidMessage)

        then:
        1 * jmsTemplate.convertAndSend(_, "Policy number should have at least 8 digits")
    }
}

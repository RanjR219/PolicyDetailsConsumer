package com.example.policyDetailsConsumer.listener

import com.example.policyDetailsConsumer.service.PolicyDetailsService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.jms.core.JmsTemplate
import spock.lang.Specification

@SpringBootTest
class PolicyDetailsListenerSpec extends Specification {
    @Autowired
    private JmsTemplate jmsTemplate

    @SpringBean
    PolicyDetailsService policyDetailsService = Mock()


    def "Test JMS Listener"() {
        when:
        jmsTemplate.convertAndSend("policyDetailsTestQueue","Helooooooo")
        Thread.sleep(0)

        then:
        1 * policyDetailsService.sendMessage(_)
    }

}

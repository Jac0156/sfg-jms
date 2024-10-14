package guru.springframework.sfgjms.listener;

import java.util.UUID;

import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(
        @Payload HelloWorldMessage helloWorldMessage,
        @Headers MessageHeaders headers,
        Message message ) {

        //System.out.println("I've got a message");
        //System.out.println(helloWorldMessage);
    }

    @JmsListener(destination = JmsConfig.MY_SEND_RCV_QUEUE)
    public void listenForHello(
        @Payload HelloWorldMessage helloWorldMessage,
        @Headers MessageHeaders headers,
        Message message ) throws JmsException, JMSException {

        HelloWorldMessage payloadMsg = HelloWorldMessage
            .builder()
            .id(UUID.randomUUID())
            .message("Wold!!")
            .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMsg);

    }

}

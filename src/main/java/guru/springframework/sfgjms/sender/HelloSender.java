package guru.springframework.sfgjms.sender;

import java.util.UUID;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {

        // System.out.println("I'm Sending a message");

        HelloWorldMessage message = HelloWorldMessage
            .builder()
            .id(UUID.randomUUID())
            .message("Hello World!")
            .build();
        
        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);

        // System.out.println("Message sent!");
    }

    @SuppressWarnings("null")
    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() {

        HelloWorldMessage message = HelloWorldMessage
            .builder()
            .id(UUID.randomUUID())
            .message("Hello")
            .build();
        
        Message receiveMsg = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, new MessageCreator() {
            @SuppressWarnings("null")
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMessage = null;
                try {
                    helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                    helloMessage.setStringProperty("_type", "guru.springframework.sfgjms.model.HelloWorldMessage");
                    
                    System.out.println("###### sending hello #####");
                    
                    return helloMessage;
                } catch (JsonProcessingException e) {
                    throw new JMSException("Boom");
                }
            }
            
        } );

        try {
            System.out.println("#######" + receiveMsg.getBody(String.class));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}

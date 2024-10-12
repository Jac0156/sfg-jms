package guru.springframework.sfgjms.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import jakarta.jms.Message;

@Component
public class HelloMessaheListener {

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(
        @Payload HelloWorldMessage helloWorldMessage,
        @Headers MessageHeaders headers,
        Message message ) {

        System.out.println("I've got a message");
        System.out.println(helloWorldMessage);
    }

}

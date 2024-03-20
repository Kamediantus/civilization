package ge.rodichev.civilization.kafka;

import org.springframework.kafka.annotation.*;
import org.springframework.stereotype.*;

@Component
public class KafkaConsumerService {
    @KafkaListener(topics = "crushedFactories", groupId = "my-group")
    public void receiveMessage(String message) {
        // Process the received message
        System.out.println("Received message: " + message);
    }
}

package ge.rodichev.eventgenerator.kafka;

import java.util.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import ge.rodichev.eventgenerator.dto.*;
import ge.rodichev.eventgenerator.generators.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.kafka.annotation.*;
import org.springframework.stereotype.*;

@Service
public class KafkaConsumerService {

    @Autowired
    Crusher crusher;

    @KafkaListener(topics = "stats", groupId = "my-group")
    public void receiveMessage(String message) {
        crusher.processState(message);
    }
}

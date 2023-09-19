package ge.rodichev.eventgenerator.kafka;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import ge.rodichev.eventgenerator.dto.*;
import org.springframework.kafka.annotation.*;
import org.springframework.stereotype.*;

@Service
public class KafkaConsumerService {
    @KafkaListener(topics = "stats", groupId = "my-group")
    public void receiveMessage(String message) {
        CivilizationStats stats = parseCivilizationStats(message);
    }


    public CivilizationStats parseCivilizationStats(String rawJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(rawJson, CivilizationStats.class);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

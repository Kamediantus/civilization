package ge.rodichev.eventgenerator.generators;

import java.util.*;

import com.fasterxml.jackson.databind.*;
import ge.rodichev.eventgenerator.dto.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.kafka.core.*;
import org.springframework.stereotype.*;

@Service
public class Crusher {
    private static final String TOPIC_NAME = "crushedFactories";
    private static final int MIN_FACTORIES_TO_SAVE = 3;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void processState(String rawStats) {
        pushCrushedFactories(getFactoriesToCrash(parseCivilizationStats(rawStats), 10));
    }

    private List<Long> getFactoriesToCrash(CivilizationStats stats, int countToCrash) {
        Resource resourceToCrash = Resource.WOOD;
        double maxResourceValue = 0d;
        for (Map.Entry<Resource, Double> entry : stats.getResourcePack().entrySet()) {
            if (!entry.getKey().isBurnOnEndOfTick() && maxResourceValue < entry.getValue()) {
                resourceToCrash = entry.getKey();
                maxResourceValue = entry.getValue();
            }
        }
        Resource finalResourceToCrash = resourceToCrash;
        List<Factory> factoriesWithCrushedResource = stats.getFactories().stream().filter(factory -> factory.getProducedResourceType().equals(finalResourceToCrash)).toList();
        if (factoriesWithCrushedResource.size() < MIN_FACTORIES_TO_SAVE) {
            return Collections.emptyList();
        }
        countToCrash = countToCrash >= factoriesWithCrushedResource.size() - MIN_FACTORIES_TO_SAVE
                ? countToCrash
                : MIN_FACTORIES_TO_SAVE - factoriesWithCrushedResource.size();
        return factoriesWithCrushedResource.subList(0, countToCrash)
                .stream().map(Factory::getId).toList();
    }

    public void pushCrushedFactories(List<Long> ids) {
        kafkaTemplate.send(TOPIC_NAME, getJsonFactoriesIds(ids));
    }

    @SneakyThrows
    private String getJsonFactoriesIds(List<Long> ids) {
        return new ObjectMapper().writeValueAsString(ids);
    }

    @SneakyThrows
    public CivilizationStats parseCivilizationStats(String rawJson) {
        return new ObjectMapper().readValue(rawJson, CivilizationStats.class);
    }

}

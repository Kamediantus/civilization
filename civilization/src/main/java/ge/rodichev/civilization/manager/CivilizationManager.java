package ge.rodichev.civilization.manager;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import ge.rodichev.civilization.dto.*;
import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.building.factory.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.kafka.core.*;

@Getter
public class CivilizationManager extends Manager {
    private static final String TOPIC_NAME = "stats";
    private long tickCounter;
    @Autowired
    MultiplyManager multiplyManager;
    @Autowired
    HousingManager housingManager;
    @Autowired
    FactoryManager factoryManager;
    @Autowired
    CitizensManager citizensManager;
    @Autowired
    ResourceManager resourceManager;
    @Autowired
    Factories factories;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg) {
        kafkaTemplate.send(TOPIC_NAME, msg);
    }

    @Autowired
    public CivilizationManager() {
        this.tickCounter = 0;
    }

    @Override
    public void tick() {
        citizensManager.tick();
        multiplyManager.tick();
        resourceManager.tick();
        housingManager.tick();
        factoryManager.tick();
        this.tickCounter++;
        if (this.tickCounter % 100 == 0) {
            pushStats();
        }
    }

    public void pushStats() {
        kafkaTemplate.send(TOPIC_NAME, getJsonFactories());
    }

    public String getJsonFactories() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(new CivilizationStats(this.getFactories(), housingManager.getHousings().size(), resourceManager.getResourcePack(), citizens.size(), this.tickCounter));
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

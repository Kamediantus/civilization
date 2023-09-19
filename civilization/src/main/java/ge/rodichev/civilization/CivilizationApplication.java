package ge.rodichev.civilization;


import java.util.logging.*;
import java.util.stream.*;

import ge.rodichev.civilization.config.*;
import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.building.factory.*;
import ge.rodichev.civilization.entity.consts.*;
import ge.rodichev.civilization.kafka.*;
import ge.rodichev.civilization.manager.*;
import ge.rodichev.civilization.resource.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;

@SpringBootApplication
public class CivilizationApplication {
    private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ManagerConfig.class, KafkaProducerConfig.class);

    private static CivilizationManager civilizationManager;
    private static Factories factories;
    private static Citizens citizens;

	public static void main(String[] args) {
        SpringApplication.run(CivilizationApplication.class, args);
        runCivilization();
	}

    public static void runCivilization() {
        civilizationManager = context.getBean(CivilizationManager.class);
        factories = context.getBean(Factories.class);
        citizens = context.getBean(Citizens.class);

        init();

        IntStream.range(0, 1000).forEach(i -> civilizationManager.tick());
        IntStream.range(1000, 2000).forEach(i -> civilizationManager.tick());
        IntStream.range(2000, 3000).forEach(i -> civilizationManager.tick());
        IntStream.range(3000, 10000).forEach(i -> civilizationManager.tick());
        IntStream.range(10000, 100000).forEach(i -> civilizationManager.tick());

        Logger.getLogger("test").log(Level.ALL, "end/`");
    }

    public static void initResourceManager(ResourcePack startResources) {
        ResourceManager resourceManager = context.getBean(ResourceManager.class);
        startResources.forEach((k,v) -> resourceManager.getResourcePack().replace(k, v));
    }

    public static Citizens createNormCitizens(int count) {
        Citizens citizens = new Citizens();
        IntStream.range(0, count).forEach(c -> citizens.add(new Citizen(Age.MATURE, Health.NORM)));
        return citizens;
    }

    public static void init() {
        ConstructionWorkshop workshop = new ConstructionWorkshop();
        Citizens citizensToAdd = createNormCitizens(10);
        IntStream.range(0, 5).forEach(i -> {
            Citizen citizen = citizensToAdd.get(i);
            citizen.setFactory(workshop);
            workshop.getEmployee().add(citizen);
        });
        citizens.addAll(citizensToAdd);
        factories.add(workshop);
        initResourceManager(ResourcePack.createFilledCommonResourcePack(500));
        civilizationManager.getHousingManager().buildSimpleHut();
    }
}

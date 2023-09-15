package ge.rodichev.civilization;


import java.util.logging.*;
import java.util.stream.*;

import ge.rodichev.civilization.config.*;
import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.building.factory.*;
import ge.rodichev.civilization.entity.consts.*;
import ge.rodichev.civilization.manager.*;
import ge.rodichev.civilization.resource.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;

@SpringBootApplication
public class CivilizationApplication {
    private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ManagerConfig.class);
    private static MultiplyManager multiplyManager;
    private static HousingManager housingManager;
    private static FactoryManager factoryManager;
    private static CitizensManager citizensManager;
    private static ResourceManager resourceManager;
    private static Factories factories;
    private static Citizens citizens;

	public static void main(String[] args) {
        SpringApplication.run(CivilizationApplication.class, args);
        runCivilization();
	}

    public static void runCivilization() {
        multiplyManager = context.getBean(MultiplyManager.class);
        housingManager = context.getBean(HousingManager.class);
        factoryManager = context.getBean(FactoryManager.class);
        citizensManager = context.getBean(CitizensManager.class);
        resourceManager = context.getBean(ResourceManager.class);
        factories = context.getBean(Factories.class);
        citizens = context.getBean(Citizens.class);

        init();

        IntStream.range(0, 1000).forEach(i -> generalTick());
        IntStream.range(1000, 2000).forEach(i -> generalTick());
        IntStream.range(2000, 3000).forEach(i -> generalTick());
        IntStream.range(3000, 10000).forEach(i -> generalTick());
        IntStream.range(10000, 100000).forEach(i -> generalTick());

        Logger.getLogger("test").log(Level.ALL, "end/`");
    }

    private static void generalTick() {
        citizensManager.tick();
        multiplyManager.tick();
        resourceManager.tick();
        housingManager.tick();
        factoryManager.tick();
    }

    public static void initCitizens(Citizens citizensToAdd) {
        Citizens citizens = context.getBean(Citizens.class);
        citizens.addAll(citizensToAdd);
    }

    public static void initResourceManager(ResourcePack startResources) {
        ResourceManager resourceManager = context.getBean(ResourceManager.class);
        startResources.forEach((k,v) -> resourceManager.getResourcePack().replace(k, v));
    }

    public static Citizens createNormCitizens(int count) {
        Citizens citizens = new Citizens();
        IntStream.range(0, count).forEach(c -> citizens.add(new Citizen(c, Age.MATURE, Health.NORM)));
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
        initResourceManager(ResourcePack.createFilledResourcePack(200));
        housingManager.buildSimpleHut();
    }
}

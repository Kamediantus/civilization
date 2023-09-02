package ge.rodichev.civilization;


import java.util.stream.*;

import ge.rodichev.civilization.config.*;
import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.manager.*;
import ge.rodichev.civilization.resource.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;

@SpringBootApplication
public class CivilizationApplication {
    private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ManagerConfig.class);


	public static void main(String[] args) {
		SpringApplication.run(CivilizationApplication.class, args);
        MultiplyManager multiplyManager = context.getBean(MultiplyManager.class);
        HousingManager housingManager = context.getBean(HousingManager.class);
        FactoryManager factoryManager = context.getBean(FactoryManager.class);
        CitizensManager citizensManager = context.getBean(CitizensManager.class);
        housingManager.buildSimpleHut();

        initCitizens(TestRunner.prepareCitizens(true));
        initResourceManager(ResourcePack.createFilledResourcePack(100));

        IntStream.range(0, 100).forEach(i -> {
            citizensManager.tick();
            multiplyManager.tick();
            housingManager.tick();
            factoryManager.tick();
        });

        IntStream.range(100, 200).forEach(i -> {
            citizensManager.tick();
            multiplyManager.tick();
            housingManager.tick();
            factoryManager.tick();
        });

        IntStream.range(200, 300).forEach(i -> {
            citizensManager.tick();
            multiplyManager.tick();
            housingManager.tick();
            factoryManager.tick();
        });

        IntStream.range(300, 400).forEach(i -> {
            citizensManager.tick();
            multiplyManager.tick();
            housingManager.tick();
            factoryManager.tick();
        });
        System.out.println("ss");
	}

    public static void initCitizens(Citizens citizensToAdd) {
        Citizens citizens = context.getBean(Citizens.class);
        citizens.addAll(citizensToAdd);
    }

    public static void initResourceManager(ResourcePack startResources) {
        ResourceManager resourceManager = context.getBean(ResourceManager.class);
        startResources.forEach((k,v) -> resourceManager.getResourcePack().replace(k, v));
    }
}

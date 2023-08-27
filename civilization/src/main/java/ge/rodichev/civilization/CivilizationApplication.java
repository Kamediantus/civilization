package ge.rodichev.civilization;


import java.util.*;
import java.util.stream.*;

import ge.rodichev.civilization.config.*;
import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.manager.*;
import ge.rodichev.civilization.resource.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.*;
import org.springframework.context.annotation.*;

@SpringBootApplication
public class CivilizationApplication {
    private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ManagerConfig.class);


	public static void main(String[] args) {
		SpringApplication.run(CivilizationApplication.class, args);
        MultiplyManager multiplyManager = context.getBean(MultiplyManager.class);
        HousingManager housingManager = context.getBean(HousingManager.class);
        ResourceManager resourceManager = context.getBean(ResourceManager.class);

        initCitizens(TestRunner.prepareCitizens(true));
        initResourceManager(ResourcePack.createFilledResourcePack(100));

        IntStream.range(0, 100).forEach(i -> {
            context.getBean(Citizens.class).forEach(Citizen::tick);
            multiplyManager.tick();
            housingManager.tick();
        });
        System.out.println();

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

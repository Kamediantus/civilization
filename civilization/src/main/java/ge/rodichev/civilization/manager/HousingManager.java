package ge.rodichev.civilization.manager;

import java.util.*;

import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.building.housing.*;
import ge.rodichev.civilization.resource.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;

@Getter
@Setter
public class HousingManager extends Manager {
    public static final double PERCENTAGE_TO_PREFER_BUILD = 0.75d;
    private ArrayList<Housing> housings;
    @Autowired
    private Citizens citizens;
    @Autowired
    private ResourceManager resourceManager;

    public HousingManager() {
        this.housings = new ArrayList<>();
    }


    @Override
    public void tick() {
        if (isPreferToBuildHouse() && resourceManager.getResourcePack().hasEnoughResources(RequiredResources.RESOURCE_MAP.get(SimpleHut.class))) {
            buildSimpleHut();
        }
    }

    public double getHousingFilledPercentage() {
        return getHousingCapacity() == 0
                ? 1
                : Double.valueOf(this.citizens.size()) / getHousingCapacity();
    }

    public int getHousingCapacity() {
        return this.housings.stream().map(housing -> housing.getCapacity()).reduce(0, Integer::sum);
    }

    private boolean isPreferToBuildHouse() {
        return getHousingFilledPercentage() > PERCENTAGE_TO_PREFER_BUILD;
    }

    // TODO make transactional
    private void buildSimpleHut() {
        resourceManager.degreaseResources(RequiredResources.RESOURCE_MAP.get(SimpleHut.class));
        SimpleHut simpleHut = new SimpleHut();
        housings.add(simpleHut);
    }
}

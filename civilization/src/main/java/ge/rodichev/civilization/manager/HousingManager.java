package ge.rodichev.civilization.manager;

import java.util.*;

import ge.rodichev.civilization.entity.building.housing.*;
import ge.rodichev.civilization.resource.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;

@Getter
@Setter
public class HousingManager extends Manager {
    public static final double PERCENTAGE_TO_PREFER_BUILD = 0.75d;
    private ArrayList<Housing> housings; //TODO decide: probably it is no need to store all buildings
    private int housingCapacity;
    private int ticksAfterLastBuild;

    @Autowired
    private ResourceManager resourceManager;

    public HousingManager() {
        this.housingCapacity = 0;
        this.ticksAfterLastBuild = -1;
        this.housings = new ArrayList<>();
    }

    @Override
    public void tick() {
        if (isPreferToBuildHouse()
                && resourceManager.getResourcePack().hasEnoughResources(RequiredResources.RESOURCE_MAP.get(SimpleHut.class))
                && ticksAfterLastBuild == -1) {
            buildSimpleHut();
            ticksAfterLastBuild += 1;
        } else {
            ticksAfterLastBuild = ticksAfterLastBuild == 2 ? -1 : ticksAfterLastBuild + 1;
        }
    }

    public double getHousingFilledPercentage() {
        return getHousingCapacity() == 0
                ? 1
                : Double.valueOf(this.citizens.size()) / getHousingCapacity();
    }

    private boolean isPreferToBuildHouse() {
        return getHousingFilledPercentage() > PERCENTAGE_TO_PREFER_BUILD;
    }

    // TODO make transactional
    public void buildSimpleHut() {
        resourceManager.degreaseResources(RequiredResources.RESOURCE_MAP.get(SimpleHut.class));
        SimpleHut simpleHut = new SimpleHut();
        housings.add(simpleHut);
        this.housingCapacity += simpleHut.getCapacity();
    }
}

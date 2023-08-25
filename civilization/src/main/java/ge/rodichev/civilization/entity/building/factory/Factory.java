package ge.rodichev.civilization.entity.building.factory;

import java.util.*;

import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.building.*;
import ge.rodichev.civilization.resource.*;
import lombok.*;

@Getter
@Setter
public abstract class Factory extends Building {
    private int requiredCitizenCount;
    private int maxCitizenCount;
    private Citizens employee;

    abstract ResourcePack getGeneratedResources();
    public double getGenerationEfficiency() {
        return employee.size() < requiredCitizenCount
                ? 0
                : employee.size() / maxCitizenCount;
    }

    abstract int getMaxCitizenCount();
}

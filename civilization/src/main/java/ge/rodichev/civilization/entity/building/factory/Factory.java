package ge.rodichev.civilization.entity.building.factory;

import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.building.*;
import ge.rodichev.civilization.resource.*;
import lombok.*;

@Getter
@Setter
public abstract class Factory extends Building {
    private Citizens employee;

    protected Factory() {
        this.employee = new Citizens();
    }

    public abstract int getMaxCitizenCount();
    public abstract int getRequiredCitizenCount();
    public abstract ResourcePack getMaxGeneratedResourcesPerTick();

    public ResourcePack getActualGeneratedResourcesPerTick() {
        ResourcePack rawGeneratedResources = (ResourcePack) getMaxGeneratedResourcesPerTick().clone();

        rawGeneratedResources.keySet()
                .forEach(k -> rawGeneratedResources.replace(k,  rawGeneratedResources.get(k) * getGenerationEfficiency()));
        return rawGeneratedResources;
    }

    public double getGenerationEfficiency() {
        return getEmployee().size() < getRequiredCitizenCount()
                ? 0
                : Double.valueOf(getEmployee().size()) / getMaxCitizenCount();
    }

}

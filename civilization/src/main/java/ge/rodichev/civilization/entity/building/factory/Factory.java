package ge.rodichev.civilization.entity.building.factory;

import java.util.concurrent.atomic.*;

import com.fasterxml.jackson.annotation.*;
import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.building.*;
import ge.rodichev.civilization.resource.*;
import lombok.*;

@Getter
@Setter
public abstract class Factory extends Building {
    @JsonIgnore
    private Citizens employee;

    static final AtomicLong NEXT_ID = new AtomicLong(0);
    final long id = NEXT_ID.getAndIncrement();

    protected Factory() {
        this.employee = new Citizens();
    }

    public abstract Resource getProducedResourceType();

    @JsonIgnore
    public abstract int getMaxCitizenCount();
    @JsonIgnore
    public abstract int getRequiredCitizenCount();
    @JsonIgnore
    public abstract ResourcePack getMaxGeneratedResourcesPerTick();
    @JsonIgnore
    public boolean isProduceBurnedResource() {
        return false;
    }
    @JsonIgnore
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

    public void addEmployee(Citizen citizen) {
        this.getEmployee().add(citizen);
        citizen.setFactory(this);
    }

    public void addEmployees(Citizens citizens) {
        this.getEmployee().addAll(citizens);
        citizens.forEach(citizen -> citizen.setFactory(this));
    }
}

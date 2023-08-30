package ge.rodichev.civilization.manager;

import java.util.*;

import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.building.factory.*;
import ge.rodichev.civilization.entity.building.housing.*;
import ge.rodichev.civilization.resource.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;

@Getter
@Setter
public class FactoryManager {
    private List<Factory> factories;
    private Citizens freeToWorkCitizens;
    private Map<Resource, Class<? extends Factory>> resourceFactoryMap;

    @Autowired
    Citizens citizens;

    @Autowired
    ResourceManager resourceManager;

    public FactoryManager() {
        this.factories = new ArrayList<>();
        this.resourceFactoryMap = createResourceFactoryMap();
    }

    public void tick() {
        ResourcePack producedResources = getProducedResourcesPerTick();
        this.freeToWorkCitizens = citizens.getFreeToWorkCitizens();
        if (!freeToWorkCitizens.isEmpty()) {
            Resource mostValuableResource = getMostValuableResourceToImprove(getResourcesValue(producedResources));
            // add 1 citizen to each factory
            addCitizensToFactories(mostValuableResource);
            tryToBuildMostValuableResource(mostValuableResource);
        }
        //produce resources
        getResourceManager().getResourcePack().concatResources(producedResources);
    }

    protected void addCitizensToFactories(Resource mostValuableResource) {
        factories.stream().filter(factory -> factory.getClass() == getResourceFactoryMap().get(mostValuableResource)
                        && factory.getEmployee().size() < factory.getMaxCitizenCount())
                .forEach(factory -> {
                    Optional<Citizen> newEmployeeOptional = freeToWorkCitizens.stream().findFirst();
                    if (newEmployeeOptional.isPresent()) {
                        Citizen newEmployee = newEmployeeOptional.get();
                        factory.getEmployee().add(newEmployee);
                        newEmployee.setFactory(factory);
                        freeToWorkCitizens.remove(newEmployee);
                    }
                });
    }

    protected void tryToBuildMostValuableResource(Resource mostValuableResource) {
        if (mostValuableResource == Resource.WOOD && ableToBuild(Sawmill.class)) {
            build(new Sawmill());
        } else if (mostValuableResource == Resource.STONE && ableToBuild(StonePit.class)) {
            build(new StonePit());
        }
    }

    protected void build(Factory factoryToBuild) {
        resourceManager.degreaseResources(factoryToBuild.getRequiredResourcesForBuild());
        List<Citizen> employees = freeToWorkCitizens.size() >= factoryToBuild.getMaxCitizenCount() / 2
                ? freeToWorkCitizens.subList(0, factoryToBuild.getMaxCitizenCount() / 2)
                : freeToWorkCitizens.subList(0, factoryToBuild.getRequiredCitizenCount());

        factoryToBuild.getEmployee().addAll(employees);
        employees.forEach(citizen -> citizen.setFactory(factoryToBuild));
        freeToWorkCitizens.removeAll(employees); //TODO add test
        this.factories.add(factoryToBuild);
    }

    protected boolean ableToBuild(Class<? extends Factory> buildClass) {
        return resourceManager.getResourcePack().hasEnoughResources(RequiredResources.RESOURCE_MAP.get(buildClass))
                && freeToWorkCitizens.size() >= RequiredResources.CITIZENS_MAP.get(buildClass);
    }

    protected Resource getMostValuableResourceToImprove(ResourcePack valuePerResource) {
        Resource mostValuableResource = Resource.WOOD;
        Double minValue = valuePerResource.get(mostValuableResource);
        for (Map.Entry<Resource, Double> entry: valuePerResource.entrySet()) {
            if (entry.getValue() < minValue) {
               minValue = entry.getValue();
               mostValuableResource = entry.getKey();
            }
        }
        return mostValuableResource;
    }

    protected ResourcePack getResourcesValue(ResourcePack actualGenPerTick) {
        ResourcePack resourcePackWithRatio = ResourcePack.multiplyResources(getResourceManager().getResourcePack(), 0.1d);
        ResourcePack genResourcesWithRatio = ResourcePack.multiplyResources(actualGenPerTick, 5);

        return ResourcePack.multiplyResourcePacks(resourcePackWithRatio, genResourcesWithRatio);
    }

    protected ResourcePack getProducedResourcesPerTick() {
        ResourcePack producedResources = ResourcePack.createEmptyResourcePack();
        factories.forEach(factory -> producedResources.concatResources(factory.getActualGeneratedResourcesPerTick()));
        return producedResources;
    }

    private static Map<Resource, Class<? extends Factory>> createResourceFactoryMap() {
        Map<Resource, Class<? extends Factory>> resourceFactoryMap = new EnumMap<>(Resource.class);
        resourceFactoryMap.put(Resource.WOOD, Sawmill.class);
        resourceFactoryMap.put(Resource.STONE, StonePit.class);
        return resourceFactoryMap;
    }
}
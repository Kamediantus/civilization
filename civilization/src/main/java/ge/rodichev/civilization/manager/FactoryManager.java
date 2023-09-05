package ge.rodichev.civilization.manager;

import java.util.*;
import java.util.concurrent.atomic.*;

import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.building.factory.*;
import ge.rodichev.civilization.entity.consts.*;
import ge.rodichev.civilization.resource.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;

@Getter
@Setter
public class FactoryManager extends Manager implements DeadProcessing {
    private Citizens freeToWorkCitizens;
    private Iterator<Citizen> iterator;
    private final Map<Resource, Class<? extends Factory>> resourceFactoryMap;
    private final Map<Class<? extends Factory>, Resource> factoryResourceMap;

    @Autowired
    Factories factories;

    @Autowired
    ResourceManager resourceManager;

    @Autowired
    CitizensManager citizensManager;

    public FactoryManager() {
        this.resourceFactoryMap = createResourceFactoryMap();
        this.factoryResourceMap = createFactoryResourceMap();
    }

    @Override
    public void tick() {
        removeDeadCitizens();

        ResourcePack producedCommonResources = getResourceManager().getCommonResourcesCurrentTick();
        iterator = getCitizensManager().getReadyToWorkCitizens().iterator();
        boolean addedToExistingFactory = true;
        boolean builtNewFactory = true;
        Resource mostValuableResource = getMostValuableResourceToImprove(getResourcesValue(producedCommonResources));
        while (iterator.hasNext() && (addedToExistingFactory || builtNewFactory)) {
            addedToExistingFactory = addCitizensToFactories(mostValuableResource);
            builtNewFactory = tryToBuildMostValuableFactory(mostValuableResource);
        }
    }

    // remove not dead but elderly citizens
    @Override
    public void removeDeadCitizens() {
        this.citizens.stream().filter(citizen -> citizen.getAge() == Age.ELDERLY && citizen.getFactory() != null)
                .forEach(citizen -> {
                    citizen.getFactory().getEmployee().remove(citizen);
                    citizen.setFactory(null);
                });
    }

    // true if added
    protected boolean addCitizensToFactories(Resource mostValuableResource) {
        AtomicBoolean added = new AtomicBoolean(false);
        getFactories().stream().filter(factory -> factory.getClass() == getResourceFactoryMap().get(mostValuableResource)
                        && factory.getEmployee().size() < factory.getMaxCitizenCount())
                .forEach(factory -> {
                    while (iterator.hasNext() && factory.getEmployee().size() < factory.getMaxCitizenCount()) {
                        added.set(true);
                        Citizen newEmployee = iterator.next();
                        factory.getEmployee().add(newEmployee);
                        newEmployee.setFactory(factory);
                    }
                });
        return added.get();
    }

    protected boolean tryToBuildMostValuableFactory(Resource mostValuableResource) {
        Citizens initEmployees = ableToBuild(resourceFactoryMap.get(mostValuableResource));
        if (initEmployees.isEmpty()) {
            return false;
        } else if (mostValuableResource == Resource.WOOD) {
            build(new Sawmill());
        } else if (mostValuableResource == Resource.STONE) {
            build(new StonePit());
        } else if (mostValuableResource == Resource.WORKFORCE) {
            build(new ConstructionWorkshop());
        }
        return true;
    }

    protected void build(Factory factoryToBuild) {
        getResourceManager().degreaseResources(factoryToBuild.getRequiredResourcesForBuild());
        List<Citizen> employees = new ArrayList<>();
        while (iterator.hasNext() && employees.size() < factoryToBuild.getMaxCitizenCount() / 2) {
            employees.add(iterator.next());
        }

        factoryToBuild.getEmployee().addAll(employees);
        employees.forEach(citizen -> citizen.setFactory(factoryToBuild));
        getFactories().add(factoryToBuild);
    }

    // return init employee for new building. If iterator has no enough citizens or resources not enough - return empty list
    protected Citizens ableToBuild(Class<? extends Factory> buildClass) {
        Citizens initEmployees = new Citizens();
        if (!getResourceManager().getResourcePack().hasEnoughResources(RequiredResources.RESOURCE_MAP.get(buildClass))) {
            return initEmployees;
        } else {
            while (iterator.hasNext() && initEmployees.size() < RequiredResources.CITIZENS_MAP.get(buildClass)) {
                initEmployees.add(iterator.next());
            }
        }
        return initEmployees.size() < RequiredResources.CITIZENS_MAP.get(buildClass) ? new Citizens() : initEmployees;
    }

    protected Resource getMostValuableResourceToImprove(ResourcePack valuePerResource) {
        // if workforce turn to 0 in previous tick, it needs to build more workshops
        if (valuePerResource.get(Resource.WORKFORCE) < 5d) {
            return Resource.WORKFORCE;
        }
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
        ResourcePack valueResourcePack = ResourcePack.multiplyResourcePacks(resourcePackWithRatio, genResourcesWithRatio);

        getFactories().stream().filter(Factory::isProduceBurnedResource)
                .forEach(factory -> {
                    Resource burningResource = factoryResourceMap.get(factory.getClass());
                    valueResourcePack.put(burningResource, getResourceManager().getResidueOfBurningResourcesFromPreviousTick().get(burningResource));
                });
        return valueResourcePack;
    }

    private static Map<Class<? extends Factory>, Resource> createFactoryResourceMap() {
        Map<Class<? extends Factory>, Resource> factoryResourceMap = new HashMap<>();
        Map<Resource, Class<? extends Factory>> resourceFactoryMap = createResourceFactoryMap();
        for (Map.Entry<Resource, Class<? extends Factory>> entry: resourceFactoryMap.entrySet()) {
            factoryResourceMap.put(entry.getValue(), entry.getKey());
        }
        return factoryResourceMap;
    }

    private static Map<Resource, Class<? extends Factory>> createResourceFactoryMap() {
        Map<Resource, Class<? extends Factory>> resourceFactoryMap = new EnumMap<>(Resource.class);
        resourceFactoryMap.put(Resource.WOOD, Sawmill.class);
        resourceFactoryMap.put(Resource.STONE, StonePit.class);
        resourceFactoryMap.put(Resource.WORKFORCE, ConstructionWorkshop.class);
        return resourceFactoryMap;
    }
}

package ge.rodichev.civilization.manager;

import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

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
    private Iterator<Citizen> readyToWorkCitizensIterator;
    private final Map<Resource, Class<? extends Factory>> resourceFactoryMap;
    private final Map<Class<? extends Factory>, Resource> factoryResourceMap;
    public static final Double BOTTOM_LINE_OF_BURNING_RESOURCES = 5d;

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
    public void tick() { // TODO add tick test
        removeDeadCitizens();

        ResourcePack producedCommonResources = getResourceManager().getCommonResourcesCurrentTick();
        setReadyToWorkCitizensIterator(getCitizensManager().getReadyToWorkCitizens().iterator());
        boolean addedToExistingFactory = true;
        boolean builtNewFactory = true;
        Resource mostValuableResource = getMostValuableResourceToImprove(getResourcesValue(producedCommonResources));
        while (getReadyToWorkCitizensIterator().hasNext() && (addedToExistingFactory || builtNewFactory)) {
            addedToExistingFactory = addCitizensToFactories(mostValuableResource);
            builtNewFactory = tryToBuildMostValuableFactory(mostValuableResource);
        }
    }

    // TODO remove DeadProcessing interface and rename method
    // remove not dead but elderly citizens
    @Override
    public void removeDeadCitizens() {
        getCitizens().stream().filter(citizen -> citizen.getAge() == Age.ELDERLY && citizen.getFactory() != null)
                .forEach(citizen -> {
                    citizen.getFactory().getEmployee().remove(citizen);
                    citizen.setFactory(null);
                });
    }

    // true if added
    protected boolean addCitizensToFactories(Resource mostValuableResource) {
        AtomicBoolean added = new AtomicBoolean(false);
        Class<? extends Factory> mostValuableFactory = getResourceFactoryMap().get(mostValuableResource);
        getFactories().stream().filter(factory -> factory.getClass() == mostValuableFactory
                        && factory.getEmployee().size() < factory.getMaxCitizenCount())
                .forEach(factory -> {
                    while (getReadyToWorkCitizensIterator().hasNext() && factory.getEmployee().size() < factory.getMaxCitizenCount()) {
                        added.set(true);
                        factory.addEmployee(getReadyToWorkCitizensIterator().next());
                    }
                });
        return added.get();
    }

    protected boolean tryToBuildMostValuableFactory(Resource mostValuableResource) {
        Citizens initEmployees = ableToBuild(resourceFactoryMap.get(mostValuableResource));
        if (initEmployees.isEmpty()) {
            return false;
        } else if (mostValuableResource == Resource.WOOD) {
            build(new Sawmill(), initEmployees);
        } else if (mostValuableResource == Resource.STONE) {
            build(new StonePit(), initEmployees);
        } else if (mostValuableResource == Resource.WORKFORCE) {
            build(new ConstructionWorkshop(), initEmployees);
        }
        return true;
    }

    protected void build(Factory factoryToBuild, Citizens initEmployees) {
        getResourceManager().degreaseResources(factoryToBuild.getRequiredResourcesForBuild());
        factoryToBuild.addEmployees(initEmployees);
        getFactories().add(factoryToBuild);
    }

    // return init employee for new building. If iterator has no enough citizens or resources not enough - return empty list
    protected Citizens ableToBuild(Class<? extends Factory> buildClass) {
        Citizens initEmployees = new Citizens();
        if (!getResourceManager().getResourcePack().hasEnoughResources(RequiredResources.RESOURCE_MAP.get(buildClass))) {
            return initEmployees;
        } else {
            while (getReadyToWorkCitizensIterator().hasNext() && initEmployees.size() < RequiredResources.CITIZENS_MAP.get(buildClass)) {
                initEmployees.add(getReadyToWorkCitizensIterator().next());
            }
        }
        return initEmployees.size() < RequiredResources.CITIZENS_MAP.get(buildClass) ? new Citizens() : initEmployees;
    }

    protected Resource getMostValuableResourceToImprove(ResourcePack valuePerResource) {
        if (valuePerResource.get(Resource.WORKFORCE) < BOTTOM_LINE_OF_BURNING_RESOURCES) {
            return Resource.WORKFORCE;
        }
        Resource mostValuableResource = Resource.WOOD;
        Double minValue = valuePerResource.get(mostValuableResource);
        for (Map.Entry<Resource, Double> entry: valuePerResource.entrySet().stream()
                .filter(entry -> !entry.getKey().isBurnOnEndOfTick()).collect(Collectors.toSet())) {
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

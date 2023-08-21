package ge.rodichev.civilization.entity.building.factory;

public class Sawmill extends Factory {
    @Override
    public int getRequiredCitizenCount() {
        return 2;
    }

    @Override
    public int getWoodCost() {
        return 10;
    }

    @Override
    public int getStoneCost() {
        return 5;
    }

    @Override
    public int getWoodGeneration() {
        return 1;
    }
}

package ge.rodichev.civilization.entity.building;

import java.util.*;

import ge.rodichev.civilization.resource.*;

public abstract class Building {
    public abstract ResourcePack getRequiredResourcesForBuild();
}

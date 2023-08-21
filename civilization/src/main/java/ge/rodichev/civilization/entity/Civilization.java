package ge.rodichev.civilization.entity;

import java.util.*;

import ge.rodichev.civilization.entity.building.factory.*;
import lombok.*;

@Getter
@Setter
public class Civilization {
    private List<Factory> factories;
    private List<Citizen> citizens;
    private int wood;
    private int stone;

    public void tick() {

    }

}

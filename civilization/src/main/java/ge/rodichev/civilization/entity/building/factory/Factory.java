package ge.rodichev.civilization.entity.building.factory;

import ge.rodichev.civilization.entity.building.*;
import lombok.*;

@Getter
@Setter
public class Factory extends Building {
    private int requiredCitizenCount;
    private int woodCost;
    private int stoneCost;
    private int woodGeneration;
    private int stoneGeneration;
}

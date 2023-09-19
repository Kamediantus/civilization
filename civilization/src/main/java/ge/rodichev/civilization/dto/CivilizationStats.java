package ge.rodichev.civilization.dto;

import ge.rodichev.civilization.entity.building.factory.*;
import ge.rodichev.civilization.resource.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class CivilizationStats {
    private Factories factories;
    private int simpleHutCount;
    private ResourcePack resourcePack;
    private long citizensCount;
    private long tickCount;
}

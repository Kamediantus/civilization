package ge.rodichev.civilization.entity.building.factory.housing;

import java.util.*;

import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.building.*;
import lombok.*;

@Getter
@Setter
public abstract class Housing extends Building {
    @Setter(AccessLevel.NONE)
    private int capacity;
    List<Citizen> roomers;
}

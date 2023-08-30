package ge.rodichev.civilization.entity.building.housing;

import ge.rodichev.civilization.entity.building.*;
import lombok.*;

@Getter
@Setter
public abstract class Housing extends Building {
    public abstract int getCapacity();
}

package ge.rodichev.civilization.entity.building;

import java.util.*;

import ge.rodichev.civilization.entity.*;
import lombok.*;

@Getter
@Setter
public class House {
    private int capacity;
    List<Citizen> roomers;
}

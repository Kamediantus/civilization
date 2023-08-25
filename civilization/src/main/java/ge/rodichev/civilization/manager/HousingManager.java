package ge.rodichev.civilization.manager;

import java.util.*;

import ge.rodichev.civilization.config.*;
import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.building.factory.*;
import ge.rodichev.civilization.entity.building.factory.housing.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;

@Getter
@Setter
public class HousingManager extends Manager {
//    private List<Factory> factories;
    private List<Housing> housings;
    @Autowired
    private Citizens citizens;


    @Override
    public void tick() {

    }

    public double getHousingFilledPercentage() {
        return getHousingCapacity() == 0
                ? 1
                : this.citizens.size() / getHousingCapacity();
    }

    public int getHousingCapacity() {
        return this.housings.stream().map(housing -> housing.getCapacity()).reduce(0, Integer::sum);
    }
}

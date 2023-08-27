package ge.rodichev.civilization.utils;

import java.util.stream.*;

import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.consts.*;

public class CitizensUtils {
    public static Citizens createNormCitizens(int count) {
        Citizens citizens = new Citizens();
        IntStream.range(0, count).forEach((c) -> {
            citizens.add(new Citizen(c, Age.MATURE, Health.NORM));
        });
        return citizens;
    }
}

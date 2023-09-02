package ge.rodichev.civilization;

import java.util.*;
import java.util.stream.*;

import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.building.housing.*;
import ge.rodichev.civilization.entity.consts.*;
import ge.rodichev.civilization.manager.*;

public class TestRunner {
    public static void main(String[] args) {
    }

    public static List<Housing> prepareHouses(int countBase, int countSecondGrade) {
        List<Housing> housings = new ArrayList<>();
        IntStream.range(0, countBase).forEach((count) -> housings.add(new SimpleHut()));
        return housings;
    }

    public static Citizens prepareCitizens(boolean seven) {
        Citizens citizens = new Citizens();
        citizens.add(new Citizen(1, Age.MATURE, Health.NORM));
        citizens.add(new Citizen(2, Age.MATURE, Health.NORM));
        citizens.add(new Citizen(3, Age.BABY, Health.NORM));
        citizens.add(new Citizen(4, Age.ELDERLY, Health.NORM));
        citizens.add(new Citizen(5, Age.TEENAGER, Health.NORM));
        citizens.add(new Citizen(6, Age.MATURE, Health.ATHLETIC));
        if (seven) {
            citizens.add(new Citizen(7, Age.MATURE, Health.SLIGHTLY_ILL));
        }
        return citizens;
    }
}

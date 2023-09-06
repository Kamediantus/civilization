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

    public static Citizens createCitizens(Age age, Health health, int count) {
        Citizens citizens = new Citizens();
        IntStream.range(0, count).forEach((c) -> {
            citizens.add(new Citizen(c, age, health));
        });
        return citizens;
    }

    public static Citizens createCitizens(int age, Health health, int count) {
        Citizens citizens = new Citizens();
        IntStream.range(0, count).forEach((c) -> {
            citizens.add(createCitizen(age, health));
        });
        return citizens;
    }

    public static Citizen createCitizen(int ageTick, Health health) {
        Citizen citizen = new Citizen(1l, Age.getAgeByTick(ageTick), health);
        citizen.setAgeTick(ageTick);
        return citizen;
    }

    public static Citizens createCitizens(int countMature, int countElderly) {
        Citizens citizens = new Citizens();
        IntStream.range(0, countMature).forEach((c) -> {
            citizens.add(new Citizen(c, Age.MATURE, Health.NORM));
        });
        IntStream.range(0, countElderly).forEach((c) -> {
            citizens.add(new Citizen(c, Age.ELDERLY, Health.NORM));
        });
        return citizens;
    }
}

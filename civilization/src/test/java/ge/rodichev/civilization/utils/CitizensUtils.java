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

    public static Citizens createCitizens(int countNorm, int countSlightlyIll, int countSeriousIll, int countAthletic) {
        Citizens citizens = new Citizens();
        IntStream.range(0, countNorm).forEach((c) -> {
            citizens.add(new Citizen(c, Age.MATURE, Health.NORM));
        });
        IntStream.range(0, countSlightlyIll).forEach((c) -> {
            citizens.add(new Citizen(c, Age.MATURE, Health.SLIGHTLY_ILL));
        });
        IntStream.range(0, countSeriousIll).forEach((c) -> {
            citizens.add(new Citizen(c, Age.MATURE, Health.SERIOUS_ILL));
        });
        IntStream.range(0, countAthletic).forEach((c) -> {
            citizens.add(new Citizen(c, Age.MATURE, Health.ATHLETIC));
        });
        return citizens;
    }
}

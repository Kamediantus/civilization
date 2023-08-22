package ge.rodichev.civilization;

import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.consts.*;

public class TestRunner {
    public static void main(String[] args) {
        testManager();
    }

    private static void testManager() {
        MultiplyManager manager = new MultiplyManager(prepareCitizens(true));
        for (int i = 0; i < 500; i++) {
            manager.getCitizens().forEach(Citizen::tick);
            manager.tick();
            System.out.println(manager.getCitizens().size());
        }
    }

    private static Citizens prepareCitizens(boolean seven) {
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

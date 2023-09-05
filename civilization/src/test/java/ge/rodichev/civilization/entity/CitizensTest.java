package ge.rodichev.civilization.entity;

import static org.springframework.test.util.AssertionErrors.*;

import ge.rodichev.civilization.entity.building.factory.*;
import ge.rodichev.civilization.entity.consts.*;
import ge.rodichev.civilization.utils.*;
import org.junit.jupiter.api.*;

class CitizensTest {

    @Test
    void testGetFreeToWorkCitizens() {
        Citizens citizens = new Citizens();

        citizens.addAll(CitizensUtils.createCitizens(Age.BABY, Health.ATHLETIC, 2));
        citizens.addAll(CitizensUtils.createCitizens(Age.BABY, Health.SLIGHTLY_ILL, 2));
        citizens.addAll(CitizensUtils.createCitizens(Age.BABY, Health.SERIOUS_ILL, 2));
        citizens.addAll(CitizensUtils.createCitizens(Age.BABY, Health.NORM, 2));

        citizens.addAll(CitizensUtils.createCitizens(Age.TEENAGER, Health.ATHLETIC, 2));
        citizens.addAll(CitizensUtils.createCitizens(Age.TEENAGER, Health.SLIGHTLY_ILL, 2));
        citizens.addAll(CitizensUtils.createCitizens(Age.TEENAGER, Health.SERIOUS_ILL, 2));
        citizens.addAll(CitizensUtils.createCitizens(Age.TEENAGER, Health.NORM, 2));

        citizens.addAll(CitizensUtils.createCitizens(Age.MATURE, Health.ATHLETIC, 2));
        citizens.addAll(CitizensUtils.createCitizens(Age.MATURE, Health.SLIGHTLY_ILL, 2));
        citizens.addAll(CitizensUtils.createCitizens(Age.MATURE, Health.SERIOUS_ILL, 2));
        citizens.addAll(CitizensUtils.createCitizens(Age.MATURE, Health.NORM, 2));
        Citizens busyCitizens = CitizensUtils.createCitizens(Age.MATURE, Health.NORM, 2);
        busyCitizens.forEach(citizen -> citizen.setFactory(new Sawmill()));
        citizens.addAll(busyCitizens);

        citizens.addAll(CitizensUtils.createCitizens(Age.BABY, Health.ATHLETIC, 2));
        citizens.addAll(CitizensUtils.createCitizens(Age.BABY, Health.SLIGHTLY_ILL, 2));
        citizens.addAll(CitizensUtils.createCitizens(Age.BABY, Health.SERIOUS_ILL, 2));
        citizens.addAll(CitizensUtils.createCitizens(Age.BABY, Health.NORM, 2));

        Citizens freeToWorkCitizens = citizens.getFreeToWorkCitizens();
        assertEquals("Only 6 citizens ready to work", 6, freeToWorkCitizens.size());
        freeToWorkCitizens.forEach(citizen -> {
            assertEquals("Mature", Age.MATURE, citizen.getAge());
            assertTrue("Not serious ill", citizen.getHealth() != Health.SERIOUS_ILL);
            assertTrue("Not busy", citizen.getFactory() == null);
        });
    }
}

package ge.rodichev.civilization.entity;

import static org.springframework.test.util.AssertionErrors.*;

import java.util.stream.*;

import ge.rodichev.civilization.entity.consts.*;
import org.junit.jupiter.api.*;

class CitizenTest {

    @Test
    void testTick() {
        Citizen citizen = new Citizen(Age.BABY, Health.NORM);

        assertEquals("Citizen has no cooldown at init", Citizen.TICK_READY_TO_MULTIPLY, citizen.getTickWithoutMultiply());
        assertEquals("Start age tick", Age.BABY.getStartAgeTick(), citizen.getAgeTick());

        citizen.setAgeTick(Age.TEENAGER.getStartAgeTick() - 1);
        citizen.setTickWithoutMultiply(0);
        citizen.tick();

        assertEquals("Increment age tick", Age.TEENAGER.getStartAgeTick(), citizen.getAgeTick());
        assertEquals("Increment multiplyTick tick", 1, citizen.getTickWithoutMultiply());
        assertEquals("Became teenager", Age.TEENAGER, citizen.getAge());
    }

    @Test
    void testAbleToMultiply() { // TODO add test by health when it will be implemented
        Citizen citizen = new Citizen(Age.BABY, Health.NORM);
        assertFalse("Not able", citizen.ableToMultiply());
        assertFalse("Too young to multiply", citizen.ableToMultiplyByAge());
        assertTrue("Cooldoun is ok", citizen.ableToMultiplyByCooldown());

        citizen.setAge(Age.TEENAGER);
        assertFalse("Not able", citizen.ableToMultiply());
        assertFalse("Too young to multiply", citizen.ableToMultiplyByAge());
        assertTrue("Cooldoun is ok", citizen.ableToMultiplyByCooldown());

        citizen.setAge(Age.MATURE);
        assertTrue("Able", citizen.ableToMultiply());

        citizen.setTickWithoutMultiply(0);
        assertFalse("Able", citizen.ableToMultiply());
        assertFalse("Able", citizen.ableToMultiplyByCooldown());

        IntStream.range(0, Citizen.TICKS_BETWEEN_MULTIPLIES).forEach((i) -> {
            citizen.processMultiplyTick();
            assertFalse("Not able", citizen.ableToMultiply());
            assertFalse("Not ready by cooldoun", citizen.ableToMultiplyByCooldown());
        });

        citizen.processMultiplyTick();
        assertTrue("Able", citizen.ableToMultiply());

        citizen.setAge(Age.ELDERLY);
        assertFalse("Not able", citizen.ableToMultiply());
        assertTrue("Cooldoun is ok", citizen.ableToMultiplyByCooldown());
    }
}

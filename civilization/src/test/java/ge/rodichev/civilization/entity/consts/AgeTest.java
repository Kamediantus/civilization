package ge.rodichev.civilization.entity.consts;

import static org.springframework.test.util.AssertionErrors.*;

import org.junit.jupiter.api.*;

class AgeTest {

    @Test
    void testGetAgeByTick() {
        assertEquals("Baby", Age.BABY, Age.getAgeByTick(Age.BABY.getStartAgeTick()));
        assertEquals("Baby", Age.BABY, Age.getAgeByTick(Age.TEENAGER.getStartAgeTick() - 1));
        assertEquals("Teenager", Age.TEENAGER, Age.getAgeByTick(Age.TEENAGER.getStartAgeTick()));
        assertEquals("Teenager", Age.TEENAGER, Age.getAgeByTick(Age.MATURE.getStartAgeTick() - 1));
        assertEquals("Mature", Age.MATURE, Age.getAgeByTick(Age.MATURE.getStartAgeTick()));
        assertEquals("Mature", Age.MATURE, Age.getAgeByTick(Age.ELDERLY.getStartAgeTick() - 1));
        assertEquals("Elderly", Age.ELDERLY, Age.getAgeByTick(Age.ELDERLY.getStartAgeTick()));
        assertEquals("Elderly", Age.ELDERLY, Age.getAgeByTick(Age.DEATH.getStartAgeTick() - 1));
        assertEquals("Death", Age.DEATH, Age.getAgeByTick(Age.DEATH.getStartAgeTick()));
        assertEquals("Death", Age.DEATH, Age.getAgeByTick(Age.DEATH.getStartAgeTick() + 10));
    }
}

package ge.rodichev.civilization.manager;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.*;

import java.util.*;

import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.consts.*;
import ge.rodichev.civilization.utils.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

class CitizensManagerTest {

    @Test
    void testTick() {
        CitizensManager citizensManager = spy(new CitizensManager());
        Citizens citizens = new Citizens();
        citizens.addAll(CitizensUtils.createCitizens(Age.MATURE, Health.NORM, 2)
                .stream().map(Mockito::spy).toList());

        doCallRealMethod().when(citizensManager).tick();
        doReturn(citizens).when(citizensManager).getCitizens();

        citizensManager.tick();
        citizens.forEach(citizen -> {
            verify(citizen, times(1)).tick();
        });
        verify(citizensManager, times(1)).removeDeadCitizens();
    }

    @Test
    void testRemoveDeadCitizens() {
        CitizensManager citizensManager = spy(new CitizensManager());
        Citizens citizens = new Citizens();

        List<Citizen> deadCitizens = CitizensUtils.createCitizens(100, Health.NORM, 2)
                .stream().map(Mockito::spy).toList();
        citizens.addAll(deadCitizens);

        citizens.addAll(CitizensUtils.createCitizens(99, Health.NORM, 2)
                .stream().map(Mockito::spy).toList());

        doCallRealMethod().when(citizensManager).removeDeadCitizens();
        doReturn(citizens).when(citizensManager).getCitizens();

        citizensManager.removeDeadCitizens();
        assertTrue("Should pass in dead list", deadCitizens.containsAll(citizensManager.getDeadCitizens()));
        assertEquals("Only 2 alive", 2, citizensManager.getCitizens().size());
    }
}

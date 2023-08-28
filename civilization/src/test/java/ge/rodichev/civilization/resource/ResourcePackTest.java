package ge.rodichev.civilization.resource;

import static org.springframework.test.util.AssertionErrors.*;

import org.junit.jupiter.api.*;

public class ResourcePackTest {

    @Test
    public void testConcatResources() {
        ResourcePack resourcePack = ResourcePack.createEmptyResourcePack();

        resourcePack.concatResources(ResourcePack.createFilledResourcePack(10));
        resourcePack.keySet().forEach(k -> {
            assertEquals("0 + 10 = 10", 10d, resourcePack.get(k));
        });

        resourcePack.concatResources(ResourcePack.createFilledResourcePack(12.4d));
        resourcePack.keySet().forEach(k -> {
            assertEquals("10 + 12.4 = 22.4", 22.4d, resourcePack.get(k));
        });
    }

    @Test
    public void testHasEnoughResources() {
        ResourcePack resourcePack = ResourcePack.createFilledResourcePack(10);
        assertTrue("Should be more then needed", resourcePack.hasEnoughResources(ResourcePack.createFilledResourcePack(5)));
        assertTrue("Should be equals", resourcePack.hasEnoughResources(ResourcePack.createFilledResourcePack(10)));
        assertFalse("Should be less them needed", resourcePack.hasEnoughResources(ResourcePack.createFilledResourcePack(11)));
    }
}

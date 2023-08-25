package ge.rodichev.civilization.manager;

import java.util.stream.*;

public abstract class Manager {
    public abstract void tick();
    public void tick(int ticksCount) {
        IntStream.range(0, ticksCount).forEach(i -> tick());
    }
}

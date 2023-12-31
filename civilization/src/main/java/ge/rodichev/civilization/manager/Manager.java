package ge.rodichev.civilization.manager;

import java.util.stream.*;

import ge.rodichev.civilization.entity.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;

@Getter
public abstract class Manager {
    @Autowired
    Citizens citizens;
    public abstract void tick();
    public void tick(int ticksCount) {
        IntStream.range(0, ticksCount).forEach(i -> tick());
    }
}

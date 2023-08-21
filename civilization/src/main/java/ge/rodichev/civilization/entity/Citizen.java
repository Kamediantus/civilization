package ge.rodichev.civilization.entity;

import ge.rodichev.civilization.entity.building.factory.*;
import ge.rodichev.civilization.entity.consts.*;
import lombok.*;

@Getter
@Setter
public class Citizen {
    private long id;
    private Age age;
    private Education education;
    private Factory factory;
    private Health health;
    private static final int TICKS_BETWEEN_MULTIPLIES = 10;
    private int tickWithoutMultiply;
    private static final int TICKS_BEtWEEN_AGES = 20;
    private int ageTick;

    public Citizen() {}

    public Citizen(long id, Age age, Health health) {
        this.id = id;
        this.age = age;
        this.health = health;
        this.tickWithoutMultiply = -1;
        this.ageTick = age.startAgeTick;
    }

    public void tick() {
        if (this.age == Age.DEATH) {
            return;
        }
        this.ageTick += 1;
        if (this.age.getNextAgeStartTick() == this.ageTick) {
            this.setAge(age.getNextAge());
        }
    }

    public void incrementCooldown() {
        if (this.ableToMultiplyByCooldown()) {
            this.setTickWithoutMultiply(-1);
        } else {
            this.tickWithoutMultiply += 1;
        }
    }

    public boolean canMultiply() {
        return ableToMultiplyByAge() && ableToMultiplyByHealth();
    }

    public boolean ableToMultiplyByCooldown() {
        return tickWithoutMultiply == -1 || tickWithoutMultiply > TICKS_BETWEEN_MULTIPLIES;
    }

    public boolean ableToMultiplyByAge() {
        return this.age == Age.MATURE;
    }

    public boolean ableToMultiplyByHealth() {
        return this.health == Health.ATHLETIC || this.health == Health.NORM;
    }
}

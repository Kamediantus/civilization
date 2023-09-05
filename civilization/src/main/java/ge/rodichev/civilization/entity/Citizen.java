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
    protected static final int TICKS_BETWEEN_MULTIPLIES = 10;
    protected static final int TICK_READY_TO_MULTIPLY = -1;

    private int tickWithoutMultiply;
    private int ageTick;

    public Citizen() {}

    public Citizen(long id, Age age, Health health) {
        this.id = id;
        this.age = age;
        this.health = health;
        this.tickWithoutMultiply = TICK_READY_TO_MULTIPLY;
        this.ageTick = age.getStartAgeTick();
    }

    public void tick() {
        Age currentAge = processAge();
        if (currentAge != Age.DEATH) {
            processMultiplyTick();
        }
    }

    protected void processMultiplyTick() {
        if (this.ableToMultiplyByCooldown()) {
            this.setTickWithoutMultiply(TICK_READY_TO_MULTIPLY);
        } else {
            this.tickWithoutMultiply += 1;
        }
    }

    private Age processAge() {
        if (this.age != Age.DEATH) { // TODO remove death check if move citizen tick after manager remove death
            this.ageTick += 1;
            if (this.age.getNextAgeStartTick() == this.ageTick) {
                this.age = age.getNextAge();
            }
        }
        return this.age;
    }

    public boolean ableToMultiply() {
        return ableToMultiplyByAge() && ableToMultiplyByHealth() && ableToMultiplyByCooldown();
    }

    public boolean ableToMultiplyByCooldown() {
        return tickWithoutMultiply == TICK_READY_TO_MULTIPLY || tickWithoutMultiply > TICKS_BETWEEN_MULTIPLIES;
    }

    public boolean ableToMultiplyByAge() {
        return this.age == Age.MATURE;
    }

    public boolean ableToMultiplyByHealth() {
        return this.health == Health.ATHLETIC || this.health == Health.NORM;
    }

    public void setAge(Age age) {
        this.age = age;
        this.ageTick = age.getStartAgeTick();
    }
}

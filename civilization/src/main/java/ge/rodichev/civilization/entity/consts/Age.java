package ge.rodichev.civilization.entity.consts;

public enum Age {
    BABY(0), TEENAGER(10), MATURE(25), ELDERLY(75), DEATH(100);

    public int startAgeTick;

    Age(int startAgeTick) {
        this.startAgeTick = startAgeTick;
    }

    public Age getNextAge() {
        if (this == BABY) {
            return TEENAGER;
        } else if (this == TEENAGER) {
            return MATURE;
        } else if (this == MATURE) {
            return ELDERLY;
        } else {
            return DEATH;
        }
    }

    public int getNextAgeStartTick() {
        return getNextAge().startAgeTick;
    }
}

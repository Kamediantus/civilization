package ge.rodichev.civilization.manager;

import java.util.logging.*;

import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.consts.*;
import lombok.*;

@Getter
@Setter
public class CitizensManager extends Manager implements DeadProcessing {
    private Logger logger;
    private Citizens readyToWorkCitizens;
    private Citizens deadCitizens;

    public CitizensManager() {
        this.logger = Logger.getLogger("CitizensManager");
    }

    @Override
    public void tick() {
        citizens.forEach(Citizen::tick);
        removeDeadCitizens();
        this.readyToWorkCitizens = citizens.getFreeToWorkCitizens();
    }

    @Override
    public void removeDeadCitizens() {
        this.deadCitizens = this.citizens.filterByAge(Age.DEATH);
        this.citizens.removeAll(deadCitizens);
    }
}

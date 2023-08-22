package ge.rodichev.civilization.entity;

import java.util.*;
import java.util.logging.*;

import ge.rodichev.civilization.entity.consts.*;
import lombok.*;
import org.springframework.data.util.*;

@Getter
@Setter
public class MultiplyManager {
    private Citizens citizens;
    private Logger logger;

    public MultiplyManager(Citizens citizens) {
        this.citizens = citizens;
        this.logger = Logger.getLogger("MultiplyManager");
    }

    public void tick() {
        removeDeadCitizens();
        Citizens citizensAbleToMultiply = new Citizens();
        this.citizens.stream()
                .filter(citizen -> citizen.ableToMultiply())
                .forEach(citizen -> citizensAbleToMultiply.add(citizen));

//        logger.info(String.format("Citizens ready to multiply: %d", citizensAbleToMultiply.size()));
//        logger.info(String.format("All citizens: %d", citizens.size()));
//        logger.info(citizens.getHealthStats());
//        logger.info(citizens.getAgeStats());
        multiplyCitizens(cutCitizens(citizensAbleToMultiply));
        removeDeadCitizens();
    }


    public void removeDeadCitizens() {
//        int startSize = citizens.size();
        this.citizens.removeIf(citizen -> citizen.getAge() == Age.DEATH);
//        logger.info(String.format("Dead citizens was removed. Count: %d", startSize - citizens.size()));
    }


    public void multiplyCitizens(Citizens citizens) {
        if (!citizens.isEmpty()) {
            List<Pair<Citizen, Citizen>> pairs = groupCitizensByPairs(citizens);
            pairs.forEach(pair -> {
                this.citizens.add(new Citizen(citizens.size(), Age.BABY, Health.NORM));
                pair.getFirst().setTickWithoutMultiply(0);
                pair.getSecond().setTickWithoutMultiply(0);
            });
        }
    }

    private List<Pair<Citizen, Citizen>> groupCitizensByPairs(Citizens singleCitizens) {
        List<Pair<Citizen, Citizen>> pairs = new ArrayList<>();
        for (int i = 0; i <= singleCitizens.size() - 2; i ++) {
            pairs.add(Pair.of(singleCitizens.get(i), singleCitizens.get(i+1)));
        }
        return pairs;
    }

    // TODO implement filtering by health ??
    public static Citizens cutCitizens(Citizens citizens) {
        return new Citizens(citizens.subList(0, citizens.size() / 2 * 2));
    }
}

package ge.rodichev.civilization.entity;

import java.util.*;
import java.util.logging.*;

import ge.rodichev.civilization.entity.consts.*;
import lombok.*;
import org.springframework.data.util.*;

@Getter
@Setter
public class MultiplyManager {
    private List<Citizen> citizens;
    private Logger logger;

    public MultiplyManager(List<Citizen> citizens) {
        this.citizens = citizens;
        this.logger = Logger.getLogger("MultiplyManager");
    }

    public void tick() {
        List<Citizen> citizensAbleToMultiply = new ArrayList<>();
        this.citizens.forEach(citizen -> {
            if (citizen.ableToMultiplyByAge() && citizen.ableToMultiplyByHealth()) {
                if (citizen.ableToMultiplyByCooldown()) {
                    citizensAbleToMultiply.add(citizen);
//                    logger.info(String.format("Citizen %d ready to multiply", citizen.getId()));
                } else {
                    citizen.incrementCooldown();
                }
            }
        });
//        logger.info(String.format("Citizens ready to multiply: %d", citizensAbleToMultiply.size()));
        logger.info(String.format("All citizens: %d", citizens.size()));
        multiplyCitizens(cutCitizens(citizensAbleToMultiply));
        removeDeadCitizens();
    }


    public void removeDeadCitizens() {
        int startSize = citizens.size();
        this.citizens.removeIf(citizen -> citizen.getAge() == Age.DEATH);
        logger.info(String.format("Dead citizens was removed. Count: %d", startSize - citizens.size()));
    }


    public void multiplyCitizens(List<Citizen> citizens) {
        if (!citizens.isEmpty()) {
            List<Pair<Citizen, Citizen>> pairs = groupCitizensByPairs(citizens);
            for (Pair<Citizen, Citizen> pair: pairs) {
                this.citizens.add(new Citizen(citizens.size(), Age.BABY, Health.NORM));
                pair.getFirst().setTickWithoutMultiply(0);
                pair.getSecond().setTickWithoutMultiply(0);
            }
        }
    }

    private List<Pair<Citizen, Citizen>> groupCitizensByPairs(List<Citizen> singleCitizens) {
        List<Pair<Citizen, Citizen>> pairs = new ArrayList<>();
        for (int i = 0; i <= singleCitizens.size() - 2; i ++) {
            pairs.add(Pair.of(singleCitizens.get(i), singleCitizens.get(i+1)));
        }
        return pairs;
    }

    // TODO implement filtering by health
    public static List<Citizen> cutCitizens(List<Citizen> citizens) {
        return citizens.subList(0, citizens.size() / 2 * 2);
    }
}

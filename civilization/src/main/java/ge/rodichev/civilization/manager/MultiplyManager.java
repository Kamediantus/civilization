package ge.rodichev.civilization.manager;

import java.util.*;
import java.util.logging.*;

import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.consts.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.*;

@Getter
@Setter
public class MultiplyManager extends Manager {
    private Logger logger;

    @Autowired
    HousingManager housingManager;

    public MultiplyManager() {
        this.logger = Logger.getLogger("MultiplyManager");
    }

    public MultiplyManager(Citizens citizens) {
        this.citizens = citizens;
        this.logger = Logger.getLogger("MultiplyManager");
    }

    @Override
    public void tick() {
        Citizens citizensAbleToMultiply = new Citizens();
        this.citizens.stream()
                .filter(Citizen::ableToMultiply)
                .takeWhile(citizen -> citizensAbleToMultiply.size() / 2 < housingManager.getHousingCapacity() - citizens.size()) // TODO add test on takeWhile. Probably should compare with capacity -1
                .forEach(citizensAbleToMultiply::add);

        multiplyCitizens(cutCitizens(citizensAbleToMultiply));
    }

    public void multiplyCitizens(Citizens citizens) {
        if (!citizens.isEmpty()) {
            List<Pair<Citizen, Citizen>> pairs = groupCitizensByPairs(citizens);
            pairs.forEach(pair -> {
                this.citizens.add(new Citizen(Age.BABY, Health.NORM));
                pair.getFirst().setTickWithoutMultiply(0);
                pair.getSecond().setTickWithoutMultiply(0);
            });
        }
    }

    private List<Pair<Citizen, Citizen>> groupCitizensByPairs(Citizens singleCitizens) {
        List<Pair<Citizen, Citizen>> pairs = new ArrayList<>();
        for (int i = 0; i <= singleCitizens.size() - 2; i += 2) {
            pairs.add(Pair.of(singleCitizens.get(i), singleCitizens.get(i+1)));
        }
        return pairs;
    }

    public static Citizens cutCitizens(Citizens citizens) {
        return new Citizens(citizens.subList(0, citizens.size() / 2 * 2));
    }
}

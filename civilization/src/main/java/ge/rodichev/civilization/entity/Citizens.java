package ge.rodichev.civilization.entity;

import java.util.*;
import java.util.stream.*;

import ge.rodichev.civilization.entity.consts.*;

public class Citizens extends ArrayList<Citizen> {

    public Citizens() {
        super();
    }

    public Citizens(List<Citizen> citizensList) {
        this.addAll(citizensList);
    }

    public Citizens filterByAge(Age age) {
        return new Citizens(this.stream().filter(citizen -> citizen.getAge() == age).collect(Collectors.toList()));
    }

    public Citizens filterByHealth(Health health) {
        return new Citizens(this.stream().filter(citizen -> citizen.getHealth() == health).collect(Collectors.toList()));
    }

    public void getFullStats() {
        String.format("All citizens: %d\n" +
                "", this.size());
    }

    public String getAgeStats() {
        StringBuilder builder = new StringBuilder();
        for (Age value : Age.values()) {
            builder.append(String.format("Age status: %s. Citizens count: %d\n", value.toString(), this.filterByAge(value).size()));
        }
        return builder.toString();
    }

    public String getHealthStats() {
        StringBuilder builder = new StringBuilder();
        for (Health value : Health.values()) {
            builder.append(String.format("Health status: %s. Citizens count: %d\n", value.toString(), this.filterByHealth(value).size()));
        }
        return builder.toString();
    }
}

package ge.rodichev.civilization.config;

import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.building.factory.*;
import ge.rodichev.civilization.manager.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("ge.rodichev.civilization.manager")
public class ManagerConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public MultiplyManager multiplyManager() {
        return new MultiplyManager();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public HousingManager housingManager() {
        return new HousingManager();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Citizens citizens() {
        return new Citizens();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Factories factories() {
        return new Factories();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ResourceManager resourceManager() {
        return new ResourceManager();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public FactoryManager factoryManager() {
        return new FactoryManager();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CitizensManager citizensManager() {
        return new CitizensManager();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CivilizationManager civilizationManager() {
        return new CivilizationManager();
    }
}

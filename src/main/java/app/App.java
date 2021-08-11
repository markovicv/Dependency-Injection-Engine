package app;

import di.DependencyInjectionEngine;
import di.DependencyInjectionStarter;
import model.Country;


public class App {

    public static void main(String[] args){


        DependencyInjectionStarter dependencyInjectionStarter = new DependencyInjectionStarter();
        dependencyInjectionStarter.scan();


        Country country = (Country) dependencyInjectionStarter.inject(Country.class);
        country.capitalCity.greetings();
        country.riverTerritory.waterTerritoryName();
        country.seaTerritory.waterTerritoryName();





    }

}

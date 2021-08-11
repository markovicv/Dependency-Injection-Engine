package app;

import di.DependencyInjectionEngine;
import di.DependencyInjectionStarter;
import di.DependencySupplier;
import model.City;
import model.Country;

import java.util.HashMap;

public class App {

    public static void main(String[] args){


        DependencyInjectionStarter dependencyInjectionStarter = new DependencyInjectionStarter();


        Country country = (Country) dependencyInjectionStarter.inject(Country.class);
        country.capitalCity.greetings();
        country.riverTerritory.waterTerritoryName();
        country.seaTerritory.waterTerritoryName();





    }

}

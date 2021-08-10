package di;

import java.util.HashMap;

public class DependencyInjectionStarter {

    private  DependencyInjectionEngine dependencyInjectionEngine;

    public DependencyInjectionStarter(){

        DependencySupplier dependencySupplier = new DependencySupplier(new HashMap<>());
        dependencySupplier.supplyDependencies();
        dependencyInjectionEngine = new DependencyInjectionEngine(dependencySupplier);
    }

    public  Object inject(Class cls){
        return dependencyInjectionEngine.runEngine(cls);
    }
}

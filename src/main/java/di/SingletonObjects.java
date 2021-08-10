package di;

import java.util.HashMap;
import java.util.Map;

public class SingletonObjects {

    private Map<Class,Object> singletons;
    private static SingletonObjects instance;

    private SingletonObjects(){
        this.singletons = new HashMap();
    }

    public static SingletonObjects getInstance(){
        if(instance == null){
            instance =new SingletonObjects();
        }
        return instance;
    }

    public Object getSingletonObject(Class key){
        return singletons.get(key);
    }
    public void putSingletonObject(Class key,Object object){
        singletons.put(key,object);
    }
    public boolean existSingletonObjectFor(Class key){
        return singletons.containsKey(key);
    }

}

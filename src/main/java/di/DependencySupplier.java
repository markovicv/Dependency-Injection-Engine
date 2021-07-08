package di;

import annotations.Qualifier;
import errors.DependencyExistException;
import errors.QualifierException;

import java.util.HashMap;
import java.util.Map;

/*
    What it does?
 */
public class DependencySupplier {

    private Map<String,Class> classesForQualifier;


    public DependencySupplier(Map<String,Class> classesForQualifier){
        this.classesForQualifier = classesForQualifier;
    }



    public Class getDependencyForClass(String key){
        return classesForQualifier.get(key);
    }

    public void addDependencyForClass(Class impl){

        if(impl.isAnnotationPresent(Qualifier.class)){
            Qualifier qualifierClass = (Qualifier) impl.getAnnotation(Qualifier.class);
            String key = qualifierClass.value();

            if(!classesForQualifier.containsKey(key)){
                classesForQualifier.put(key,impl);
                return;
            }
            throw new DependencyExistException("Dependency for given key already exists");
        }
        throw new QualifierException("Qualifier not present");

    }
}

package di;

import annotations.*;
import errors.AnnotationDoesntExistException;
import errors.ReflectionError;

import java.lang.reflect.Field;
import java.util.Optional;

public class DependencyInjectionEngine {

    private DependencySupplier dependencySupplier;

    public DependencyInjectionEngine(DependencySupplier dependencySupplier) {
        this.dependencySupplier = dependencySupplier;
    }


    public Object runEngine(Class cls) {
        Object createdObject = createObjectFromClass(cls).orElseThrow(() -> new AnnotationDoesntExistException("Annotation doesnt Exist"));
        createFields(cls, createdObject);

        return createdObject;

    }

    private void createFields(Class cls, Object instance) {

        for (Field field : cls.getFields()) {

            field.setAccessible(true);
            boolean isFieldAutowiredAnnotated = field.isAnnotationPresent(Autowired.class);

            if (isFieldAutowiredAnnotated) {
                Class classTypeToInjectObject = objectClassToInject(field);
                Object objectToInject = runEngine(classTypeToInjectObject);

                try {
                    field.set(instance, objectToInject);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            field.setAccessible(false);
        }
    }

    private Class objectClassToInject(Field field) {
        if (field.isAnnotationPresent(Qualifier.class))
            return getInterfaceImpl(field);
        return field.getType();
    }

    private Class getInterfaceImpl(Field field) {
        Qualifier qualifier = (Qualifier) field.getAnnotation(Qualifier.class);
        String key = qualifier.value();
        return dependencySupplier.getDependencyForClass(key);
    }


    private Optional<Object> createObjectFromClass(Class cls) {

        try {

            if ( cls.isAnnotationPresent(Component.class)) {
                Optional.of(createObject(cls));
            }
            if (cls.isAnnotationPresent(Service.class)) {
                return Optional.of(createSingletonObject(cls));
            }

            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReflectionError("Error while creating objects");
        }


    }

    private Object createSingletonObject(Class cls) throws Exception {
        if (SingletonObjects.getInstance().existSingletonObjectFor(cls))
            return SingletonObjects.getInstance().getSingletonObject(cls);

        Object singletonInstance = cls.getConstructor().newInstance();
        SingletonObjects.getInstance().putSingletonObject(cls, singletonInstance);

        return singletonInstance;
    }

    private Object createObject(Class cls) throws Exception {
        return cls.getConstructor().newInstance();
    }


}

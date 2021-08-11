## Dependency-Injection-Engine

Small dependency injection framework for injecting objects. Allows for loose coupling of components.

@Component annotation is used to tell the framework that instantiation of the class will be handled by the framework.

```java
@Component
public class MyClass{

}
```

@Service is like @Component annotation, expect it creates only a single object and stores it.
```java
@Service
public class MyClass{

}
```
@Autowierd annotation is used for field injection.

```java
public class MyClass{

  @Autowired
  public Object myObject;
}
```

@Qualifier annotation is used when we have multiple implementations of one interface. It needs to tell the framework which implementation to use.

```java
public interface MyInterface{

}

@Qualifier(value="impl1")
@Service
public class MyInterfaceImplementation1{

}

@Qualifier(value="impl2")
public class MyInterfaceImplementation2{

}

@Service
public class MyClass{

  @Autowired
  @Qualifier(value="impl1")
  public MyInterface myInterfaceImpl1;
  
  @Autowired
  @Qualifier(value="impl2")
  public MyInterface myInterfaceImpl2;
  

}
```

Calling the dependencyInjectionStarter.scan() method will scan the entire project and provide all the dependencies.
```java
DependencyInjectionStarter dependencyInjectionStarter = new DependencyInjectionStarter();
dependencyInjectionStarter.scan();
```
Final thing to do is to call the dependencyInjectionStarter.inject() on the starting class, and the framework will starting injecting everything annotated

```java
MyClass object = dependencyInjectionStarter.inject(MyClass.class);

```








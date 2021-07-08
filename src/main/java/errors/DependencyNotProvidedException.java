package errors;

public class DependencyNotProvidedException extends RuntimeException{

    public DependencyNotProvidedException(String msg){
        super(msg);
    }


}

package errors;

public class DependencyExistException extends RuntimeException{

    public DependencyExistException(String msg){
        super(msg);
    }

}

package errors;

public class AnnotationDoesntExistException extends RuntimeException{

    public AnnotationDoesntExistException(String msg){
        super(msg);
    }
}

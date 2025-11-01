package ecommerce.ecombackend.exception;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String m){
        super(m);
    }
}

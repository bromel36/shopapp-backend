package vn.ptithcm.shopapp.error;



public class UserNoLongerException extends RuntimeException{
    String message;
    public UserNoLongerException(String message) {super(message);}
}

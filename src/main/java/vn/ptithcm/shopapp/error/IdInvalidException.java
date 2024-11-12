package vn.ptithcm.shopapp.error;

public class IdInvalidException extends RuntimeException{
    public IdInvalidException(String msg){
        super(msg);
    }
}

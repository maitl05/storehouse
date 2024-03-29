package maitl.model.service;

public class NotEnoughProductAvailableException extends Exception{
    public NotEnoughProductAvailableException(){
        super("not enough products available in storehouse");
    }
    public NotEnoughProductAvailableException(Long productID){
        super("not enough products available for product number "+productID+" in storehouse");
    }
}

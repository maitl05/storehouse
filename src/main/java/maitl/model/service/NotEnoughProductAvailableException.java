package maitl.model.service;

public class NotEnoughProductAvailableException extends Exception{
    public NotEnoughProductAvailableException(){
        super("not enough products available in storehouse");
    }
}

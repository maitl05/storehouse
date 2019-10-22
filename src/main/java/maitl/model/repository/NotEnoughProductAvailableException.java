package maitl.model.repository;

public class NotEnoughProductAvailableException extends Exception{
    public NotEnoughProductAvailableException(){
        super("not enough products available in storehouse");
    }
}

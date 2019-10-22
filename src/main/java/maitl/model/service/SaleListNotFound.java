package maitl.model.service;

public class SaleListNotFound extends Exception {
    public SaleListNotFound(){
        super("requested sales list doesn't exist");
    }
    public SaleListNotFound(long productID){
        super("sales list containing product with id "+productID+" doesn't exist");
    }
}

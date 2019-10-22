package maitl.model.repository;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(){
        super("this product doesn't exist");
    }
    public ProductNotFoundException(long productID){
        super("product with the id "+productID+" doesn't exist");
    }
}

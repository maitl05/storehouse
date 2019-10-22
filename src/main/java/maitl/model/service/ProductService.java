package maitl.model.service;


import maitl.model.entity.Product;
import maitl.model.repository.ProductDataAccess;
import org.json.JSONException;

import java.sql.SQLException;

public class ProductService {
    private static ProductService productService = new ProductService();

    private ProductService(){

    }

    public static ProductService getProductServiceInstance(){
        return productService;
    }

    public void saveProduct(Product product) throws SQLException {
        try (ProductDataAccess productDataAccess = new ProductDataAccess()){
            productDataAccess.insertProduct(product);
        }

    }

    public void changeProductPrice(Product product) throws SQLException, ProductNotFoundException{
        try(ProductDataAccess productDataAccess = new ProductDataAccess()){
            if (productDataAccess.updateProduct(product) == 0){
                throw new ProductNotFoundException(product.getProductID());
            }
        }
    }

    public void changeProductQuantity(Product product) throws SQLException, ProductNotFoundException{
        try(ProductDataAccess productDataAccess = new ProductDataAccess()){
            if (productDataAccess.updateProduct(product) == 0){
                throw new ProductNotFoundException(product.getProductID());
            }
        }
    }

    public void deleteProduct(long productID) throws SQLException, ProductNotFoundException {
        try (ProductDataAccess productDataAccess = new ProductDataAccess()) {
            if (productDataAccess.deleteProduct(productID) == 0){
                throw new ProductNotFoundException(productID);
            }
        }
    }
    public String showAllProduct( ) throws SQLException {
        try (ProductDataAccess productDataAccess = new ProductDataAccess()) {
            return productDataAccess.selectAllProduct();

        }
    }
    public String showOneProduct(long productID ) throws SQLException, JSONException, ProductNotFoundException {
        try (ProductDataAccess productDataAccess = new ProductDataAccess()) {
            String res = productDataAccess.selectOneProduct(productID);
            if (res == null){
                throw new ProductNotFoundException(productID);
            } else {
                return res;
            }
        }
    }





}

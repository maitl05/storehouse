package maitl.model.service;


import maitl.model.entity.Product;
import maitl.model.repository.ProductDataAccess;

public class ProductService {
    private static ProductService productService = new ProductService();

    private ProductService(){

    }

    public static ProductService getProductServiceInstance(){
        return productService;
    }

    public void saveProduct(Product product) throws Exception{
        try (ProductDataAccess productDataAccess = new ProductDataAccess()){
            productDataAccess.insertProduct(product);
        }

    }

    public void changeProductPrice(Product product) throws Exception{
        try(ProductDataAccess productDataAccess = new ProductDataAccess()){
            productDataAccess.updateProduct(product);

        }
    }

    public void changeProductQuantity(Product product) throws Exception{
        try(ProductDataAccess productDataAccess = new ProductDataAccess()){
            productDataAccess.updateProduct(product);

        }
    }

    public void deleteProduct(long productID) throws Exception {
        try (ProductDataAccess productDataAccess = new ProductDataAccess()) {
            productDataAccess.deleteProduct(productID);
        }
    }
    public String showAllProduct( ) throws Exception {
        try (ProductDataAccess productDataAccess = new ProductDataAccess()) {
            return productDataAccess.selectAllProduct();

        }
    }
    public String showOneProduct(long productID ) throws Exception {
        try (ProductDataAccess productDataAccess = new ProductDataAccess()) {
            return productDataAccess.selectOneProduct(productID);
        }
    }





}

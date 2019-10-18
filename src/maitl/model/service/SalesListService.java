package maitl.model.service;

import maitl.model.entity.Product;
import maitl.model.entity.SalesList;
import maitl.model.repository.ProductDataAccess;
import maitl.model.repository.SalesListDataAccess;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

class notEnoughProductAvailable extends Exception{
    public notEnoughProductAvailable(){
        super("not enough products available in storehouse");
    }
}

public class SalesListService {
    private static SalesListService salesListService= new SalesListService();
    private SalesListService(){}

    public static SalesListService getSalesListServiceInstance(){
        return salesListService;
    }

    public void saveSale(SalesList salesList) throws Exception{
        try(SalesListDataAccess salesListDataAccess = new SalesListDataAccess())
        {
            String stringProduct = ProductService.getProductServiceInstance().showOneProduct(salesList.getProductID());
            JSONParser parser = new JSONParser();
            JSONObject jsonProduct = (JSONObject) parser.parse(stringProduct);
            long productQuantity = Long.parseLong(jsonProduct.get("ProductQuantityAvailableInStore").toString());

            if(productQuantity < salesList.getSalesQuantity()){
                throw new notEnoughProductAvailable();
            }
            Product product = new Product().setProductID(salesList.getProductID()).setProductQuantityAvailableInStore(productQuantity - salesList.getSalesQuantity());
            ProductDataAccess productDataAccess = new ProductDataAccess();
            productDataAccess.updateProduct(product);
            salesListDataAccess.insertSalesList(salesList);
        }
    }

    public String showAllSale()throws Exception{
        try(SalesListDataAccess salesListDataAccess = new SalesListDataAccess()){
            return salesListDataAccess.selectAllSalesList();
        }
    }

    public  String showOneProductSale(long productID) throws Exception{
        try(SalesListDataAccess salesListDataAccess = new SalesListDataAccess()){
            return salesListDataAccess.selectOneProductFromSalesList(productID);
        }
    }

}

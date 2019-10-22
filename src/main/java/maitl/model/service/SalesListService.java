package maitl.model.service;

import maitl.model.entity.Product;
import maitl.model.entity.SalesList;
import maitl.model.repository.ProductDataAccess;
import maitl.model.repository.SalesListDataAccess;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class SalesListService {
    private static SalesListService salesListService= new SalesListService();
    private SalesListService(){}

    public static SalesListService getSalesListServiceInstance(){
        return salesListService;
    }

    public void saveSale(SalesList salesList) throws SQLException, JSONException, ProductNotFoundException, NotEnoughProductAvailableException {
        try(SalesListDataAccess salesListDataAccess = new SalesListDataAccess(); ProductDataAccess productDataAccess = new ProductDataAccess())
        {
            String stringProduct = ProductService.getProductServiceInstance().showOneProduct(salesList.getProductID());
            JSONObject jsonProduct = new JSONObject(stringProduct);
            long productQuantity = Long.parseLong(jsonProduct.get("ProductQuantityAvailableInStore").toString());

            if(productQuantity < salesList.getSalesQuantity()){
                throw new NotEnoughProductAvailableException();
            }
            Product product = new Product().setProductID(salesList.getProductID()).setProductQuantityAvailableInStore(productQuantity - salesList.getSalesQuantity());
            productDataAccess.updateProduct(product);
            salesListDataAccess.insertSalesList(salesList);
        }
    }

    public String showAllSale()throws SQLException, JSONException{
        try(SalesListDataAccess salesListDataAccess = new SalesListDataAccess()){
            return salesListDataAccess.selectAllSalesList();
        }
    }

    public  String showOneProductSale(long productID) throws SQLException, JSONException {
        try(SalesListDataAccess salesListDataAccess = new SalesListDataAccess()){
            return salesListDataAccess.selectOneProductFromSalesList(productID);
        }
    }

}

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
            JSONObject jsonProduct = new JSONObject(ProductService.getProductServiceInstance().showOneProduct(salesList.getProductID()));
            long productQuantity = Long.parseLong(jsonProduct.get("ProductQuantityAvailableInStore").toString());

            if(productQuantity < salesList.getSalesQuantity()){
                throw new NotEnoughProductAvailableException();
            } else {
                productDataAccess.updateProduct(new Product().setProductID(salesList.getProductID()).setProductQuantityAvailableInStore(productQuantity - salesList.getSalesQuantity()));
                salesListDataAccess.insertSalesList(salesList);
            }
        }
    }

    public String showAllSale()throws SQLException, JSONException, SaleListNotFound{
        try(SalesListDataAccess salesListDataAccess = new SalesListDataAccess()){
            String res = salesListDataAccess.selectAllSalesList();
            if (res == null){
                throw new SaleListNotFound();
            } else {
                return res;
            }
        }
    }

    public  String showOneProductSale(long productID) throws SQLException, JSONException, SaleListNotFound{
        try(SalesListDataAccess salesListDataAccess = new SalesListDataAccess()){
            String res = salesListDataAccess.selectOneProductFromSalesList(productID);
            if (res == null){
                throw new SaleListNotFound(productID);
            } else {
                return res;
            }
        }
    }

}

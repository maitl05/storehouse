package maitl.model.service;

import maitl.model.entity.SalesList;
import maitl.model.repository.SalesListDataAccess;
import org.codehaus.jettison.json.JSONString;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
            Object object = jsonProduct.get("ProductQuantityAvailableInStore");

        }


    }







}

package maitl.controller.rest;

import maitl.model.entity.Product;
import maitl.model.entity.SalesList;
import maitl.model.repository.NotEnoughProductAvailableException;
import maitl.model.repository.ProductNotFoundException;
import maitl.model.service.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

//TODO add product not found exception to everything
@Path("/api")
public class Controller {
    final static int INDENT_SIZE = 4;
    @GET
    @Path("prod-add")
    @Produces({MediaType.TEXT_HTML})
    public Response productAdd(@QueryParam("id") String productId, @QueryParam("name") String productName, @QueryParam("category") String productCategory, @QueryParam("quantity") String productQuantity, @QueryParam("price") String productPrice) throws Exception {
        try {
            ProductService.getProductServiceInstance().saveProduct(new Product()
                    .setProductID(Long.parseLong(productId))
                    .setProductName(productName)
                    .setProductCategory(productCategory)
                    .setProductQuantityAvailableInStore(Long.parseLong(productQuantity))
                    .setPrice(Long.parseLong(productPrice))
            );
            return Response.ok().build();
        } catch (java.lang.NumberFormatException e) {
            return Response.notAcceptable(new ArrayList<>()).build();
        }
    }
    @GET
    @Path("prod-remove")
    @Produces({MediaType.TEXT_HTML})
    public Response productRemove(@QueryParam("prod-id") String productId) throws Exception {
        try {
            ProductService.getProductServiceInstance().deleteProduct(Long.parseLong(productId));
            return Response.ok().build();
        } catch (ProductNotFoundException | java.lang.NumberFormatException e) {
            return Response.notAcceptable(new ArrayList<>()).build();
        }
    }
    @GET
    @Path("prod-search")
    @Produces({MediaType.APPLICATION_JSON})
    public String productSearch() throws Exception {
        return (new JSONArray(ProductService.getProductServiceInstance().showAllProduct())).toString(INDENT_SIZE);
    }
    @GET
    @Path("prod-search-by-id")
    @Produces({MediaType.APPLICATION_JSON})
    public String productSearch(@QueryParam("prod-id") String productId) throws Exception {
        try {
            return (new JSONObject(ProductService.getProductServiceInstance().showOneProduct(Long.parseLong(productId)))).toString(INDENT_SIZE);
        } catch (ProductNotFoundException | java.lang.NumberFormatException e) {
            return (new JSONObject()).toString();
//            return Response.notAcceptable(new ArrayList<>()).build();
        }
    }
    @GET
    @Path("sale-add")
    @Produces({MediaType.TEXT_HTML})
    public Response saleAdd(@QueryParam("prod-id") String productId, @QueryParam("quantity") String saleQuantity) throws Exception {
        try {
            SalesListService.getSalesListServiceInstance().saveSale(new SalesList()
                    .setProductID(Long.parseLong(productId))
                    .setSalesQuantity(Long.parseLong(saleQuantity))
            );
            return Response.ok().build();
        } catch (NotEnoughProductAvailableException | java.lang.NumberFormatException e) {
            return Response.notAcceptable(new ArrayList<>()).build();
        }
    }
    @GET
    @Path("sale-search")
    @Produces({MediaType.APPLICATION_JSON})
    public String saleSearch() throws Exception{
        return new JSONArray(SalesListService.getSalesListServiceInstance().showAllSale()).toString(INDENT_SIZE);
    }
    @GET
    @Path("sale-search-by-product")
    @Produces({MediaType.APPLICATION_JSON})
    public String saleSearch(@QueryParam("prod-id") String productId) throws Exception {
        try {
            return (new JSONArray(SalesListService.getSalesListServiceInstance().showOneProductSale(Long.parseLong(productId)))).toString(INDENT_SIZE);
        } catch (ProductNotFoundException | java.lang.NumberFormatException e) {
            return (new JSONObject()).toString();
//            return Response.notAcceptable(new ArrayList<>()).build();
        }
    }
}
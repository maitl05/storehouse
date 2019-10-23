package maitl.controller.rest;

import maitl.model.entity.Product;
import maitl.model.entity.SalesList;
import maitl.model.service.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/api")
public class Controller {
    final static int INDENT_SIZE = 4;
    @GET
    @Path("prod-add")
    @Produces({MediaType.TEXT_HTML})
    public Response productAdd(@QueryParam("id") String productId, @QueryParam("name") String productName, @QueryParam("category") String productCategory, @QueryParam("quantity") String productQuantity, @QueryParam("price") String productPrice){
        try {
            ProductService.getProductServiceInstance().saveProduct(new Product()
                    .setProductID(Long.parseLong(productId))
                    .setProductName(productName)
                    .setProductCategory(productCategory)
                    .setProductQuantityAvailableInStore(Long.parseLong(productQuantity))
                    .setPrice(Long.parseLong(productPrice))
            );
            return Response.ok().entity("200").build();
        } catch (java.lang.NumberFormatException e) {
            return Response.status(422).build();
        } catch (SQLException e){
            return Response.serverError().build();
        }
    }
    @GET
    @Path("prod-remove")
    @Produces({MediaType.TEXT_HTML})
    public Response productRemove(@QueryParam("prod-id") String productId){
        try {
            ProductService.getProductServiceInstance().deleteProduct(Long.parseLong(productId));
            return Response.ok().entity("200").build();
        } catch (java.lang.NumberFormatException e) {
            return Response.status(422).build();
        } catch (ProductNotFoundException e){
            return Response.status(404).build();
        } catch (SQLException e){
            return Response.serverError().build();
        }
    }
    @GET
    @Path("prod-search")
    @Produces({MediaType.APPLICATION_JSON})
    public Response productSearch() {
        try {
            return Response.ok().entity((new JSONArray(ProductService.getProductServiceInstance().showAllProduct())).toString(INDENT_SIZE)).build();
        } catch (ProductNotFoundException e){
            return Response.status(404).build();
        } catch (SQLException | JSONException e){
            return Response.serverError().build();
        }
    }
    @GET
    @Path("prod-search-by-id")
    @Produces({MediaType.APPLICATION_JSON})
    public Response productSearch(@QueryParam("prod-id") String productId){
        try {
            return Response.ok().entity((new JSONObject(ProductService.getProductServiceInstance().showOneProduct(Long.parseLong(productId)))).toString(INDENT_SIZE)).build();
        } catch (java.lang.NumberFormatException e) {
            return Response.status(422).build();
        } catch (ProductNotFoundException e){
            return Response.status(404).build();
        } catch (SQLException | JSONException e){
            return Response.serverError().build();
        }
    }
    @GET
    @Path("sale-add")
    @Produces({MediaType.TEXT_HTML})
    public Response saleAdd(@QueryParam("prod-id") String productId, @QueryParam("quantity") String saleQuantity){
        try {
            SalesListService.getSalesListServiceInstance().saveSale(new SalesList()
                    .setProductID(Long.parseLong(productId))
                    .setSalesQuantity(Long.parseLong(saleQuantity))
            );
            return Response.ok().entity("200").build();
        } catch (NotEnoughProductAvailableException | java.lang.NumberFormatException e) {
            return Response.status(422).build();
        }catch (ProductNotFoundException e){
            return Response.status(404).build();
        } catch (SQLException | JSONException e){
            return Response.serverError().build();
        }
    }
    @GET
    @Path("sale-search")
    @Produces({MediaType.APPLICATION_JSON})
    public Response saleSearch(){
        try {
            return Response.ok().entity(new JSONArray(SalesListService.getSalesListServiceInstance().showAllSale()).toString(INDENT_SIZE)).build();
        }catch (SaleListNotFound e){
            return Response.status(404).build();
        } catch (SQLException | JSONException e){
            return Response.serverError().build();
        }
    }
    @GET
    @Path("sale-search-by-product")
    @Produces({MediaType.APPLICATION_JSON})
    public Response saleSearch(@QueryParam("prod-id") String productId){
        try {
            return Response.ok().entity((new JSONArray(SalesListService.getSalesListServiceInstance().showOneProductSale(Long.parseLong(productId)))).toString(INDENT_SIZE)).build();
        } catch (java.lang.NumberFormatException e) {
            return Response.status(422).build();
        }catch (SaleListNotFound e){
            return Response.status(404).build();
        } catch (SQLException | JSONException e){
            return Response.serverError().build();
        }
    }
}
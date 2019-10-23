package maitl.controller.rest;

import maitl.model.entity.Product;
import maitl.model.entity.SalesList;
import maitl.model.service.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;


@Path("/api")
public class Controller {
    final static int INDENT_SIZE = 4;
    private final static class HTTPResponses{
        private static Response unprocessableEntity(){
            return Response.status(422).entity(
                    "422: Unprocessable Entity (invalid data entered)"
            ).build();
        }
        private static Response SQLException(){
            return Response.serverError().entity(
                    "SQLException: Something is wrong with the database"
            ).build();
        }
        private static Response ok(){
            return Response.ok().entity(
                    "200: Everything is ok"
            ).build();
        }
        private static Response ok(Object response){
            return Response.ok().entity(response).build();
        }
        private static Response notFound(){
            return Response.status(404).entity(
                    "404: Not Found"
            ).build();
        }
        private static Response JSONException(){
            return Response.serverError().entity(
                    "JSONException: Something is wrong with server or database"
            ).build();
        }
        private static Response badRequest(){
            return Response.status(400).entity(
                    "400: Bad Request"
            ).build();
        }
        private static Response handleException(Exception e){
            if (e instanceof java.lang.NumberFormatException){
                return unprocessableEntity();
            } else if (e instanceof SQLException){
                return SQLException();
            } else if (e instanceof JSONException){
                return JSONException();
            } else if (e instanceof ProductNotFoundException){
                return notFound();
            } else if (e instanceof SaleListNotFound){
                return notFound();
            } else if (e instanceof NotEnoughProductAvailableException){
                return badRequest();
            } else {
                return null;
            }
        }
    }
    @POST
    @Path("prod-add")
    @Produces({MediaType.TEXT_HTML})
    public Response productAdd(@FormParam("id") String productId,
                               @FormParam("name") String productName,
                               @FormParam("category") String productCategory,
                               @FormParam("quantity") String productQuantity,
                               @FormParam("price") String productPrice){
        try {
            ProductService.getProductServiceInstance().saveProduct(new Product()
                    .setProductID(Long.parseLong(productId))
                    .setProductName(productName)
                    .setProductCategory(productCategory)
                    .setProductQuantityAvailableInStore(Long.parseLong(productQuantity))
                    .setPrice(Long.parseLong(productPrice))
            );
            return HTTPResponses.ok();
        } catch (Exception e) {
            return HTTPResponses.handleException(e);
        }
    }
    @POST
    @Path("prod-remove")
    @Produces({MediaType.TEXT_HTML})
    public Response productRemove(@FormParam("prod-id") String productId){
        try {
            ProductService.getProductServiceInstance().deleteProduct(Long.parseLong(productId));
            return HTTPResponses.ok();
        } catch (Exception e) {
            return HTTPResponses.handleException(e);
        }
    }
    @GET
    @Path("prod-search")
    @Produces({MediaType.APPLICATION_JSON})
    public Response productSearch() {
        try {
            return HTTPResponses.ok(
                    (new JSONArray(
                            ProductService.getProductServiceInstance()
                                    .showAllProduct())
                    ).toString(INDENT_SIZE));
        } catch (Exception e){
            return HTTPResponses.handleException(e);
        }
    }
    @GET
    @Path("prod-search-by-id")
    @Produces({MediaType.APPLICATION_JSON})
    public Response productSearch(@QueryParam("prod-id") String productId){
        try {
            return HTTPResponses.ok(
                    (new JSONObject(
                            ProductService.getProductServiceInstance()
                                    .showOneProduct(Long.parseLong(productId)))
                    ).toString(INDENT_SIZE));
        } catch (Exception e) {
            return HTTPResponses.handleException(e);
        }
    }
    @POST
    @Path("sale-add")
    @Produces({MediaType.TEXT_HTML})
    public Response saleAdd(@FormParam("prod-id") String productId,
                            @FormParam("quantity") String saleQuantity){
        try {
            SalesListService.getSalesListServiceInstance().saveSale(new SalesList()
                    .setProductID(Long.parseLong(productId))
                    .setSalesQuantity(Long.parseLong(saleQuantity))
            );
            return HTTPResponses.ok();
        } catch (Exception e) {
            return HTTPResponses.handleException(e);
        }
    }
    @GET
    @Path("sale-search")
    @Produces({MediaType.APPLICATION_JSON})
    public Response saleSearch(){
        try {
            return HTTPResponses.ok(
                    new JSONArray(
                            SalesListService.getSalesListServiceInstance()
                                    .showAllSale()
                    ).toString(INDENT_SIZE)
            );
        }catch (Exception e) {
            return HTTPResponses.handleException(e);
        }
    }
    @GET
    @Path("sale-search-by-product")
    @Produces({MediaType.APPLICATION_JSON})
    public Response saleSearch(@QueryParam("prod-id") String productId){
        try {
            return HTTPResponses.ok(
                    (new JSONArray(
                            SalesListService.getSalesListServiceInstance()
                                    .showOneProductSale(Long.parseLong(productId)))
                    ).toString(INDENT_SIZE));
        } catch (Exception e) {
            return HTTPResponses.handleException(e);
        }
    }
}
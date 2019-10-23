package maitl.controller.rest;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class Index{
    final static String HTML_INDEX_PAGE = "<!doctypehtml><html lang=en><meta charset=UTF-8><title>Document</title><h1>The storehouse managment page</h1><br><form action=http://localhost:8080/storehouse/api/prod-add method=post><input placeholder=ID name=id> <input placeholder=Name name=name><br><input placeholder=Category name=category> <input placeholder=Quantity name=quantity> <input placeholder=Price name=price><br><input type=submit value=\"Add product\"></form><br><br><form action=http://localhost:8080/storehouse/api/sale-add method=post><input placeholder=\"Product ID\"name=prod-id> <input placeholder=Quantity name=quantity><br><input type=submit value=\"Make a sale\"></form><br><br><form action=http://localhost:8080/storehouse/api/prod-remove method=post><input placeholder=\"Product ID\"name=prod-id> <input placeholder=\"Product ID\"type=submit value=\"Remove product\"></form><br><br><form action=http://localhost:8080/storehouse/api/prod-search-by-id><input placeholder=\"Product ID\"name=prod-id> <input type=submit value=\"Search product\"></form><br><br><form action=http://localhost:8080/storehouse/api/sale-search-by-product><input placeholder=\"Product ID\"name=prod-id> <input type=submit value=\"Search product in sale\"></form><br><br><table><tr><td><form action=http://localhost:8080/storehouse/api/prod-search><button>Show all products</button></form><td><form action=http://localhost:8080/storehouse/api/sale-search><button>Show all sales</button></form></table>";
    @GET
    @Produces({MediaType.TEXT_HTML})
    public String getMessage(){return HTML_INDEX_PAGE;}
}

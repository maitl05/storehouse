package maitl.controller.rest;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Path("/")
public class Index{
    @GET
    @Produces({MediaType.TEXT_HTML})
    public String getMessage(){return "200 ok";}
}

package maitl.controller.rest;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class RestApp extends ResourceConfig {
    public RestApp() {
        packages("maitl.controller.rest");
    }
}
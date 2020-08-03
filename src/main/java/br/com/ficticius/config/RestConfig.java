package br.com.ficticius.config;

import br.com.ficticius.services.VeiculoService;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class RestConfig extends ResourceConfig {
    public RestConfig() {
        packages("br.com.ficticius.rest");
        register(VeiculoService.class);
    }

}

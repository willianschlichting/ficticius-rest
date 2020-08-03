package br.com.ficticius.rest;

import br.com.ficticius.config.Json;
import br.com.ficticius.model.Veiculo;
import br.com.ficticius.repository.VeiculoRepository;
import br.com.ficticius.services.VeiculoService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("veiculo")
public class VeiculoRest {

    @Inject
    private VeiculoService veiculoService;

    @Inject
    private VeiculoRepository veiculoRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVeiculos(@QueryParam("placa") String placa,
                                @QueryParam("modelo") String modelo,
                                @QueryParam("marca") String marca) {
        return Response.ok(Json.toJson(veiculoRepository.findAll(placa, modelo, marca))).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{oid}")
    public Response getVeiculos(@PathParam("oid") String oid) {
        return Response.ok(Json.toJson(veiculoRepository.findByOid(oid))).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveVeiculo(String jsonVeiculo) {
        Veiculo veiculo = Json.fromJson(jsonVeiculo, Veiculo.class);
        if (veiculo.getOid() != null && veiculo.getOid().trim().length() > 0) {
            veiculo = veiculoService.update(veiculo);
        } else {
            veiculo = veiculoService.save(veiculo);
        }
        return Response.ok(Json.toJson(veiculo)).build();
    }

    @DELETE
    @Path("{oid}")
    public Response delete(@PathParam("oid") String oid) {
        Veiculo veiculo = veiculoRepository.findByOid(oid);
        if (veiculo != null) {
            veiculoService.delete(veiculo);
            return Response.ok().build();
        } else {
            return Response.status(404).build();
        }
    }
}

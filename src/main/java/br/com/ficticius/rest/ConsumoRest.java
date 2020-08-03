package br.com.ficticius.rest;

import br.com.ficticius.config.Json;
import br.com.ficticius.model.Veiculo;
import br.com.ficticius.repository.VeiculoRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

@Path("consumo")
public class ConsumoRest {

    /*@Inject
    private VeiculoRepository veiculoRepository;*/

    private Boolean valorValido(Integer valor) {
        if (valor != null && valor > 0) {
            return true;
        }
        return false;
    }

    public static Date montaData(int ano, int mes, int dia) {
        Calendar data = Calendar.getInstance();
        data.set(1, ano);
        data.set(2, mes - 1);
        data.set(5, dia);
        return data.getTime();
    }

    private List<Veiculo> addVeiculos() {
        List<Veiculo> veiculos = new ArrayList<Veiculo>();
        veiculos.add(montaVeiculo("aaa1234", "FENEME", "FENEME", "FENEME", montaData(1955, 5, 25), 12.6, 22.3));
        veiculos.add(montaVeiculo("aaa1235", "VW", "VW", "VW", montaData(2010, 5, 25), 4.1, 9.3));
        veiculos.add(montaVeiculo("aaa1236", "VOLVO", "VOLVO", "VOLVO", montaData(2013, 5, 25), 23.1, 32.6));
        return veiculos;
    }

    private Veiculo montaVeiculo(String placa, String nome, String marca, String modelo, Date fabricacao, double consumoCidade, double consumoRodovia) {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(placa);
        veiculo.setNome(nome);
        veiculo.setModelo(modelo);
        veiculo.setDataFabricacao(fabricacao);
        veiculo.setConsumoCidade(consumoCidade);
        veiculo.setConsumoRodovia(consumoRodovia);
        return veiculo;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response consumo(@QueryParam("precoGasolina") Double precoGasolina,
                            @QueryParam("kmCidade") Integer totalKmCidade,
                            @QueryParam("kmRodovia") Integer totalKmRodovia) {
        List<Veiculo> veiculos = addVeiculos();//veiculoRepository.findAll(null, null, null);

        if (precoGasolina == null || precoGasolina <= 0) {
            return Response.status(400).entity("Atenção, Informe o preço da Gasolina").build();
        }

        if (!valorValido(totalKmCidade) && valorValido(totalKmRodovia)) {
            return Response.status(400).entity("Atenção, Informe o Total de KM percorrido em cidade ou rodovia").build();
        }

        if (veiculos != null && !veiculos.isEmpty()) {
            for (Veiculo veiculo : veiculos) {
                if (veiculo.getDataFabricacao() != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(veiculo.getDataFabricacao());
                    veiculo.setAno(cal.get(1));
                }
                Double litrosCidade = 0d;
                Double litrosRodovia = 0d;
                if (valorValido(totalKmCidade)) {
                    litrosCidade = new Double(Math.round(totalKmCidade / veiculo.getConsumoCidade()));
                }
                if (valorValido(totalKmRodovia)) {
                    litrosRodovia = new Double(Math.round(totalKmRodovia / veiculo.getConsumoRodovia()));
                }

                veiculo.setCombustivelGasto(litrosCidade + litrosRodovia);
                veiculo.setValorGasto(new Double(Math.round(veiculo.getCombustivelGasto() * precoGasolina)));
            }
        }

        List<Veiculo> ordenado = veiculos.stream().sorted(Comparator.comparingDouble(Veiculo::getValorGasto).reversed()).collect(Collectors.toList());

        return Response.ok(Json.toJson(ordenado)).build();

    }
}

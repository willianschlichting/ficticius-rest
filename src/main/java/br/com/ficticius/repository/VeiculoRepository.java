package br.com.ficticius.repository;

import br.com.ficticius.config.Consulta;
import br.com.ficticius.model.Veiculo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class VeiculoRepository {

    @PersistenceContext
    private EntityManager em;

    private Boolean stringValida(String valor) {
        return valor != null && valor.trim().length() > 0;
    }

    public List<Veiculo> findAll(String placa, String modelo, String marca) {
        Consulta consulta = new Consulta(em);
        consulta.select("v").from("Veiculo v");
        if (stringValida(placa)) {
            consulta.where("v.placa = :placa").param("placa", placa);
        }
        if (stringValida(modelo)) {
            consulta.where("v.modelo = :modelo").param("modelo", modelo);
        }
        if (stringValida(marca)) {
            consulta.where("v.marca = :marca").param("marca", marca);
        }
        return consulta.executar();
     }

    public Veiculo findByOid(String oid) {
        Consulta consulta = new Consulta(em);
        consulta.select("v").from("Veiculo v").where("v.oid = :oid").param("oid", oid);
        return (Veiculo) consulta.primeiroRegistro();
    }
}

package br.com.ficticius.services;

import br.com.ficticius.config.Managed;
import br.com.ficticius.model.Veiculo;

import javax.ejb.*;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class VeiculoService extends Managed {

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Veiculo save(Veiculo veiculo){
        validar(veiculo);
        persist(veiculo);
        return veiculo;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Veiculo update(Veiculo veiculo) {
        validar(veiculo);
        veiculo = merge(veiculo);
        return veiculo;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private void validar(Veiculo veiculo) {

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(Veiculo veiculo) {
        this.remove(veiculo);
    }

}

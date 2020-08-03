package br.com.ficticius.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "veiculo")
public class Veiculo extends DefaultEntity {

    @Column(name = "placa", length = 8)
    private String placa;

    @Column(name = "nome", length = 120)
    private String nome;

    @Column(name = "marca", length = 120)
    private String marca;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "data_fabricacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFabricacao;

    @Column(name = "consumo_cidade")
    private Double consumoCidade;

    @Column(name = "consumo_rodovia")
    private Double consumoRodovia;

    @Transient
    private Double combustivelGasto;

    @Transient
    private Double valorGasto;

    @Transient
    private Integer ano;


    @PrePersist
    private void prePersist() {
        this.setOid(UUID.randomUUID().toString());
        setProperties();
    }

    @PreUpdate
    private void preUpdate() {
        setProperties();
    }

    private void setProperties() {
        this.setDataCriacao(new Date());
    }

}

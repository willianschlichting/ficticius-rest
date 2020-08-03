package br.com.ficticius.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
public class DefaultEntity {

    @Id
    @Column(name = "oid", length = 38)
    private String oid;

    @Column(
            name = "data_criacao",
            nullable = false,
            updatable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;


}

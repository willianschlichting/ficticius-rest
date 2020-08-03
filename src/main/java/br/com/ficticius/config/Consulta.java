package br.com.ficticius.config;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Consulta {

    private EntityManager entityManager;
    private String sql;
    private List<Object> parametros = new ArrayList();
    private Boolean where;

    public Consulta(EntityManager entityManager) {
        this.entityManager = entityManager;
        sql = "";
        where = false;
    }

    public Consulta select(String select) {
        sql = "Select " + select + " ";
        return this;
    }

    public Consulta from(String from) {
        sql += " from " + from + " ";
        return this;
    }

    public Consulta where(String where) {
        if (!this.where) {
            this.where = true;
            sql += " where " + where + " ";
        } else {
            sql += " and " + where + " ";
        }
        return this;
    }

    public List executar(){
        Query query = this.entityManager.createQuery(sql);
        setParamQuery(query);
        List retorno = query.getResultList();
        return retorno;
    }

    public Object primeiroRegistro(){
        Query query = this.entityManager.createQuery(sql);
        query.setMaxResults(1);
        setParamQuery(query);
        List<?> retorno = query.getResultList();
        return retorno.isEmpty() ? null : retorno.get(0);
    }



    private void setParamQuery(Query q) {
        if (this.parametros != null && !this.parametros.isEmpty()) {
            for(int i = 0; i < this.parametros.size(); ++i) {
                List<?> l = (List)this.parametros.get(i);
                if (l.size() == 2) {
                    q.setParameter((String)l.get(0), l.get(1));
                } else {
                    q.setParameter((String)l.get(0), (Date)l.get(1), (TemporalType)l.get(2));
                }
            }
        }

    }

    private List<Object> addParametro(String nome, Object valor) {
        List<Object> l = new ArrayList();
        l.add(nome);
        l.add(valor);
        return l;
    }

    public Consulta param(String nome, Object valor) {
        this.parametros.add(this.addParametro(nome, valor));
        return this;
    }

    public void param(String nome, Date valor) {
        List<Object> l = this.addParametro(nome, valor);
        l.add(TemporalType.DATE);
        this.parametros.add(l);
    }
}

package br.com.ficticius.config;

import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.hibernate.jpa.internal.EntityManagerImpl;

import javax.enterprise.inject.Vetoed;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Vetoed
public abstract class Managed {

    @PersistenceContext
    private EntityManager manager;

    public Managed() {
    }

    private EntityManager getManager() {
        return this.manager;
    }

    protected void persist(Object entity) {
        this.getManager().persist(entity);
    }

    protected <T> T merge(T entity) {
        return this.getManager().merge(entity);
    }

    protected void remove(Object entity) {
        this.getManager().remove(entity);
    }

    public Connection getConnection() {
        EntityManagerImpl emi = (EntityManagerImpl)this.getManager().getDelegate();
        Session session = emi.getSession();
        return ((SessionImpl)session).connection();
    }

    public Boolean isPersisted(Object entity) {
        return this.getManager().contains(entity);
    }
}

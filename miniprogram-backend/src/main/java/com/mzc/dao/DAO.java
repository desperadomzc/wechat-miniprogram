package com.mzc.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class DAO {

    private static final Logger log = LogManager.getLogger(DAO.class);

    private static final ThreadLocal sessionThread = new ThreadLocal();

    private static final SessionFactory sessionfactory = new Configuration().configure("hibernate.cfg.xml")
//            .setProperty("hibernate.connection.url", "jdbc:mysql://" + System.getenv("DB_HOSTNAME") + ":3306/csye6225?createDatabaseIfNotExist=true&useSSL=true&requireSSL=true")
            .buildSessionFactory();

    protected DAO() {
    }

    protected static Session getSession() {
        Session session = (Session) sessionThread.get();

        if (session == null) {
            session = sessionfactory.openSession();
            sessionThread.set(session);
        }
        return session;
    }

    protected static void begin() {
        if (!getSession().getTransaction().getStatus().equals(TransactionStatus.ACTIVE))
            getSession().beginTransaction();
    }

    protected static void commit() {
        if (getSession().getTransaction().getStatus().equals(TransactionStatus.ACTIVE)) {
            getSession().getTransaction().commit();
        }
    }

    protected static void close() {
        getSession().close();
        sessionThread.set(null);
    }

    protected static void rollback() {
        try {
            getSession().getTransaction().rollback();
        } catch (HibernateException e) {
            log.warn("Cannot rollback", e);
        }
        try {
            getSession().close();
        } catch (HibernateException e) {
            log.warn("Cannot close", e);
        }
        sessionThread.set(null);
    }
}

package com.mzc.dao;

import com.mzc.pojo.Product;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class ProductDAO extends DAO {

    public Product createProduct(String RIN) {
        try {
            begin();
            int x = getSession().createCriteria(Product.class).add(Restrictions.eq("RIN", RIN)).list().size();
            if (x > 0) {
                return null;
            }

            Product p = new Product();
            p.setCreated_ts(System.currentTimeMillis());
            p.setSealStatus("created");
            p.setScan_count(0);

            //store hashed RIN to prevent server hacked risk
            //RIN is secured in this way
            String hashed = BCrypt.hashpw(RIN, BCrypt.gensalt());
            p.setRIN(hashed);

            getSession().save(p);

            commit();
            return p;
        } catch (HibernateException e) {
            rollback();
            e.printStackTrace();
        }
        return null;
    }

    public Product getProduct(String pid, String RIN) {
        try {
            begin();
            Product p = (Product) getSession().createCriteria(Product.class)
                    .add(Restrictions.eq("id", pid)).uniqueResult();
            if(p == null){
                return null;
            }
            boolean match = BCrypt.checkpw(RIN,p.getRIN());
            if(!match){
                return null;
            }
            commit();
            return p;
        } catch (HibernateException e) {
            rollback();
            e.printStackTrace();
        }
        return null;
    }

    public void updateProduct(Product p){
        try {
            begin();
            getSession().saveOrUpdate(p);

            commit();
        } catch (HibernateException e) {
            rollback();
            e.printStackTrace();
        }
    }
}

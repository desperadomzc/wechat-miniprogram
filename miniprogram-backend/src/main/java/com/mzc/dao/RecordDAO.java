package com.mzc.dao;

import com.mzc.pojo.Product;
import com.mzc.pojo.Record;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecordDAO extends DAO {

    public Record createRecord(Product product, String username) {
        try {
            begin();

            int scancount = product.getScan_count();

            Record record = new Record();
            record.setTime(System.currentTimeMillis());
            record.setProduct(product);
            //scan_count + 1 => new record serial number
            record.setSerial_num(scancount + 1);

            //update product scan count
            product.setScan_count(scancount + 1);
            record.setUser_nickname(username);

            getSession().saveOrUpdate(product);
            getSession().save(record);

            commit();
            return record;
        } catch (HibernateException e) {
            rollback();
            e.printStackTrace();
        }
        return null;
    }

    public List<Record> getRecords(Product product) {
        try {
            begin();
            List<Record> records = getSession().createCriteria(Record.class)
                    .add(Restrictions.eq("product", product)).list();

            commit();
            return records;
        } catch (HibernateException e) {
            rollback();
            e.printStackTrace();
        }
        return null;
    }

}

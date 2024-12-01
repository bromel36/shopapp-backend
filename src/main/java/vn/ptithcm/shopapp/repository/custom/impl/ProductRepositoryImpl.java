package vn.ptithcm.shopapp.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import vn.ptithcm.shopapp.model.entity.Product;
import vn.ptithcm.shopapp.repository.custom.ProductRepositoryCustom;

import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Product> find(String tag, List<String> brands, List<Double> prices) {
        String price = prices.stream()
                .max(Double::compareTo)
                .map(String::valueOf)
                .orElse(null);
        System.out.println(price);
        StringBuilder sql = new StringBuilder("SELECT  p.* FROM products p ");
        resolveJoinTable(brands, sql);
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        resolveNormalQuery(brands,price, tag, where);
        sql.append(where);
        sql.append(" LIMIT 5 ");
        Query query = entityManager.createNativeQuery(sql.toString(),Product.class);
        return query.getResultList();
    }

    public void resolveJoinTable(List<String> brands, StringBuilder sql) {
        if (brands != null && brands.size() > 0) {
            sql.append(" JOIN brand b ON b.id = p.brand_id ");
        }
    }

    public void resolveNormalQuery(List<String> brands, String price, String tag, StringBuilder where) {

        if (price != null) {
            where.append(" AND p.price" + " =" + price);
        }
        if (tag != null) {
            where.append(" AND p.tag" + " like '%" + tag + "%' ");
        }
        if (brands != null && brands.size() > 0) {
            where.append(" AND (");
            brands.forEach(brand -> {
                where.append(" b.name" + " like '%" + brand + "%' ");
                where.append(" OR ");
            });
            where.append(" FALSE )");
        }
    }
}


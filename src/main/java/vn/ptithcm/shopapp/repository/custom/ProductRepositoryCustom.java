package vn.ptithcm.shopapp.repository.custom;

import vn.ptithcm.shopapp.model.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> find(String tag, List<String> brands, List<Double> price);
}

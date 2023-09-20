package com.zzb.shopping.repository;

import com.zzb.shopping.model.Product;

import java.util.List;

public interface ProductRepository {
     List<Product> getProductList();

     void addProduct(List<Product> productList);

     void addProductSingle(Product product);

     void delProduct(Product product);
}

package com.zzb.shopping.repository.impl;

import com.zzb.shopping.model.Product;
import com.zzb.shopping.repository.ProductRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public List<Product> getProductList(){
        HashOperations hashOperations = redisTemplate.opsForHash();
        Map<Integer,Product> productMap = hashOperations.entries("productList");
        if(productMap.isEmpty() && productMap.size()<=0) return null;
        ArrayList<Product> productArrayList = new ArrayList<>(productMap.values());
        return productArrayList;
    }
    @Override
    public void addProduct(List<Product> productList){
        HashOperations hashOperations = redisTemplate.opsForHash();
        for (Product product : productList) {
            hashOperations.put("productList",product.getId(),product);
        }
    }
    @Override
    public void addProductSingle(Product product){
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("productList",product.getId(),product);
    }
    @Override
    public void delProduct(Product product){
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete("productList",product.getId(),product);
    }

}

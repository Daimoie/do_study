package com.zzb.shopping.vo;

import com.zzb.shopping.model.Category;
import com.zzb.shopping.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO {
    private Product product;
    private Category category;

}

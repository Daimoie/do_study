package com.zzb.shopping.dto;

import com.zzb.shopping.common.validation.product.ProductPage;
import com.zzb.shopping.vo.ProductVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFenyeDTO {
    private List<ProductVO> productList;
    private String keyword;
    @NotNull(message = "当前页不能为空",groups = ProductPage.class)
    private Integer current;
    @NotNull(message = "当前页条数不能为空",groups = ProductPage.class)
    private Integer pageSize;
    private Integer startNum;
}

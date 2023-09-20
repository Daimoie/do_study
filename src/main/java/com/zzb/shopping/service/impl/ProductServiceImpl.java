package com.zzb.shopping.service.impl;

import com.zzb.shopping.dao.CategoryDAO;
import com.zzb.shopping.dao.ProductDAO;
import com.zzb.shopping.dao.ProductSpecDAO;
import com.zzb.shopping.dto.ProductFenyeDTO;
import com.zzb.shopping.model.Product;
import com.zzb.shopping.repository.ProductRepository;
import com.zzb.shopping.service.ProductService;
import com.zzb.shopping.common.util.ResultModel;
import com.zzb.shopping.vo.ProductVO;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDAO productDao;
    @Autowired
    ProductSpecDAO productSpecDao;
    @Autowired
    CategoryDAO categoryDao;
    @Autowired
    ProductRepository productRepository;
    static int SQL_RETURN_NUM = 0;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean add(Product product, HttpServletRequest request) {
        product.setIsDelect(0);
        return productSpecDao.add(product) > SQL_RETURN_NUM;
    }

    @Override
    public Boolean update(Product product, HttpServletRequest request) {
        return productDao.updateByPrimaryKeySelective(product) > SQL_RETURN_NUM;
    }

    @Override
    public Boolean del(Product product, HttpServletRequest request) {
        return productDao.updateByPrimaryKeySelective(product) > SQL_RETURN_NUM;
    }

    @Override
    public ResultModel<ProductFenyeDTO> SearchList(ProductFenyeDTO productFenyeDto) {
        Integer current = productFenyeDto.getCurrent();
        String keyword = productFenyeDto.getKeyword();
        Integer pageSize = productFenyeDto.getPageSize();
        Integer startNum = (current - 1) * pageSize;
        List<Product> productListRep = productRepository.getProductList();
        List<ProductVO> productVoList = new ArrayList<>();
        if(productListRep==null){
            List<Product> list = productSpecDao.list(keyword, startNum, pageSize);
            productRepository.addProduct(list);
            for (Product product : list) {
                productVoList.add(new ProductVO(product, categoryDao.selectByPrimaryKey(product.getCategoryId())));
            }
        }else{
            for (Product product : productListRep) {
                productVoList.add(new ProductVO(product, categoryDao.selectByPrimaryKey(product.getCategoryId())));
            }
        }

        productFenyeDto.setProductList(productVoList);
        return ResultModel.success(productFenyeDto);
    }
    @Override
    public byte[] getXslx() {

        // 获取商品信息
        List<Product> productList = productDao.selectByExample(null);

        // 创建xslx
        XSSFWorkbook xslx = new XSSFWorkbook();
        // 创建工作页
        XSSFSheet xslxSheet = xslx.createSheet("商品列表");
        // 创建导航行
        int rowNum = 0;
        XSSFRow firstRow = xslxSheet.createRow(rowNum);
        rowNum++;
        // 添加数据
        for (Product product : productList) {
            // 创建数据行
            XSSFRow xslxSheetRow = xslxSheet.createRow(rowNum);
            int colNum = 0;


            firstRow.createCell(colNum).setCellValue("商品编号");
            if (product.getId() != null) {
                xslxSheetRow.createCell(colNum).setCellValue(product.getId());
            }
            colNum++;

            firstRow.createCell(colNum).setCellValue("商品名称");
            if (product.getName() != null) {
                xslxSheetRow.createCell(colNum).setCellValue(product.getName());
            }
            colNum++;

            firstRow.createCell(colNum).setCellValue("商品价格");
            if (product.getPrice() != null) {
                xslxSheetRow.createCell(colNum).setCellValue(product.getPrice().longValue());
            }
            colNum++;

            firstRow.createCell(colNum).setCellValue("商品类型");
            if (product.getCategoryId() != null){
                xslxSheetRow.createCell(colNum).setCellValue(product.getCategoryId());
            }


            rowNum++;

        }
        //字节流输出数据
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            xslx.write(out);
            xslx.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

}

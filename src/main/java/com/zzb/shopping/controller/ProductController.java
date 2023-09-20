package com.zzb.shopping.controller;

import com.zzb.shopping.common.dozer.ReturnImg;
import com.zzb.shopping.common.validation.product.ProductPage;
import com.zzb.shopping.dto.ImgDTO;
import com.zzb.shopping.dto.ProductFenyeDTO;
import com.zzb.shopping.model.Img;
import com.zzb.shopping.model.Product;
import com.zzb.shopping.service.ImgService;
import com.zzb.shopping.service.ProductImgService;
import com.zzb.shopping.service.ProductService;
import com.zzb.shopping.common.util.ResultModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Transactional
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
     ProductService productService;
    @Autowired
    ImgService imgService;
    @Autowired
    ProductImgService productImgService;
    @Autowired
    ReturnImg returnImg;
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultModel<Boolean> add(Product product, HttpServletRequest request, ImgDTO imgDTO){
        String username = (String) request.getSession().getAttribute("username");
        product.setAuthor(username);
        Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义新的日期格式
//        //format():将给定的 Date 格式化为日期/时间字符串。即：date--->String
//        String dateString = formatter.format(currentTime);
        product.setCreateTime(currentTime);
        Boolean b = productService.add(product,request);
        //图片
        Boolean a = false;
        Img img = null;
        try {
            img = returnImg.getImg(imgDTO);
            img.setCreatedUser(username);
            img.setCreatedDate(currentTime);
            a = imgService.add(img);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultModel.error("图片上传失败");
        }
        //关系
        Boolean c= productImgService.add(product.getId(),img.getId());
        return a&&b&&c?ResultModel.success(b):ResultModel.error();
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultModel<Boolean> update(Product product, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        product.setUpdatePerson(username);
        Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义新的日期格式
//        //format():将给定的 Date 格式化为日期/时间字符串。即：date--->String
//        String dateString = formatter.format(currentTime);
        product.setUpdateTime(currentTime);
        Boolean b = productService.update(product, request);
        return b?ResultModel.success(b):ResultModel.error();
    }

    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public ResultModel<Boolean> del(Product product, HttpServletRequest request){
        product.setIsDelect(1);
        String username = (String) request.getSession().getAttribute("username");
        product.setUpdatePerson(username);
        Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义新的日期格式
//        //format():将给定的 Date 格式化为日期/时间字符串。即：date--->String
//        String dateString = formatter.format(currentTime);
        product.setUpdateTime(currentTime);
        Boolean b = productService.del(product,request);
        return b?ResultModel.success(b):ResultModel.error();
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public ResultModel<ProductFenyeDTO> Search(@Validated(ProductPage.class) ProductFenyeDTO productFenyeDto){
        return productService.SearchList(productFenyeDto);
    }

//    @PostMapping("test")
//    public ResultModel test(ImgDTO imgDTO){
//        //图片
//        Img img = returnImg.getImg(imgDTO);
//        return ResultModel.success(img);
//    }

    @RequestMapping(value = "/outxslx", method = RequestMethod.GET, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"+";charset=utf-8")
    public byte[] outXslx(HttpServletResponse response,Integer type){
        byte[] xslxDataBase = null;
        try {
            xslxDataBase = productService.getXslx();
            //设置导出的报表
            String fileName = URLEncoder.encode("2023-08问题日志表.xlsx", "UTF-8");
            //设置导出的格式
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return xslxDataBase;
    }
    @RequestMapping(value = "/inxslx", method = RequestMethod.POST)
    public ResultModel inXslx(MultipartFile file){
        if(file==null){
            return ResultModel.error("无文件上传");
        }
        //获取文件名
        String fileName = file.getOriginalFilename();
        System.out.println("fileName = " + fileName);
        String suffix = fileName.substring(fileName.indexOf("."));
        System.out.println("suffix = " + suffix);
        if(!suffix.equals(".xlsx")&!suffix.equals(".xls")){
            return ResultModel.error("文件格式错误");
        }
        if(suffix.equals(".xls")){
            HSSFWorkbook hssfWorkbook = null;
            try {
                InputStream in = file.getInputStream();
                hssfWorkbook = new HSSFWorkbook(in);
                System.out.println("hssfWorkbook = " + hssfWorkbook);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("hssfWorkbook = " + hssfWorkbook);
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
            //接收表数据
            List<Product> productList = new ArrayList<>();
            for (int row = 1; row< sheet.getPhysicalNumberOfRows(); row++) {
                HSSFRow sheetRow = sheet.getRow(row);
                //创建实体类
                Product product = new Product();
                //获取每个单元格的数据
                for (int cel = 0; cel < sheetRow.getPhysicalNumberOfCells(); cel++) {
                    HSSFCell cell = sheetRow.getCell(cel);
                    if(cel == 0){
                        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                        }
                        product.setId(Double.valueOf(cell.getStringCellValue()).intValue());
                    }
                    if(cel == 1){
                        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                        }

                        product.setName(cell.getStringCellValue());
                    }
                    if(cel == 2){
                        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                        }
                        product.setPrice(new BigDecimal(cell.getStringCellValue()));
                    }
                    if( cel == 3){
                        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                        }
                        product.setCategoryId(Double.valueOf(cell.getStringCellValue()).intValue());
                    }
                }
                productList.add(product);
            }
            return ResultModel.success(productList);
        }
        InputStream in = null;
        XSSFWorkbook xssfWorkbook = null;
        try {
            in = file.getInputStream();
            xssfWorkbook = new XSSFWorkbook(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet sheet = xssfWorkbook.getSheet("商品列表");
        //接收表数据
        List<Product> productList = new ArrayList<>();
        for (int row = 1; row< sheet.getPhysicalNumberOfRows(); row++) {
            XSSFRow sheetRow = sheet.getRow(row);
            //创建实体类
            Product product = new Product();
            //获取每个单元格的数据
            for (int cel = 0; cel < sheetRow.getPhysicalNumberOfCells(); cel++) {
                XSSFCell cell = sheetRow.getCell(cel);

                if(cel == 0){
                    if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    }
                    product.setId(Double.valueOf(cell.getStringCellValue()).intValue());
                }
                if(cel == 1){
                    if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    }

                    product.setName(cell.getStringCellValue());
                }
                if(cel == 2){
                    if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    }
                    product.setPrice(new BigDecimal(cell.getStringCellValue()));
                }
                if( cel == 3){
                    if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    }
                    product.setCategoryId(Double.valueOf(cell.getStringCellValue()).intValue());
                }
            }
            productList.add(product);
        }
        return ResultModel.success(productList);
    }

    @RequestMapping(value = "/inexcel",method = RequestMethod.POST)
    public ResultModel inexcel(MultipartFile file){
        //创建excel对象
        Workbook workbook = null;
        try {
            workbook = new WorkbookFactory().create(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        //获取工作页
        Sheet sheet = workbook.getSheetAt(0);
        //创建list接收
        ArrayList<Product> productList = new ArrayList<>();
        for (int row = 1; row < sheet.getPhysicalNumberOfRows(); row++) {
            //获取每一行
            Row sheetRow = sheet.getRow(row);
            //创建对象接收
            Product product = new Product();
            for (int cel = 0; cel < sheetRow.getPhysicalNumberOfCells(); cel++) {
                //获取每一个单元格
                Cell cell = sheetRow.getCell(cel);
                if(cel == 0){
                    if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    }
                    product.setId(Double.valueOf(cell.getStringCellValue()).intValue());
                }
                if(cel == 1){
                    if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    }

                    product.setName(cell.getStringCellValue());
                }
                if(cel == 2){
                    if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    }
                    product.setPrice(new BigDecimal(cell.getStringCellValue()));
                }
                if( cel == 3){
                    if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    }
                    product.setCategoryId(Double.valueOf(cell.getStringCellValue()).intValue());
                }
            }
            productList.add(product);
        }
        return ResultModel.success(productList);
    }
}

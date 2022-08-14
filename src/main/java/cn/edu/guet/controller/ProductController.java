package cn.edu.guet.controller;




import cn.edu.guet.bean.Product;
import cn.edu.guet.bean.ProductParm;
import cn.edu.guet.bean.SelectType;
import cn.edu.guet.http.HttpResult;
import cn.edu.guet.http.ResultUtils;
import cn.edu.guet.service.ProductService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Author 张家维
 * @Date 2022/7/31 21:48
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public HttpResult addProduct(@RequestBody Product product) {
        product.setCreateTime(new Date());
        System.out.println(product);
        if (productService.save(product)) {
            return ResultUtils.success("新增商品成功");
        } else {
            return ResultUtils.error("新增商品失败!");
        }
    }

    @PutMapping
    public HttpResult editProduct(@RequestBody Product product){
        product.setLastUpdateTime(new Date());
        if(productService.updateById(product)){
            return ResultUtils.success("编辑商品成功");
        }else {
            return  ResultUtils.error("编辑商品失败");
        }
    }

    @DeleteMapping("/{productId}")
    public HttpResult deletProduct(@PathVariable("productId") Long productId){
        if(productService.removeById(productId)){
            return ResultUtils.success("删除成功");
        }else {
            return ResultUtils.error("删除失败");
        }
    }

    @GetMapping("/list")
    public HttpResult getList(ProductParm parm){
        IPage<Product> list=productService.getList(parm);
        return ResultUtils.success("查询成功",list);
    }


    @GetMapping("/getSelect")
    public HttpResult getListSelect(){
        List<Product> list= productService.list();
        List<SelectType> selectTypeList= new ArrayList<>();
        if (list.size() > 0) {
            list.stream().forEach(item -> {
                SelectType type=new SelectType();
                type.setValue(item.getProductId());
                type.setLabel(item.getProductType());
                selectTypeList.add(type);
            });
        }
        return ResultUtils.success("查询成功", selectTypeList);
    }

    @PostMapping("/wx/getProductList")
    public List<Product> getProductList(){
        System.out.println("success");
        List<Product> list= productService.list();
        System.out.println(list);
        return list;
    }

}

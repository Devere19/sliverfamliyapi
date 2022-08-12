package cn.edu.guet.service;


import cn.edu.guet.bean.Product;
import cn.edu.guet.bean.ProductParm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 商品管理
 *
 * @Author Liwei
 * @Date 2021-08-14 18:05
 */
public interface ProductService extends IService<Product> {

    IPage<Product> getList(ProductParm parm);
}

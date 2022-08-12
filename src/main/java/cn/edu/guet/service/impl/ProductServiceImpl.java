package cn.edu.guet.service.impl;


import cn.edu.guet.bean.Product;
import cn.edu.guet.bean.ProductParm;
import cn.edu.guet.mapper.ProductMapper;
import cn.edu.guet.service.ProductService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {


    @Autowired
    ProductMapper sysProductMapper;

    @Override
    public IPage<Product> getList(ProductParm parm) {
        //创造分页对象
        IPage<Product> sysProductIPage =new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //创造查询条件
        QueryWrapper<Product> queryWrapper= new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getProductName())){
            queryWrapper.lambda().like(Product::getProductName, parm.getProductName());
        }


        return this.baseMapper.selectPage(sysProductIPage,queryWrapper);
    }
}

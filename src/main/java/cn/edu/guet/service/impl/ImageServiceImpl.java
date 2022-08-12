package cn.edu.guet.service.impl;

import cn.edu.guet.bean.Image;
import cn.edu.guet.mapper.ImageMapper;
import cn.edu.guet.service.ImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {

}

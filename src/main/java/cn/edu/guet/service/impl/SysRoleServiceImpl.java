package cn.edu.guet.service.impl;


import cn.edu.guet.bean.RoleParm;
import cn.edu.guet.bean.SysRole;
import cn.edu.guet.mapper.SysRoleMapper;
import cn.edu.guet.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Liwei
 * @Date 2021-08-13 18:12
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Override
	public List<SysRole> findAll() {
		return sysRoleMapper.findAll();
	}


	@Override
	public IPage<SysRole> getList(RoleParm parm) {
		//构造一个分页对象
		IPage<SysRole> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
		//构造查询条件
		QueryWrapper<SysRole> query = new QueryWrapper<>();
		if (StringUtils.isNotEmpty(parm.getRoleName())) {
			query.lambda().like(SysRole::getName, parm.getRoleName());
		}
		return this.baseMapper.selectPage(page, query);
	}
	
}

package com.ztkj.platform.update.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ztkj.platform.update.beans.ProductEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: zhang-jun
 * @Date: 2020/8/8 09:34
 * @Description:
 */
@Repository
@Mapper
public interface ProductMapper extends BaseMapper<ProductEntity>{
}

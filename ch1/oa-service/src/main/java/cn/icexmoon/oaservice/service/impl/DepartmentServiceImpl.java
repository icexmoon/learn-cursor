package cn.icexmoon.oaservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.icexmoon.oaservice.entity.Department;
import cn.icexmoon.oaservice.service.DepartmentService;
import cn.icexmoon.oaservice.mapper.DepartmentMapper;
import org.springframework.stereotype.Service;

/**
* @author 70748
* @description 针对表【department】的数据库操作Service实现
* @createDate 2025-05-22 11:06:18
*/
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
    implements DepartmentService{

}





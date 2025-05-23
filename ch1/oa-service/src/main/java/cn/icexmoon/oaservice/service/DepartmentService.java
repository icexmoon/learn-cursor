package cn.icexmoon.oaservice.service;

import cn.icexmoon.oaservice.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 70748
* @description 针对表【department】的数据库操作Service
* @createDate 2025-05-22 11:06:18
*/
public interface DepartmentService extends IService<Department> {

    /**
     * 获取根节点部门（不属于部门树）
     * @return 根节点部门
     */
    Department getRootDept();
}

package cn.icexmoon.oaservice.service.impl;

import cn.icexmoon.oaservice.entity.Department;
import cn.icexmoon.oaservice.entity.User;
import cn.icexmoon.oaservice.mapper.UserMapper;
import cn.icexmoon.oaservice.service.DepartmentService;
import cn.icexmoon.oaservice.service.UserService;
import cn.icexmoon.oaservice.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 70748
 * @description 针对表【users】的数据库操作Service实现
 * @createDate 2025-05-20 16:40:36
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Autowired
    private DepartmentService departmentService;

    @Override
    public User getByPhone(String phone) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        User user = this.getOne(queryWrapper);
        if (user != null) {
            return user;
        }
        // 没有用户，创建
        User newUser = new User();
        newUser.setPhone(phone);
        this.save(newUser);
        return newUser;
    }

    /**
     * 填充部门名称信息（适用于用户不多的情况，大量查询最好联表查询）
     *
     * @param userList 用户列表
     */
    private void fillDeptName(List<User> userList) {
        // 获取涉及的部门 id 集合
        List<Long> deptIds = userList.stream()
                .map(User::getDeptId)
                .filter(Objects::nonNull) // 过滤掉缺少部门 id 为 null 的
                .toList();
        if (deptIds.isEmpty()) {
            // 没有需要填充名称的部门
            return;
        }
        // 查询相关的部门
        QueryWrapper<Department> qw = new QueryWrapper<>();
        qw.in("id", deptIds)
                .select("id", "name"); // 只需要返回id和部门名称
        List<Department> departments = departmentService.list(qw);
        // 转换为 Map 结构
        Map<Long, Department> departmentMap = departments.stream().collect(Collectors.toMap(Department::getId, dept -> dept));
        // 填充部门名称
        userList.forEach(user -> {
            if (user.getDeptId() != null) {
                user.setDeptName(departmentMap.get(user.getDeptId()).getName());
            }
        });
    }

    @Override
    public Result<IPage<User>> getPageResult(Long pageNo, Long pageSize) {
        IPage<User> page = new Page<>(pageNo, pageSize);
        IPage<User> pageResult = this.page(page);
        if (pageResult.getRecords().isEmpty()) {
            return Result.success(pageResult);
        }
        fillDeptName(pageResult.getRecords());
        return Result.success(pageResult);
    }
}





package cn.icexmoon.oaservice.service;

import cn.icexmoon.oaservice.dto.RoleMenuPermitDTO;
import cn.icexmoon.oaservice.entity.Role;
import cn.icexmoon.oaservice.util.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 70748
* @description 针对表【role(角色表)】的数据库操作Service
* @createDate 2025-05-29 11:37:36
*/
public interface RoleService extends IService<Role> {

    /**
     * 添加角色
     * @param role 新角色
     * @return 成功/失败
     */
    Result<Integer> addRole(Role role);

    /**
     * 修改角色
     * @param role 角色
     * @return 成功/失败
     */
    Result<Void> editRole(Role role);

    /**
     * 修改角色的菜单权限设置
     * @param roleMenuPermitDTO 菜单权限设置
     * @return 成功/失败
     */
    Result<Void> editMenuPermission(RoleMenuPermitDTO roleMenuPermitDTO);
}

package cn.icexmoon.oaservice.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.icexmoon.oaservice.dto.RoleMenuPermitDTO;
import cn.icexmoon.oaservice.entity.Role;
import cn.icexmoon.oaservice.mapper.RoleMapper;
import cn.icexmoon.oaservice.service.RoleService;
import cn.icexmoon.oaservice.util.Result;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 70748
 * @description 针对表【role(角色表)】的数据库操作Service实现
 * @createDate 2025-05-29 11:37:36
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

    @Override
    public Result<Integer> addRole(Role role) {
        try {
            boolean saved = save(role);
            if (saved) {
                return Result.success(role.getId());
            }
            return Result.fail(-1, "新增角色失败");
        } catch (DuplicateKeyException e) {
            // 唯一索引冲突
            return Result.fail(-1, "角色的 key 不能重复");
        }
    }

    @Override
    public Result<Void> editRole(Role role) {
        try {
            boolean updated = updateById(role);
            if (updated) {
                return Result.success(null, "修改角色成功");
            }
            return Result.fail("修改角色失败");
        } catch (DuplicateKeyException e) {
            return Result.fail("角色的 key 不能重复");
        }
    }

    @Override
    public Result<Void> editMenuPermission(RoleMenuPermitDTO roleMenuPermitDTO) {
        Role role = new Role();
        List<Role.MenuPermission> menuPermissions = roleMenuPermitDTO.getMenuPermissions();
        // 不保存增删改查权限都没有的菜单权限设置
        menuPermissions = menuPermissions.stream().filter(mp -> BooleanUtil.isTrue(mp.getView())
                || BooleanUtil.isTrue(mp.getEdit())
                || BooleanUtil.isTrue(mp.getDelete())
                || BooleanUtil.isTrue(mp.getAdd())).toList();
        role.setMenuPermissions(menuPermissions);
        role.setId(roleMenuPermitDTO.getRoleId());
        boolean updated = this.updateById(role);
        if (updated) {
            return Result.success();
        }
        return Result.fail(null, "更新角色菜单权限失败");
    }
}





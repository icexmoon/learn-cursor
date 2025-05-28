package cn.icexmoon.oaservice.service.impl;

import cn.icexmoon.oaservice.dto.BindInterfaceDTO;
import cn.icexmoon.oaservice.entity.Interface;
import cn.icexmoon.oaservice.entity.MenuInterface;
import cn.icexmoon.oaservice.mapper.MenuInterfaceMapper;
import cn.icexmoon.oaservice.service.InterfaceService;
import cn.icexmoon.oaservice.service.MenuInterfaceService;
import cn.icexmoon.oaservice.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 70748
 * @description 针对表【menu_interface(菜单和接口的绑定关系)】的数据库操作Service实现
 * @createDate 2025-05-28 17:37:05
 */
@Service
public class MenuInterfaceServiceImpl extends ServiceImpl<MenuInterfaceMapper, MenuInterface>
        implements MenuInterfaceService {
    @Autowired
    private InterfaceService interfaceService;

    @Override
    @Transactional
    public Result<Void> bind(BindInterfaceDTO bindInterfaceDTO) {
        if (bindInterfaceDTO.getMenuId() == null) {
            throw new RuntimeException("菜单id不能为空");
        }
        if (bindInterfaceDTO.getInterfaceIds() == null || bindInterfaceDTO.getInterfaceIds().isEmpty()) {
            // 删除所有该菜单的绑定关系
            QueryWrapper<MenuInterface> qw = new QueryWrapper<>();
            qw.eq("menu_id", bindInterfaceDTO.getMenuId());
            boolean removed = this.remove(qw);
            if (!removed) {
                return Result.fail("绑定接口失败");
            }
            return Result.success();
        }
        // 更新菜单的绑定关系
        // 更新前先删除旧的绑定关系
        this.remove(new QueryWrapper<MenuInterface>().eq("menu_id", bindInterfaceDTO.getMenuId()));
        List<MenuInterface> bindList = new ArrayList<>();
        for (Integer interfaceId : bindInterfaceDTO.getInterfaceIds()) {
            MenuInterface menuInterface = new MenuInterface();
            menuInterface.setMenuId(bindInterfaceDTO.getMenuId());
            menuInterface.setInterfaceId(interfaceId);
            bindList.add(menuInterface);
        }
        boolean savedBatch = this.saveBatch(bindList);
        if (!savedBatch) {
            return Result.fail("绑定接口失败");
        }
        return Result.success();
    }

    @Override
    public Result<List<Interface>> binded(Integer menuId) {
        List<MenuInterface> menuInterfaces = this.list(new QueryWrapper<MenuInterface>().eq("menu_id", menuId));
        Set<Integer> ids = menuInterfaces.stream().map(mi -> mi.getInterfaceId()).collect(Collectors.toSet());
        if (ids.isEmpty()) {
            return Result.success(Collections.emptyList());
        }
        List<Interface> interfaces = interfaceService.listByIds(ids);
        return Result.success(interfaces);
    }
}





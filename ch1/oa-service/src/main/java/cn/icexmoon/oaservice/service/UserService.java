package cn.icexmoon.oaservice.service;

import cn.icexmoon.oaservice.entity.User;
import cn.icexmoon.oaservice.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 70748
 * @description 针对表【users】的数据库操作Service
 * @createDate 2025-05-20 16:40:36
 */
public interface UserService extends IService<User> {

    /**
     * 根据手机号获取用户（如果不存在，创建）
     *
     * @param phone 手机号
     * @return 用户
     */
    User getByPhone(String phone);

    /**
     * 获取员工的分页信息
     *
     * @param pageNo 当前页码
     * @param pageSize 页宽
     * @return 分页信息
     */
    Result<IPage<User>> getPageResult(Long pageNo, Long pageSize);
}

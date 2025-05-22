package cn.icexmoon.oaservice.controller;

import cn.icexmoon.oaservice.entity.User;
import cn.icexmoon.oaservice.service.UserService;
import cn.icexmoon.oaservice.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : 魔芋红茶
 * @version : 1.0
 * @Project : oa-service
 * @Package : cn.icexmoon.oaservice.controller
 * @ClassName : .java
 * @createTime : 2025/5/19 18:22
 * @Email : icexmoon@qq.com
 * @Website : https://icexmoon.cn
 * @Description :
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Result<List<User>> list(){
        List<User> list = userService.list();
        return Result.success(list);
    }

    @GetMapping("/pageList")
    public Result<IPage<User>> pageList(@RequestParam("pageNo") Long pageNo, @RequestParam("pageSize") Long pageSize){
        // 分页查询
        return userService.getPageResult(pageNo, pageSize);
    }

}

package cn.icexmoon.oaservice.controller;

import cn.icexmoon.oaservice.dto.DepartmentDTO;
import cn.icexmoon.oaservice.entity.Department;
import cn.icexmoon.oaservice.service.DepartmentService;
import cn.icexmoon.oaservice.util.DeptTree;
import cn.icexmoon.oaservice.util.Result;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : 魔芋红茶
 * @version : 1.0
 * @Project : oa-service
 * @Package : cn.icexmoon.oaservice.controller
 * @ClassName : .java
 * @createTime : 2025/5/23 上午11:27
 * @Email : icexmoon@qq.com
 * @Website : https://icexmoon.cn
 * @Description : 部门接口
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DeptTree deptTree;
    @Autowired
    private DepartmentService departmentService;

    /**
     * 获取部门树
     *
     * @return
     */
    @GetMapping("/tree")
    @ResponseBody
    public String getDepartmentTree() {
        Department rootNode = deptTree.getRootNode(true);
        Result<Department> success = Result.success(rootNode);
        return JSON.toJSONString(success);
    }

    @GetMapping("/pageList")
    public Result<IPage<Department>> pageList(@RequestParam Long pageNum, @RequestParam Long pageSize) {
        return departmentService.getPageResult(pageNum, pageSize);
    }

    /**
     * 新增部门
     *
     * @param deptDTO 部门信息
     * @return 新部门的id
     */
    @PostMapping("/add")
    public Result<Long> add(@RequestBody DepartmentDTO deptDTO) {
        return departmentService.addDepartment(deptDTO);
    }


    /**
     * 删除部门
     *
     * @param id 部门id
     * @return
     */
    @DeleteMapping("delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        // 级联删除部门
        boolean result = departmentService.cascadeDelete(id);
        if (result) {
            return Result.success();
        }
        return Result.fail("删除部门失败");
    }
}

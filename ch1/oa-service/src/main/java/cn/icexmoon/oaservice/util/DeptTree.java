package cn.icexmoon.oaservice.util;

import cn.icexmoon.oaservice.entity.Department;
import cn.icexmoon.oaservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : 魔芋红茶
 * @version : 1.0
 * @Project : oa-service
 * @Package : cn.icexmoon.oaservice.util
 * @ClassName : .java
 * @createTime : 2025/5/23 上午10:13
 * @Email : icexmoon@qq.com
 * @Website : https://icexmoon.cn
 * @Description : 部门树
 */
@Component
public class DeptTree {
    private Department root;
    @Autowired
    private DepartmentService departmentService;

    /**
     * 返回作为根节点的部门对象
     *
     * @return 根节点部门
     */
    public Department getRootNode() {
        if (root == null) {
            // 根节点不存在，生成部门树
            initTree();
        }
        return root;
    }

    /**
     * 初始化部门树
     */
    private void initTree() {
        // 读取全部部门信息
        List<Department> departments = departmentService.list();
        // 获取根节点
        Department rootDeptNode = null;
        for (Department department : departments) {
            if (department.getParentId() == 0) {
                // 第一个父部门是0的节点视作根
                rootDeptNode = department;
                break;
            }
        }
        if (rootDeptNode == null) {
            // 数据库错误，缺少根节点
            throw new RuntimeException("部门数据错误，缺少根部门");
        }
        // 从根节点开始，生成部门树
        genDeptTreeNode(rootDeptNode, departments);
        this.root = rootDeptNode;
    }

    /**
     * 生成部门树的指定节点
     *
     * @param currentNode    要生成的部门节点
     * @param allDepartments 所有的部门信息
     */
    private void genDeptTreeNode(Department currentNode, List<Department> allDepartments) {
        // 获取当前节点的子节点
        for (Department department : allDepartments) {
            if (Objects.equals(department.getParentId(), currentNode.getId())) {
                // 是当前节点的子节点，加入当前节点的子节点集合
                currentNode.getChildren().add(department);
                // 设置子节点的父节点
                department.setParent(currentNode);
                // 深度遍历，继续处理子节点的子节点
                genDeptTreeNode(department, allDepartments);
            }
        }

    }

    /**
     * 遍历指定节点，执行某种操作（深度遍历）
     *
     * @param deptNode 用于遍历的起始节点
     * @param consumer 自定义执行节点操作的匿名函数
     */
    private void traversalWithDeepFirst(Department deptNode, Consumer<Department> consumer) {
        // 对当前节点执行操作
        consumer.accept(deptNode);
        // 对子节点重复此处理
        for (Department child : deptNode.getChildren()) {
            traversalWithDeepFirst(child, consumer);
        }
    }

    /**
     * 遍历指定节点，执行某种操作（广度优先）
     *
     * @param deptNode 用于遍历的起始节点
     * @param consumer 自定义执行节点操作的匿名函数
     */
    private void traversalWithBreathFirst(Department deptNode, Consumer<Department> consumer) {
        traversalWithBreathFirst(deptNode, consumer, true);
    }

    /**
     * 遍历指定节点，执行某种操作（广度优先）
     *
     * @param deptNode 用于遍历的起始节点
     * @param consumer 自定义执行节点操作的匿名函数
     * @param isFirst  是否第一次迭代
     */
    private void traversalWithBreathFirst(Department deptNode, Consumer<Department> consumer, boolean isFirst) {
        // 如果是第一次迭代，先执行匿名函数
        if (isFirst) {
            consumer.accept(deptNode);
        }
        // 获取所有的子节点
        Set<Department> children = deptNode.getChildren();
        // 对所有子节点执行匿名函数
        for (Department child : children) {
            consumer.accept(child);
        }
        // 对所有子节点递归调用本方法
        for (Department child : children) {
            traversalWithBreathFirst(child, consumer, false);
        }
    }

    /**
     * 遍历部门树
     *
     * @param consumer       遍历时要执行的匿名函数（广度优先）
     */
    public void traversalTree(Consumer<Department> consumer) {
        traversalTree(consumer, true);
    }

    /**
     * 遍历部门树
     *
     * @param consumer       遍历时要执行的匿名函数
     * @param useBreathFirst 是否使用广度优先遍历，如果否，将使用深度优先遍历
     */
    public void traversalTree(Consumer<Department> consumer, boolean useBreathFirst) {
        Department rootNode = getRootNode();
        if (useBreathFirst) {
            traversalWithBreathFirst(rootNode, consumer);
        } else {
            traversalWithDeepFirst(rootNode, consumer);
        }
    }

    /**
     * 获取指定节点的完整部门名称
     * @param deptNode 部门节点
     */
    public String getFullDeptName(Department deptNode){
        Stack<String> stack = new Stack<>();
        stackDeptNames(deptNode, stack);
        StringBuilder stringBuilder = new StringBuilder();
        while (!stack.isEmpty()) {
            String deptName = stack.pop();
            stringBuilder.append(deptName);
            stringBuilder.append("/");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private void stackDeptNames(Department deptNode, Stack<String> stack){
        stack.push(deptNode.getName());
        if (deptNode.getParent()!=null){
            stackDeptNames(deptNode.getParent(), stack);
        }
    }
}

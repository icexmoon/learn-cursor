package cn.icexmoon.oaservice.util;

import cn.icexmoon.oaservice.entity.Department;
import cn.icexmoon.oaservice.service.DepartmentService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
    private final DepartmentService departmentService;

    public DeptTree(@Lazy DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public Department getRootNode() {
        return getRootNode(false);
    }

    /**
     * 返回作为根节点的部门对象
     *
     * @param withNoParent 是否不需要父部门关联
     * @return 根节点部门
     */
    public Department getRootNode(boolean withNoParent) {
        if (withNoParent) {
            // 不需要关联父部门，临时生成一个部门树返回
            return genDeptTree(false);
        }
        if (root == null) {
            // 根节点不存在，生成部门树
            root = genDeptTree(true);
        }
        return root;
    }

    /**
     * 生成部门树
     *
     * @param linkParent 是否关联父部门
     */
    private Department genDeptTree(boolean linkParent) {
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
        genDeptTreeNode(rootDeptNode, departments, linkParent);
        return rootDeptNode;
    }

    /**
     * 生成部门树的指定节点
     *
     * @param currentNode    要生成的部门节点
     * @param allDepartments 所有的部门信息
     * @param linkParent     是否关联父节点
     */
    private void genDeptTreeNode(Department currentNode, List<Department> allDepartments, boolean linkParent) {
        // 获取当前节点的子节点
        for (Department department : allDepartments) {
            if (Objects.equals(department.getParentId(), currentNode.getId())) {
                // 是当前节点的子节点，加入当前节点的子节点集合
                currentNode.getChildren().add(department);
                if (linkParent) {
                    // 设置子节点的父节点
                    department.setParent(currentNode);
                }
                // 深度遍历，继续处理子节点的子节点
                genDeptTreeNode(department, allDepartments, linkParent);
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
     * @param consumer 遍历时要执行的匿名函数（广度优先）
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
     *
     * @param deptNode 部门节点
     */
    public String getFullDeptName(Department deptNode) {
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

    /**
     * 根据部门 id 获取部门完整名称
     *
     * @param deptId 部门id
     * @return 部门完整名称
     */
    public String getFullDeptName(long deptId) {
        Department node = findNode(deptId);
        if (node == null) {
            // 匹配不到，返回空字符串
            return "";
        }
        return getFullDeptName(node);
    }

    /**
     * 查找部门节点
     *
     * @param deptId 部门id
     * @return 部门节点
     */
    private Department findNode(long deptId) {
        return findNode(dept -> dept != null && dept.getId() == deptId);
    }

    /**
     * 在部门树上查找一个符合条件的节点
     *
     * @param predicate 条件
     * @return 部门节点，null表示没有符合条件的
     */
    public Department findNode(Predicate<Department> predicate) {
        //遍历树，如果满足条件，返回节点
        return traversalAndFind(getRootNode(), predicate);
    }

    /**
     * 遍历节点，并根据匹配条件找到第一个符合条件的节点
     *
     * @param currentNode 当前遍历的节点
     * @param predicate   匹配条件
     * @return 符合条件的节点，null 表示没有符合条件的节点
     */
    private Department traversalAndFind(Department currentNode, Predicate<Department> predicate) {
        if (predicate.test(currentNode)) {
            return currentNode;
        }
        for (Department child : currentNode.getChildren()) {
            Department result = traversalAndFind(child, predicate);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private void stackDeptNames(Department deptNode, Stack<String> stack) {
        stack.push(deptNode.getName());
        if (deptNode.getParent() != null) {
            stackDeptNames(deptNode.getParent(), stack);
        }
    }


    /**
     * 获取所有子部门的id集合
     *
     * @param deptId 父部门的id
     * @return 子部门的id集合
     */
    public Set<Long> getAllSubDeptIds(long deptId) {
        Set<Long> deptIds = new HashSet<>();
        // 匹配部门节点
        Department node = findNode(deptId);
        if (node == null) {
            // 没匹配到，部门不存在
            return deptIds;
        }
        Set<Department> subDepts = getSubDepts(node);
        if (subDepts == null || subDepts.isEmpty()) {
            return deptIds;
        }
        for (Department subDept : subDepts) {
            deptIds.add(subDept.getId());
        }
        return deptIds;
    }

    public Set<Department> getSubDepts(Department currentNode) {
        Set<Department> children = new HashSet<>();
        fillSubDept(currentNode, children);
        return children;
    }

    /**
     * 填充当前节点的子节点到指定集合（递归调用）
     *
     * @param node          当前节点
     * @param departmentSet 子节点集合
     */
    private void fillSubDept(Department node, Set<Department> departmentSet) {
        for (Department child : node.getChildren()) {
            departmentSet.add(child);
            fillSubDept(child, departmentSet);
        }
    }

    /**
     * 销毁部门树（数据库中的部门结构发生变化后应该调用此方法）
     */
    public void destroy(){
        this.root = null;
    }
}

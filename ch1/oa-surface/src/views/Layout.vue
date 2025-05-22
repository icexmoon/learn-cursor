<template>
  <el-container style="height: 100vh; width: 87vw; background: #f6f8fa;">
    <!-- 顶部 -->
    <el-header class="header">
      <div class="logo">员工管理系统</div>
      <div class="user-info">
        <el-avatar icon="el-icon-user" />
        <span class="username">张三</span>
        <el-link type="danger" @click="logout">退出</el-link>
      </div>
    </el-header>
    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="220px" class="aside">
        <el-menu :default-openeds="['org', 'office', 'sys']" router>
          <el-menu-item index="/">
            <el-icon>
              <House />
            </el-icon>
            <span>首页</span>
          </el-menu-item>
          <el-sub-menu index="org">
            <template #title>
              <el-icon>
                <UserFilled />
              </el-icon>
              <span>组织架构</span>
            </template>
            <el-menu-item index="/employee">
              <el-icon>
                <User />
              </el-icon>
              <span>员工管理</span>
            </el-menu-item>
            <el-menu-item index="/department">
              <el-icon>
                <OfficeBuilding />
              </el-icon>
              <span>部门管理</span>
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="office">
            <template #title>
              <el-icon>
                <Document />
              </el-icon>
              <span>日常办公</span>
            </template>
            <el-menu-item index="/apply">
              <el-icon>
                <Edit />
              </el-icon>
              <span>提交申请</span>
            </el-menu-item>
            <el-menu-item index="/todo">
              <el-icon>
                <List />
              </el-icon>
              <span>审批待办</span>
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="sys">
            <template #title>
              <el-icon>
                <Setting />
              </el-icon>
              <span>系统管理</span>
            </template>
            <el-menu-item index="/menu">
              <el-icon>
                <Menu />
              </el-icon>
              <span>菜单管理</span>
            </el-menu-item>
            <el-menu-item index="/perm">
              <el-icon>
                <Lock />
              </el-icon>
              <span>权限管理</span>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <!-- 内容区 -->
      <el-main class="main">
        <div class="main-inner">
          <router-view />
        </div>
      </el-main>
    </el-container>
    <!-- 底部 -->
    <el-footer class="footer">
      ©2024 企业员工管理系统 - 备案号：粤ICP备xxxx号
    </el-footer>
  </el-container>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { House, UserFilled, User, OfficeBuilding, Document, Edit, List, Setting, Menu, Lock } from '@element-plus/icons-vue'
import request from '@/util/request'
const router = useRouter()
const logout = () => {
  // 调用后端退出登录接口
  request.post('/api/logout').then(resp => {
    console.log("退出成功", resp.data)
    // 清除本地存储的token
    localStorage.removeItem('token')
    // 清除登录状态
    router.push('/login')
  }).catch(error => {
    console.log("退出失败", error)
  })
}
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #eaf1fb;
  color: #333;
  height: 60px;
  border-bottom: 1px solid #e0e6ed;
}

.logo {
  font-size: 22px;
  font-weight: bold;
  color: #2d8cf0;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 16px;
}

.aside {
  background: #f6f8fa;
  color: #333;
  border-right: 1px solid #e0e6ed;
}

.main {
  background: #f6f8fa;
  padding: 24px 0 24px 24px;
  min-height: 600px;
  width: 100%;
  box-sizing: border-box;
  flex: 1;
  display: flex;
  justify-content: flex-start;
}

.main-inner {
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
  padding: 32px;
  width: 100%;
  min-height: 500px;
  margin-right: 0px;
  /* 右侧预留边框宽度 */
  box-sizing: border-box;
}

.footer {
  text-align: center;
  color: #888;
  background: #f6f8fa;
  padding: 12px 0;
  font-size: 14px;
  border-top: 1px solid #e0e6ed;
}
</style>
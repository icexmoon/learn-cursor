<template>
  <el-card>
    <h2>菜单管理</h2>
    <el-table :data="tableData" style="width: 100%; margin-top: 20px;">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="菜单名称" />
      <el-table-column prop="path" label="路径" />
      <el-table-column prop="icon" label="图标" />
    </el-table>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'
const tableData = ref([
  { id: 1, name: '首页', path: '/', icon: 'House' },
  { id: 2, name: '员工管理', path: '/employee', icon: 'User' },
  { id: 3, name: '部门管理', path: '/department', icon: 'OfficeBuilding' },
  { id: 4, name: '菜单管理', path: '/menu', icon: 'Menu' }
])
</script> 
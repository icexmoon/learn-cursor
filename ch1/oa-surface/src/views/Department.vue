<template>
  <el-card>
    <h2>部门管理</h2>
    <el-table :data="tableData" style="width: 100%; margin-top: 20px;">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="部门名称" />
      <el-table-column prop="leader" label="负责人" />
      <el-table-column prop="count" label="人数" />
    </el-table>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'
const tableData = ref([
  { id: 1, name: '研发部', leader: '张三', count: 10 },
  { id: 2, name: '市场部', leader: '李四', count: 8 },
  { id: 3, name: '财务部', leader: '王五', count: 5 }
])
</script> 
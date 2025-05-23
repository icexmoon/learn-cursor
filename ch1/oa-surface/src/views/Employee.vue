<template>
  <el-card>
    <h2>员工管理</h2>
    <el-table :data="tableData" style="width: 100%; margin-top: 20px;">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="phone" label="手机号" />
      <el-table-column prop="deptName" label="部门" />
    </el-table>
    
    <!-- 添加分页组件 -->
    <div class="pagination-container">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[5, 10, 20, 50]"
        :total="total"
        layout="total, sizes, prev, pager, next"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '@/util/request'

const tableData = ref([])
const currentPage = ref(1)
const pageSize = ref(5)
const total = ref(0)

// 获取员工列表数据
const fetchEmployeeList = async () => {
  try {
    const response = await request.get(`/api/user/pageList?pageNo=${currentPage.value}&pageSize=${pageSize.value}`)
    console.log(response)
    if (response.success) {
      tableData.value = response.data.records
      total.value = response.data.total
    }
  } catch (error) {
    console.error('获取员工列表失败', error)
  }
}

// 处理页码改变
const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchEmployeeList()
}

// 处理每页条数改变
const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
  fetchEmployeeList()
}

// 组件挂载时获取数据
onMounted(() => {
  fetchEmployeeList()
})
</script>

<style scoped>
.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style> 
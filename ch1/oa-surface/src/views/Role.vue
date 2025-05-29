<template>
  <div class="role-container">
    <el-card class="list-card">
      <template #header>
        <div class="card-header">
          <span>角色列表</span>
          <el-button type="primary" @click="handleAdd">新增角色</el-button>
        </div>
      </template>
      
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="key" label="角色标识" />
        <el-table-column prop="name" label="角色名称" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加/编辑角色对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '新增角色' : '编辑角色'"
      width="30%"
    >
      <el-form :model="roleForm" label-width="100px">
        <el-form-item label="角色标识">
          <el-input v-model="roleForm.key" placeholder="请输入角色标识" />
        </el-form-item>
        <el-form-item label="角色名称">
          <el-input v-model="roleForm.name" placeholder="请输入角色名称" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '@/util/request'
import { ElMessage, ElMessageBox } from 'element-plus'

const tableData = ref([])
const dialogVisible = ref(false)
const dialogType = ref('add')
const roleForm = ref({
  id: null,
  key: '',
  name: ''
})

// 获取角色列表数据
const fetchRoleList = async () => {
  try {
    const response = await request.get('/api/role/list')
    if (response.success) {
      tableData.value = response.data
    }
  } catch (error) {
    console.error('获取角色列表失败', error)
    ElMessage.error('获取角色列表失败')
  }
}

// 添加角色
const handleAdd = () => {
  dialogType.value = 'add'
  roleForm.value = {
    id: null,
    key: '',
    name: ''
  }
  dialogVisible.value = true
}

// 编辑角色
const handleEdit = (row) => {
  dialogType.value = 'edit'
  roleForm.value = {
    id: row.id,
    key: row.key,
    name: row.name
  }
  dialogVisible.value = true
}

// 删除角色
const handleDelete = (row) => {
  ElMessageBox.confirm(
    '确定要删除该角色吗？删除后不可恢复！',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      const response = await request.delete(`/api/role/del/${row.id}`)
      if (response.success) {
        ElMessage.success('删除成功')
        fetchRoleList()
      } else {
        ElMessage.error(response.message || '删除失败')
      }
    } catch (error) {
      console.error('删除角色失败', error)
      ElMessage.error('删除失败')
    }
  }).catch(() => {
    // 用户取消删除操作
  })
}

// 提交表单
const submitForm = async () => {
  try {
    const url = dialogType.value === 'add' ? '/api/role/add' : '/api/role/edit'
    const response = await request.post(url, roleForm.value)
    if (response.success) {
      ElMessage.success(dialogType.value === 'add' ? '添加成功' : '更新成功')
      dialogVisible.value = false
      fetchRoleList()
    } else {
      ElMessage.error(response.message || (dialogType.value === 'add' ? '添加失败' : '更新失败'))
    }
  } catch (error) {
    console.error(dialogType.value === 'add' ? '添加角色失败' : '更新角色失败', error)
    ElMessage.error(dialogType.value === 'add' ? '添加失败' : '更新失败')
  }
}

// 组件挂载时获取数据
onMounted(() => {
  fetchRoleList()
})
</script>

<style scoped>
.role-container {
  padding: 20px;
}

.list-card {
  width: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style> 
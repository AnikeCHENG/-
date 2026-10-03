<template>
  <div class="admin-users">
    <div class="admin-header">
      <h1>用户管理</h1>
      <router-link to="/admin" class="back-link">返回控制面板</router-link>
    </div>
    
    <div class="user-search">
      <input 
        type="text" 
        v-model="searchQuery" 
        placeholder="搜索用户名或邮箱..."
        class="search-input"
      />
      <button @click="fetchUsers" class="search-btn">搜索</button>
    </div>
    
    <div class="users-table-container">
      <table class="users-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>用户名</th>
            <th>邮箱</th>
            <th>手机号</th>
            <th>角色</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in users" :key="user.id">
            <td>{{ user.id }}</td>
            <td>{{ user.username }}</td>
            <td>{{ user.email }}</td>
            <td>{{ user.phone || '未设置' }}</td>
            <td>{{ user.role === 1 ? '管理员' : '普通用户' }}</td>
            <td>{{ user.status === 1 ? '正常' : '禁用' }}</td>
            <td>{{ formatDate(user.createdTime) }}</td>
            <td>
              <button 
                class="action-button" 
                :class="{ 'disable-button': user.status === 1, 'enable-button': user.status === 0 }"
                @click="toggleUserStatus(user.id, user.status)"
              >
                {{ user.status === 1 ? '禁用' : '启用' }}
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <!-- 分页 -->
    <div v-if="total > 0" class="pagination">
      <button 
        class="pagination-button" 
        @click="changePage(1)" 
        :disabled="page === 1"
      >
        首页
      </button>
      <button 
        class="pagination-button" 
        @click="changePage(page - 1)" 
        :disabled="page === 1"
      >
        上一页
      </button>
      <span class="pagination-info">
        第 {{ page }} 页，共 {{ totalPages }} 页，总计 {{ total }} 条
      </span>
      <button 
        class="pagination-button" 
        @click="changePage(page + 1)" 
        :disabled="page === totalPages"
      >
        下一页
      </button>
      <button 
        class="pagination-button" 
        @click="changePage(totalPages)" 
        :disabled="page === totalPages"
      >
        末页
      </button>
    </div>
    
    <div v-if="users.length === 0" class="no-users">
      <p>暂无用户</p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'

const users = ref([])
const searchQuery = ref('')
const page = ref(1)
const pageSize = ref(10)
const total = ref(0)

const totalPages = computed(() => {
  return Math.ceil(total.value / pageSize.value)
})

const fetchUsers = async () => {
  try {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token')
    const response = await axios.get('/api/admin/users', {
      headers: {
        Authorization: `Bearer ${token}`
      },
      params: {
        search: searchQuery.value,
        page: page.value,
        pageSize: pageSize.value
      }
    })
    if (response.data && response.data.data) {
      users.value = response.data.data.items
      total.value = response.data.data.total
    }
  } catch (error) {
    console.error('获取用户列表失败:', error)
  }
}

const toggleUserStatus = async (userId, currentStatus) => {
  try {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token')
    const newStatus = currentStatus === 1 ? 0 : 1
    await axios.put(`/api/admin/users/${userId}/status`, newStatus, {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    })
    // 更新本地用户列表
    const user = users.value.find(u => u.id === userId)
    if (user) {
      user.status = newStatus
    }
  } catch (error) {
    console.error('更新用户状态失败:', error)
  }
}

const changePage = (newPage) => {
  if (newPage >= 1 && newPage <= totalPages.value) {
    page.value = newPage
    fetchUsers()
  }
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleString()
}

onMounted(() => {
  fetchUsers()
})
</script>

<style scoped>
.admin-users {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.admin-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.admin-header h1 {
  font-size: 2rem;
  color: #333;
}

.back-link {
  text-decoration: none;
  color: #333;
  padding: 10px 15px;
  background-color: #f5f5f5;
  border-radius: 5px;
  transition: all 0.3s ease;
}

.back-link:hover {
  background-color: #e0e0e0;
}

/* 搜索样式 */
.user-search {
  margin-bottom: 30px;
  display: flex;
  gap: 10px;
  align-items: center;
}

.search-input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 16px;
}

.search-btn {
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: all 0.3s ease;
}

.search-btn:hover {
  background-color: #45a049;
}

/* 分页样式 */
.pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  margin-top: 30px;
  padding-top: 20px;
  border-top: 1px solid #eee;
}

.pagination-button {
  padding: 8px 16px;
  background-color: #f5f5f5;
  color: #333;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
}

.pagination-button:hover:not(:disabled) {
  background-color: #e0e0e0;
  transform: translateY(-1px);
}

.pagination-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.pagination-info {
  font-size: 14px;
  color: #666;
  font-weight: 500;
}

.no-users {
  text-align: center;
  padding: 50px;
  color: #999;
  font-size: 1.2rem;
}

.users-table-container {
  overflow-x: auto;
}

.users-table {
  width: 100%;
  border-collapse: collapse;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.users-table th,
.users-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #e0e0e0;
}

.users-table th {
  background-color: #f5f5f5;
  font-weight: bold;
  color: #333;
}

.users-table tr:hover {
  background-color: #f9f9f9;
}

.action-button {
  padding: 6px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.disable-button {
  background-color: #ff6b6b;
  color: white;
}

.disable-button:hover {
  background-color: #ff5252;
}

.enable-button {
  background-color: #4caf50;
  color: white;
}

.enable-button:hover {
  background-color: #45a049;
}
</style>

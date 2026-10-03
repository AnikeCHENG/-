<template>
  <div class="admin-categories">
    <h1>分类管理</h1>
    
    <div class="category-form">
      <h3>添加分类</h3>
      <div class="form-group">
        <label for="category-name">分类名称:</label>
        <input 
          type="text" 
          id="category-name" 
          v-model="newCategory.name" 
          placeholder="输入分类名称"
          class="form-input"
        />
      </div>
      <button @click="addCategory" class="btn-primary">添加分类</button>
    </div>
    
    <div class="category-list">
      <h3>分类列表</h3>
      <div class="category-item" v-for="category in categories" :key="category.id">
        <div class="category-info">
          <span class="category-name">{{ category.name }}</span>
          <span class="category-count">{{ category.productCount }} 个商品</span>
        </div>
        <div class="category-actions">
          <button @click="editCategory(category)" class="btn-secondary">编辑</button>
          <button @click="deleteCategory(category.id)" class="btn-danger">删除</button>
        </div>
      </div>
    </div>
    
    <div v-if="categories.length === 0" class="no-categories">
      <p>暂无分类</p>
    </div>
    
    <!-- 编辑分类模态框 -->
    <div v-if="editingCategory" class="modal">
      <div class="modal-content">
        <h3>编辑分类</h3>
        <div class="form-group">
          <label for="edit-category-name">分类名称:</label>
          <input 
            type="text" 
            id="edit-category-name" 
            v-model="editingCategory.name" 
            placeholder="输入分类名称"
            class="form-input"
          />
        </div>
        <div class="modal-actions">
          <button @click="saveCategory" class="btn-primary">保存</button>
          <button @click="editingCategory = null" class="btn-secondary">取消</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const categories = ref([])
const newCategory = ref({ name: '' })
const editingCategory = ref(null)

const fetchCategories = async () => {
  try {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token')
    const response = await axios.get('/api/admin/categories', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    categories.value = response.data.data
  } catch (error) {
    console.error('获取分类失败:', error)
  }
}

const addCategory = async () => {
  if (!newCategory.value.name.trim()) {
    alert('请输入分类名称')
    return
  }
  
  try {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token')
    await axios.post('/api/admin/categories', newCategory.value, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    // 清空表单
    newCategory.value.name = ''
    // 重新获取分类列表
    fetchCategories()
  } catch (error) {
    console.error('添加分类失败:', error)
  }
}

const editCategory = (category) => {
  editingCategory.value = { ...category }
}

const saveCategory = async () => {
  if (!editingCategory.value.name.trim()) {
    alert('请输入分类名称')
    return
  }
  
  try {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token')
    await axios.put(`/api/admin/categories/${editingCategory.value.id}`, editingCategory.value, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    // 关闭模态框
    editingCategory.value = null
    // 重新获取分类列表
    fetchCategories()
  } catch (error) {
    console.error('更新分类失败:', error)
  }
}

const deleteCategory = async (categoryId) => {
  if (confirm('确定要删除这个分类吗？')) {
    try {
      const token = localStorage.getItem('token') || sessionStorage.getItem('token')
      await axios.delete(`/api/admin/categories/${categoryId}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      })
      // 重新获取分类列表
      fetchCategories()
    } catch (error) {
      console.error('删除分类失败:', error)
    }
  }
}

onMounted(() => {
  fetchCategories()
})
</script>

<style scoped>
.admin-categories {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.admin-categories h1 {
  font-size: 2rem;
  color: #333;
  margin-bottom: 30px;
}

.category-form {
  background-color: #f9f9f9;
  border-radius: 10px;
  padding: 20px;
  margin-bottom: 30px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.category-form h3 {
  font-size: 1.2rem;
  color: #333;
  margin-bottom: 15px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  color: #666;
  font-weight: bold;
}

.form-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 16px;
}

.btn-primary {
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.btn-primary:hover {
  background-color: #45a049;
}

.category-list {
  background-color: #f9f9f9;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.category-list h3 {
  font-size: 1.2rem;
  color: #333;
  margin-bottom: 15px;
}

.category-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  background-color: white;
  border-radius: 5px;
  margin-bottom: 10px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.category-info {
  display: flex;
  gap: 20px;
}

.category-name {
  font-size: 16px;
  font-weight: bold;
  color: #333;
}

.category-count {
  font-size: 14px;
  color: #666;
}

.category-actions {
  display: flex;
  gap: 10px;
}

.btn-secondary {
  padding: 8px 16px;
  background-color: #2196F3;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
}

.btn-secondary:hover {
  background-color: #0b7dda;
}

.btn-danger {
  padding: 8px 16px;
  background-color: #f44336;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
}

.btn-danger:hover {
  background-color: #da190b;
}

.no-categories {
  text-align: center;
  padding: 50px;
  color: #999;
  font-size: 1.2rem;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background-color: white;
  border-radius: 10px;
  padding: 30px;
  width: 400px;
  max-width: 90%;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}

.modal-content h3 {
  font-size: 1.2rem;
  color: #333;
  margin-bottom: 20px;
}

.modal-actions {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
  margin-top: 20px;
}
</style>
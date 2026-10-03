const BASE = import.meta.env.VITE_API_BASE || '/api'

async function request(path, options = {}) {
  const token = localStorage.getItem('token') || sessionStorage.getItem('token')
  try {
    const res = await fetch(`${BASE}${path}`, {
      headers: { 
        'Content-Type': 'application/json',
        'Authorization': token ? `Bearer ${token}` : ''
      },
      ...options,
    })
    const data = await res.json().catch(() => ({ message: '服务器返回非JSON' }))
    if (!res.ok) {
      const err = new Error(data?.message || `HTTP ${res.status}`)
      err.response = data
      throw err
    }
    if (data && typeof data.code === 'number' && data.code !== 200) {
      const err = new Error(data.message || '请求未成功')
      err.response = data
      throw err
    }
    return data
  } catch (e) {
    const err = new Error('Network request failed')
    err.cause = e
    throw err
  }
}

export async function getProductList() {
  return await request('/products/list')
}

export async function getMyProducts() {
  return await request('/products/my')
}

export async function getProductsByCategory(category) {
  return await request(`/products/category/${encodeURIComponent(category)}`)
}

export async function searchProducts(keyword) {
  return await request(`/products/search?keyword=${encodeURIComponent(keyword)}`)
}

export async function getProductDetail(id) {
  return await request(`/products/${id}`)
}

export async function createProduct(params) {
  return await request('/products/create', {
    method: 'POST',
    body: JSON.stringify(params)
  })
}

export async function updateProduct(id, params) {
  return await request(`/products/${id}`, {
    method: 'PUT',
    body: JSON.stringify(params)
  })
}

export async function deleteProduct(id) {
  return await request(`/products/delete/${id}`, {
    method: 'DELETE'
  })
}

export async function markAsSold(id) {
  return await request(`/products/${id}/sold`, {
    method: 'PUT'
  })
}

export default { 
  getProductList, 
  getMyProducts, 
  getProductsByCategory, 
  searchProducts, 
  getProductDetail,
  createProduct,
  updateProduct,
  deleteProduct,
  markAsSold
}

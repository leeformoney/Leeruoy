import request from '@/utils/request'

// 查询媒体分类列表
export function listCategory(query) {
  return request({
    url: '/media/category/list',
    method: 'get',
    params: query
  })
}

// 查询媒体分类详细
export function getCategory(categoryId) {
  return request({
    url: '/media/category/' + categoryId,
    method: 'get'
  })
}

// 新增媒体分类
export function addCategory(data) {
  return request({
    url: '/media/category',
    method: 'post',
    data: data
  })
}

// 修改媒体分类
export function updateCategory(data) {
  return request({
    url: '/media/category',
    method: 'put',
    data: data
  })
}

// 删除媒体分类
export function delCategory(categoryId) {
  return request({
    url: '/media/category/' + categoryId,
    method: 'delete'
  })
}

// 查询媒体分类下拉树结构
export function treeselect() {
  return request({
    url: '/media/category/treeselect',
    method: 'get'
  })
}
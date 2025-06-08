import request from '@/utils/request'

// 查询媒体文件列表
export function listFile(query) {
  return request({
    url: '/media/file/list',
    method: 'get',
    params: query
  })
}

// 查询媒体文件详细
export function getFile(fileId) {
  return request({
    url: '/media/file/' + fileId,
    method: 'get'
  })
}

// 新增媒体文件
export function addFile(data) {
  return request({
    url: '/media/file',
    method: 'post',
    data: data
  })
}

// 修改媒体文件
export function updateFile(data) {
  return request({
    url: '/media/file',
    method: 'put',
    data: data
  })
}

// 删除媒体文件
export function delFile(fileId) {
  return request({
    url: '/media/file/' + fileId,
    method: 'delete'
  })
}

// 上传媒体文件
export function uploadMedia(data) {
  return request({
    url: '/media/file/upload',
    method: 'post',
    data: data
  })
}

// 播放媒体文件
export function playMedia(fileId) {
  return request({
    url: '/media/file/play/' + fileId,
    method: 'post'
  })
}

// 点赞媒体文件
export function likeMedia(fileId) {
  return request({
    url: '/media/file/like/' + fileId,
    method: 'post'
  })
}
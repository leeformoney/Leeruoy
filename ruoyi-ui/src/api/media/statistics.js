import request from '@/utils/request'

// 获取播放统计概览
export function getOverview() {
  return request({
    url: '/media/statistics/overview',
    method: 'get'
  })
}

// 获取播放排行榜
export function getPlayRanking(limit = 10) {
  return request({
    url: '/media/statistics/playRanking',
    method: 'get',
    params: { limit }
  })
}

// 获取点赞排行榜
export function getLikeRanking(limit = 10) {
  return request({
    url: '/media/statistics/likeRanking',
    method: 'get',
    params: { limit }
  })
}

// 获取文件类型分布
export function getTypeDistribution() {
  return request({
    url: '/media/statistics/typeDistribution',
    method: 'get'
  })
}
<template>
  <div class="app-container">
    <h2>播放统计</h2>
    
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="mb20">
      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>总文件数</span>
          </div>
          <div class="text item">
            <span class="number">{{ statistics.totalFiles }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>总播放次数</span>
          </div>
          <div class="text item">
            <span class="number">{{ statistics.totalViews }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>总点赞数</span>
          </div>
          <div class="text item">
            <span class="number">{{ statistics.totalLikes }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>今日播放</span>
          </div>
          <div class="text item">
            <span class="number">{{ statistics.todayViews }}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 热门内容排行 -->
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>播放排行榜</span>
          </div>
          <el-table :data="topPlayedList" style="width: 100%">
            <el-table-column prop="title" label="标题" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column prop="fileType" label="类型" width="80">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.fileType === 'audio'" type="success">音频</el-tag>
                <el-tag v-else-if="scope.row.fileType === 'video'" type="primary">视频</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="viewCount" label="播放次数" width="100"></el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>点赞排行榜</span>
          </div>
          <el-table :data="topLikedList" style="width: 100%">
            <el-table-column prop="title" label="标题" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column prop="fileType" label="类型" width="80">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.fileType === 'audio'" type="success">音频</el-tag>
                <el-tag v-else-if="scope.row.fileType === 'video'" type="primary">视频</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="likeCount" label="点赞数" width="100"></el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>

    <!-- 文件类型分布 -->
    <el-row :gutter="20" class="mt20">
      <el-col :span="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>文件类型分布</span>
          </div>
          <el-row :gutter="20">
            <el-col :span="8">
              <div class="stat-item">
                <div class="stat-title">音频文件</div>
                <div class="stat-value">{{ fileTypeStats.audio }}</div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="stat-item">
                <div class="stat-title">视频文件</div>
                <div class="stat-value">{{ fileTypeStats.video }}</div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="stat-item">
                <div class="stat-title">总文件数</div>
                <div class="stat-value">{{ fileTypeStats.total }}</div>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Statistics",
  data() {
    return {
      // 统计数据
      statistics: {
        totalFiles: 156,
        totalViews: 12580,
        totalLikes: 3420,
        todayViews: 245
      },
      // 播放排行
      topPlayedList: [
        { title: '热门音乐1', fileType: 'audio', viewCount: 1250 },
        { title: '精彩视频1', fileType: 'video', viewCount: 980 },
        { title: '经典音乐2', fileType: 'audio', viewCount: 856 },
        { title: '搞笑视频2', fileType: 'video', viewCount: 742 },
        { title: '流行音乐3', fileType: 'audio', viewCount: 698 }
      ],
      // 点赞排行
      topLikedList: [
        { title: '超赞视频1', fileType: 'video', likeCount: 456 },
        { title: '动听音乐1', fileType: 'audio', likeCount: 389 },
        { title: '精彩视频2', fileType: 'video', likeCount: 342 },
        { title: '经典音乐2', fileType: 'audio', likeCount: 298 },
        { title: '热门视频3', fileType: 'video', likeCount: 267 }
      ],
      // 文件类型统计
      fileTypeStats: {
        audio: 65,
        video: 91,
        total: 156
      }
    };
  }
};
</script>

<style scoped>
.mb20 {
  margin-bottom: 20px;
}

.mt20 {
  margin-top: 20px;
}

.number {
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
}

.text.item {
  text-align: center;
  padding: 20px 0;
}

.stat-item {
  text-align: center;
  padding: 20px;
}

.stat-title {
  font-size: 14px;
  color: #666;
  margin-bottom: 10px;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #409EFF;
}
</style>
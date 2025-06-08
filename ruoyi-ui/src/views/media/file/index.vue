<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="文件名称" prop="fileName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入文件名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件类型" prop="fileType">
        <el-select v-model="queryParams.fileType" placeholder="请选择文件类型" clearable>
          <el-option label="音频" value="audio" />
          <el-option label="视频" value="video" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="fileList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="文件ID" align="center" prop="fileId" width="80" />
      <el-table-column label="媒体标题" align="center" prop="title" />
      <el-table-column label="文件名称" align="center" prop="fileName" :show-overflow-tooltip="true" />
      <el-table-column label="文件类型" align="center" prop="fileType" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.fileType === 'audio'" type="success">音频</el-tag>
          <el-tag v-else-if="scope.row.fileType === 'video'" type="primary">视频</el-tag>
          <el-tag v-else type="info">其他</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="文件格式" align="center" prop="fileFormat" width="100" />
      <el-table-column label="播放次数" align="center" prop="viewCount" width="100" />
      <el-table-column label="点赞数" align="center" prop="likeCount" width="100" />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '0'" type="success">正常</el-tag>
          <el-tag v-else-if="scope.row.status === '1'" type="danger">停用</el-tag>
          <el-tag v-else type="warning">审核中</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-video-play"
            @click="handlePlay(scope.row)"
          >播放</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改媒体文件对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="媒体标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入媒体标题" />
        </el-form-item>
        <el-form-item label="媒体描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入媒体描述" />
        </el-form-item>
        <el-form-item label="标签" prop="tags">
          <el-input v-model="form.tags" placeholder="请输入标签，多个标签用逗号分隔" />
        </el-form-item>
        <el-form-item label="是否公开" prop="isPublic">
          <el-radio-group v-model="form.isPublic">
            <el-radio label="1">公开</el-radio>
            <el-radio label="0">私有</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
            <el-radio label="2">审核中</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="排序" prop="sortOrder">
          <el-input-number v-model="form.sortOrder" :min="0" />
        </el-form-item>
        <el-form-item label="媒体文件" prop="fileUrl" v-if="!form.fileId">
          <el-upload
            class="upload-demo"
            action="/common/upload"
            :headers="upload.headers"
            :file-list="upload.fileList"
            :on-success="handleFileSuccess"
            :before-upload="handleBeforeUpload"
            :limit="1"
            accept=".mp3,.mp4,.avi,.mov,.wmv,.flv,.mkv,.webm,.wav,.wma,.aac,.flac,.ogg,.m4a"
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传音视频文件，且不超过100MB</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 媒体播放对话框 -->
    <el-dialog title="媒体播放" :visible.sync="playOpen" width="800px" append-to-body>
      <div v-if="currentMedia">
        <h3>{{ currentMedia.title }}</h3>
        <p>{{ currentMedia.description }}</p>
        <div v-if="currentMedia.fileType === 'video'">
          <video :src="currentMedia.fileUrl" controls style="width: 100%; max-height: 400px;">
            您的浏览器不支持视频播放
          </video>
        </div>
        <div v-else-if="currentMedia.fileType === 'audio'">
          <audio :src="currentMedia.fileUrl" controls style="width: 100%;">
            您的浏览器不支持音频播放
          </audio>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import { listFile, getFile, addFile, updateFile, delFile, playMedia } from "@/api/media/file";

export default {
  name: "File",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 媒体文件表格数据
      fileList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 播放对话框
      playOpen: false,
      // 当前播放媒体
      currentMedia: null,
      // 上传参数
      upload: {
        headers: { Authorization: "Bearer " + getToken() },
        fileList: []
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fileName: null,
        fileType: null,
        title: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          { required: true, message: "媒体标题不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询媒体文件列表 */
    getList() {
      this.loading = true;
      listFile(this.queryParams).then(response => {
        this.fileList = response.rows;
        this.total = response.total;
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        fileId: null,
        title: null,
        description: null,
        tags: null,
        status: "0",
        isPublic: "1",
        sortOrder: 0,
        fileUrl: null
      };
      this.upload.fileList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.fileId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加媒体文件";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const fileId = row.fileId || this.ids[0];
      getFile(fileId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改媒体文件";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.fileId != null) {
            updateFile(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFile(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const fileIds = row.fileId || this.ids;
      this.$modal.confirm('是否确认删除媒体文件编号为"' + fileIds + '"的数据项？').then(() => {
        delFile(fileIds).then(response => {
          this.$modal.msgSuccess("删除成功");
          this.getList();
        });
      }).catch(() => {});
    },
    /** 播放按钮操作 */
    handlePlay(row) {
      this.currentMedia = row;
      this.playOpen = true;
    },
    /** 文件上传成功处理 */
    handleFileSuccess(response, file) {
      this.form.fileUrl = response.url;
      this.$modal.msgSuccess("上传成功");
    },
    /** 上传前校验格式 */
    handleBeforeUpload(file) {
      const isMedia = /\.(mp3|mp4|avi|mov|wmv|flv|mkv|webm|wav|wma|aac|flac|ogg|m4a)$/i.test(file.name);
      const isLt100M = file.size / 1024 / 1024 < 100;
      
      if (!isMedia) {
        this.$modal.msgError('上传文件只能是音视频格式!');
        return false;
      }
      if (!isLt100M) {
        this.$modal.msgError('上传文件大小不能超过 100MB!');
        return false;
      }
      return true;
    }
  }
};
</script>

<style scoped>
.upload-demo {
  margin-top: 10px;
}
</style>
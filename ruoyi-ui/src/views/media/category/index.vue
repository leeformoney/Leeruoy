 <template>
  <div class="app-container">
    <h2>媒体分类管理</h2>
    
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
    </el-row>

    <el-table
      v-loading="loading"
      :data="categoryList"
      row-key="categoryId"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column prop="categoryName" label="分类名称"></el-table-column>
      <el-table-column prop="orderNum" label="排序" width="200"></el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '0'" type="success">正常</el-tag>
          <el-tag v-else type="danger">停用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改媒体分类对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="分类名称">
          <el-input v-model="form.categoryName" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="显示排序">
          <el-input-number v-model="form.orderNum" :min="0" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Category",
  data() {
    return {
      loading: false,
      categoryList: [
        {
          categoryId: 1,
          categoryName: '音频',
          parentId: 0,
          orderNum: 1,
          status: '0',
          children: [
            {
              categoryId: 11,
              categoryName: '音乐',
              parentId: 1,
              orderNum: 1,
              status: '0'
            },
            {
              categoryId: 12,
              categoryName: '播客',
              parentId: 1,
              orderNum: 2,
              status: '0'
            }
          ]
        },
        {
          categoryId: 2,
          categoryName: '视频',
          parentId: 0,
          orderNum: 2,
          status: '0',
          children: [
            {
              categoryId: 21,
              categoryName: '电影',
              parentId: 2,
              orderNum: 1,
              status: '0'
            },
            {
              categoryId: 22,
              categoryName: '短视频',
              parentId: 2,
              orderNum: 2,
              status: '0'
            }
          ]
        }
      ],
      title: "",
      open: false,
      form: {}
    };
  },
  methods: {
    handleAdd(row) {
      this.form = {
        categoryName: '',
        orderNum: 0,
        status: '0',
        parentId: row ? row.categoryId : 0
      };
      this.title = "添加媒体分类";
      this.open = true;
    },
    handleUpdate(row) {
      this.form = Object.assign({}, row);
      this.title = "修改媒体分类";
      this.open = true;
    },
    handleDelete(row) {
      this.$modal.confirm('是否确认删除分类"' + row.categoryName + '"？').then(() => {
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    submitForm() {
      this.$modal.msgSuccess("操作成功");
      this.open = false;
    },
    cancel() {
      this.open = false;
    }
  }
};
</script>

<style scoped>
.mb8 {
  margin-bottom: 8px;
}
</style> 
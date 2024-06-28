<template>
  <div>
    <!-- 搜索框 -->
    <div>
      <el-input
          style="width: 240px"
          placeholder="请输入用户名"
          v-model="params.username"
      ></el-input>
      <el-input
          style="width: 240px; margin-left: 5px"
          placeholder="请输入评价内容"
          v-model="params.content"
      ></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="loadData">
        <i class="el-icon-search"></i>&ensp;搜索
      </el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset">
        <i class="el-icon-refresh"></i>&ensp;重置
      </el-button>
      <el-button style="margin-left: 5px" type="success" @click="handleAdd">
        <i class="el-icon-document-add"></i>&ensp;添加书评
      </el-button>
    </div>

    <!-- 数据 -->
    <el-table :data="tableData" stripe>
      <el-table-column prop="id" label="编号" width="50px"></el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="content" label="评价内容"></el-table-column>
      <el-table-column prop="reviewTime" label="评价时间" width="180px"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150px">
        <template #default="scope">
          <el-popconfirm
              title="您确定要删除吗?"
              @confirm="handleDelete(scope.row.id)"
          >
            <template #reference>
              <el-button size="mini" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 编辑时的弹窗 -->
    <el-dialog
        :title="form.id ? '编辑书评' : '添加书评'"
        :visible.sync="dialogVisible"
        width="30%"
    >
      <el-form
          :model="form"
          label-width="120px"
          :rules="rules"
          ref="ruleForm"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" style="width: 75%"></el-input>
        </el-form-item>
        <el-form-item label="评价内容" prop="content">
          <el-input
              type="textarea"
              v-model="form.content"
              style="width: 75%"
          ></el-input>
        </el-form-item>
        <el-form-item label="评价时间" prop="reviewTime">
          <el-date-picker
              v-model="form.reviewTime"
              type="datetime"
              style="width: 75%"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveData">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import request from "../../utils/request";
import Cookies from "js-cookie";

export default {
  name: 'Review',
  data() {
    return {
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
      form: {},
      dialogVisible: false,
      tableData: [],
      params: {
        username: '',
        content: '',
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        content: [
          {required: true, message: '请输入评价内容', trigger: 'blur'}
        ],
      }
    }
  },
  created() {
    this.loadData();
  },
  methods: {
    loadData() {
      request.get("/review/all").then(res => {
        console.log(res)
        if (res) {
          this.tableData = res;
        } else {
          alert(res.msg);
        }
      })
    },
    reset() {
      this.params = {
        username: '',
        content: '',
      };
      this.loadData();
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
    },
    handleAdd() {
      this.form = {};
      this.dialogVisible = true;
    },
    saveData() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/review/add', this.form).then(res => {
            if (res) {
              alert("新增成功");
              this.dialogVisible = false;
              this.loadData();
            } else {
              alert(res.msg);
            }
          })
        }
      })
    },
    handleDelete(id) {
      request.delete('/review/delete/' + id).then(res => {
        if (res) {
          alert("删除成功");
          this.loadData();
        } else {
          alert(res.msg);
        }
      })
    },
  }
}
</script>

<template>
    <div>
        <!--搜索框-->
        <div>
            <el-input style="width: 240px" placeholder="请输入分类名称" v-model="params.name"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>&ensp;搜索</el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>&ensp;重置</el-button>
        </div>

        <!--数据-->
        <el-table :data="tableData" stripe row-key="id" default-expand-all>
            <el-table-column prop="id" label="编号"></el-table-column>
            <el-table-column prop="name" label="名称"></el-table-column>
            <el-table-column prop="remark" label="备注"></el-table-column>
            <el-table-column prop="createTime" label="创建时间"></el-table-column>
            <el-table-column prop="updateTime" label="更新时间"></el-table-column>
            <el-table-column fixed="right" label="操作" width="305px">
                <template #default="scope">
                    <el-button size="mini" type="success" v-if="!scope.row.pid" @click="handleADD(scope.row)">添加二级分类</el-button>
                    <el-button size="mini" type="primary" style="margin-left: 5px" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-popconfirm title="您确定要删除吗?" @confirm="handleDelete(scope.row.id)">
                        <template #reference>
                            <el-button size="mini" style="margin-left: 5px" type="danger">删除</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <!--分页-->
        <div style="margin-top: 20px">
            <el-pagination
                background
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                layout="total, sizes, prev, pager, next, jumper"
                :current-page="params.pageNum"
                :page-sizes="[5, 10, 20]"
                :page-size="params.pageSize"
                :total="total">
            </el-pagination>
        </div>

        <!--编辑时的弹窗-->
        <el-dialog title="编辑分类" :visible.sync="dialogVisible" width="30%">
            <el-form :model="form" label-width="120px" :rules="rules" ref="ruleForm">
                <el-form-item label="名称：" prop="name">
                    <el-input v-model="form.name" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="备注：">
                    <el-input v-model="form.remark" style="width: 75%"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="update">确认</el-button>
              </span>
            </template>
        </el-dialog>

        <!--添加二级分类时的弹窗-->
        <el-dialog title="添加二级分类" :visible.sync="dialogVisible2" width="35%">
            <el-form :model="form2" label-width="120px" :rules="rules" ref="ruleForm2">
                <el-form-item label="父级id：">
                    <el-input v-model="form2.pid" readonly style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="名称：" prop="name">
                    <el-input v-model="form2.name" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="备注：">
                    <el-input v-model="form2.remark" style="width: 75%"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="dialogVisible2 = false">取消</el-button>
                <el-button type="primary" @click="update2">确认</el-button>
              </span>
            </template>
        </el-dialog>

    </div>
</template>

<script>

    import request from "../../utils/request";

    export default {
        name: 'Category',
        data() {
            return {
                form: {},
                form2: {},
                dialogVisible: false,
                dialogVisible2: false,
                tableData: [],
                total: 0,
                params: {
                    name: '',
                    pageNum: 1,
                    pageSize: 10,
                },
                rules: {
                    name: [
                        { required: true, message: '该项必须填写哦', trigger: 'blur'},
                    ],
                }
            }
        },
        //页面加载之后会调用
        created() {
            this.load();
        },
        methods: {

            load() {
                request.get("/category/page", { params: this.params}).then(res => {
                    if (res.code === '200'){
                        this.tableData = res.data.list;
                        this.total = res.data.total;
                    } else {
                        alert(res.msg);
                    }
                })
            },

            reset() {
                this.params = {
                    name: '',
                    pageNum: 1,
                    pageSize: 10,
                };
                this.load();
            },

            handleCurrentChange(pageNum){
                this.params.pageNum = pageNum;
                this.load();
            },

            handleSizeChange(pageSize){
                this.params.pageSize = pageSize;
                this.load();
            },

            handleEdit(row){
                this.form = JSON.parse(JSON.stringify(row));
                this.dialogVisible = true;
            },

            handleADD(row){
                this.form2 = {};
                this.form2.pid = JSON.parse(JSON.stringify(row)).id;
                this.dialogVisible2 = true;
            },

            update(){
                this.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        request.post('/category/update',this.form).then(res => {
                            if (res.code === '200'){
                                alert("修改成功");
                                this.dialogVisible = false;
                                this.load();
                            } else {
                                alert(res.msg);
                            }
                        })
                    }
                })
            },

            update2(){
                this.$refs['ruleForm2'].validate((valid) => {
                    if (valid) {
                        request.post("/category/add2",this.form2).then(res => {
                            if (res.code === '200'){
                                this.$notify.success("新增成功");
                                this.dialogVisible2 = false;
                                this.load();
                            } else {
                                this.$notify.error(res.msg);
                            }
                        })
                    }
                })
            },

            handleDelete(id){
                request.delete('/category/delete/' + id).then(res => {
                    if (res.code === '200'){
                        alert("删除成功");
                        this.load();
                    } else {
                        alert(res.msg);
                    }
                })
            }

        }
    }
</script>


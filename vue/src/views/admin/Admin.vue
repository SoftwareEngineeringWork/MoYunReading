<template>
    <div>
        <!--搜索框-->
        <div>
            <el-input style="width: 240px" placeholder="请输入用户名" v-model="params.username"></el-input>
            <el-input style="width: 240px; margin-left: 5px" placeholder="请输入联系方式" v-model="params.phone"></el-input>
            <el-input style="width: 240px; margin-left: 5px" placeholder="请输入邮箱" v-model="params.email"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>&ensp;搜索</el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>&ensp;重置</el-button>
        </div>

        <!--数据-->
        <el-table :data="tableData" stripe>
            <el-table-column prop="id" label="编号" width="70px" sortable></el-table-column>
            <el-table-column prop="username" label="用户名"></el-table-column>
            <el-table-column prop="phone" label="电话"></el-table-column>
            <el-table-column prop="createtime" label="创建时间"></el-table-column>
            <el-table-column prop="updatetime" label="更新时间"></el-table-column>

            <el-table-column fixed="right" label="操作" width="400px">
                <template #default="scope">
                    <el-tooltip :content="'Switch value: ' + scope.row.status " placement="top">
                        <el-switch
                                v-model="scope.row.status"
                                active-color="#13ce66"
                                inactive-color="#ff4949"
                                @change="changeStatus(scope.row)"
                                :active-value="1"
                                :inactive-value="0" >
                        </el-switch>
                    </el-tooltip>
                    <el-button style="margin-left: 5px" size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-popconfirm title="您确定要删除吗?" @confirm="handleDelete(scope.row.id)">
                        <template #reference>
                            <el-button style="margin-left: 5px" size="mini" type="danger">删除</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <!--分页-->
        <div style="margin-top: 20px">
            <el-pagination
                    background
                    layout="total, prev, pager, next"
                    :current-page="params.pageNum"
                    :page-size="params.pageSize"
                    @current-change="handleCurrentChange"
                    :total="total">
            </el-pagination>
        </div>

        <!--编辑时的弹窗-->
        <el-dialog title="编辑信息" :visible.sync="dialogVisible" width="30%">
            <el-form :model="form" label-width="120px">
                <el-form-item label="编号">
                    <el-input v-model="form.id" disabled style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="用户名">
                    <el-input v-model="form.username" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="form.phone" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="form.email" style="width: 75%"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="update">确认</el-button>
              </span>
            </template>
        </el-dialog>

        <!--修改密码时的弹窗-->
        <el-dialog title="修改密码" :visible.sync="dialogVisible2" width="30%">
            <el-form :model="pass" label-width="100px" ref="formRef" :rules="rules">
                <el-form-item label="新密码:" prop="password">
                    <el-input type="password" show-password v-model="pass.password" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible2 = false">取 消</el-button>
                <el-button type="primary" @click="savePass">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>

    import request from "../../utils/request";
    import Cookies from 'js-cookie';

    export default {
        name: 'Admin',
        data() {
            return {
                form: {},
                pass: {},
                dialogVisible: false,
                dialogVisible2: false,
                tableData: [],
                total: 0,
                params: {
                    username: '',
                    phone: '',
                    email:'',
                    pageNum: 1,
                    pageSize: 10,
                },
                rules: {
                    password: [
                        {required: true, message: '请输入新密码', trigger: 'blur'},
                        {min: 3, max: 20, message: '长度在3-20个字符', trigger: 'blur' }
                    ]
                }
            }
        },
        //页面加载之后会调用
        created() {
            this.load();
        },

        methods: {

            load() {
                request.get("/admin/page", { params: this.params }).then(res => {
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
                    username: '',
                    phone: '',
                    pageNum: 1,
                    pageSize: 10,
                };
                this.load();
            },

            handleCurrentChange(pageNum){
                this.params.pageNum = pageNum;
                this.load();
            },

            handleEdit(row){
                this.form = JSON.parse(JSON.stringify(row));
                this.dialogVisible = true;
            },

            update(){
                request.post('/admin/update',this.form).then(res => {
                    if (res.code === '200'){
                        alert("修改成功");
                        this.dialogVisible = false;
                        this.load();
                    } else {
                        alert(res.msg);
                    }
                })
            },

            handleDelete(id){
                request.delete('/admin/delete/' + id).then(res => {
                    if (res.code === '200'){
                        alert("删除成功");
                        this.load();
                    } else {
                        alert(res.msg);
                    }
                })
            },

            handleChangePassword(row) {
                this.pass = JSON.parse(JSON.stringify(row));
                this.pass.password = '';
                this.dialogVisible2 = true;
            },

            savePass() {
                this.$refs['formRef'].validate((valid) => {
                    if (valid) {
                        request.put('/admin/update',this.pass).then(res => {
                            if (res.code === '200') {
                                this.$notify.success("修改成功");
                                if (this.pass.id === JSON.parse(Cookies.get('user')).id){
                                    alert("修改成功，请重新登录！");
                                    Cookies.remove('user');
                                    this.dialogVisible2 = false;
                                    this.$router.push('/login');
                                } else {
                                    this.pass = {};
                                    this.dialogVisible2 = false;
                                    this.load();
                                }
                            } else {
                                this.$notify.error(res.msg);
                            }
                        })
                    }
                })
            },

            changeStatus(row) {
                if (row.id === JSON.parse(Cookies.get('user')).id){
                    row.status = 1;
                    this.$notify.warning("您的操作不合法，不可以禁用自己账号");
                } else {
                    request.put('/admin/delete/' + row.id).then(res => {
                        if (res.code === '200'){
                            this.$notify.success("更新成功");
                            this.load();
                        }
                    })
                }
            }
        }
    }
</script>


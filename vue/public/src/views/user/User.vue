<template>
    <div>
        <!--搜索框-->
        <div>
            <el-input style="width: 240px" placeholder="请输入姓名" v-model="params.name"></el-input>
            <el-input style="width: 240px; margin-left: 5px" placeholder="请输入联系方式" v-model="params.phone"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>&ensp;搜索</el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>&ensp;重置</el-button>
        </div>

        <!--数据-->
        <el-table :data="tableData" stripe>
            <el-table-column prop="id" label="编号" width="70px" sortable></el-table-column>
            <el-table-column prop="username" label="会员号" width="135px"></el-table-column>
            <el-table-column prop="name" label="姓名" width="75px"></el-table-column>
            <el-table-column prop="age" label="年龄" width="50px"></el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column prop="sex" label="性别" width="70px"></el-table-column>
            <el-table-column prop="phone" label="电话" width="135px"></el-table-column>
            <el-table-column prop="account" label="积分" width="70px"></el-table-column>
            <el-table-column prop="createtime" label="创建时间" width="150px"></el-table-column>
            <el-table-column fixed="right" label="操作" width="250px">
                <template #default="scope">
                    <el-button size="mini" type="success" @click="accountEdit(scope.row)">充值</el-button>
                    &nbsp;
                    <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
                    &nbsp;
                    <el-popconfirm title="您确定要删除吗?" @confirm="handleDelete(scope.row.id)">
                        <template #reference>
                            <el-button size="mini" type="danger">删除</el-button>
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
        <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
            <el-form :model="form" label-width="120px">
                <el-form-item label="会员号">
                    <el-input v-model="form.username" disabled style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="form.name" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-radio v-model="form.sex" label="男">男</el-radio>
                    <el-radio v-model="form.sex" label="女">女</el-radio>
                </el-form-item>
                <el-form-item label="年龄" prop="age">
                    <el-input v-model="form.age" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="联系方式" prop="phone">
                    <el-input v-model="form.phone" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="form.email" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="form.address" style="width: 75%"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="update">确认</el-button>
              </span>
            </template>
        </el-dialog>

        <!--充值时的弹窗-->
        <el-dialog title="提示" :visible.sync="dialogVisible2" width="30%">
            <el-form :model="form2" label-width="120px" :rules="rules" ref="ruleForm">
                <el-form-item label="现有积分">
                    <el-input v-model="form2.account" disabled style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="充值金额" prop="money">
                    <el-input v-model="form2.money" style="width: 75%"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="dialogVisible2 = false">取消</el-button>
                <el-button type="primary" @click="updateAccount">确认</el-button>
              </span>
            </template>
        </el-dialog>

    </div>
</template>

<script>

    import request from "../../utils/request";

    export default {
        name: 'User',
        data() {
            const checkNums = (rule, value, callback) => {
                value = parseInt(value);
                if (value <= 0 || value >= 1000) {
                    callback(new Error('请输入大于0小于1000的整数'));
                }
                callback()
            };
            const checkEmail = (rule, value, callback) => {
                const reg = /^([a-zA-Z0-9]+[-_\.]?)+@[a-zA-Z0-9]+\.[a-z]+$/;
                if (value == '' || value == undefined || value == null) {
                    callback();
                } else {
                    if (!reg.test(value)) {
                        callback(new Error('请输入正确的邮箱地址'));
                    } else {
                        callback();
                    }
                }
            };
            return {
                form: {},
                form2: {},
                dialogVisible: false,
                dialogVisible2: false,
                tableData: [],
                total: 0,
                params: {
                    name: '',
                    phone: '',
                    pageNum: 1,
                    pageSize: 10,
                },
                rules: {
                    money: [
                        { required: true, message: '请输入充值金额', trigger: 'blur'},
                        { validator: checkNums, trigger: 'blur' }
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
                request.get("/user/page", { params: this.params }).then(res => {
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

            handleSizeChange(pageSize){
                this.params.pageSize = pageSize;
                this.load();
            },

            handleEdit(row){
                this.form = JSON.parse(JSON.stringify(row));
                this.dialogVisible = true;
            },

            accountEdit(row){
                this.form2 = JSON.parse(JSON.stringify(row));
                this.dialogVisible2 = true;
            },

            update(){
                request.post('/user/update',this.form).then(res => {
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
                request.delete('/user/delete/' + id).then(res => {
                    if (res.code === '200'){
                        alert("删除成功");
                        this.load();
                    } else {
                        alert(res.msg);
                    }
                })
            },

            updateAccount(){
                this.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        request.post('/user/recharge',this.form2).then(res => {
                            if (res.code === '200'){
                                alert("充值成功");
                                this.dialogVisible2 = false;
                                this.form2 = {};
                                this.load();
                            } else {
                                alert(res.msg);
                            }
                        })
                    }
                })
            },

        }
    }
</script>


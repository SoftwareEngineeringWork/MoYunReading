<template>
    <div>
        <!--搜索框-->
        <div>
            <el-input style="width: 240px" placeholder="请输入书名" v-model="params.bookName"></el-input>
            <el-input style="width: 240px; margin-left: 5px" placeholder="请输入书圈号" v-model="params.userName"></el-input>
            <el-input style="width: 240px; margin-left: 5px" placeholder="请输入创建人手机号" v-model="params.userPhone"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>&ensp;搜索</el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>&ensp;重置</el-button>
            <el-button style="margin-left: 5px" type="success" @click="handleAdd"><i class="el-icon-document-add"></i>&ensp;新增借阅 </el-button>
        </div>

        <!--数据-->
        <el-table :data="tableData" stripe>
            <el-table-column prop="id" label="编号" width="50px"></el-table-column>
            <el-table-column prop="bookName" label="书名" width="130px"></el-table-column>
            <el-table-column prop="bookNum" label="书圈号"></el-table-column>
            <el-table-column prop="userName" label="创办人姓名"></el-table-column>
            <el-table-column prop="userPhone" label="创办人手机号"></el-table-column>
            <el-table-column prop="score" label="积分" width="50px"></el-table-column>
            <el-table-column prop="days" label="成立天数"></el-table-column>
            <el-table-column fixed="right" label="操作" width="270px">
                <template #default="scope">
                    <el-popconfirm title="您确定要关闭吗?" @confirm="handleEdit(scope.row)">
                        <template #reference>
                            <el-button size="mini" type="success"> 关闭 </el-button>
                        </template>
                    </el-popconfirm>&nbsp;
                    <el-button style="margin-left: 4px" size="mini" type="primary" v-if="scope.row.days > 30" @click="handleEdit(scope.row)"> 升级 </el-button>
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

        <!--本来是打算做借书编辑的，后来考虑到编辑时需要做的复杂后端改动，例如删除数据，归还积分，重新生成，故删除了编辑功能-->
        <!--编辑时的弹窗-->
        <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
            <el-form :model="form" label-width="120px" :rules="rules" ref="ruleForm">
                <el-form-item label="书名">
                    <el-input v-model="form.bookName" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="序列号" prop="bookNum">
                    <el-select v-model="form.bookNum" clearable filterable placeholder="请选择" @change="selBook">
                        <el-option
                                v-for="item in books"
                                :key="item.id"
                                :label="item.bookNum"
                                :value="item.bookNum">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="会员码" prop="userId">
                    <el-select v-model="form.userId" filterable placeholder="请选择" @change="selUser">
                        <el-option
                                v-for="item in users"
                                :key="item.id"
                                :label="item.username"
                                :value="item.username">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="借阅者姓名">
                    <el-input v-model="form.userName" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="借阅者联系方式">
                    <el-input v-model="form.userPhone" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="借阅者剩余积分" v-if="!this.form.id">
                    <el-input v-model="form.account" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="所需积分">
                    <el-input disabled v-model="form.score" style="width: 75%"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="update">确认</el-button>
              </span>
            </template>
        </el-dialog>

    </div>
</template>

<script>

    import request from "../../utils/request";
    import Cookies from "js-cookie";

    export default {
        name: 'BookBorrow',
        data() {
            return {
                user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
                form: {},
                books: [],
                users: [],
                dialogVisible: false,
                tableData: [],
                total: 0,
                params: {
                    userName: '',
                    bookName: '',
                    userPhone: '',
                    pageNum: 1,
                    pageSize: 10,
                },
                rules: {
                    bookNum: [
                        { required: true, message: '请输入/选择图书标准码', trigger: 'blur'}
                    ],
                    userId: [
                        { required: true, message: '请输入/选择会员码', trigger: 'blur'}
                    ]
                }
            }
        },
        //页面加载之后会调用
        created() {
            this.init();
            this.load();
        },
        methods: {

            init() {
                request.get('/book/list').then(res => {
                    this.books = res.data;
                });
                request.get('/user/list').then(res => {
                    this.users = res.data;
                });
            },

            load() {
                request.get("/borrow/page", { params: this.params }).then(res => {
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
                    userName: '',
                    bookName: '',
                    userPhone: '',
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
                request.post('/borrow/remind',this.form).then(res => {
                    if (res.code === '200'){
                        alert("邮件提醒成功~");
                        this.form = {};
                        this.load();
                    } else {
                        alert(res.msg);
                    }
                })
            },

            handleAdd(){
                this.init();
                this.form = {};
                this.dialogVisible = true;
            },

            update(){
                // if (this.form.id){
                //     this.$refs['ruleForm'].validate((valid) => {
                //         if (valid) {
                //             request.post('/borrow/update',this.form).then(res => {
                //                 if (res.code === '200'){
                //                     alert("修改成功");
                //                     this.dialogVisible = false;
                //                     this.$refs['ruleForm'].resetFields();
                //                     this.load();
                //                 } else {
                //                     alert(res.msg);
                //                 }
                //             })
                //         }
                //     });
                // } else {
                this.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        request.post('/borrow/add',this.form).then(res => {
                            if (res.code === '200'){
                                alert("借阅成功");
                                this.dialogVisible = false;
                                this.$refs['ruleForm'].resetFields();
                                this.load();
                            } else {
                                alert(res.msg);
                            }
                        })
                    }
                })
            },

            handleDelete(id){
                request.delete('/borrow/delete/' + id).then(res => {
                    if (res.code === '200'){
                        alert("撤销成功");
                        this.load();
                    } else {
                        alert(res.msg);
                    }
                })
            },

            selBook() {
                let book = this.books.find(v => v.bookNum === this.form.bookNum);
                request.get('/book/' + book.id).then(res => {
                    this.$set(this.form, 'score', res.data.score);
                    this.$set(this.form, 'bookName', res.data.name);
                })
            },

            selUser() {
                let user = this.users.find(v => v.username === this.form.userId)
                request.get('/user/' + user.id).then(res => {
                    this.$set(this.form, 'userName', res.data.name);
                    this.form.userPhone = res.data.phone;
                    this.form.account = res.data.account;
                })
            }

        }
    }
</script>


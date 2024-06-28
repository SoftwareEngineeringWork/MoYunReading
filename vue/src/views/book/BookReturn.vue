<template>
    <div>
        <!--搜索框-->
        <div>
            <el-input style="width: 240px" placeholder="请输入书名" v-model="params.bookName"></el-input>
            <el-input style="width: 240px; margin-left: 5px" placeholder="请输入借阅者姓名" v-model="params.userName"></el-input>
            <el-input style="width: 240px; margin-left: 5px" placeholder="请输入借阅者手机号" v-model="params.userPhone"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>&ensp;搜索</el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>&ensp;重置</el-button>
        </div>

        <!--数据-->
        <el-table :data="tableData" stripe>
            <el-table-column prop="id" label="编号" width="50px"></el-table-column>
            <el-table-column prop="bookName" label="书名" width="130px"></el-table-column>
            <el-table-column prop="bookNum" label="序列号"></el-table-column>
            <el-table-column prop="userId" label="借阅者ID" width="150px"></el-table-column>
            <el-table-column prop="userName" label="借阅者姓名"></el-table-column>
            <el-table-column prop="userPhone" label="借阅者电话"></el-table-column>
            <el-table-column prop="score" label="积分" width="50px"></el-table-column>
            <el-table-column prop="createTime" label="借出时间"></el-table-column>
            <el-table-column prop="returnTime" label="归还时间"></el-table-column>
            <el-table-column prop="days" label="借出天数"></el-table-column>
            <el-table-column prop="status" label="状态"></el-table-column>
            <el-table-column fixed="right" label="操作" width="170px">
                <template #default="scope">
                    <el-button size="mini" type="primary" @click="handleEdit(scope.row)"> 详情 </el-button>
                    <el-popconfirm title="您确定要删除吗?(记录会回退到借书记录中)" style="margin-left: 5px" @confirm="handleDelete(scope.row.id)">
                        <template #reference>
                            <el-button style="margin-left: 5px" size="mini" type="danger"> 删除 </el-button>
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

        <!--详情时的弹窗-->
        <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
            <el-form :model="form" label-width="120px" style="margin-top: 5px">
                <el-form-item label="书名">
                    <el-input v-model="form.bookName" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="图书序列号">
                    <el-input v-model="form.bookNum" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="借阅者ID">
                    <el-input v-model="form.userId" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="借阅者姓名">
                    <el-input v-model="form.userName" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="借阅者联系方式">
                    <el-input v-model="form.userPhone" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="借出时间">
                    <el-input v-model="form.createTime" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="归还时间">
                    <el-input v-model="form.returnTime" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="借出天数">
                    <el-input v-model="form.days" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="消耗积分">
                    <el-input v-model="form.score" style="width: 75%"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button type="primary" @click="dialogVisible = false"> 返回 </el-button>
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
            }
        },
        //页面加载之后会调用
        created() {
            this.load();
        },
        methods: {

            load() {
                request.get("/borrow/page2", { params: this.params }).then(res => {
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
                this.dialogVisible = true;
            },

            handleDelete(id){
                request.delete('/borrow/return/' + id).then(res => {
                    if (res.code === '200'){
                        alert("删除成功");
                        this.load();
                    } else {
                        alert(res.msg);
                    }
                })
            },

        }
    }
</script>


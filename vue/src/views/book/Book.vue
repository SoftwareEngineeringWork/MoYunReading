<template>
    <div>
        <!--搜索框-->
        <div>
            <el-input style="width: 240px" placeholder="请输入书名" v-model="params.name"></el-input>
            <el-input style="width: 240px; margin-left: 5px" placeholder="请输入作者" v-model="params.author"></el-input>
            <el-input style="width: 240px; margin-left: 5px" placeholder="请输入序列号" v-model="params.bookNum"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>&ensp;搜索</el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>&ensp;重置</el-button>
            <el-button style="margin-left: 5px" type="success" @click="handleAdd"><i class="el-icon-document-add"></i>&ensp;添加图书</el-button>
        </div>

        <!--数据-->
        <el-table :data="tableData" stripe>
            <el-table-column prop="id" label="编号" width="50px"></el-table-column>
            <el-table-column prop="name" label="书名"></el-table-column>
            <el-table-column prop="author" label="作者"></el-table-column>
            <el-table-column prop="publisher" label="出版社"></el-table-column>
            <el-table-column prop="publishDate" label="出版时间"></el-table-column>
            <el-table-column prop="bookNum" label="序列号"></el-table-column>
            <el-table-column prop="category" label="分类"></el-table-column>
            <el-table-column label="封面">
                <template #default="scope">
                    <el-image
                            style="width: 100px; height: 100px"
                            :src="scope.row.cover"
                            :preview-src-list="[scope.row.cover]"
                            :preview-teleported="true">
                    </el-image>
                    <!-- preview-teleported="true" 解决图片穿模问题 -->
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="250px">
                <template #default="scope">

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
                :page-sizes="[4, 10, 20]"
                :page-size="params.pageSize"
                :total="total">
            </el-pagination>
        </div>

        <!--编辑时的弹窗-->
        <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
            <el-form :model="form" label-width="120px" :rules="rules" ref="ruleForm">
                <el-form-item label="书名" prop="name">
                    <el-input v-model="form.name" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input type="textarea" v-model="form.description" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="分类" prop="category">
                    <el-cascader
                            :props="{value: 'name', label: 'name'}"
                            v-model="form.categories"
                            :options="categories">
                    </el-cascader>
                </el-form-item>
                <el-form-item label="作者">
                    <el-input v-model="form.author" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="出版社">
                    <el-input v-model="form.publisher" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="出版日期">
                    <el-date-picker @input="$forceUpdate()" v-model="form.publishDate" value-format="yyyy-MM-dd" type="date" style="width: 75%"></el-date-picker>
                </el-form-item>
                <el-form-item label="库存数量" prop="nums">
                    <el-input v-model="form.nums" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="序列号" prop="bookNum">
                    <el-input v-model="form.bookNum" style="width: 75%"></el-input>
                </el-form-item>
                <el-form-item label="借阅积分" prop="score">
                    <el-input-number v-model="form.score" :min="1" :max="30" style="width: 50%"></el-input-number>
                </el-form-item>
                <el-form-item label="封面">
                    <el-upload ref="upload" :action="'http://localhost:9090/api/files/upload?token=' + this.user.token" :on-success="filesUploadSuccess">
                        <el-button type="primary">点击上传</el-button>
                    </el-upload>
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
        name: 'Book',
        data() {
            const checkNums = (rule, value, callback) => {
                value = parseInt(value);
                if (value < 0 || value >= 1000) {
                    callback(new Error('请输入大于等于0小于1000的整数'));
                }
                callback()
            };
            return {
                user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
                form: {},
                categories: [],
                dialogVisible: false,
                tableData: [],
                total: 0,
                params: {
                    name: '',
                    author: '',
                    bookNum: '',
                    pageNum: 1,
                    pageSize: 4,
                },
                rules: {
                    name: [
                        { required: true, message: '请输入书名', trigger: 'blur'}
                    ],
                    nums: [
                        { required: true, message: '请输入库存数量', trigger: 'blur'},
                        { validator: checkNums, trigger: 'blur' }
                    ],
                    score: [
                        { required: true, message: '请选择借阅积分', trigger: 'blur'},
                    ],
                    bookNum: [
                        { required: true, message: '请输入图书标准码', trigger: 'blur'},
                        { min: 11, max: 11, message: '长度为11个字符', trigger: 'blur' }
                    ],
                }
            }
        },
        //页面加载之后会调用
        created() {
            this.getCategory();
            this.load();
        },
        methods: {

            getCategory(){
                request.get('/category/tree').then(res => {
                    this.categories = res.data;
                });
            },

            load() {
                request.get("/book/page", { params: this.params }).then(res => {
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
                    author: '',
                    bookNum: '',
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
                if (this.$refs['upload']){
                    this.$refs['upload'].clearFiles();  //清除历史文件列表
                }
                this.getCategory();
                this.form = JSON.parse(JSON.stringify(row));
                if (this.form.category) {
                    this.form.categories = this.form.category.split(' > ')
                }
                this.dialogVisible = true;
            },

            handleAdd(){
                if (this.$refs['upload']){
                    this.$refs['upload'].clearFiles();  //清除历史文件列表
                }
                this.getCategory();
                this.form = {};
                this.dialogVisible = true;
            },

            update(){
                if (this.form.id){
                    this.$refs['ruleForm'].validate((valid) => {
                        if (valid) {
                            request.post('/book/update',this.form).then(res => {
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
                } else {
                    this.$refs['ruleForm'].validate((valid) => {
                        if (valid) {
                            request.post('/book/add',this.form).then(res => {
                                if (res.code === '200'){
                                    alert("新增成功");
                                    this.dialogVisible = false;
                                    this.load();
                                } else {
                                    alert(res.msg);
                                }
                            })
                        }
                    })
                }
            },

            handleDelete(id){
                request.delete('/book/delete/' + id).then(res => {
                    if (res.code === '200'){
                        alert("删除成功");
                        this.load();
                    } else {
                        alert(res.msg);
                    }
                })
            },

            filesUploadSuccess(res){
                console.log(res);
                this.form.cover = res.data;
            },

        }
    }
</script>


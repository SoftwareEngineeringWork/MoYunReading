<template>
    <div style="width: 80%">
        <div style="margin-bottom: 30px">新增管理员</div>
        <el-form :inline="true" :model="form" label-width="100px" :rules="rules" ref="ruleForm">
            <el-form-item label="用户名" prop="username">
                <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
            </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.email" placeholder="请输入密码"></el-input>
          </el-form-item>
            <el-form-item label="联系方式" prop="phone">
                <el-input v-model="form.phone" placeholder="请输入联系方式" style=""></el-input>
            </el-form-item>
        </el-form>
        <div style="text-align: center; margin-top: 30px">
            <el-button type="primary" @click="save" size="medium">提交</el-button>
        </div>
    </div>
</template>

<script>
    import request from "@/utils/request";

    export default {
        name: "AddAdmin",
        data() {

            return {
                form: {},
                //表单校验规则
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur'},
                        { min: 3, max: 10, message: '用户名请在3-10字符之间', trigger: 'blur'}
                    ],
                    phone: [
                        { required: true, message: '手机号码不能为空', trigger: 'blur' },
                        {
                            pattern: /^[1]([3-9])[0-9]{9}$/,
                            message: '请输入有效的手机号码',
                            trigger: 'blur'
                        }
                    ]
                }

            }
        },
        methods: {
            save() {
                this.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        request.post("/admin/add",this.form).then(res => {
                            if (res.code === '200'){
                                this.$notify.success("新增成功");
                                this.form = {};
                            } else {
                                this.$notify.error(res.msg);
                            }
                        })
                    }
                })
            }
        }
    }
</script>


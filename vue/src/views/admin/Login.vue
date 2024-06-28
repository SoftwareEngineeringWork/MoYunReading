<template>
    <div style="height: 100vh; position: relative; overflow: hidden">
        <el-card class="cover" v-if="loginAdmin.id">
            <slide-verify :l="42"
                          :r="10"
                          :w="310"
                          :h="155"
                          :accuracy="5"
                          :imgs="['http://localhost:9090/api/files/3.jpg',
                    'http://localhost:9090/api/files/4.png',
                    'http://localhost:9090/api/files/5.jpg']"
                          slider-text="向右滑动"
                          @success="onSuccess"
                          @fail="onFail"
                          @refresh="onRefresh"
            ></slide-verify>
        </el-card>
        <div style="width: 500px; height: 400px; background-color: white; border-radius: 10px; margin: 150px auto; padding:50px">
            <div style="margin: 30px; text-align: center; font-size: 30px; font-weight: bold; color: dodgerblue">登 录</div>
            <el-form :model="admin" ref="loginForm">
                <el-form-item prop="username">
                    <el-input placeholder="请输入账号" prefix-icon="el-icon-user" size="medium" v-model="admin.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" show-password="show-password" placeholder="请输入账号" prefix-icon="el-icon-lock" size="medium" v-model="admin.password"></el-input>
                </el-form-item>
                <div>{{msg}}</div>
                <el-form-item>
                    <el-button style="width: 100%" size="medium" type="primary" @click="login">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import request from "../../utils/request";
    import Cookies from 'js-cookie';

    export default {
        name: "login",
        data() {
            return {
                loginAdmin: {},
                admin: {},
            }
        },
        methods: {
            login() {
                //如果上个用户不是通过退出系统退出的，ta的cookie有可能还在浏览器中
                if (Cookies.get('user') != null){
                    Cookies.remove('user');
                }
                request.post("/admin/login",this.admin).then(res => {
                    if (res.code === '200'){
                        if (res.data != null){
                          Cookies.set('user', JSON.stringify(this.loginAdmin));
                          alert("登陆成功,欢迎使用!");
                          this.$router.push('/')
                        }
                    } else {
                        this.$notify.error(res.msg);
                    }
                })
            },

            onSuccess() { // 滑块验证通过之后触发的
                Cookies.set('user', JSON.stringify(this.loginAdmin));
                alert("登陆成功,欢迎使用!");
                this.$router.push('/')
            },

            onFail() {
                console.log('onFail')
            },

            onRefresh() {
                console.log('refresh')
            }
        }
    }
</script>

<style scoped>
    .cover {
        width: fit-content;
        background-color: white;
        position: absolute;
        top:50%;
        left:50%;
        transform: translate(-50%, -50%);
        z-index: 1000;
    }
</style>

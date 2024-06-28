<template>

    <div>
        <!--头部区域-->
        <div style="height: 60px; line-height: 60px; background-color: white; margin-bottom: 2px; display: flex" >
            <div style="width: 300px">
                <img src="@/assets/logo.png" alt="" style="width: 40px; position: relative; top: 10px; left: 20px">
                <span style="margin-left: 25px; font-size: 24px">墨韵读书会书籍共享平台</span>
            </div>
            <div style="flex: 1; text-align: right; padding-right: 20px">
                <el-dropdown size="medium">
                    <el-button type="primary" style="cursor: pointer">
                        {{ user.username }}<i class="el-icon-arrow-down el-icon--right"></i>
                    </el-button>
                    <el-dropdown-menu slot="dropdown" style="margin-top: -5px">
                        <el-dropdown-item><div style="width: 50px; text-align: center" @click="logout">退出</div></el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>

        </div>

        <!--侧边栏和主体-->
        <div style="display: flex">

            <!--左侧侧边栏导航-->
            <div style="width: 200px; min-height: calc(100vh - 62px); overflow: hidden; margin-right: 2px; background-color: white">
                <!--:default-openeds="['/user']"-->
                <el-menu :default-active="$route.path" router class="el-menu-demo">
                    <el-menu-item index="/">
                        <i class="el-icon-eleme"></i>
                        <span>首页</span>
                    </el-menu-item>
                    <el-submenu index="/user">
                        <template slot="title">
                            <i class="el-icon-user"></i>
                            <span>用户</span>
                        </template>
                        <el-menu-item index="/user/add"><i class="el-icon-add-location"></i> 用户注册 </el-menu-item>
                        <el-menu-item index="/user/list"><i class="el-icon-user"></i> 用户列表 </el-menu-item>
                    </el-submenu>
                    <el-submenu index="/admin">
                        <template slot="title">
                            <i class="el-icon-user"></i>
                            <span>管理员</span>
                        </template>
                        <el-menu-item index="/admin/add"><i class="el-icon-circle-plus"></i> 管理员注册 </el-menu-item>
                        <el-menu-item index="/admin/list"><i class="el-icon-user"></i> 管理员列表 </el-menu-item>
                    </el-submenu>
                    <el-submenu index="/category">
                        <template slot="title">
                            <i class="el-icon-s-operation"></i>
                            <span>书库</span>
                        </template>
                        <el-menu-item index="/category/add"><i class="el-icon-plus"></i> 图书分类添加 </el-menu-item>
                        <el-menu-item index="/category/list"><i class="el-icon-tickets"></i> 图书分类列表 </el-menu-item>
                      <el-menu-item index="/book/list"><i class="el-icon-notebook-2"></i> 图书下架 </el-menu-item>
                    </el-submenu>
                        <el-menu-item index="/book/borrow"><i class="el-icon-notebook-2"></i> 书圈 </el-menu-item>
                        <el-menu-item index="/review"><i class="el-icon-notebook-2"></i> 书评 </el-menu-item>
                </el-menu>
            </div>

            <!--右侧数据的展示-->
            <div style="flex: 1; background-color: white; padding: 10px">
                <!--展示我们路由显示的界面-->
                <router-view/>
            </div>
        </div>
    </div>

</template>

<script>
    import Cookies from 'js-cookie';
    export default {
        name: "Layout",
        data(){
            return {
                user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {}
            }
        },
        methods: {

            logout() {
                //清除浏览器用户数据
                Cookies.remove('user');
                this.$router.push('/login');
            }
        }
    }
</script>

<style scoped>

</style>

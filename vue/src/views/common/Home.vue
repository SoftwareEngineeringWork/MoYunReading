<template>
    <div>
        <div class="item">用户名：admin</div>
        <div class="item">手机号：1352684957</div>
        <div class="item">邮箱：223245@qq.com</div>
    </div>
</template>

<script>
    import Cookies from 'js-cookie';

export default {
    name: 'HomeView',
    data() {
        return {
            user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {}
        }
    }
}
</script>

<style>
    .item {
        margin: 10px 0;
        color: cornflowerblue;
    }
</style>

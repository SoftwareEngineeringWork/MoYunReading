<template>
    <div>
        <div class="item">用户名：{{user.username}}</div>
        <div class="item">手机号：{{user.phone}}</div>
        <div class="item">邮箱： {{user.email}}</div>
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

<template>
  <div class="dashboard-container">
    <h1>科研管理系统</h1>
    <h2>项目简介</h2>
    <h4>
      本项目采用SpringBoot+Vue开发，数据库采用Mysql储存静态数据、采用redis储存用户缓存数据（由于项目重点不在用户，默认admin密码111111
      就能登陆）。在后端借助了SpringBoot搭建基础框架，采用MyBatis、lombok等快速开发工具搭建对数据库开发的框架，然后逐步完善业务逻辑，
      实现大体数据的增删改查。在前端借助了vue-admin-template模板，但大体内容仍为自己操刀。</h4>
    <div>数据库系统的E-R图模型：</div>

    <img src="./123.png" width="1000px" height="650px">
  </div>
</template>

<script>

</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }

  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>

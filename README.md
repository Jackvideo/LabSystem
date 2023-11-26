# 科研管理系统

## 作者：Jackvideo

## 项目简介

本项目是华南理工大学软件学院数据库系统的课设作业，目的是开发一个可管理研究室、研究人员、研究项目和结果等数据的管理系统。

该项目采用SpringBoot搭建后端+Vue-Admin-Template前端框架，部分操作参考https://www.bilibili.com/video/BV1dG4y1T7yp/?spm_id_from=333.1007.top_right_bar_window_custom_collection.content.click&vd_source=9ba03e26217bb8f579a12ea5e63daf99 (感谢青青菜鸟老师)

仅供交流学习，切勿直接CV。

## 技术栈

**前端技术**                                **说明**

Vue                                    前端框架

ElementUl                         前端UI框架

vue-element-admin           项目脚手架

**后端技术**                                 **说明**

SpringBoot                        容器+MVC框架

MyBatis                               ORM框架

MyBatis-plus                     MyBatis增强工具

MySQL                              关系型数据库

Redis                                非关系型数据库



## 启动方法

1.后端配置

1.1使用Java的IDE导入LabSys项目，使用Maven构建。

1.2建立mysql数据库，导入项目文件中的search.sql，并在后端application.yml文件中修改自己的mysql连接地址和端口号

1.3下载并安装redis，使用默认配置（无密码，端口6379）

2.前端配置

使用VS Code打开front_end文件夹，运行npm install安装所需依赖。

3.启动

先启动redis server，再运行后端application，然后在VS Code终端，定位到front_end，执行npm run dev.

访问网址localhost:8888。
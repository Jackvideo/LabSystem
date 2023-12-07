<template>
    <el-row>
        <el-col :span="14">
            <el-card>
                <el-form ref="form" :model="user" label-width="80px">
                    <el-form-item label="用户名">
                        <el-input v-model="user.username" width="200px"></el-input>
                    </el-form-item>
                    <el-form-item label="用户密码">
                        <el-input v-model="user.password"></el-input>
                    </el-form-item>
                    <el-form-item label="用户权限">
                        <el-input v-model="user.authority"></el-input>
                    </el-form-item>
                    <el-form-item label="禁止修改">
                        <el-switch v-model="form.delivery" @change="refuseSwitch"></el-switch>
                    </el-form-item>
                </el-form>
            </el-card>
        </el-col>
        <el-col :span="5">
            <div>
                <img class="avatar" src="./avatar.jpg" width="182px" height="1px">
            </div>
            -----------用户头像-----------
        </el-col>
        <el-col :span="5">
            <el-upload class="avatar-uploader" action="https://jsonplaceholder.typicode.com/posts/" :show-file-list="true"
                :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                <img v-if="imageUrl" :src="imageUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            -----------上传头像-----------
        </el-col>
    </el-row>
</template>


<script>
import UserApi from '@/api/user'
export default {
    data() {
        return {
            user: {
                username: 'admin',
                password: '123456',
                authority: 'admin',
            },
            imageUrl: '',
            form: {
                delivery: true
            }
        }
    },
    methods: {
        onSubmit() {
            console.log('submit!');
        },
        handleAvatarSuccess(res, file) {
            this.imageUrl = URL.createObjectURL(file.raw);
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },
        refuseSwitch() {
            this.$message.error('禁止修改！！！');
            this.form.delivery = true;
        }
    }
}
</script>
<style>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.avatar-uploader .el-upload:hover {
    border-color: #409EFF;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}

.avatar {
    width: 178px;
    height: 178px;
    display: block;
}
</style>
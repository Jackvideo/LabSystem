<template>
    <div>
        <!-- 搜索框 -->
        <el-card id="search">
            <el-row>
                <el-col :span="22">
                    <el-input v-model.trim="contact.contactid" placeholder="联系人ID"></el-input>
                    <el-input v-model.trim="contact.workphone" placeholder="工作电话" clearable></el-input>
                    <el-button @click="getContactList" type="primary" round icon="el-icon-search">查询</el-button>
                </el-col>
                <el-col :span="2">
                    <el-button @click="openEdit(null)" type="primary" icon="el-icon-plus" circle></el-button>
                </el-col>
            </el-row>
        </el-card>

        <!-- 结果列表 -->
        <el-card id="result">
            <el-table :data="contactList" stripe style="width: 100%">
                <el-table-column label="#" width="100">
                    <template v-slot="scope">
                        {{ (contact.pageNo - 1) * contact.pageSize + scope.$index + 1 }}
                    </template>
                </el-table-column>
                <!-- 这里的prop要对应api中左边的变量名，也就是数据库的变量名 -->
                <el-table-column prop="contactid" label="联系人ID" width="150">
                </el-table-column>
                <el-table-column prop="workphone" label="工作电话" width="250">
                </el-table-column>
                <el-table-column prop="mobilephone" label="移动电话" width="250">
                </el-table-column>
                <el-table-column prop="email" label="邮箱" width="250">
                </el-table-column>
                <el-table-column label="  操作" width="250">
                    <template v-slot="scope">
                        <el-button @click="openEdit(scope.row.contactid)" type="primary" icon="el-icon-edit"
                            circle></el-button>
                        <el-button @click="deleteContactorNot(scope.row)" type="danger" icon="el-icon-delete"
                            circle></el-button>

                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!-- 分页组件 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="contact.pageNo"
            :page-sizes="[2, 5, 10, 100]" :page-size="10" layout="total, sizes, prev, pager, next, jumper" :total=total>
        </el-pagination>

        <!-- 新增对象对话框 -->
        <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="contactForm" ref="contactFormRef" :rules="rules">
                <el-form-item label="工作电话" prop="workphone" :label-width="formLabelWidth">
                    <el-input v-model="contactForm.workphone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="移动电话" prop="mobilephone" :label-width="formLabelWidth">
                    <el-input v-model="contactForm.mobilephone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="totalfund" :label-width="formLabelWidth">
                    <el-input v-model="contactForm.totalfund" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveContact">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>

import CommonApi from '@/api/back_end'
export default {
    data() {
        return {
            title: "",
            formLabelWidth: '150px',
            contactForm: {},
            //表单验证规则
            rules: {
                workphone: [
                    { required: true, message: '请输入联系人工作电话', trigger: 'blur' },
                ],
                email: [
                    { required: true, message: '请输入联系人邮箱地址', trigger: 'blur' }
                ]
            },
            dialogFormVisible: false,
            total: 0,
            contact: {
                pageNo: 1,
                pageSize: 10
            },
            contactList: []
        }
    },
    methods: {
        handleSizeChange(pageSize) {
            this.contact.pageSize = pageSize;
            this.getContactList();

        },
        handleCurrentChange(pageNo) {
            this.contact.pageNo = pageNo;
            this.getContactList();
        },
        getContactList() {
            // 按参数获取结果
            CommonApi.getContactList(this.contact).then(response => {
                this.contactList = response.data.rows;
                this.total = response.data.total;
            });
        },
        openEdit(id) {
            //打开对话框
            if (id == null)
                this.title = "新增项目";
            else {
                this.title = "修改项目信息";
                //查询数据
                CommonApi.findOneContact(id).then(response => {
                    this.contactForm = response.data;
                });

            }
            this.dialogFormVisible = true;
        },
        clearForm() {
            //清除缓存验证
            this.contactForm = {};
            this.$refs.contactFormRef.clearValidate();
        },
        saveContact() {
            this.$refs.contactFormRef.validate((valid) => {
                if (valid) {
                    alert('成功提交');
                    CommonApi.saveContact(this.contactForm).then(response => {
                        //成功消息
                        this.$message({
                            message: '提交成功！',
                            type: 'success'
                        });
                        //关闭对话框
                        this.dialogFormVisible = false;
                        //刷新表格
                        this.getContactList();

                    })
                } else {
                    console.log('错误提交!!');
                    return false;
                }
            });
        },
        deleteContactorNot(Contact) {
            this.$confirm('此操作将永久删除该联系人, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                CommonApi.deleteContact(Contact.contactid).then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getContactList();
                });

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        }
    },
    created() {
        this.getContactList();
    }
};

</script>

<style>
#search .el-input {
    width: 150px;
    margin-right: 10px;
}

el-dialog.el-input {
    width: 90%;
}
</style>
<template>
    <div>
        <!-- 搜索框 -->
        <el-card id="search">
            <el-row>
                <el-col :span="22">
                    <el-input v-model.trim="contactrelation.contactid" placeholder="联系人ID" clearable></el-input>
                    <el-input v-model.trim="contactrelation.departmentid" placeholder="单位ID" clearable></el-input>
                    <el-input v-model.trim="contactrelation.departmentname" placeholder="单位名称" clearable></el-input>
                    <el-button @click="getContactrelationList" type="primary" round icon="el-icon-search">查询</el-button>
                </el-col>
                <el-col :span="2">
                    <el-button @click="openEdit(null)" type="primary" icon="el-icon-plus" circle></el-button>
                </el-col>
            </el-row>
        </el-card>

        <!-- 结果列表 -->
        <el-card id="result">
            <el-table :data="contactrelationList" stripe style="width: 100%" border
                :header-cell-style="{ textAlign: 'center' }" :cell-style="{ textAlign: 'center' }">
                <el-table-column label="#" width="150">
                    <template v-slot="scope">
                        {{ (contactrelation.pageNo - 1) * contactrelation.pageSize + scope.$index + 1 }}
                    </template>
                </el-table-column>
                <!-- 这里的prop要对应api中左边的变量名，也就是数据库的变量名 -->
                <el-table-column prop="contactid" label="联系人ID" width="275">
                </el-table-column>
                <el-table-column prop="departmentid" label="单位ID" width="275">
                </el-table-column>
                <el-table-column prop="departmentname" label="单位名称" width="300">
                </el-table-column>
                <el-table-column label="  操作" width="250">
                    <template v-slot="scope">
                        <el-button @click="openEdit(scope.row.contactid, scope.row.departmentid)" type="primary"
                            icon="el-icon-edit" circle></el-button>
                        <el-button @click="deleteContactrelationorNot(scope.row)" type="danger" icon="el-icon-delete"
                            circle></el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!-- 分页组件 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="contactrelation.pageNo" :page-sizes="[2, 5, 10, 100]" :page-size="10"
            layout="total, sizes, prev, pager, next, jumper" :total=total>
        </el-pagination>

        <!-- 新增对象对话框 -->
        <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="contactrelationForm" ref="contactrelationFormRef" :rules="rules">
                <el-form-item label="联系人ID" prop="contactid" :label-width="formLabelWidth">
                    <el-input v-model="contactrelationForm.contactid" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="单位ID" prop="departmentid" :label-width="formLabelWidth">
                    <el-input v-model="contactrelationForm.departmentid" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="单位名称" prop="departmentname" :label-width="formLabelWidth">
                    <el-input v-model="contactrelationForm.departmentname" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveContactrelation">确 定</el-button>
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
            contactrelationForm: {},
            //表单验证规则
            rules: {
                contactid: [
                    { required: true, message: '请选择联系人ID', trigger: 'blur' },
                ],
                departmentid: [
                    { required: true, message: '请输入单位ID', trigger: 'blur' },
                ]
            },
            dialogFormVisible: false,
            spVisible: false,
            total: 0,
            contactrelation: {
                pageNo: 1,
                pageSize: 10
            },
            contactrelationList: [],
        }
    },
    methods: {
        handleSizeChange(pageSize) {
            this.contactrelation.pageSize = pageSize;
            this.getContactrelationList();

        },
        handleCurrentChange(pageNo) {
            this.contactrelation.pageNo = pageNo;
            this.getContactrelationList();
        },
        getContactrelationList() {
            // 按参数获取结果
            CommonApi.getContactrelationList(this.contactrelation).then(response => {
                this.contactrelationList = response.data.rows;
                this.total = response.data.total;
            });
        },
        openEdit(oid, rid) {
            //打开对话框
            if (oid == null && rid == null)
                this.title = "新增联系人记录";
            else {
                this.title = "修改记录信息";
                //查询数据
                CommonApi.findOneContactrelation(oid, rid).then(response => {
                    this.contactrelationForm = response.data;
                });
            }
            this.dialogFormVisible = true;
        },
        clearForm() {
            //清除缓存验证
            this.contactrelationForm = {};
            this.$refs.contactrelationFormRef.clearValidate();
        },
        saveContactrelation() {
            this.$refs.contactrelationFormRef.validate((valid) => {
                if (valid) {
                    alert('成功提交');
                    CommonApi.saveContactrelation(this.contactrelationForm).then(response => {
                        //成功消息
                        this.$message({
                            message: '提交成功！',
                            type: 'success'
                        });
                        //关闭对话框
                        this.dialogFormVisible = false;
                        //刷新表格
                        this.getContactrelationList();

                    })
                } else {
                    console.log('错误提交!!');
                    return false;
                }
            });
        },
        deleteContactrelationorNot(Contactrelation) {
            this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                CommonApi.deleteContactrelation(Contactrelation.contactid, Contactrelation.departmentid).then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getContactrelationList();
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
        this.getContactrelationList();
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
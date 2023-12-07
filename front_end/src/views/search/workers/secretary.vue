<template>
    <div>
        <!-- 搜索框 -->
        <el-card id="search">
            <el-row>
                <el-col :span="22">
                    <el-input v-model.trim="secretary.secretaryid" placeholder="秘书ID"></el-input>
                    <el-button @click="getSecretaryList" type="primary" round icon="el-icon-search">查询</el-button>
                </el-col>
                <el-col :span="2">
                    <el-button @click="openEdit(null)" type="primary" icon="el-icon-plus" circle></el-button>
                </el-col>
            </el-row>
        </el-card>

        <!-- 结果列表 -->
        <el-card id="result">
            <el-table :data="secretaryList" stripe style="width: 100%" border :header-cell-style="{ textAlign: 'center' }"
                :cell-style="{ textAlign: 'center' }">
                <el-table-column label="#" width="150">
                    <template v-slot="scope">
                        {{ (secretary.pageNo - 1) * secretary.pageSize + scope.$index + 1 }}
                    </template>
                </el-table-column>
                <!-- 这里的prop要对应api中左边的变量名，也就是数据库的变量名 -->
                <el-table-column prop="secretaryid" label="秘书ID" width="150">
                </el-table-column>
                <el-table-column prop="hiretime" label="聘用时间" width="250">
                </el-table-column>
                <el-table-column prop="responsibility" label="职责" width="450">
                </el-table-column>
                <el-table-column label="  操作" width="250">
                    <template v-slot="scope">
                        <el-button @click="openEdit(scope.row.secretaryid)" type="primary" icon="el-icon-edit"
                            circle></el-button>
                        <el-button @click="deleteSecretaryorNot(scope.row)" type="danger" icon="el-icon-delete"
                            circle></el-button>

                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!-- 分页组件 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="secretary.pageNo" :page-sizes="[2, 5, 10, 100]" :page-size="10"
            layout="total, sizes, prev, pager, next, jumper" :total=total>
        </el-pagination>

        <!-- 新增对象对话框 -->
        <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="secretaryForm" ref="secretaryFormRef" :rules="rules">
                <el-form-item label="聘用时间" prop="startdate" :label-width="formLabelWidth">
                    <el-date-picker v-model="secretaryForm.hiretime" type="date" placeholder="选择日期"></el-date-picker>
                </el-form-item>
                <el-form-item label="职责" prop="responsibility" :label-width="formLabelWidth">
                    <el-input v-model="secretaryForm.responsibility" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveSecretary">确 定</el-button>
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
            secretaryForm: {},
            //表单验证规则
            rules: {
                hiretime: [
                    { required: true, message: '请选择聘用时间', trigger: 'blur' }
                ]
            },
            dialogFormVisible: false,
            total: 0,
            secretary: {
                pageNo: 1,
                pageSize: 10
            },
            secretaryList: []
        }
    },
    methods: {
        handleSizeChange(pageSize) {
            this.secretary.pageSize = pageSize;
            this.getSecretaryList();

        },
        handleCurrentChange(pageNo) {
            this.secretary.pageNo = pageNo;
            this.getSecretaryList();
        },
        getSecretaryList() {
            // 按参数获取结果
            CommonApi.getSecretaryList(this.secretary).then(response => {
                this.secretaryList = response.data.rows;
                this.total = response.data.total;
            });
        },
        openEdit(id) {
            //打开对话框
            if (id == null)
                this.title = "新增研究室秘书";
            else {
                this.title = "修改秘书信息";
                //查询数据
                CommonApi.findOneSecretary(id).then(response => {
                    this.secretaryForm = response.data;
                });

            }
            this.dialogFormVisible = true;
        },
        clearForm() {
            //清除缓存验证
            this.secretaryForm = {};
            this.$refs.secretaryFormRef.clearValidate();
        },
        saveSecretary() {
            this.$refs.secretaryFormRef.validate((valid) => {
                if (valid) {
                    alert('成功提交');
                    CommonApi.saveSecretary(this.secretaryForm).then(response => {
                        //成功消息
                        this.$message({
                            message: '提交成功！',
                            type: 'success'
                        });
                        //关闭对话框
                        this.dialogFormVisible = false;
                        //刷新表格
                        this.getSecretaryList();

                    })
                } else {
                    console.log('错误提交!!');
                    return false;
                }
            });
        },
        deleteSecretaryorNot(Secretary) {
            this.$confirm('此操作将永久删除该秘书, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                CommonApi.deleteSecretary(Secretary.secretaryid).then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getSecretaryList();
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
        this.getSecretaryList();
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
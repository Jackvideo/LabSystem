<template>
    <div>
        <!-- 搜索框 -->
        <el-card id="search">
            <el-row>
                新增子项目
                <el-button @click="openEdit(null)" type="primary" icon="el-icon-plus" circle></el-button>
            </el-row>
        </el-card>

        <!-- 结果列表 -->
        <el-card id="result">
            <el-table :data="subprojectList" border stripe style="width: 100%" :header-cell-style="{ textAlign: 'center' }"
                :cell-style="{ textAlign: 'center' }">
                <el-table-column label="#" width="50">
                    <template v-slot="scope">
                        {{ (subproject.pageNo - 1) * subproject.pageSize + scope.$index + 1 }}
                    </template>
                </el-table-column>
                <!-- 这里的prop要对应api中左边的变量名，也就是数据库的变量名 -->
                <el-table-column prop="subprojectid" label="子项目ID" width="100">
                </el-table-column>
                <el-table-column prop="leaderid" label="负责人ID" width="100">
                </el-table-column>
                <el-table-column prop="deadline" label="DeadLine" type="date" width="250">
                </el-table-column>
                <el-table-column prop="budget" label="经费" width="200">
                </el-table-column>
                <el-table-column prop="technicalindex" label="技术指标" width="200">
                </el-table-column>
                <el-table-column label="操作" width="200">
                    <template v-slot="scope">
                        <el-button @click="openEdit(scope.row.subprojectid)" type="primary" icon="el-icon-edit"
                            circle></el-button>
                        <el-button @click="deleteSubprojectorNot(scope.row)" type="danger" icon="el-icon-delete"
                            circle></el-button>

                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!-- 分页组件 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="subproject.pageNo" :page-sizes="[2, 5, 10, 100]" :page-size="10"
            layout="total, sizes, prev, pager, next, jumper" :total=total>
        </el-pagination>

        <!-- 新增对象对话框 -->
        <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="subprojectForm" ref="subprojectFormRef" :rules="rules">
                <el-form-item label="项目ID" prop="projectid" :label-width="formLabelWidth">
                    <el-input v-model="subprojectForm.projectid" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="负责人ID" prop="leadid" :label-width="formLabelWidth">
                    <el-input v-model="subprojectForm.leaderid" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="DeadLine" prop="deadline" :label-width="formLabelWidth">
                    <el-date-picker v-model="subprojectForm.deadline" type="date" placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="经费" prop="bugdet" :label-width="formLabelWidth">
                    <el-input v-model="subprojectForm.budget" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="技术指标" prop="technicalindex" :label-width="formLabelWidth">
                    <el-input v-model="subprojectForm.technicalindex" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveSubproject">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>

import CommonApi from '@/api/back_end'
export default {
    props: ['theprojectid'],
    data() {
        return {
            title: "",
            formLabelWidth: '150px',
            subprojectForm: {},
            //表单验证规则
            rules: {
                projectid: [
                    { required: true, message: '请输入父项目ID', trigger: 'blur' },
                ],
                leaderid: [
                    { required: true, message: '请输入负责人ID', trigger: 'blur' },
                ]
            },
            dialogFormVisible: false,
            total: 0,
            subproject: {
                pageNo: 1,
                pageSize: 10,
                projectid: this.theprojectid

            },
            subprojectList: []
        }
    },
    methods: {
        handleSizeChange(pageSize) {
            this.subproject.pageSize = pageSize;
            this.getSubprojectList();

        },
        handleCurrentChange(pageNo) {
            this.subproject.pageNo = pageNo;
            this.getSubprojectList();
        },
        getSubprojectList() {
            // 按参数获取结果
            CommonApi.getSubprojectList(this.subproject).then(response => {
                this.subprojectList = response.data.rows;
                this.total = response.data.total;
            });
        },
        closeMoule(id) {
            this.subproject.projectid = id;
            this.getSubprojectList();
        },
        openEdit(id) {
            //打开对话框
            if (id == null)
                this.title = "新增子项目";
            else {
                this.title = "修改子项目信息";
                //查询数据
                CommonApi.findOneSubproject(id).then(response => {
                    this.subprojectForm = response.data;
                });

            }
            this.dialogFormVisible = true;
        },
        clearForm() {
            //清除缓存验证
            this.subprojectForm = {};
            this.$refs.subprojectFormRef.clearValidate();
        },
        saveSubproject() {
            this.$refs.subprojectFormRef.validate((valid) => {
                if (valid) {
                    alert('成功提交');
                    CommonApi.saveSubproject(this.subprojectForm).then(response => {
                        //成功消息
                        this.$message({
                            message: '提交成功！',
                            type: 'success'
                        });
                        //关闭对话框
                        this.dialogFormVisible = false;
                        //刷新表格
                        this.getSubprojectList();

                    })
                } else {
                    console.log('错误提交!!');
                    return false;
                }
            });
        },
        deleteSubprojectorNot(Subproject) {
            this.$confirm('此操作将永久删除该子项目, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                CommonApi.deleteSubproject(Subproject.subprojectid).then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getSubprojectList();
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
        this.getSubprojectList();
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
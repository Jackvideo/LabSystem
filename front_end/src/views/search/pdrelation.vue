<template>
    <div>
        <!-- 搜索框 -->
        <el-card id="search">
            <el-row>
                <el-col :span="22">
                    <el-input v-model.trim="pdrelation.projectid" placeholder="项目ID" clearable></el-input>
                    <el-button @click="getPdrelationList" type="primary" round icon="el-icon-search">查询</el-button>
                </el-col>
                <el-col :span="2">
                    <el-button @click="openEdit(null)" type="primary" icon="el-icon-plus" circle></el-button>
                </el-col>
            </el-row>
        </el-card>

        <!-- 结果列表 -->
        <el-card id="result">
            <el-table :data="pdrelationList" stripe style="width: 100%" border :header-cell-style="{ textAlign: 'center' }"
                :cell-style="{ textAlign: 'center' }">
                <el-table-column label="#" width="100">
                    <template v-slot="scope">
                        {{ (pdrelation.pageNo - 1) * pdrelation.pageSize + scope.$index + 1 }}
                    </template>
                </el-table-column>
                <!-- 这里的prop要对应api中左边的变量名，也就是数据库的变量名 -->
                <el-table-column prop="projectid" label="项目ID" width="250">
                </el-table-column>
                <el-table-column prop="projectname" label="项目名称" width="100">
                </el-table-column>
                <el-table-column prop="principle" label="委托方" width="100">
                </el-table-column>
                <el-table-column prop="partner" label="合作方" width="250">
                </el-table-column>
                <el-table-column prop="qualifier" label="监测方" width="250">
                </el-table-column>
                <el-table-column label="  操作" width="200">
                    <template v-slot="scope">
                        <el-button @click="openEdit(scope.row.pdrelationid)" type="primary" icon="el-icon-edit"
                            circle></el-button>
                        <el-button @click="deletePdrelationorNot(scope.row)" type="danger" icon="el-icon-delete"
                            circle></el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!-- 分页组件 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="pdrelation.pageNo" :page-sizes="[2, 5, 10, 100]" :page-size="10"
            layout="total, sizes, prev, pager, next, jumper" :total=total>
        </el-pagination>

        <!-- 新增对象对话框 -->
        <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="pdrelationForm" ref="pdrelationFormRef" :rules="rules">
                <el-form-item label="项目ID" prop="projectid" :label-width="formLabelWidth">
                    <el-input v-model="pdrelationForm.projectid" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="项目名称" prop="projectname" :label-width="formLabelWidth">
                    <el-input v-model="pdrelationForm.projectname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="委托方" prop="principle" :label-width="formLabelWidth">
                    <el-input v-model="pdrelationForm.principle" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="合作方" prop="partner" :label-width="formLabelWidth">
                    <el-input v-model="pdrelationForm.partner" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="监测方" prop="qualifier" :label-width="formLabelWidth">
                    <el-input v-model="pdrelationForm.qualifier" autocomplete="off"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="savePdrelation">确 定</el-button>
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
            pdrelationForm: {},
            //表单验证规则
            rules: {
                projectid: [
                    { required: true, message: '请输入项目ID', trigger: 'blur' },
                ],
                projectname: [
                    { required: true, message: '请输入项目名称', trigger: 'blur' },
                ]
            },
            dialogFormVisible: false,
            spVisible: false,
            total: 0,
            pdrelation: {
                pageNo: 1,
                pageSize: 10
            },
            pdrelationList: [],
        }
    },
    methods: {
        handleSizeChange(pageSize) {
            this.pdrelation.pageSize = pageSize;
            this.getPdrelationList();

        },
        handleCurrentChange(pageNo) {
            this.pdrelation.pageNo = pageNo;
            this.getPdrelationList();
        },
        getPdrelationList() {
            // 按参数获取结果
            CommonApi.getPdrelationList(this.pdrelation).then(response => {
                this.pdrelationList = response.data.rows;
                this.total = response.data.total;
            });
        },
        openEdit(id) {
            //打开对话框
            if (id == null)
                this.title = "新增项目单位关系";
            else {
                this.title = "修改关系信息";
                //查询数据
                CommonApi.findOnePdrelation(id).then(response => {
                    this.pdrelationForm = response.data;
                });

            }
            this.dialogFormVisible = true;
        },
        clearForm() {
            //清除缓存验证
            this.pdrelationForm = {};
            this.$refs.pdrelationFormRef.clearValidate();
        },
        savePdrelation() {
            this.$refs.pdrelationFormRef.validate((valid) => {
                if (valid) {
                    alert('成功提交');
                    CommonApi.savePdrelation(this.pdrelationForm).then(response => {
                        //成功消息
                        this.$message({
                            message: '提交成功！',
                            type: 'success'
                        });
                        //关闭对话框
                        this.dialogFormVisible = false;
                        //刷新表格
                        this.getPdrelationList();

                    })
                } else {
                    console.log('错误提交!!');
                    return false;
                }
            });
        },
        deletePdrelationorNot(Pdrelation) {
            this.$confirm('此操作将永久删除该关系记录n, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                CommonApi.deletePdrelation(Pdrelation.pdrelationid).then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getPdrelationList();
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
        this.getPdrelationList();
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
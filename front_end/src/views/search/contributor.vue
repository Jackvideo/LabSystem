<template>
    <div>
        <!-- 搜索框 -->
        <el-card id="search">
            <el-row>
                <el-col :span="22">
                    <el-input v-model.trim="contributor.outcomeid" placeholder="结果ID" clearable></el-input>
                    <el-input v-model.trim="contributor.researcherid" placeholder="研究人员ID" clearable></el-input>
                    <el-button @click="getContributorList" type="primary" round icon="el-icon-search">查询</el-button>
                </el-col>
                <el-col :span="2">
                    <el-button @click="openEdit(null)" type="primary" icon="el-icon-plus" circle></el-button>
                </el-col>
            </el-row>
        </el-card>

        <!-- 结果列表 -->
        <el-card id="result">
            <el-table :data="contributorList" stripe style="width: 100%" border :header-cell-style="{ textAlign: 'center' }"
                :cell-style="{ textAlign: 'center' }">
                <el-table-column label="#" width="150">
                    <template v-slot="scope">
                        {{ (contributor.pageNo - 1) * contributor.pageSize + scope.$index + 1 }}
                    </template>
                </el-table-column>
                <!-- 这里的prop要对应api中左边的变量名，也就是数据库的变量名 -->
                <el-table-column prop="outcomeid" label="结果ID" width="275">
                </el-table-column>
                <el-table-column prop="researcherid" label="研究人员ID" width="275">
                </el-table-column>
                <el-table-column prop="ranks" label="贡献排名" width="300">
                </el-table-column>
                <el-table-column label="  操作" width="250">
                    <template v-slot="scope">
                        <el-button @click="openEdit(scope.row.outcomeid, scope.row.researcherid)" type="primary"
                            icon="el-icon-edit" circle></el-button>
                        <el-button @click="deleteContributororNot(scope.row)" type="danger" icon="el-icon-delete"
                            circle></el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!-- 分页组件 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="contributor.pageNo" :page-sizes="[2, 5, 10, 100]" :page-size="10"
            layout="total, sizes, prev, pager, next, jumper" :total=total>
        </el-pagination>

        <!-- 新增对象对话框 -->
        <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="contributorForm" ref="contributorFormRef" :rules="rules">
                <el-form-item label="结果ID" prop="outcomeid" :label-width="formLabelWidth">
                    <el-input v-model="contributorForm.outcomeid" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="研究人员ID" prop="researcherid" :label-width="formLabelWidth">
                    <el-input v-model="contributorForm.researcherid" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="贡献排名" prop="ranks" :label-width="formLabelWidth">
                    <el-input v-model="contributorForm.ranks" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveContributor">确 定</el-button>
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
            contributorForm: {},
            //表单验证规则
            rules: {
                outcomeid: [
                    { required: true, message: '请选择结果ID', trigger: 'blur' },
                ],
                researcherid: [
                    { required: true, message: '请输入研究人员ID', trigger: 'blur' },
                ],
                ranks: [
                    { required: true, message: '贡献度排名', trigger: 'blur' }
                ]
            },
            dialogFormVisible: false,
            spVisible: false,
            total: 0,
            contributor: {
                pageNo: 1,
                pageSize: 10
            },
            contributorList: [],
        }
    },
    methods: {
        handleSizeChange(pageSize) {
            this.contributor.pageSize = pageSize;
            this.getContributorList();

        },
        handleCurrentChange(pageNo) {
            this.contributor.pageNo = pageNo;
            this.getContributorList();
        },
        getContributorList() {
            // 按参数获取结果
            CommonApi.getContributorList(this.contributor).then(response => {
                this.contributorList = response.data.rows;
                this.total = response.data.total;
            });
        },
        openEdit(oid, rid) {
            //打开对话框
            if (oid == null && rid == null)
                this.title = "新增贡献记录";
            else {
                this.title = "修改记录信息";
                //查询数据
                CommonApi.findOneContributor(oid, rid).then(response => {
                    this.contributorForm = response.data;
                });
            }
            this.dialogFormVisible = true;
        },
        clearForm() {
            //清除缓存验证
            this.contributorForm = {};
            this.$refs.contributorFormRef.clearValidate();
        },
        saveContributor() {
            this.$refs.contributorFormRef.validate((valid) => {
                if (valid) {
                    alert('成功提交');
                    CommonApi.saveContributor(this.contributorForm).then(response => {
                        //成功消息
                        this.$message({
                            message: '提交成功！',
                            type: 'success'
                        });
                        //关闭对话框
                        this.dialogFormVisible = false;
                        //刷新表格
                        this.getContributorList();

                    })
                } else {
                    console.log('错误提交!!');
                    return false;
                }
            });
        },
        deleteContributororNot(Contributor) {
            this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                CommonApi.deleteContributor(Contributor.outcomeid, Contributor.researcherid).then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getContributorList();
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
        this.getContributorList();
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
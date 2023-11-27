<template>
    <div>
        <!-- 搜索框 -->
        <el-card id="search">
            <el-row>
                <el-col :span="22">
                    <el-input v-model.trim="researchlab.labid" placeholder="研究室id"></el-input>
                    <el-input v-model.trim="researchlab.labname" placeholder="研究室名称" clearable></el-input>
                    <el-input v-model.trim="researchlab.researcharea" placeholder="研究领域" clearable></el-input>
                    <el-button @click="getLabList" type="primary" round icon="el-icon-search">查询</el-button>
                </el-col>
                <el-col :span="2">
                    <el-button @click="openEdit(null)" type="primary" icon="el-icon-plus" circle></el-button>
                </el-col>
            </el-row>
        </el-card>

        <!-- 结果列表 -->
        <el-card id="result">
            <el-table :data="labList" stripe style="width: 100%" border :header-cell-style="{ textAlign: 'center' }"
                :cell-style="{ textAlign: 'center' }">
                <el-table-column label="#" width="250">
                    <template v-slot="scope">
                        {{ (researchlab.pageNo - 1) * researchlab.pageSize + scope.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column prop="labid" label="ID" width="250">
                </el-table-column>
                <el-table-column prop="labname" label="研究室名称" width="250">
                </el-table-column>
                <el-table-column prop="researcharea" label="研究领域" width="300">
                </el-table-column>
                <el-table-column label="操作" width="200">
                    <template v-slot="scope">
                        <el-button @click="openEdit(scope.row.labid)" type="primary" icon="el-icon-edit" circle></el-button>
                        <el-button @click="deleteLaborNot(scope.row)" type="danger" icon="el-icon-delete"
                            circle></el-button>

                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!-- 分页组件 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="researchlab.pageNo" :page-sizes="[2, 5, 10, 100]" :page-size="10"
            layout="total, sizes, prev, pager, next, jumper" :total=total>
        </el-pagination>

        <!-- 新增对象对话框 -->
        <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="labForm" ref="labFormRef" :rules="rules">
                <el-form-item label="研究室名称" prop="labname" :label-width="formLabelWidth">
                    <el-input v-model="labForm.labname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="研究室领域" prop="researcharea" :label-width="formLabelWidth">
                    <el-input v-model="labForm.researcharea" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveLab">确 定</el-button>
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
            labForm: {},
            //表单验证规则
            rules: {
                labname: [
                    { required: true, message: '请输入研究室名称', trigger: 'blur' },
                    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
                ],
                researcharea: [
                    { required: true, message: '请输入研究领域', trigger: 'blur' },
                    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
                ]
            },
            dialogFormVisible: false,
            total: 0,
            researchlab: {
                pageNo: 1,
                pageSize: 10
            },
            labList: []
        }
    },
    methods: {
        handleSizeChange(pageSize) {
            this.researchlab.pageSize = pageSize;
            this.getLabList();

        },
        handleCurrentChange(pageNo) {
            this.researchlab.pageNo = pageNo;
            this.getLabList();
        },
        getLabList() {
            // 按参数获取结果
            CommonApi.getLabList(this.researchlab).then(response => {
                this.labList = response.data.rows;
                this.total = response.data.total;
            });
        },
        openEdit(id) {
            //打开对话框
            if (id == null)
                this.title = "新增研究室";
            else {
                this.title = "修改研究室信息";
                //查询数据
                CommonApi.findOneLab(id).then(response => {
                    this.labForm = response.data;
                });

            }
            this.dialogFormVisible = true;
        },
        clearForm() {
            //清除缓存验证
            this.labForm = {};
            this.$refs.labFormRef.clearValidate();
        },
        saveLab() {
            this.$refs.labFormRef.validate((valid) => {
                if (valid) {
                    alert('成功提交');
                    CommonApi.saveLab(this.labForm).then(response => {
                        //成功消息
                        this.$message({
                            message: '提交成功！',
                            type: 'success'
                        });
                        //关闭对话框
                        this.dialogFormVisible = false;
                        //刷新表格
                        this.getLabList();

                    })
                } else {
                    console.log('错误提交!!');
                    return false;
                }
            });
        },
        deleteLaborNot(Lab) {
            this.$confirm('此操作将永久删除该实验室, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                CommonApi.deleteLab(Lab.labid).then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getLabList();
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
        this.getLabList();
    }
};

</script>

<style>
#search .el-input {
    width: 250px;
    margin-right: 10px;
}

el-dialog.el-input {
    width: 90%;
}
</style>
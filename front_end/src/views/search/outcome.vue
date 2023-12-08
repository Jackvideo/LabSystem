<template>
    <div>
        <!-- 搜索框 -->
        <el-card id="search">
            <el-row>
                <el-col :span="22">
                    <el-input v-model.trim="outcome.outcomeid" placeholder="结果ID"></el-input>
                    <el-input v-model.trim="outcome.projectid" placeholder="项目ID" clearable></el-input>
                    <el-input v-model.trim="outcome.outcomename" placeholder="结果名称" clearable></el-input>
                    <el-input v-model.trim="outcome.achievedate" placeholder="取得时间" clearable></el-input>
                    <!-- 这里不知道为什么通过时间选择器查询不了 -->

                    <el-input v-model="outcome.type" placeholder="类型" clearable></el-input>
                    <el-button @click="getOutcomeList" type="primary" round icon="el-icon-search">查询</el-button>
                </el-col>
                <el-col :span="2">
                    <el-button @click="openEdit(null)" type="primary" icon="el-icon-plus" circle></el-button>
                </el-col>
            </el-row>
        </el-card>

        <!-- 结果列表 -->
        <el-card id="result">
            <el-table :data="outcomeList" border stripe style="width: 100%" :header-cell-style="{ textAlign: 'center' }"
                :cell-style="{ textAlign: 'center' }">
                <el-table-column label="#" width="100">
                    <template v-slot="scope">
                        {{ (outcome.pageNo - 1) * outcome.pageSize + scope.$index + 1 }}
                    </template>
                </el-table-column>
                <!-- 这里的prop要对应api中左边的变量名，也就是数据库的变量名 -->
                <el-table-column prop="outcomeid" label="结果ID" width="150">
                </el-table-column>
                <el-table-column prop="projectid" label="项目ID" width="150">
                </el-table-column>
                <el-table-column prop="outcomename" label="结果名称" width="300">
                </el-table-column>
                <el-table-column prop="achievedate" label="取得时间" type="date" width="200">
                </el-table-column>
                <el-table-column prop="type" label="类型" width="150">
                </el-table-column>
                <el-table-column label="操作" width="200">
                    <template v-slot="scope">
                        <el-button @click="openEdit(scope.row.outcomeid)" type="primary" icon="el-icon-edit"
                            circle></el-button>
                        <el-button @click="deleteOutcomeorNot(scope.row)" type="danger" icon="el-icon-delete"
                            circle></el-button>

                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!-- 分页组件 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="outcome.pageNo"
            :page-sizes="[2, 5, 10, 100]" :page-size="10" layout="total, sizes, prev, pager, next, jumper" :total=total>
        </el-pagination>

        <!-- 新增对象对话框 -->
        <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="outcomeForm" ref="outcomeFormRef" :rules="rules">
                <el-form-item label="项目ID" prop="projectid" :label-width="formLabelWidth">
                    <el-input v-model="outcomeForm.projectid" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="项目名称" prop="outcomename" :label-width="formLabelWidth">
                    <el-input v-model="outcomeForm.outcomename" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="取得时间" prop="achievedate" :label-width="formLabelWidth">
                    <el-date-picker v-model="outcomeForm.achievedate" type="date" placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="类型" prop="type" :label-width="formLabelWidth">
                    <el-select v-model="outcomeForm.type" placeholder="请选择">
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveOutcome">确 定</el-button>
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
            outcomeForm: {},
            //表单验证规则
            rules: {
                outcomename: [
                    { required: true, message: '请输入结果名称', trigger: 'blur' },
                ],
                projectid: [
                    { required: true, message: '请输入项目ID', trigger: 'blur' },
                ],
                achievedate: [
                    { required: true, message: '请选择取得时间', trigger: 'blur' }
                ],
                type: [
                    { required: true, message: '请选择结果类型', trigger: 'blur' },
                ]
            },
            options: [{
                value: '专利',
                label: '专利'
            },
            {
                value: '发明专利',
                label: '发明专利'
            },
            {
                value: '实用新型专利',
                label: '实用新型专利'
            },
            {
                value: '外观专利',
                label: '外观专利'
            }, {
                value: '论文',
                label: '论文'
            }, {
                value: '软件著作权',
                label: '软件著作权'
            }],
            dialogFormVisible: false,
            total: 0,
            outcome: {
                pageNo: 1,
                pageSize: 10
            },
            outcomeList: []
        }
    },
    methods: {
        handleSizeChange(pageSize) {
            this.outcome.pageSize = pageSize;
            this.getOutcomeList();

        },
        handleCurrentChange(pageNo) {
            this.outcome.pageNo = pageNo;
            this.getOutcomeList();
        },
        getOutcomeList() {
            // 按参数获取结果
            CommonApi.getOutcomeList(this.outcome).then(response => {
                this.outcomeList = response.data.rows;
                this.total = response.data.total;
            });
        },
        openEdit(id) {
            //打开对话框
            if (id == null)
                this.title = "新增项目结果";
            else {
                this.title = "修改项目结果信息";
                //查询数据
                CommonApi.findOneOutcome(id).then(response => {
                    this.outcomeForm = response.data;
                });

            }
            this.dialogFormVisible = true;
        },
        clearForm() {
            //清除缓存验证
            this.outcomeForm = {};
            this.$refs.outcomeFormRef.clearValidate();
        },
        saveOutcome() {
            this.$refs.outcomeFormRef.validate((valid) => {
                if (valid) {
                    alert('成功提交');
                    CommonApi.saveOutcome(this.outcomeForm).then(response => {
                        //成功消息
                        this.$message({
                            message: '提交成功！',
                            type: 'success'
                        });
                        //关闭对话框
                        this.dialogFormVisible = false;
                        //刷新表格
                        this.getOutcomeList();

                    })
                } else {
                    console.log('错误提交!!');
                    return false;
                }
            });
        },
        deleteOutcomeorNot(Outcome) {
            this.$confirm('此操作将永久删除该项目结果, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                CommonApi.deleteOutcome(Outcome.outcomeid).then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getOutcomeList();
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
        this.getOutcomeList();
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
<template>
    <div>
        <!-- 搜索框 -->
        <el-card id="search">
            <el-row>
                <el-col :span="22">
                    <el-input v-model.trim="record.projectid" placeholder="项目ID" clearable></el-input>
                    <el-input v-model.trim="record.researcherid" placeholder="研究人员ID" clearable></el-input>
                    <el-button @click="getRecordList" type="primary" round icon="el-icon-search">查询</el-button>
                </el-col>
                <el-col :span="2">
                    <el-button @click="openEdit(null)" type="primary" icon="el-icon-plus" circle></el-button>
                </el-col>
            </el-row>
        </el-card>

        <!-- 结果列表 -->
        <el-card id="result">
            <el-table :data="recordList" stripe style="width: 100%" border :header-cell-style="{ textAlign: 'center' }"
                :cell-style="{ textAlign: 'center' }">
                <el-table-column label="#" width="100">
                    <template v-slot="scope">
                        {{ (record.pageNo - 1) * record.pageSize + scope.$index + 1 }}
                    </template>
                </el-table-column>
                <!-- 这里的prop要对应api中左边的变量名，也就是数据库的变量名 -->
                <el-table-column prop="projectid" label="项目ID" width="100">
                </el-table-column>
                <el-table-column prop="researcherid" label="研究人员ID" width="100">
                </el-table-column>
                <el-table-column prop="participationdate" label="参加时间" width="250">
                </el-table-column>
                <el-table-column prop="workload" label="工作量" type="date" width="250">
                </el-table-column>
                <el-table-column prop="allocatedfund" label="可支配经费" width="250">
                </el-table-column>
                <el-table-column label="  操作" width="200">
                    <template v-slot="scope">
                        <el-button @click="openEdit(scope.row.recordid)" type="primary" icon="el-icon-edit"
                            circle></el-button>
                        <el-button @click="deleteRecordorNot(scope.row)" type="danger" icon="el-icon-delete"
                            circle></el-button>

                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!-- 分页组件 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="record.pageNo"
            :page-sizes="[2, 5, 10, 100]" :page-size="10" layout="total, sizes, prev, pager, next, jumper" :total=total>
        </el-pagination>

        <!-- 新增对象对话框 -->
        <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="recordForm" ref="recordFormRef" :rules="rules">
                <el-form-item label="项目ID" prop="recordid" :label-width="formLabelWidth">
                    <el-input v-model="recordForm.recordid" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="研究人员ID" prop="researcherid" :label-width="formLabelWidth">
                    <el-input v-model="recordForm.researcherid" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="参加时间" prop="participationdate" :label-width="formLabelWidth">
                    <el-date-picker v-model="recordForm.participationdate" type="date" placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="工作量" prop="workload" :label-width="formLabelWidth">
                    <el-input v-model="recordForm.workload" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="可支配经费" prop="allocatedfund" :label-width="formLabelWidth">
                    <el-input v-model="recordForm.allocatedfund" autocomplete="off"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveRecord">确 定</el-button>
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
            recordForm: {},
            //表单验证规则
            rules: {
                projectid: [
                    { required: true, message: '请输入项目ID', trigger: 'blur' },
                ],
                researcherid: [
                    { required: true, message: '请输入研究人员ID', trigger: 'blur' },
                ]
            },
            dialogFormVisible: false,
            spVisible: false,
            total: 0,
            record: {
                pageNo: 1,
                pageSize: 10
            },
            recordList: [],
        }
    },
    methods: {
        handleSizeChange(pageSize) {
            this.record.pageSize = pageSize;
            this.getRecordList();

        },
        handleCurrentChange(pageNo) {
            this.record.pageNo = pageNo;
            this.getRecordList();
        },
        getRecordList() {
            // 按参数获取结果
            CommonApi.getRecordList(this.record).then(response => {
                this.recordList = response.data.rows;
                this.total = response.data.total;
            });
        },
        openEdit(id) {
            //打开对话框
            if (id == null)
                this.title = "新增参与记录";
            else {
                this.title = "修改记录信息";
                //查询数据
                CommonApi.findOneRecord(id).then(response => {
                    this.recordForm = response.data;
                });

            }
            this.dialogFormVisible = true;
        },
        clearForm() {
            //清除缓存验证
            this.recordForm = {};
            this.$refs.recordFormRef.clearValidate();
        },
        saveRecord() {
            this.$refs.recordFormRef.validate((valid) => {
                if (valid) {
                    alert('成功提交');
                    CommonApi.saveRecord(this.recordForm).then(response => {
                        //成功消息
                        this.$message({
                            message: '提交成功！',
                            type: 'success'
                        });
                        //关闭对话框
                        this.dialogFormVisible = false;
                        //刷新表格
                        this.getRecordList();

                    })
                } else {
                    console.log('错误提交!!');
                    return false;
                }
            });
        },
        deleteRecordorNot(Record) {
            this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                CommonApi.deleteRecord(Record.recordid).then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getRecordList();
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
        this.getRecordList();
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
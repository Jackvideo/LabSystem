<template>
    <div>
        <!-- 搜索框 -->
        <el-card id="search">
            <el-row>
                <el-col :span="22">
                    <el-input v-model.trim="leader.leaderid" placeholder="负责人ID"></el-input>
                    <el-input v-model.trim="leader.workphone" placeholder="工作电话" clearable></el-input>
                    <el-button @click="getLeaderList" type="primary" round icon="el-icon-search">查询</el-button>
                </el-col>
                <el-col :span="2">
                    <el-button @click="openEdit(null)" type="primary" icon="el-icon-plus" circle></el-button>
                </el-col>
            </el-row>
        </el-card>

        <!-- 结果列表 -->
        <el-card id="result">
            <el-table :data="leaderList" stripe style="width: 100%" border :header-cell-style="{ textAlign: 'center' }"
                :cell-style="{ textAlign: 'center' }">
                <el-table-column label="#" width="100">
                    <template v-slot="scope">
                        {{ (leader.pageNo - 1) * leader.pageSize + scope.$index + 1 }}
                    </template>
                </el-table-column>
                <!-- 这里的prop要对应api中左边的变量名，也就是数据库的变量名 -->
                <el-table-column prop="leaderid" label="负责人ID" width="150">
                </el-table-column>
                <el-table-column prop="workphone" label="工作电话" width="250">
                </el-table-column>
                <el-table-column prop="mobilephone" label="移动电话" width="250">
                </el-table-column>
                <el-table-column prop="email" label="邮箱" width="250">
                </el-table-column>
                <el-table-column label="  操作" width="250">
                    <template v-slot="scope">
                        <el-button @click="openEdit(scope.row.leaderid)" type="primary" icon="el-icon-edit"
                            circle></el-button>
                        <el-button @click="deleteLeaderorNot(scope.row)" type="danger" icon="el-icon-delete"
                            circle></el-button>

                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!-- 分页组件 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="leader.pageNo"
            :page-sizes="[2, 5, 10, 100]" :page-size="10" layout="total, sizes, prev, pager, next, jumper" :total=total>
        </el-pagination>

        <!-- 新增对象对话框 -->
        <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="leaderForm" ref="leaderFormRef" :rules="rules">
                <el-form-item label="工作电话" prop="workphone" :label-width="formLabelWidth">
                    <el-input v-model="leaderForm.workphone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="移动电话" prop="mobilephone" :label-width="formLabelWidth">
                    <el-input v-model="leaderForm.mobilephone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email" :label-width="formLabelWidth">
                    <el-input v-model="leaderForm.email" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveLeader">确 定</el-button>
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
            leaderForm: {},
            //表单验证规则
            rules: {
                workphone: [
                    { required: true, message: '请输入负责人工作电话', trigger: 'blur' },
                ],
                email: [
                    { required: true, message: '请输入负责人邮箱地址', trigger: 'blur' }
                ]
            },
            dialogFormVisible: false,
            total: 0,
            leader: {
                pageNo: 1,
                pageSize: 10
            },
            leaderList: []
        }
    },
    methods: {
        handleSizeChange(pageSize) {
            this.leader.pageSize = pageSize;
            this.getLeaderList();

        },
        handleCurrentChange(pageNo) {
            this.leader.pageNo = pageNo;
            this.getLeaderList();
        },
        getLeaderList() {
            // 按参数获取结果
            CommonApi.getLeaderList(this.leader).then(response => {
                this.leaderList = response.data.rows;
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
                CommonApi.findOneLeader(id).then(response => {
                    this.leaderForm = response.data;
                });

            }
            this.dialogFormVisible = true;
        },
        clearForm() {
            //清除缓存验证
            this.leaderForm = {};
            this.$refs.leaderFormRef.clearValidate();
        },
        saveLeader() {
            this.$refs.leaderFormRef.validate((valid) => {
                if (valid) {
                    alert('成功提交');
                    CommonApi.saveLeader(this.leaderForm).then(response => {
                        //成功消息
                        this.$message({
                            message: '提交成功！',
                            type: 'success'
                        });
                        //关闭对话框
                        this.dialogFormVisible = false;
                        //刷新表格
                        this.getLeaderList();

                    })
                } else {
                    console.log('错误提交!!');
                    return false;
                }
            });
        },
        deleteLeaderorNot(Leader) {
            this.$confirm('此操作将永久删除该负责人, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                CommonApi.deleteLeader(Leader.leaderid).then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getLeaderList();
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
        this.getLeaderList();
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
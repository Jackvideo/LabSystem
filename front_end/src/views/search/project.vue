<template>
    <div>
        <!-- 搜索框 -->
        <el-card id="search">
            <el-row>
                <el-col :span="22">
                    <el-input v-model.trim="project.projectid" placeholder="项目ID"></el-input>
                    <el-input v-model.trim="project.leaderid" placeholder="负责人ID" clearable></el-input>
                    <el-input v-model.trim="project.projectname" placeholder="项目名称" clearable></el-input>
                    <!-- 这里不知道为什么通过时间选择器查询不了 -->
                    <el-button @click="getProjectList" type="primary" round icon="el-icon-search">查询</el-button>
                </el-col>
                <el-col :span="2">
                    <el-button @click="openEdit(null)" type="primary" icon="el-icon-plus" circle></el-button>
                </el-col>
            </el-row>
        </el-card>

        <!-- 结果列表 -->
        <el-card id="result">
            <el-table :data="projectList" stripe style="width: 100%" border :header-cell-style="{ textAlign: 'center' }"
                :cell-style="{ textAlign: 'center' }">
                <el-table-column label="#" width="50">
                    <template v-slot="scope">
                        {{ (project.pageNo - 1) * project.pageSize + scope.$index + 1 }}
                    </template>
                </el-table-column>
                <!-- 这里的prop要对应api中左边的变量名，也就是数据库的变量名 -->
                <el-table-column prop="projectid" label="项目ID" width="100">
                </el-table-column>
                <el-table-column prop="leaderid" label="负责人ID" width="100">
                </el-table-column>
                <el-table-column prop="projectname" label="项目名称" width="200">
                </el-table-column>
                <el-table-column prop="content" label="内容" type="date" width="200">
                </el-table-column>
                <el-table-column prop="totalfund" label="经费" width="120">
                </el-table-column>
                <el-table-column prop="startdate" label="开始时间" width="125">
                </el-table-column>
                <el-table-column prop="enddate" label="结束时间" width="125">
                </el-table-column>
                <el-table-column label="  操作" width="200">
                    <template v-slot="scope">
                        <el-button @click="openEdit(scope.row.projectid)" type="primary" icon="el-icon-edit"
                            circle></el-button>
                        <el-button @click="deleteProjectorNot(scope.row)" type="danger" icon="el-icon-delete"
                            circle></el-button>
                        <el-button @click="openSubEdit(scope.row.projectid)" icon="el-icon-more" type="info"
                            circle></el-button>

                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!-- 分页组件 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="project.pageNo"
            :page-sizes="[2, 5, 10, 100]" :page-size="10" layout="total, sizes, prev, pager, next, jumper" :total=total>
        </el-pagination>

        <!-- 新增对象对话框 -->
        <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="projectForm" ref="projectFormRef" :rules="rules">
                <el-form-item label="项目ID" prop="projectid" :label-width="formLabelWidth">
                    <el-input v-model="projectForm.projectid" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="项目名称" prop="projectname" :label-width="formLabelWidth">
                    <el-input v-model="projectForm.projectname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="项目内容" prop="content" :label-width="formLabelWidth">
                    <el-input v-model="projectForm.content" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="项目经费" prop="totalfund" :label-width="formLabelWidth">
                    <el-input v-model="projectForm.totalfund" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="开始时间" prop="startdate" :label-width="formLabelWidth">
                    <el-date-picker v-model="projectForm.startdate" type="date" placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="结束时间" prop="enddate" :label-width="formLabelWidth">
                    <el-date-picker v-model="projectForm.enddate" type="date" placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveProject">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 子项目对话框 -->
        <el-dialog title="子项目" :visible.sync="spVisible" width="80%">
            <!-- 引入子组件 -->
            <Subproject :theprojectid='pid'></Subproject>
            <span slot="footer" class="dialog-footer">
                <el-button @click="spVisible = false">取 消</el-button>
                <el-button type="primary" @click="spVisible = false">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 存在问题，theprojectid不刷新，只能获取第一次点击的子项目值 -->
    </div>
</template>

<script>

import CommonApi from '@/api/back_end'
import Subproject from './subproject.vue'
export default {
    components: { Subproject },
    data() {
        return {
            title: "",
            formLabelWidth: '150px',
            projectForm: {},
            //表单验证规则
            rules: {
                projectname: [
                    { required: true, message: '请输入项目名称', trigger: 'blur' },
                ],
                leaderid: [
                    { required: true, message: '请输入负责人ID', trigger: 'blur' },
                ],
                content: [
                    { required: true, message: '请输入项目内容', trigger: 'blur' }
                ],
                totalfund: [
                    { required: true, message: '请输入项目经费', trigger: 'blur' },
                ]
            },
            pid: 1,
            dialogFormVisible: false,
            spVisible: false,
            total: 0,
            project: {
                pageNo: 1,
                pageSize: 10
            },
            projectList: [],
        }
    },
    methods: {
        handleSizeChange(pageSize) {
            this.project.pageSize = pageSize;
            this.getProjectList();

        },
        handleCurrentChange(pageNo) {
            this.project.pageNo = pageNo;
            this.getProjectList();
        },
        getProjectList() {
            // 按参数获取结果
            CommonApi.getProjectList(this.project).then(response => {
                this.projectList = response.data.rows;
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
                CommonApi.findOneProject(id).then(response => {
                    this.projectForm = response.data;
                });

            }
            this.dialogFormVisible = true;
        },
        openSubEdit(id) {
            //打开子项目对话框
            //查询数据
            this.pid = id;
            this.spVisible = true;
        },
        clearForm() {
            //清除缓存验证
            this.projectForm = {};
            this.$refs.projectFormRef.clearValidate();
        },
        saveProject() {
            this.$refs.projectFormRef.validate((valid) => {
                if (valid) {
                    alert('成功提交');
                    CommonApi.saveProject(this.projectForm).then(response => {
                        //成功消息
                        this.$message({
                            message: '提交成功！',
                            type: 'success'
                        });
                        //关闭对话框
                        this.dialogFormVisible = false;
                        //刷新表格
                        this.getProjectList();

                    })
                } else {
                    console.log('错误提交!!');
                    return false;
                }
            });
        },
        deleteProjectorNot(Project) {
            this.$confirm('此操作将永久删除该项目, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                CommonApi.deleteProject(Project.projectid).then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getProjectList();
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
        this.getProjectList();
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
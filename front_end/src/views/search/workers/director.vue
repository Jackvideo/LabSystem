<template>
    <div>
        <!-- 搜索框 -->
        <el-card id="search">
            <el-row>
                <el-col :span="22">
                    <el-input v-model.trim="director.directorid" placeholder="主任ID"></el-input>
                    <el-input v-model.trim="director.labid" placeholder="研究室ID" clearable></el-input>
                    <el-button @click="getDirectorList" type="primary" round icon="el-icon-search">查询</el-button>
                </el-col>
                <el-col :span="2">
                    <el-button @click="openEdit(null)" type="primary" icon="el-icon-plus" circle></el-button>
                </el-col>
            </el-row>
        </el-card>

        <!-- 结果列表 -->
        <el-card id="result">
            <el-table :data="directorList" stripe style="width: 100%">
                <el-table-column label="#" width="100">
                    <template v-slot="scope">
                        {{ (director.pageNo - 1) * director.pageSize + scope.$index + 1 }}
                    </template>
                </el-table-column>
                <!-- 这里的prop要对应api中左边的变量名，也就是数据库的变量名 -->
                <el-table-column prop="directorid" label="主任ID" width="150">
                </el-table-column>
                <el-table-column prop="labid" label="研究室ID" width="250">
                </el-table-column>
                <el-table-column prop="startdate" label="上任时间" width="250">
                </el-table-column>
                <el-table-column prop="term" label="任期" width="250">
                </el-table-column>
                <el-table-column label="  操作" width="250">
                    <template v-slot="scope">
                        <el-button @click="openEdit(scope.row.directorid)" type="primary" icon="el-icon-edit"
                            circle></el-button>
                        <el-button @click="deleteDirectororNot(scope.row)" type="danger" icon="el-icon-delete"
                            circle></el-button>

                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!-- 分页组件 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="director.pageNo"
            :page-sizes="[2, 5, 10, 100]" :page-size="10" layout="total, sizes, prev, pager, next, jumper" :total=total>
        </el-pagination>

        <!-- 新增对象对话框 -->
        <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="directorForm" ref="directorFormRef" :rules="rules">
                <el-form-item label="负责的研究室ID" prop="labid" :label-width="formLabelWidth">
                    <el-input v-model="directorForm.labid" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="上任时间" prop="startdate" :label-width="formLabelWidth">
                    <el-date-picker v-model="directorForm.startdate" type="date" placeholder="选择日期"></el-date-picker>
                </el-form-item>
                <el-form-item label="任期" prop="term" :label-width="formLabelWidth">
                    <el-input v-model="directorForm.term" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveDirector">确 定</el-button>
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
            directorForm: {},
            //表单验证规则
            rules: {
                labid: [
                    { required: true, message: '请输入负责研究室ID', trigger: 'blur' },
                ],
                startdate: [
                    { required: true, message: '请选择上任时间', trigger: 'blur' }
                ]
            },
            dialogFormVisible: false,
            total: 0,
            director: {
                pageNo: 1,
                pageSize: 10
            },
            directorList: []
        }
    },
    methods: {
        handleSizeChange(pageSize) {
            this.director.pageSize = pageSize;
            this.getDirectorList();

        },
        handleCurrentChange(pageNo) {
            this.director.pageNo = pageNo;
            this.getDirectorList();
        },
        getDirectorList() {
            // 按参数获取结果
            CommonApi.getDirectorList(this.director).then(response => {
                this.directorList = response.data.rows;
                this.total = response.data.total;
            });
        },
        openEdit(id) {
            //打开对话框
            if (id == null)
                this.title = "新增研究室主任";
            else {
                this.title = "修改主任信息";
                //查询数据
                CommonApi.findOneDirector(id).then(response => {
                    this.directorForm = response.data;
                });

            }
            this.dialogFormVisible = true;
        },
        clearForm() {
            //清除缓存验证
            this.directorForm = {};
            this.$refs.directorFormRef.clearValidate();
        },
        saveDirector() {
            this.$refs.directorFormRef.validate((valid) => {
                if (valid) {
                    alert('成功提交');
                    CommonApi.saveDirector(this.directorForm).then(response => {
                        //成功消息
                        this.$message({
                            message: '提交成功！',
                            type: 'success'
                        });
                        //关闭对话框
                        this.dialogFormVisible = false;
                        //刷新表格
                        this.getDirectorList();

                    })
                } else {
                    console.log('错误提交!!');
                    return false;
                }
            });
        },
        deleteDirectororNot(Director) {
            this.$confirm('此操作将永久删除该主任, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                CommonApi.deleteDirector(Director.directorid).then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getDirectorList();
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
        this.getDirectorList();
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
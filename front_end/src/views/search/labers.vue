<template>
    <div>
        <!-- 搜索框 -->
        <el-card id="search">
            <el-row>
                <el-col :span="22">
                    <el-input v-model.trim="researcher.id" placeholder="人员ID"></el-input>
                    <el-input v-model.trim="researcher.labid" placeholder="研究室ID" clearable></el-input>
                    <el-input v-model.trim="researcher.name" placeholder="姓名" clearable></el-input>
                    <el-input v-model.trim="researcher.gender" placeholder="性别"></el-input>
                    <el-input v-model.trim="researcher.researcherarea" placeholder="研究领域" clearable></el-input>
                    <el-button @click="getLaberList" type="primary" round icon="el-icon-search">查询</el-button>
                </el-col>
                <el-col :span="2">
                    <el-button @click="openEdit(null)" type="primary" icon="el-icon-plus" circle></el-button>
                </el-col>
            </el-row>
        </el-card>

        <!-- 结果列表 -->
        <el-card id="result">
            <el-table :data="laberList" border stripe style="width: 100%" :header-cell-style="{ textAlign: 'center' }"
                :cell-style="{ textAlign: 'center' }">
                <el-table-column label="#" width="100">
                    <template v-slot="scope">
                        {{ (researcher.pageNo - 1) * researcher.pageSize + scope.$index + 1 }}
                    </template>
                </el-table-column>
                <!-- 这里的prop要对应api中左边的变量名，也就是数据库的变量名 -->
                <el-table-column prop="researcherid" label="人员ID" width="100">
                </el-table-column>
                <el-table-column prop="labid" label="隶属研究室" width="100">
                </el-table-column>
                <el-table-column prop="researchername" label="姓名" width="150">
                </el-table-column>
                <el-table-column prop="gender" label="性别" width="150">
                </el-table-column>
                <el-table-column prop="level" label="职称" width="150">
                </el-table-column>
                <el-table-column prop="age" label="年龄" width="150">
                </el-table-column>
                <el-table-column prop="researcharea" label="研究领域" width="250">
                </el-table-column>
                <el-table-column label="操作" width="100">
                    <template v-slot="scope">
                        <el-button @click="openEdit(scope.row.researcherid)" type="primary" icon="el-icon-edit"
                            circle></el-button>
                        <el-button @click="deleteLaberorNot(scope.row)" type="danger" icon="el-icon-delete"
                            circle></el-button>

                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!-- 分页组件 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="researcher.pageNo" :page-sizes="[2, 5, 10, 100]" :page-size="10"
            layout="total, sizes, prev, pager, next, jumper" :total=total>
        </el-pagination>

        <!-- 新增对象对话框 -->
        <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="laberForm" ref="laberFormRef" :rules="rules">
                <el-form-item label="研究人员姓名" prop="researchername" :label-width="formLabelWidth">
                    <el-input v-model="laberForm.researchername" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="隶属研究室" prop="labid" :label-width="formLabelWidth">
                    <el-input v-model="laberForm.labid" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="gender" :label-width="formLabelWidth">
                    <el-input v-model="laberForm.gender" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="职称" prop="level" :label-width="formLabelWidth">
                    <el-input v-model="laberForm.level" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="年龄" prop="age" :label-width="formLabelWidth">
                    <el-input v-model="laberForm.age" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="研究室领域" prop="researcharea" :label-width="formLabelWidth">
                    <el-input v-model="laberForm.researcharea" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveLaber">确 定</el-button>
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
            laberForm: {},
            //表单验证规则
            rules: {
                researchername: [
                    { required: true, message: '请输入人员姓名', trigger: 'blur' },
                    { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
                ],
                labid: [
                    { required: true, message: '请输入隶属研究室ID', trigger: 'blur' },
                ],
                gender: [
                    { required: true, message: '请输入人员性别', trigger: 'blur' }
                ],
                researcharea: [
                    { required: true, message: '请输入研究领域', trigger: 'blur' },
                    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
                ]
            },
            dialogFormVisible: false,
            total: 0,
            researcher: {
                pageNo: 1,
                pageSize: 10
            },
            laberList: []
        }
    },
    methods: {
        handleSizeChange(pageSize) {
            this.researcher.pageSize = pageSize;
            this.getLaberList();

        },
        handleCurrentChange(pageNo) {
            this.researcher.pageNo = pageNo;
            this.getLaberList();
        },
        getLaberList() {
            // 按参数获取结果
            CommonApi.getLaberList(this.researcher).then(response => {
                this.laberList = response.data.rows;
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
                CommonApi.findOneLaber(id).then(response => {
                    this.laberForm = response.data;
                });

            }
            this.dialogFormVisible = true;
        },
        clearForm() {
            //清除缓存验证
            this.laberForm = {};
            this.$refs.laberFormRef.clearValidate();
        },
        saveLaber() {
            this.$refs.laberFormRef.validate((valid) => {
                if (valid) {
                    alert('成功提交');
                    CommonApi.saveLaber(this.laberForm).then(response => {
                        //成功消息
                        this.$message({
                            message: '提交成功！',
                            type: 'success'
                        });
                        //关闭对话框
                        this.dialogFormVisible = false;
                        //刷新表格
                        this.getLaberList();

                    })
                } else {
                    console.log('错误提交!!');
                    return false;
                }
            });
        },
        deleteLaberorNot(Laber) {
            this.$confirm('此操作将永久删除该实验室, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                CommonApi.deleteLaber(Laber.researcherid).then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getLaberList();
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
        this.getLaberList();
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
<template>
    <div>
        <!-- 搜索框 -->
        <el-card id="search">
            <el-row>
                <el-col :span="22">
                    <el-input v-model.trim="department.departmentid" placeholder="单位ID" clearable></el-input>
                    <el-select @visible-change="getDepartmentList" v-model="department.type" placeholder="单位类型" clearable>
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                    <el-button @click="getDepartmentList" type="primary" round icon="el-icon-search">查询</el-button>
                </el-col>
                <el-col :span="2">
                    <el-button @click="openEdit(null)" type="primary" icon="el-icon-plus" circle></el-button>
                </el-col>
            </el-row>
        </el-card>

        <!-- 结果列表 -->
        <el-card id="result">
            <el-table :data="departmentList" stripe style="width: 100%" border :header-cell-style="{ textAlign: 'center' }"
                :cell-style="{ textAlign: 'center' }">
                <el-table-column label="#" width="100">
                    <template v-slot="scope">
                        {{ (department.pageNo - 1) * department.pageSize + scope.$index + 1 }}
                    </template>
                </el-table-column>
                <!-- 这里的prop要对应api中左边的变量名，也就是数据库的变量名 -->
                <el-table-column prop="type" label="单位类型" width="250">
                </el-table-column>
                <el-table-column prop="departmentid" label="单位ID" width="100">
                </el-table-column>
                <el-table-column prop="leaderid" label="负责人ID" width="100">
                </el-table-column>
                <el-table-column prop="departmentname" label="单位名称" width="250">
                </el-table-column>
                <el-table-column prop="address" label="单位地址" width="250">
                </el-table-column>
                <el-table-column label="  操作" width="200">
                    <template v-slot="scope">
                        <el-button @click="openEdit(scope.row.departmentid)" type="primary" icon="el-icon-edit"
                            circle></el-button>
                        <el-button @click="deleteDepartmentorNot(scope.row)" type="danger" icon="el-icon-delete"
                            circle></el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!-- 分页组件 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="department.pageNo" :page-sizes="[2, 5, 10, 100]" :page-size="10"
            layout="total, sizes, prev, pager, next, jumper" :total=total>
        </el-pagination>

        <!-- 新增对象对话框 -->
        <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="departmentForm" ref="departmentFormRef" :rules="rules">
                <el-form-item label="单位类型" prop="type" :label-width="formLabelWidth">
                    <el-select v-model="departmentForm.type" placeholder="请选择" clearable>
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="负责人ID" prop="leaderid" :label-width="formLabelWidth">
                    <el-input v-model="departmentForm.leaderid" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="单位名称" prop="departmentname" :label-width="formLabelWidth">
                    <el-input v-model="departmentForm.departmentname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="单位地址" prop="address" :label-width="formLabelWidth">
                    <el-input v-model="departmentForm.address" autocomplete="off"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveDepartment">确 定</el-button>
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
            departmentForm: {},
            //表单验证规则
            rules: {
                type: [
                    { required: true, message: '请选择单位类型', trigger: 'blur' },
                ],
                departmentname: [
                    { required: true, message: '请输入单位名称', trigger: 'blur' },
                ]
            },
            options: [{
                value: '委托方',
                label: '委托方'
            }, {
                value: '合作方',
                label: '合作方'
            }, {
                value: '监测方',
                label: '监测方'
            }],
            dialogFormVisible: false,
            spVisible: false,
            total: 0,
            department: {
                pageNo: 1,
                pageSize: 10
            },
            departmentList: [],
        }
    },
    methods: {
        handleSizeChange(pageSize) {
            this.department.pageSize = pageSize;
            this.getDepartmentList();

        },
        handleCurrentChange(pageNo) {
            this.department.pageNo = pageNo;
            this.getDepartmentList();
        },
        getDepartmentList() {
            // 按参数获取结果
            CommonApi.getDepartmentList(this.department).then(response => {
                this.departmentList = response.data.rows;
                this.total = response.data.total;
            });
        },
        openEdit(id) {
            //打开对话框
            if (id == null)
                this.title = "新增单位";
            else {
                this.title = "修改单位信息";
                //查询数据
                CommonApi.findOneDepartment(id).then(response => {
                    this.departmentForm = response.data;
                });

            }
            this.dialogFormVisible = true;
        },
        clearForm() {
            //清除缓存验证
            this.departmentForm = {};
            this.$refs.departmentFormRef.clearValidate();
        },
        saveDepartment() {
            this.$refs.departmentFormRef.validate((valid) => {
                if (valid) {
                    alert('成功提交');
                    CommonApi.saveDepartment(this.departmentForm).then(response => {
                        //成功消息
                        this.$message({
                            message: '提交成功！',
                            type: 'success'
                        });
                        //关闭对话框
                        this.dialogFormVisible = false;
                        //刷新表格
                        this.getDepartmentList();

                    })
                } else {
                    console.log('错误提交!!');
                    return false;
                }
            });
        },
        deleteDepartmentorNot(Department) {
            this.$confirm('此操作将永久删除该单位, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                CommonApi.deleteDepartment(Department.departmentid).then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getDepartmentList();
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
        this.getDepartmentList();
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
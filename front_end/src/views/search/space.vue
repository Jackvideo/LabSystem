<template>
    <div>
        <!-- 搜索框 -->
        <el-card id="search">
            <el-row>
                <el-col :span="22">
                    <el-input v-model.trim="officespace.spaceid" placeholder="场地ID"></el-input>
                    <el-input v-model.trim="officespace.labid" placeholder="研究室ID" clearable></el-input>
                    <el-input v-model.trim="officespace.spacename" placeholder="场地名称" clearable></el-input>
                    <el-button @click="getSpaceList" type="primary" round icon="el-icon-search">查询</el-button>
                </el-col>
                <el-col :span="2">
                    <el-button @click="openEdit(null)" type="primary" icon="el-icon-plus" circle></el-button>
                </el-col>
            </el-row>
        </el-card>

        <!-- 结果列表 -->
        <el-card id="result">
            <el-table :data="officespaceList" stripe style="width: 100%">
                <el-table-column label="#" width="100">
                    <template v-slot="scope">
                        {{ (officespace.pageNo - 1) * officespace.pageSize + scope.$index + 1 }}
                    </template>
                </el-table-column>
                <!-- 这里的prop要对应api中左边的变量名，也就是数据库的变量名 -->
                <el-table-column prop="spaceid" label="场地ID" width="150">
                </el-table-column>
                <el-table-column prop="labid" label="研究室ID" width="150">
                </el-table-column>
                <el-table-column prop="spacename" label="场地名称" width="300">
                </el-table-column>
                <el-table-column prop="area" label="面积" type="date" width="200">
                </el-table-column>
                <el-table-column prop="address" label="地址" width="150">
                </el-table-column>
                <el-table-column label="操作" width="200">
                    <template v-slot="scope">
                        <el-button @click="openEdit(scope.row.spaceid)" type="primary" icon="el-icon-edit"
                            circle></el-button>
                        <el-button @click="deleteSpaceorNot(scope.row)" type="danger" icon="el-icon-delete"
                            circle></el-button>

                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!-- 分页组件 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="officespace.pageNo" :page-sizes="[2, 5, 10, 100]" :page-size="10"
            layout="total, sizes, prev, pager, next, jumper" :total=total>
        </el-pagination>

        <!-- 新增对象对话框 -->
        <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="officespaceForm" ref="officespaceFormRef" :rules="rules">
                <el-form-item label="所属研究室ID" prop="labid" :label-width="formLabelWidth">
                    <el-input v-model.trim="officespaceForm.labid" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="场地名称" prop="spacename" :label-width="formLabelWidth">
                    <el-input v-model.trim="officespaceForm.spacename" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="场地面积" prop="area" :label-width="formLabelWidth">
                    <el-input v-model.trim="officespaceForm.area" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="地址" prop="address" :label-width="formLabelWidth">
                    <el-input v-model.trim="officespaceForm.address" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveSpace">确 定</el-button>
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
            officespaceForm: {},
            //表单验证规则
            rules: {
                spacename: [
                    { required: true, message: '请输入场地名称', trigger: 'blur' },
                ],
                area: [
                    { required: true, message: '请输入场地面积', trigger: 'blur' }
                ],
                address: [
                    { required: true, message: '请输入场地地址', trigger: 'blur' },
                ]
            },
            dialogFormVisible: false,
            total: 0,
            officespace: {
                pageNo: 1,
                pageSize: 10
            },
            officespaceList: []
        }
    },
    methods: {
        handleSizeChange(pageSize) {
            this.officespace.pageSize = pageSize;
            this.getSpaceList();

        },
        handleCurrentChange(pageNo) {
            this.officespace.pageNo = pageNo;
            this.getSpaceList();
        },
        getSpaceList() {
            // 按参数获取结果
            CommonApi.getSpaceList(this.officespace).then(response => {
                this.officespaceList = response.data.rows;
                this.total = response.data.total;
            });
        },
        openEdit(id) {
            //打开对话框
            if (id == null)
                this.title = "新增场地";
            else {
                this.title = "修改场地信息";
                //查询数据
                CommonApi.findOneSpace(id).then(response => {
                    this.officespaceForm = response.data;
                });

            }
            this.dialogFormVisible = true;
        },
        clearForm() {
            //清除缓存验证
            this.officespaceForm = {};
            this.$refs.officespaceFormRef.clearValidate();
        },
        saveSpace() {
            this.$refs.officespaceFormRef.validate((valid) => {
                if (valid) {
                    alert('成功提交');
                    CommonApi.saveSpace(this.officespaceForm).then(response => {
                        //成功消息
                        this.$message({
                            message: '提交成功！',
                            type: 'success'
                        });
                        //关闭对话框
                        this.dialogFormVisible = false;
                        //刷新表格
                        this.getSpaceList();

                    })
                } else {
                    console.log('错误提交!!');
                    return false;
                }
            });
        },
        deleteSpaceorNot(Space) {
            this.$confirm('此操作将永久删除该场地, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                CommonApi.deleteSpace(Space.officespaceid).then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getSpaceList();
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
        this.getSpaceList();
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
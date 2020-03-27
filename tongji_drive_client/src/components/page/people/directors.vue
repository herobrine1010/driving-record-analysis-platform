<template>
    <div>
        <el-card shadow="hover">
            <div slot="header">
                <span>导演信息管理</span>
            </div>
            <el-form :inline="true">
                <el-form-item>
                    <el-button type="primary" @click="create_form_visible = true">新建导演</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="danger" @click="handleBatchDelete">批量删除</el-button>
                </el-form-item>
            </el-form>
            <el-table :data="directors" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="director_id" label="导演ID" align="center"> </el-table-column>
                <el-table-column prop="name" label="导演名称" align="center"> </el-table-column>
                <el-table-column prop="gender" label="导演性别" align="center">
                    <template slot-scope="scope">
                        <div v-if="scope.row.gender === '1'">
                            <span>男</span>
                        </div>
                        <div v-else-if="scope.row.gender === '2'">
                            <span>女</span>
                        </div>
                        <div v-else>
                            <span>未知</span>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" fixed="right">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="showEditForm(scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" primary @click="handleSingleDelete(scope.row.director_id)"
                            >删除</el-button
                        >
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
        <br />
        <el-dialog title="编辑导演信息" :visible.sync="edit_form_visible" width="30%">
            <el-form :model="edit_form" label-width="100px" label-position="right" :inline="true">
                <el-form-item label="导演ID: ">
                    <el-input v-model="edit_form.director_id" style="width:220px" :disabled="true" prefix-icon="el-icon-discount"></el-input>
                </el-form-item>
                <br />
                <el-form-item label="导演名称: ">
                    <el-input v-model="edit_form.name" style="width:220px" prefix-icon="el-icon-user" clearable></el-input>
                </el-form-item>
                <el-form-item label="导演性别: ">
                    <el-radio-group v-model="edit_form.gender" size="small">
                        <el-radio label="0">未知</el-radio>
                        <el-radio label="1">男</el-radio>
                        <el-radio label="2">女</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="edit_form_visible = false">取 消</el-button>
                <el-button type="primary" @click="uploadEditForm">提 交</el-button>
            </span>
        </el-dialog>

        <el-dialog title="新增导演信息" :visible.sync="create_form_visible">
            <el-form :model="create_form" :ref="create_form" label-width="100px" label-position="right" :inline="true">
                <el-form-item label="导演名称: ">
                    <el-input v-model="create_form.title" style="width:220px" prefix-icon="el-icon-user" clearable></el-input>
                </el-form-item>
                <br />
                <el-form-item label="导演性别: ">
                    <el-radio-group v-model="create_form.gender" size="small">
                        <el-radio label="0">未知</el-radio>
                        <el-radio label="1">男</el-radio>
                        <el-radio label="2">女</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="create_form_visible = false">取 消</el-button>
                <el-button type="primary" @click="uploadCreateForm">提 交</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data() {
        return {
            directors: [
                {
                    director_id: 0,
                    name: 'test1',
                    gender: '1'
                },
                {
                    director_id: 1,
                    name: 'test1',
                    gender: '0'
                }
            ],
            edit_form: {
                director_id: '',
                name: '',
                gender: ''
            },
            create_form: {
                director_id: '',
                name: '',
                gender: ''
            },
            create_form_visible: false,
            edit_form_visible: false,
            delete_dialog_visible: false,
            select_list: []
        };
    },
    methods: {
        showEditForm: function(director) {
            console.log(director)
            this.edit_form_visible = true;
            this.edit_form.director_id = director.director_id;
            this.edit_form.name = director.name;
            this.edit_form.gender = director.gender;
        },
        uploadEditForm: function() {
            console.log(this.edit_form);
            this.$confirm('确定要更新选中导演的信息吗？', '提示', {
                type: 'warning'
            }).then();
            // todo： 向后端提交更新导演的请求
        },
        uploadCreateForm: function() {
            console.log(this.create_form);
            this.$confirm('确定要新建导演信息吗？', '提示', {
                type: 'warning'
            }).then();
        },
        handleSelectionChange: function(row_num_list) {
            console.log(row_num_list);
            this.select_list = row_num_list;
        },
        handleSingleDelete: function(director_id) {
            console.log(director_id);
            this.$confirm('确定要删除选中导演吗？', '提示', {
                type: 'warning'
            }).then();
            // todo: 向后端提交删除单个导演的请求
        },
        handleBatchDelete: function() {
            let delete_list = [];
            for (var i = 0; i < this.select_list.length; i++) {
                delete_list.push(this.select_list[i].director_id);
            }
            console.log(delete_list);
            this.$confirm('确定要删除选中导演吗？', '提示', {
                type: 'warning'
            }).then();
            // todo: 向后端提交批量删除导演的请求
        }
    }
};
</script>

<style scoped>
.red {
    color: #ff0000;
}
</style>

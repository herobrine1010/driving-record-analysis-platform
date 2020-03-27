<template>
    <div>
        <el-card shadow="hover">
            <div slot="header">
                <span>类别信息管理</span>
            </div>
            <el-form :inline="true">
                <el-form-item>
                    <el-button type="primary" @click="create_form_visible = true">新建类别</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="danger" @click="handleBatchDelete">批量删除</el-button>
                </el-form-item>
            </el-form>
            <el-table :data="genres" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="genre_id" label="类别ID" align="center"> </el-table-column>
                <el-table-column prop="name" label="类别名称" align="center" > </el-table-column>
                <el-table-column label="操作" align="center" fixed="right">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="showEditForm(scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" primary @click="handleSingleDelete(scope.row.genre_id)"
                            >删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
        <br />
        <el-dialog title="编辑类别信息" :visible.sync="edit_form_visible" width="30%">
            <el-form :model="edit_form" label-width="100px" label-position="right" :inline="true">
                <el-form-item label="类别ID: ">
                    <el-input v-model="edit_form.genre_id" style="width:220px" :disabled="true" prefix-icon="el-icon-s-ticket"></el-input>
                </el-form-item>
                <br />
                <el-form-item label="类别名称: ">
                    <el-input v-model="edit_form.name" style="width:220px" prefix-icon="el-icon-collection-tag" clearable></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="edit_form_visible = false">取 消</el-button>
                <el-button type="primary" @click="uploadEditForm">提 交</el-button>
            </span>
        </el-dialog>

        <el-dialog title="新增类别信息" :visible.sync="create_form_visible">
            <el-form :model="create_form" :ref="create_form" label-width="100px" label-position="right" :inline="true">
                <el-form-item label="类别名称: ">
                    <el-input v-model="create_form.title" style="width:220px" prefix-icon="el-icon-video-camera" clearable></el-input>
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
            genres: [
                {
                    genre_id: 0,
                    name: 'test1',
                },
                {
                    genre_id: 1,
                    name: 'test1',
                }
            ],
            edit_form: {
                genre_id: '',
                name: '',
            },
            create_form: {
                genre_id: '',
                name: ''
            },
            create_form_visible: false,
            edit_form_visible: false,
            delete_dialog_visible: false,
            select_list: []
        };
    },
    methods: {
        showEditForm: function(genre) {
            this.edit_form_visible = true;
            this.edit_form = genre;
        },
        uploadEditForm: function() {
            console.log(this.edit_form);
            this.$confirm('确定要更新选中类别的信息吗？', '提示', {
                type: 'warning'
            }).then();
            // todo： 向后端提交更新类别的请求
        },
        uploadCreateForm: function() {
            console.log(this.create_form);
            this.$confirm('确定要新建类别信息吗？', '提示', {
                type: 'warning'
            }).then();
        },
        handleSelectionChange: function(row_num_list) {
            console.log(row_num_list);
            this.select_list = row_num_list;
        },
        handleSingleDelete: function(genre_id) {
            console.log(genre_id);
            this.$confirm('确定要删除选中类别吗？', '提示', {
                type: 'warning'
            }).then();
            // todo: 向后端提交删除单个类别的请求
        },
        handleBatchDelete: function() {
            let delete_list = [];
            for (var i = 0; i < this.select_list.length; i++) {
                delete_list.push(this.select_list[i].genre_id);
            }
            console.log(delete_list);
            this.$confirm('确定要删除选中类别吗？', '提示', {
                type: 'warning'
            }).then();
            // todo: 向后端提交批量删除类别的请求
        }
    }
};
</script>

<style scoped>
.red {
    color: #ff0000;
}
</style>

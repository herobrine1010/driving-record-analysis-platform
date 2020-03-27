<template>
    <div>
        <el-card shadow="hover">
            <div slot="header">
                <span>系列信息管理</span>
            </div>
            <el-form :inline="true">
                <el-form-item>
                    <el-button type="primary" @click="create_form_visible = true">新建系列</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="danger" @click="handleBatchDelete">批量删除</el-button>
                </el-form-item>
            </el-form>
            <el-table :data="collections" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="collection_id" label="系列ID" align="center"> </el-table-column>
                <el-table-column prop="name" label="系列名称" align="center" > </el-table-column>
                <el-table-column label="操作" align="center" fixed="right">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="showEditForm(scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" primary @click="handleSingleDelete(scope.row.collection_id)"
                            >删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
        <br />
        <el-dialog title="编辑系列信息" :visible.sync="edit_form_visible" width="30%">
            <el-form :model="edit_form" label-width="100px" label-position="right" :inline="true">
                <el-form-item label="系列ID: ">
                    <el-input v-model="edit_form.collection_id" style="width:220px" :disabled="true" prefix-icon="el-icon-s-ticket"></el-input>
                </el-form-item>
                <br />
                <el-form-item label="系列名称: ">
                    <el-input v-model="edit_form.name" style="width:220px" prefix-icon="el-icon-collection-tag" clearable></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="edit_form_visible = false">取 消</el-button>
                <el-button type="primary" @click="uploadEditForm">提 交</el-button>
            </span>
        </el-dialog>

        <el-dialog title="新增系列信息" :visible.sync="create_form_visible">
            <el-form :model="create_form" :ref="create_form" label-width="100px" label-position="right" :inline="true">
                <el-form-item label="系列名称: ">
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
            collections: [
                {
                    collection_id: 0,
                    name: 'test1',
                },
                {
                    collection_id: 1,
                    name: 'test1',
                }
            ],
            edit_form: {
                collection_id: '',
                name: '',
            },
            create_form: {
                collection_id: '',
                name: ''
            },
            create_form_visible: false,
            edit_form_visible: false,
            delete_dialog_visible: false,
            select_list: []
        };
    },
    methods: {
        showEditForm: function(collection) {
            this.edit_form_visible = true;
            this.edit_form = collection;
        },
        uploadEditForm: function() {
            console.log(this.edit_form);
            this.$confirm('确定要更新选中系列的信息吗？', '提示', {
                type: 'warning'
            }).then();
            // todo： 向后端提交更新系列的请求
        },
        uploadCreateForm: function() {
            console.log(this.create_form);
            this.$confirm('确定要新建系列信息吗？', '提示', {
                type: 'warning'
            }).then();
        },
        handleSelectionChange: function(row_num_list) {
            console.log(row_num_list);
            this.select_list = row_num_list;
        },
        handleSingleDelete: function(collection_id) {
            console.log(collection_id);
            this.$confirm('确定要删除选中系列吗？', '提示', {
                type: 'warning'
            }).then();
            // todo: 向后端提交删除单个系列的请求
        },
        handleBatchDelete: function() {
            let delete_list = [];
            for (var i = 0; i < this.select_list.length; i++) {
                delete_list.push(this.select_list[i].collection_id);
            }
            console.log(delete_list);
            this.$confirm('确定要删除选中系列吗？', '提示', {
                type: 'warning'
            }).then();
            // todo: 向后端提交批量删除系列的请求
        }
    }
};
</script>

<style scoped>
.red {
    color: #ff0000;
}
</style>

<template>
    <div>
        <el-card shadow="hover">
            <div slot="header">
                <span>电影信息管理</span>
            </div>
            <el-form :inline="true">
                <el-form-item>
                    <el-button type="primary" @click="create_form_visible = true">新建电影</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="danger" @click="handleBatchDelete">批量删除</el-button>
                </el-form-item>
            </el-form>
            <el-table :data="movies" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="title" label="标题" align="center" show-overflow-tooltip min-width="80"> </el-table-column>
                <el-table-column prop="overview" label="概述" align="center" show-overflow-tooltip> </el-table-column>
                <el-table-column prop="popularity" label="热度" align="center" min-width="55"> </el-table-column>
                <el-table-column prop="runtime" label="时长" align="center" min-width="55"> </el-table-column>
                <el-table-column prop="release_date" label="上映日期" align="center"  min-width="55"> </el-table-column>
                <el-table-column prop="vote_average" label="平均评分" align="center" min-width="175">
                    <template slot-scope="scope">
                        <el-rate v-model="scope.row.vote_average" disabled show-score text-color="#ff9900" style="width:10"> </el-rate>
                    </template>
                </el-table-column>
                <el-table-column prop="country" label="出品国家" align="center" min-width="55"> </el-table-column>
                <el-table-column label="操作" align="center" fixed="right" min-width="150">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="showEditForm(scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" primary @click="handleSingleDelete(scope.row.movie_id)"
                            >删除</el-button
                        >
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
        <br />
        <el-dialog title="编辑电影信息" :visible.sync="edit_form_visible">
            <el-form :model="edit_form" label-width="100px" label-position="right" :inline="true">
                <el-form-item label="电影ID: ">
                    <el-input v-model="edit_form.movie_id" style="width:220px" :disabled="true" prefix-icon="el-icon-s-ticket"></el-input>
                </el-form-item>
                <br />
                <el-form-item label="电影标题: ">
                    <el-input v-model="edit_form.title" style="width:220px" prefix-icon="el-icon-video-camera" clearable></el-input>
                </el-form-item>
                <el-form-item label="电影时长: ">
                    <el-input v-model.number="edit_form.runtime" style="width:220px" prefix-icon="el-icon-time" clearable></el-input>
                </el-form-item>
                <el-form-item label="电影热度: ">
                    <el-input v-model.number="edit_form.popularity" style="width:220px" prefix-icon="el-icon-s-data" clearable></el-input>
                </el-form-item>
                <el-form-item label="平均评分: ">
                    <el-input
                        v-model.number="edit_form.vote_average"
                        style="width:220px"
                        prefix-icon="el-icon-star-off"
                        clearable
                    ></el-input>
                </el-form-item>
                <el-form-item label="上映日期: ">
                    <el-date-picker v-model="edit_form.release_date" type="date" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="出品国家: ">
                    <el-input v-model="edit_form.country" style="width:220px" prefix-icon="el-icon-map-location" clearable></el-input>
                </el-form-item>
                <el-form-item label="电影概述: ">
                    <el-input v-model="edit_form.overview" type="textarea" :autosize="true" style="width:550px"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="edit_form_visible = false">取 消</el-button>
                <el-button type="primary" @click="uploadEditForm">提 交</el-button>
            </span>
        </el-dialog>

        <el-dialog title="新增电影信息" :visible.sync="create_form_visible">
            <el-form :model="create_form" :ref="create_form" label-width="100px" label-position="right" :inline="true">
                <el-form-item label="电影标题: ">
                    <el-input v-model="create_form.title" style="width:220px" prefix-icon="el-icon-video-camera" clearable></el-input>
                </el-form-item>
                <el-form-item label="电影时长: ">
                    <el-input v-model.number="create_form.runtime" style="width:220px" prefix-icon="el-icon-time" clearable></el-input>
                </el-form-item>
                <el-form-item label="电影热度: ">
                    <el-input v-model.number="create_form.popularity" style="width:220px" prefix-icon="el-icon-s-data" clearable></el-input>
                </el-form-item>
                <el-form-item label="平均评分: ">
                    <el-input
                        v-model.number="create_form.vote_average"
                        style="width:220px"
                        prefix-icon="el-icon-star-off"
                        clearable
                    ></el-input>
                </el-form-item>
                <el-form-item label="上映日期: ">
                    <el-date-picker v-model="create_form.release_date" type="date" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="出品国家: ">
                    <el-input v-model="create_form.country" style="width:220px" prefix-icon="el-icon-map-location" clearable></el-input>
                </el-form-item>
                <el-form-item label="电影概述: ">
                    <el-input v-model="create_form.overview" type="textarea" :autosize="true" style="width:550px"></el-input>
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
            movies: [
                {
                    movie_id: 0,
                    title: 'test',
                    overview: 'This is the overview of this movie',
                    popularity: 10,
                    runtime: 90,
                    release_date: '2019-1-1',
                    vote_average: 4.5,
                    country: 'China'
                },
                {
                    movie_id: 0,
                    title: 'test',
                    overview: 'This is the overview of this movie',
                    popularity: 10,
                    runtime: 90,
                    release_date: '2019-1-1',
                    vote_average: 4.5,
                    country: 'China'
                }
            ],
            edit_form: {
                movie_id: '',
                title: '',
                overview: '',
                popularity: 0,
                release_date: '',
                runtime: 0,
                vote_average: 0,
                vote_count: 0,
                budget: 0,
                revenue: 0,
                country: ''
            },
            create_form: {
                title: '',
                overview: '',
                popularity: 0,
                release_date: '',
                runtime: 0,
                vote_average: 0,
                vote_count: 0,
                budget: 0,
                revenue: 0,
                country: ''
            },
            create_form_visible: false,
            edit_form_visible: false,
            delete_dialog_visible: false,
            select_list: []
        };
    },
    methods: {
        showEditForm: function(movie) {
            this.edit_form_visible = true;
            this.edit_form = movie;
        },
        uploadEditForm: function() {
            console.log(this.edit_form);
            this.$confirm('确定要更新选中电影的信息吗？', '提示', {
                type: 'warning'
            }).then();
            // todo： 向后端提交更新电影的请求
        },
        uploadCreateForm: function() {
            console.log(this.create_form);
            this.$confirm('确定要新建电影信息吗？', '提示', {
                type: 'warning'
            }).then();
        },
        handleSelectionChange: function(row_num_list) {
            console.log(row_num_list);
            this.select_list = row_num_list;
        },
        handleSingleDelete: function(movie_id) {
            console.log(movie_id);
            this.$confirm('确定要删除选中电影吗？', '提示', {
                type: 'warning'
            }).then();
            // todo: 向后端提交删除单个电影的请求
        },
        handleBatchDelete: function() {
            let delete_list = [];
            for (var i = 0; i < this.select_list.length; i++) {
                delete_list.push(this.select_list[i].movie_id);
            }
            console.log(delete_list);
            this.$confirm('确定要删除选中电影吗？', '提示', {
                type: 'warning'
            }).then();
            // todo: 向后端提交批量删除电影的请求
        }
    }
};
</script>

<style scoped>
.red {
    color: #ff0000;
}
</style>

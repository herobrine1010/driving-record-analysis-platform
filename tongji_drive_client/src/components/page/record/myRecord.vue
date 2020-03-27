<template>
    <div>
        <el-card shadow="hover">
            <div slot="header">
                <span>已上传记录</span>
            </div>
            <el-form :inline="true">
                <el-form-item>
                    <el-button type="primary" @click="goToUpload()">新建记录</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="danger" @click="handleBatchDelete">批量删除</el-button>
                </el-form-item>
            </el-form>
            <el-table :data="record" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="id" label="ID" align="center"> </el-table-column>
                <el-table-column prop="alias" label="记录名称" align="center"> </el-table-column>
                <el-table-column prop="driver" label="司机姓名" align="center"> </el-table-column>
                <el-table-column prop="rating" label="评分" align="center" min-width="175">
                    <template slot-scope="scope">
                        <el-rate v-model="scope.row.rating" disabled show-score text-color="#ff9900" style="width:10;"> </el-rate>
                    </template>
                </el-table-column>
                <el-table-column prop="status" label="状态" align="center">
                    <template slot-scope="scope">
                        <div v-if="scope.row.status === 'generated'">
                            <span style="color: darkred">待上传数据</span>
                        </div>
                        <div v-else-if="scope.row.status === 'uploaded'">
                            <span>处理中</span>
                        </div>
                        <div v-else>
                            <span style="color: #00a854" @click="goToEvent(scope.row.id,scope.row.alias,scope.row.driver)">已完成</span>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="comment" label="备注" align="center"> </el-table-column>
                <el-table-column label="操作" align="center" fixed="right">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="showEditForm(scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" primary @click="handleSingleDelete(scope.row.id)"
                        >删除</el-button
                        >
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
        <br />
        <el-dialog title="编辑记录" :visible.sync="edit_form_visible" width="30%">
            <el-form :model="edit_form" label-width="100px" label-position="right" :inline="true">
                <el-form-item label="ID: ">
                    <el-input v-model="edit_form.id" style="width:220px" :disabled="true" prefix-icon="el-icon-discount"></el-input>
                </el-form-item>
                <br />
                <el-form-item label="记录名称: ">
                    <el-input v-model="edit_form.alias" style="width:220px" prefix-icon="el-icon-note" clearable></el-input>
                </el-form-item>
                <el-form-item label="司机姓名: ">
                    <el-input v-model="edit_form.driver" style="width:220px" prefix-icon="el-icon-user" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="评分: ">
                    <el-input
                            v-model.number="edit_form.rating"
                            style="width:220px"
                            prefix-icon="el-icon-star-off"
                            :disabled="true"
                            clearable
                    ></el-input>
                </el-form-item>
                <br/>
                <el-form-item label="备注: ">
                    <el-input v-model="edit_form.comment" style="height:100px" :disabled="true" prefix-icon="el-icon-discount"></el-input>
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
    import Axios from 'axios';

    export default {
        //inject:['reload'],
        data() {
            return {
                record: [
                    {
                        id: -1,
                        alias:'default record',
                        driver:'default driver',
                        rating:5.0,
                        status:'finished',
                        comment:'default comment'
                    }
                ],
                edit_form: {
                    id: 0,
                    alias:'记录1',
                    driver:'testDriver1',
                    rating:5.0,
                    status:'finished',
                    comment:'goooooooooooood'
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

            goToUpload:function(){
                this.$router.push('/upload');
            },
            goToEvent:function(rid,alias,driver){
                this.$router.push({name:'event', params:{rid: rid,alias:alias,driver:driver}});
            },

            showEditForm: function(record) {
                console.log(record)
                this.edit_form_visible = true;
                this.edit_form.id=record.id;
                this.edit_form.alias=record.alias;
                this.edit_form.driver=record.driver;
                this.edit_form.rating=record.rating;
                this.edit_form.status=record.status;
                this.edit_form.comment=record.comment;

            },
            uploadEditForm: function() {
                console.log(this.edit_form);
                this.$confirm('确定要更新记录吗？', '提示', {
                    type: 'warning'
                }).then(()=>{
                    Axios.put("api/record",this.edit_form).then((res)=>{
                            if(res.data.status==="200") {
                                this.$message.success("更新成功！");
                                Axios.get("api/myRecord").then((res)=>{
                                    if(res.data.status==="200"){
                                        console.log("ok");
                                        this.record=res.data.result;}

                                    });
                                this.edit_form_visible= false;
                            }
                            else this.$message.error("更新失败！");
                        }
                    );
                }).catch(()=>{
                    console.log("已取消");
                });


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
                this.$confirm('确定要删除选中记录吗？', '提示', {
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
        },
        created:function(){
        Axios.get("api/myRecord").then((res)=>{
        if(res.data.status==="200"){
            console.log("ok");
            this.record=res.data.result;
            //this.reload();

        }
        else{
            console.log(res.data.error);
        }
    }).catch((err)=>{console.log(err);})
        }
    };
</script>

<style scoped>
    .red {
        color: #ff0000;
    }
</style>

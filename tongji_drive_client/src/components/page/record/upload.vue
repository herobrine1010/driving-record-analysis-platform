<template>
    <div>

        <el-form :model="recordForm" label-width="100px" label-position="right" :inline="true">
            <el-form-item label="ID: ">
                <el-input v-model="recordForm.id" style="width:220px" :disabled="true" prefix-icon="el-icon-discount"></el-input>
            </el-form-item>
            <br />
            <el-form-item label="记录名称: ">
                <el-input v-model="recordForm.alias" style="width:220px" prefix-icon="el-icon-note" clearable></el-input>
            </el-form-item>
            <el-form-item label="司机姓名: ">
                <el-input v-model="recordForm.driver" style="width:220px" prefix-icon="el-icon-user" ></el-input>
            </el-form-item>
            <el-form-item label="评分: ">
                <el-input v-model="recordForm.rating" style="width:200px" prefix-icon="el-icon-user" ></el-input>
            </el-form-item>
            <br/>
            <el-form-item label="备注: ">
                <el-input v-model="recordForm.comment" style="width:860px"  ></el-input>
            </el-form-item>

        </el-form>


        <div style="float:left">
        <el-upload

                class="upload-demo"
                ref="uploadCsv"
                action="api/uploadCsv"
                :limit="1"
                :auto-upload="false"

        >
            <br/><br/><br/><br/><br/>
            <p>上传运动学数据(*.csv)</p>
        </el-upload>
        </div>
<!--        <el-button size="small" type="primary">选择文件</el-button>-->
<!--        <el-button @click="submitCsv()">上传运动学数据(*.csv)</el-button>-->

        <div style="float:left">
        <el-upload
                class="upload-demo"
                ref="uploadVideo1"
                action="api/uploadVideo1"
                :limit="1"
                :auto-upload="false"
                :multiple="false"
        >
            <br/><br/><br/><br/><br/>
            <p>上传前向视频(*.mp4)</p>
        </el-upload>
<!--        <el-button size="small" type="primary">选择文件</el-button>-->
<!--        <el-button @click="submitVideo1()">上传前向视频(*.mp4)</el-button>-->
        </div>
        <div style="float:left;border:10px">
        <el-upload
                class="upload-demo"
                ref="uploadVideo2"
                action="api/uploadVideo2"
                :limit="1"
                :auto-upload="false"
                :multiple="false"
        >
            <br/><br/><br/><br/><br/>
            <p>上传司机向视频(*.mp4)</p>
        </el-upload>
<!--        <el-button size="small" type="primary">选择文件</el-button>-->
<!--        <el-button @click="submitVideo2">上传司机向视频(*.mp4)</el-button>-->
        </div>

        <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
        <span slot="footer" class="dialog-footer" style="align-items: center">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                <el-button type="primary" @click="uploadForm(); submitCsv();submitVideo1();submitVideo2();" style="align-items:center">
                    提 交</el-button>
            </span>

    </div>
</template>

<script>
    import Axios from 'axios'
    export default {
        name: 'upload',
        api:'http://localhost:8028',
        data(){
            return{
                url:"http://localhost:8028/uploadCsv",
                //fileList:[{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],
                recordForm:{
                    id:0,
                    alias:'',
                    driver:-1,
                    rating:0,
                   status:'generated',
                    comment:'',
                    video1_path:'',
                    video2_path:'',
                    csv_path:''
                },
                uploadF:{
                    id:0,
                    alias:'',
                    driver:-1,
                    rating:0,
                    comment:''
                }
            }
        },

        created(){
            Axios.post('api/currentRecord').then((res)=>{
                let f=res.data.result[0];
                this.recordForm.alias=f.alias;
                this.recordForm.id=f.id;
                this.recordForm.comment=f.comment;
                this.recordForm.rating=f.rating;
                this.recordForm.driver=f.driver;

            })
        },

        methods:{
            submitCsv(){
                console.log("!!!!");
                this.$refs.uploadCsv.submit();
            },
            submitVideo1(){
                console.log("2!!!!");
                this.$refs.uploadVideo1.submit();
            },
            submitVideo2(){
                console.log("3!!!!");
                this.$refs.uploadVideo2.submit();
            },
            uploadForm(){
                this.$confirm('确定要提交吗？', '提示', {
                    type: 'warning'
                }).then(()=>{
                    this.uploadF.alias=this.recordForm.alias;
                    this.uploadF.comment=this.recordForm.comment;
                    this.uploadF.driver=this.recordForm.driver;
                    this.uploadF.id=this.recordForm.id;
                    this.uploadF.rating=this.recordForm.rating;

                    Axios.put("api/record",this.uploadF).then((res)=>{
                            if(res.data.status==="200") {

                                Axios.post("api/checkFileUploaded").then((res2)=>{
                                    console.log(res2.data);
                                    if(res2.data.status==="404"){
                                        this.$message.warning("基本数据上传成功，可继续上传文件！");

                                    }
                                    else{
                                        this.$message.success("上传成功！");
                                        this.$router.push('/myRecord');
                                    }
                                });

                            }
                            else this.$message.error("更新失败！");
                        }
                    );
                }).catch(()=>{
                    console.log("已取消");
                });

            }
        }
    };
</script>

<style scoped>




</style>

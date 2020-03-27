<template>
    <div>

        <baidu-map class="map" :center="{lng: this.initLongitude, lat:this.initLatitude}" :zoom="15">
            <bm-scale anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-scale>
            <bm-navigation anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-navigation>
            <bm-copyright
                    anchor="BMAP_ANCHOR_TOP_RIGHT"
                    :copyright="[{id: 1, content: '同济大学软件学院', bounds: {ne: {lng: 110, lat: 40}, sw:{lng: 0, lat: 0}}}]">
            </bm-copyright>
                <bm-point-collection :points="points" shape="BMAP_POINT_SHAPE_RHOMBUS" color="red" size="BMAP_POINT_SIZE_BIGGER" @click="clickHandler"></bm-point-collection>
        </baidu-map>


        <el-card shadow="hover">
            <div slot="header">
                <span>危险场景还原(
                记录编号:{{this.recordId}} | 记录名称:{{this.alias}} | 驾驶员:{{this.driver}})

                    </span>
            </div>
            <el-form :inline="true">
                <el-form-item>
                    <el-button type="primary" @click="create_form_visible = true">新建记录</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="danger" @click="handleBatchDelete">批量删除</el-button>
                </el-form-item>
            </el-form>
            <div style="float:left">
            <template >
                <el-radio v-model="radio" label="1">  全部</el-radio>
                <el-radio v-model="radio" label="2">危险场景</el-radio>
                <el-radio v-model="radio" label="3">非危险场景</el-radio>
            </template></div>
            <br/>
            <el-table :data="event" @selection-change="handleSelectionChange" :key="this.formKey">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="id" label="ID" align="center" @click="showFormByRow()"> </el-table-column>
                <el-table-column prop="timestamp" label="时间戳" align="center"> </el-table-column>
                <el-table-column prop="longitude" label="经度" align="center"> </el-table-column>
                <el-table-column prop="latitude" label="纬度" align="center"> </el-table-column>
                <el-table-column prop="pred" label="是否认为危险场景" align="center">
                    <template slot-scope="scope">
                        <div v-if="scope.row.pred === 1">
                            <span>是</span>
                        </div>
                        <div v-else>
                            <span>否</span>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="accel" label="加速度" align="center"> </el-table-column>
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


        <el-dialog title="危险场景信息" :visible.sync="info_form_visible">
            <el-form :model="temp_event" :ref="temp_event"   :inline="true">
                <el-form-item label="场景ID: " >
                    <div>{{temp_event.id}}</div>
                </el-form-item>
                <br/>
                <el-form-item label="时间戳: "align="center">
                    <div>{{temp_event.timestamp}}</div>
                </el-form-item>
                <br/>
                <el-form-item label="是否被判定为危险场景: "align="center">
                    <div>{{temp_event.pred}}&emsp;</div>
                </el-form-item>
                <el-button @click="changePred()">修正预测值</el-button>
                <br/>
                <el-form-item label="经度: "align="center">
                    <div>{{temp_event.longitude}}</div>
                </el-form-item>
                <br/>
                <el-form-item label="纬度: "align="center">
                    <div>{{temp_event.latitude}}</div>
                </el-form-item>
                <br/>
                <el-form-item label="加速度: "align="center">
                    <div>{{temp_event.accel}}</div>
                </el-form-item>
                <br/>
                <el-form-item label="备注: "align="center">
                    <div>{{temp_event.comment}}</div>
                </el-form-item>
                <br/>


            </el-form>
            <span slot="footer" class="dialog-footer">
                 <el-button @click="goToFrontVideo();info_form_visible = false">查看前向视频</el-button>
                <el-button @click="goToFaceVideo();info_form_visible = false">查看司机向视频</el-button>
                <el-button @click="info_form_visible = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>

</template>

<script>
    import Axios from 'axios'
    export default {

        name: 'event',
        data(){
            return{
                radio:'1',

                formKey:1,
                frontUrl:'',
                faceUrl:'',
                api:'http://localhost:8028/',
                initLongitude:0,//-78.646587
                initLatitude:0,//35.779813
                recordId:0,
                alias:'',
                driver:'',
                event:[{
                    id:-1,
                    timestamp:0,
                    longitude:0.0,
                    latitude:0.0,
                    comment:'',
                    pred:0,
                    accel:0
                    }
                ],
                temp_event:{
                    id:-1,
                    timestamp:0,
                    longitude:0.0,
                    latitude:0.0,
                    comment:'',
                    pred:0,
                    accel:0,
                    faceUrl:'',
                    frontUrl:''
                },
                points:[],
                info_form_visible:false,
            }
        },
        methods: {
            changePred(){
                this.$confirm('确定要修正预测事件吗？', '提示', {
                    type: 'warning'
                }).then(()=>{
                    this.temp_event.pred=this.temp_event.pred==='是'?'否':'是';
                    let updateEvent={
                        id:this.temp_event.id,
                        pred:this.temp_event.pred==='是'?1:0,
                    };
                    Axios.put('api/event',updateEvent).then((res)=>{
                        if(res.data.status==="200"){
                            this.$message.success("更新成功！");
                            this.event[this.temp_event.id].pred=this.temp_event.pred;


                            Axios.post('api/getEvent?rid='+this.recordId.toString()).then((res)=>{
                                if(res.data.status==="200"){
                                    console.log("ok");
                                    this.event=res.data.result;
                                    //this.reload();

                                }
                                else{
                                    console.log(res.data.error);
                                }
                            }).catch((err)=>{console.log(err);})
                            console.log(this.event.length);

                            this.formKey++;
                            console.log(this.formKey);
                        }
                        else this.$message.success("更新失败！");
                    })
                }).catch(()=>{
                    console.log("已取消");
                });

            },
            clickHandler (e) {
                console.log(`单击点的坐标为：${e.point.lng}, ${e.point.lat}`);
                //切换地图中心视角
                this.initLongitude=e.point.lng;
                this.initLatitude=e.point.lat;
                //获取对应event
               this.temp_event.longitude=e.point.lng;
               this.temp_event.latitude=e.point.lat;
                for (let i=0;i<this.event.length;i++){
                    if(this.event[i].longitude===e.point.lng && this.event[i].latitude===e.point.lat){
                        this.temp_event.id=this.event[i].id;
                        this.temp_event.timestamp=this.event[i].timestamp;
                        this.temp_event.comment=this.event[i].comment;
                        this.temp_event.pred=this.event[i].pred===1?'是':'否';
                        this.temp_event.accel=this.event[i].accel;
                        this.frontUrl=this.api+'file/frontVideo?rid='+this.recordId+'&t='+this.temp_event.timestamp;
                        this.faceUrl=this.api+'file/faceVideo?rid='+this.recordId+'&t='+this.temp_event.timestamp;
                        console.log(this.frontUrl);
                        console.log(this.faceUrl);
                        break;
                    }
                }
                this.info_form_visible=true;
            },
            addPoints () {
                const points = [];
                for (var i = 0; i <this.event.length; i++) {
                    console.log(this.event[i].longitude,this.event[i].latitude);
                    const position = {lng:this.event[i].longitude, lat:this.event[i].latitude};
                    points.push(position)
                }
                this.points = points;
                console.log(this.points);
            },
            loadEvent(){
                Axios.post('api/getEvent?rid='+this.recordId.toString()).then(()=>{
                    if(res.data.status==="200"){
                        console.log("ok");
                        this.event=res.data.result;
                        //this.reload();
                    }
                    else{
                        console.log(res.data.error);
                    }
                }).catch((err)=>{console.log(err);})

            },

            goToFaceVideo:function(){
                console.log();
                this.$router.push({name:'video', params:{url:this.faceUrl}});
            },
            goToFrontVideo:function(){
                console.log();
                this.$router.push({name:'video', params:{url:this.frontUrl}});
            }
        },
        mounted: function () {//可操作dom元素
            this.$nextTick(function () {
                //获取传递的参数
                this.addPoints();
                this.initLongitude=this.points[0].lng;
                this.initLatitude=this.points[0].lat;
            })
        },
        created:function(){
            this.recordId=this.$route.params.rid;
            this.alias=this.$route.params.alias;
            this.driver=this.$route.params.driver;

        },
        activated:function(){

            console.log('api/getEvent?rid='+this.recordId.toString());
            Axios.post('api/getEvent?rid='+this.recordId.toString()).then((res)=>{
                if(res.data.status==="200"){
                    console.log("ok");
                    this.event=res.data.result;
                    //this.reload();

                }
                else{
                    console.log(res.data.error);
                }
            }).catch((err)=>{console.log(err);})
            console.log(this.event.length);
        },
        watch:{
            event(val,oldVal){
                this.addPoints();

            },
            points(val,oldVal){
                this.initLongitude=this.points[0].lng;
                this.initLatitude=this.points[0].lat;
            },
            radio(val){
                if(this.radio==='1'){
                    Axios.post('api/getEvent?rid='+this.recordId.toString()).then((res)=>{
                        if(res.data.status==="200"){
                            console.log("ok");
                            this.event=res.data.result;
                            //this.reload();

                        }
                        else{
                            console.log(res.data.error);
                        }
                    }).catch((err)=>{console.log(err);})
                    console.log(this.event.length);

                    this.formKey++;
                }
                else if(this.radio==='2'){
                    Axios.post('api/getDangerousEvent?rid='+this.recordId.toString()).then((res)=>{
                        if(res.data.status==="200"){
                            console.log("ok");
                            this.event=res.data.result;
                            //this.reload();

                        }
                        else{
                            console.log(res.data.error);
                        }
                    }).catch((err)=>{console.log(err);})
                    console.log(this.event.length);

                    this.formKey++;

                }else{
                    Axios.post('api/getNonDangerousEvent?rid='+this.recordId.toString()).then((res)=>{
                        if(res.data.status==="200"){
                            console.log("ok");
                            this.event=res.data.result;
                            //this.reload();

                        }
                        else{
                            console.log(res.data.error);
                        }
                    }).catch((err)=>{console.log(err);})
                    console.log(this.event.length);

                    this.formKey++;

                }
            }
        }

    };
</script>

<style scoped>
    .map {
        width: 100%;
        height: 400px;
    }

</style>

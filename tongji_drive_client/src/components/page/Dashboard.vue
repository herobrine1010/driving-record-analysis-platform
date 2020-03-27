<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="8">
                <el-card shadow="hover" class="mgb20" style="height:252px;">
                    <div class="user-info">
                        <img src="../../assets/img/img.jpg" class="user-avator" alt />
                        <div class="user-info-cont">
                            <div class="user-info-name">{{ name }}</div>
                            <div>{{ role }}</div>
                        </div>
                    </div>
                    <div class="user-info-list">
                        Hope for the best,
                    </div>
                    <div class="user-info-list">
                        prepare for the worst.
                    </div>
                </el-card>
                <el-card shadow="hover" style="height:350px;">
                    <div slot="header">
                        <span>项目成员</span>
                    </div>
                    <el-table :data="member" show-header>
                        <el-table-column prop="name" label="姓名" align="center" />
                        <el-table-column prop="school" label="学院" align="center" />
                    </el-table>
                </el-card>
            </el-col>
            <el-col :span="16">
                <el-row :gutter="20" class="mgb20">
                    <el-col :span="12">
                        <el-card shadow="hover" :body-style="{ padding: '0px' }">
                            <div class="grid-content grid-con-1">
                                <i class="el-icon-lx-people grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">3</div>
                                    <div>Group Member</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="12">
                        <el-card shadow="hover" :body-style="{ padding: '0px' }">
                            <div class="grid-content grid-con-2">
                                <i class="el-icon-lx-notice grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">Mar-18</div>
                                    <div>Deadline</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
                <el-card shadow="hover" style="height:501px;">
                    <div slot="header" class="clearfix">
                        <span>使用指南</span>
                    </div>
                    <div style="line-height: 30px">1. 每个用户新建记录并且没有提交所有文件时不可以新建另一条记录（“菜单”-“已上传记录”中会显示记录状态为“待上传数据”）。在此状态下，用户试图新建记录的操作会被重定向至“上传数据”并提示补齐文件。
                        <br/>
                        2. 用户可以提交多条记录的基础数据和它们的文件，即使一些记录的状态是“处理中”，这表示后台算法正在处理这些数据。
                        <br/>
                        3. 已处理完成的记录状态将显示为“已完成”，可以点击进入“危险事件还原”页面。
                        <br/>
                        4. 记录的分数是管理员用户可修改的、驾驶员用户只读的。记录初始化时，其默认值为0。
                    </div>
                </el-card>

            </el-col>
        </el-row>
    </div>
</template>

<script>
import Schart from 'vue-schart';
import bus from '../common/bus';
export default {
    name: 'dashboard',
    data() {
        return {
            name: localStorage.getItem('ms_username'),
            todoList: [
                { title: 'Oracle', status: false },
                {
                    title: 'Timesten',
                    status: false
                },
                {
                    title: 'Test',
                    status: false
                }
            ],
            member: [
                {

                    name: '许靖宁',
                    school: '软件学院'
                },
                {
                    name: '李心怡',
                    school:'土木学院'
                },
                {
                    school:'软件学院',
                    name: '李雨龙'
                },

            ]
        };
    },
    components: {
        Schart
    },
    computed: {
        role() {
            return ':)';
        }
    },
    methods: {
        changeDate() {
            const now = new Date().getTime();
            this.data.forEach((item, index) => {
                const date = new Date(now - (6 - index) * 86400000);
                item.name = `${date.getFullYear()}/${date.getMonth() + 1}/${date.getDate()}`;
            });
        }
    }
};
</script>

<style scoped>
.grid-content {
    display: flex;
    align-items: center;
    height: 100px;
}

.grid-cont-right {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: #999;
}

.grid-num {
    font-size: 30px;
    font-weight: bold;
}

.grid-con-icon {
    font-size: 50px;
    width: 100px;
    height: 100px;
    text-align: center;
    line-height: 100px;
    color: #fff;
}

.grid-con-1 .grid-con-icon {
    background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
    background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
    background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
    color: rgb(242, 94, 67);
}

.user-info {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 2px solid #ccc;
    margin-bottom: 20px;
}

.user-avator {
    width: 120px;
    height: 120px;
    border-radius: 50%;
}

.user-info-cont {
    padding-left: 50px;
    flex: 1;
    font-size: 14px;
    color: #999;
}

.user-info-cont div:first-child {
    font-size: 30px;
    color: #222;
}

.user-info-list {
    font-size: 14px;
    color: #999;
    line-height: 25px;
}

.user-info-list span {
    margin-left: 70px;
}

.mgb20 {
    margin-bottom: 20px;
}
</style>

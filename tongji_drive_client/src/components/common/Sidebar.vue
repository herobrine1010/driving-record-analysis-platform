<template>
    <div class="sidebar">
        <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="collapse"
            background-color="#324157"
            text-color="#bfcbd9"
            active-text-color="#20a0ff"
            unique-opened
            router
        >
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu v-if="subItem.subs" :index="subItem.index" :key="subItem.index">
                                <template slot="title">{{ subItem.title }}</template>
                                <el-menu-item v-for="(threeItem, i) in subItem.subs" :key="i" :index="threeItem.index">{{
                                    threeItem.title
                                }}</el-menu-item>
                            </el-submenu>
                            <el-menu-item v-else :index="subItem.index" :key="subItem.index">{{ subItem.title }}</el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
import bus from '../common/bus';
export default {
    data() {
        return {
            collapse: false,
            items: [
                {
                    icon: 'el-icon-lx-home',
                    index: 'dashboard',
                    title: '项目简介'
                },
                // {
                //     icon: 'el-icon-search',
                //     index: 'search',
                //     title: '检索模块'
                // },
                // {
                //     icon: 'el-icon-video-camera-solid',
                //     index: 'movies',
                //     title: '电影模块',
                //     subs: [
                //         {
                //             index: 'movie-details',
                //             title: '电影信息管理'
                //         },
                //         {
                //             index: 'movie-genres',
                //             title: '类别信息管理'
                //         },
                //         {
                //             index: 'movie-collections',
                //             title: '系列信息管理'
                //         }
                //     ]
                // },
                // {
                //     icon: 'el-icon-user',
                //     index: 'people',
                //     title: '人员模块',
                //     subs: [
                //         {
                //             index: 'people-directors',
                //             title: '导演信息管理'
                //         },
                //         {
                //             index: 'people-actors',
                //             title: '演员信息管理'
                //         }
                //     ]
                // },
                // {
                //     icon: 'el-icon-medal',
                //     index: 'rate',
                //     title: '评分模块'
                // },
                {
                    icon: 'el-icon-upload',
                    index: 'upload',
                    title: '上传数据'
                },
                {
                    icon: 'el-icon-document',
                    index: 'myRecord',
                    title: '已上传记录'
                },
                {
                    icon: 'el-icon-info',
                    index: 'myInfo',
                    title: '个人信息'
                },
                {
                    icon: 'el-icon-map',
                    index: 'video',
                    title: ''
                }
            ]
        };
    },
    computed: {
        onRoutes() {
            return this.$route.path.replace('/', '');
        }
    },
    created() {
        // 通过 Event Bus 进行组件间通信，来折叠侧边栏
        bus.$on('collapse', msg => {
            this.collapse = msg;
            bus.$emit('collapse-content', msg);
        });
    }
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
    width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}
.sidebar > ul {
    height: 100%;
}
</style>

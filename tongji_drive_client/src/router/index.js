import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/dashboard'
        },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
                {
                    path: '/dashboard',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/Dashboard.vue'),
                    meta: { title: '项目简介' }
                },
                {
                    path: '/movie-details',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/movie/details.vue'),
                    meta: { title: '电影信息管理' }
                },
                {
                    path: '/movie-genres',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/movie/genres.vue'),
                    meta: { title: '类别信息管理' }
                },
                {
                    path: '/movie-collections',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/movie/collections.vue'),
                    meta: { title: '系列信息管理' }
                },
                {
                    path: '/people-directors',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/people/directors.vue'),
                    meta: { title: '导演信息管理' }
                },
                {
                    path: '/people-actors',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/people/actors.vue'),
                    meta: { title: '演员信息管理' }
                },
                {
                    path: '/rate',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/rate/rate.vue'),
                    meta: { title: '评分管理' }
                },
                {
                    path: '/search',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/rate/rate.vue'),
                    meta: { title: '检索模块' }
                },
                {
                    path: '/myRecord',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/record/myRecord.vue'),
                    meta: { title: '已上传记录' }
                },
                {
                    path: '/upload',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/record/upload.vue'),
                    meta: { title: '上传数据' }
                },
                {
                    path: '/myInfo',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/myInfo.vue'),
                    meta: { title: '个人信息' }
                },
                {
                    path: '/event',name:'event',
                    component: () => import('../components/page/map/event.vue'),
                    meta: { title: '危险场景还原' }
                },
                {
                    path: '/video',name:'video',
                    component: () => import('../components/page/map/video.vue'),
                    meta: { title: '危险场景视频' }
                }
            ]
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/Login.vue'),
            meta: { title: '登录' }
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
});

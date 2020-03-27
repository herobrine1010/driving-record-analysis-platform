<template>
    <video-player  class="video-player vjs-custom-skin"
                   ref="videoPlayer"
                   :playsinline="true"
                   :options="playerOptions"
    ></video-player>
</template>
<script>
    import Axios from 'axios'
    export default {
        name: 'video',
        data () {
            return {
                //url:'',
                // 视频播放
                playerOptions: {
                    playbackRates: [0.7, 1.0, 1.5, 2.0], //播放速度
                    autoplay: false, //如果true,浏览器准备好时开始回放。
                    muted: false, // 默认情况下将会消除任何音频。
                    loop: false, // 导致视频一结束就重新开始。
                    preload: 'auto', // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
                    language: 'zh-CN',
                    aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
                    fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
                    sources: [{
                        type: "video/mp4",
                        // src:''
                        src: 'http://localhost:8028/file/faceVideo?rid=1236&t=218100'//url地址
                        //src: 'http://localhost:8028/file/1/1236/result/video/FILE_ID_1236_218100_Face.mp4'
                        // src: "" //url地址
                    }],
                    poster: "", //你的封面地址
                    // width: document.documentElement.clientWidth,
                    notSupportedMessage: '此视频暂无法播放，请稍后再试', //允许覆盖Video.js无法播放媒体源时显示的默认信息。
                    controlBar: {
                        timeDivider: true,
                        durationDisplay: true,
                        remainingTimeDisplay: false,
                        fullscreenToggle: true  //全屏按钮
                    }
                }
            }
        },
        created:function(){
            this.playerOptions.sources[0].src=this.$route.params.url;
            console.log(this.playerOptions.sources[0].src);
            console.log(this.$route.params.url);
        },
        mounted: function () {//可操作dom元素
            this.$nextTick(function () {
                this.playerOptions.sources[0].src=this.$route.params.url;
                console.log(this.playerOptions.sources[0].src);
                console.log(this.$route.params.url);
            })
        },
        watch:function(){


        },
        methods:{
            getVideo(){
                Axios.get("api/file/video?rid=1236&t=218101").then(
                    ()=>{
                        console.log(res);
                        this.playerOptions.sources[0].src=res.data.resultData
                    }
                )
            }

        }
    }
</script>

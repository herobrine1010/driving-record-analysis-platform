<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">营运驾驶危险事件智能评价系统</div>
            <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
                <el-form-item prop="username">
                    <el-input v-model="param.username" placeholder="username">
                        <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input
                            type="password"
                            placeholder="password"
                            v-model="param.pwd"
                            @keyup.enter.native="submitForm()"
                    >
                        <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm()">登录</el-button>

                    <el-button type="primary" @click="submitFormRegister()">注册</el-button>
                </div>


<!--                <p class="login-tips">Tips : 用户名和密码随便填。</p>-->
            </el-form>
        </div>
    </div>
</template>

<script>
    import global from '../common/Global.vue';
    import Axios from 'axios';
    export default {
        data: function() {
            return {

                param: {
                    username: 'manager1',
                    pwd: '',

                },
                rules: {
                    username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                    pwd: [{ required: true, message: '请输入密码', trigger: 'blur' }]
                }
            };
        },
        methods: {
            submitForm() {
                console.log("login");
                this.$refs.login.validate(valid => {
                    if (valid) {
                        console.log("valid");
                        let url="api/login";
                        //let data={"username":"manager1","pwd":"123456"};
                        console.log(this.param);
                        Axios.post(url,this.param).then((res)=>{
                            if(res.data.status==="200"){
                                console.log("ok");
                                this.$message.success('登录成功');
                                localStorage.setItem('ms_username', this.param.username);
                                this.$router.push('/');
                            }
                            else{
                                console.log(res.data.error);
                                this.$router.push('/login');
                                this.$message.warning("用户名或密码错误!");

                            }
                        }).catch((err)=>{console.log(err);})

                    } else {
                        this.$message.error('请输入账号和密码');
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            submitFormRegister(){
                console.log("register");
                this.$refs.login.validate(valid => {
                    if (valid) {
                        console.log("valid");
                        let url="api/register";
                        //let data={"username":"manager1","pwd":"123456"};
                        console.log(this.param);
                        Axios.post(url,this.param).then((res)=>{
                            if(res.data.status==="200"){
                                console.log("ok");
                                this.$message.success('注册成功！请重新输入密码以登录');
                                //localStorage.setItem('ms_username', this.param.username);
                                //this.$router.push('/');
                                this.param.pwd='';
                            }
                            else{
                                console.log(res.data.error);
                                this.$router.push('/login');
                                this.$message.warning("未知错误!");

                            }
                        }).catch((err)=>{console.log(err);})

                    } else {
                        this.$message.error('请输入账号和密码');
                        console.log('error submit!!');
                        return false;
                    }
                });
            }
        }
    };
</script>

<style scoped>
    .login-wrap {
        position: relative;
        width: 100%;
        height: 100%;
        background-image: url(../../assets/img/login-bg3.jpg);
        background-size: 100%;
    }

    .ms-title {
        width: 100%;
        line-height: 50px;
        text-align: center;
        font-size: 23px;
        color: #fff;
        border-bottom: 1px solid #ddd;
    }

    .ms-login {
        position: absolute;
        left: 50%;
        top: 50%;
        width: 350px;
        margin: -190px 0 0 -175px;
        border-radius: 5px;
        background: rgba(255, 255, 255, 0.3);
        overflow: hidden;
    }

    .ms-content {
        padding: 30px 30px;
    }

    .login-btn {
        text-align: center;
    }

    .login-btn button {
        width: 45%;
        height: 36px;
        margin-bottom: 10px;
    }

    .login-tips {
        font-size: 12px;
        line-height: 30px;
        color: #fff;
    }
</style>

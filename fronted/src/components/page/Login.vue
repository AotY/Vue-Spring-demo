<template>
  <div class="user-wrapper">
    <div class="user-con">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="80px" label-position="left"
               class="demo-ruleForm">

        <el-form-item label="用户名" prop="username">
          <el-input type="text" v-model="ruleForm.username" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="ruleForm.password" auto-complete="off"
                    @keyup.enter.native="submitForm('ruleForm')"></el-input>
        </el-form-item>

        <el-form-item>
          <el-row :gutter="0">
            <el-col :span="12">
              <el-button type="primary" size="large" @click="submitForm('ruleForm')">登录</el-button>
            </el-col>
            <el-col :span="12">
              <el-button size="large" @click="cancelLogin">取消</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <div class="link-item">
          <a class="link password-forget" href="#/password-reset" target="_blank">忘记密码</a>
          <a class="link register" href="#/register" target="_blank">免费注册</a>
        </div>
      </el-form>
    </div>
  </div>
</template>


<script>
  import userApi from './../../api/portal/userapi.js'
  import userSession from '../../util/usersession.js'
  // 请求服务器数据
  export default {
    data () {
      return {
        ruleForm: {
          username: '',
          password: ''
        },
        rules: {
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ],
          username: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
            {min: 3, max: 24, message: '长度在 3 到 24 个字符', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      submitForm (formName) {
        let self = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            // 提交数据
            userApi.login(self, self.ruleForm.username, self.ruleForm.password).then(function (reponse) {
              console.log(reponse)
              if (reponse.data.status === 1) {
                self.$message.error(reponse.data.msg)
              } else {
                // 记录用户登录信息
                userSession.saveSession(reponse.data.data)
                self.$message({
                  message: '登录成功',
                  type: 'success'
                })
//                self.$router.go(-1)
                self.$router.push({name: 'Recommend'})
//                window.history.back()
              }
            })
          } else {
            return false
          }
        })
      },
      cancelLogin () {
        // 返回
//        window.history.back()
        this.$router.push({name: 'Recommend'})
//        this.$router.go(-1, )
      }
    }
  }
</script>

<style scoped>

  .user-wrapper {
    width: 100%;
    padding: 40px 0;
    background: #ecf0f1;
  }

  .user-con {
    position: relative;
    margin: 0 auto;
    padding: 40px;
    width: 400px;
    background: #fff;
    text-align: left;
  }

  .user-con .link-item {
    text-align: left;
    line-height: 2;
    font-size: 12px;
    height: 30px;
    margin: 0px auto 0;
    text-align: right;
    color: #666;
  }

  .user-con .link {
    padding: 0 5px;
    color: #666;
    text-decoration: none;
  }

  .link {
    cursor: pointer;
    text-decoration: none;
  }

</style>

<template>
  <div class="user-wrapper">

    <div class="user-con">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="80px" label-position="right"
               class="demo-ruleForm">

        <el-form-item label="用户名" prop="username">
          <el-input v-model="ruleForm.username"></el-input>
        </el-form-item>

        <el-form-item prop="email" label="邮箱">
          <el-input v-model="ruleForm.email"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="ruleForm.password" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="确认密码" prop="checkPassword">
          <el-input type="password" v-model="ruleForm.checkPassword" @keyup.enter.native="submitForm('ruleForm')"
                    auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
          <el-button @click="cancel">取消</el-button>
        </el-form-item>

      </el-form>

    </div>
  </div>

</template>

<script>
  import userApi from './../../api/portal/userapi.js'

  export default {
    data () {
      var validatePassword = (rule, value, callback) => {
        if (this.ruleForm.checkPassword !== '') {
          this.$refs.ruleForm.validateField('checkPassword')
        }
        callback()
      }
      var validatePassword2 = (rule, value, callback) => {
        if (value !== this.ruleForm.password) {
          callback(new Error('两次输入密码不一致!'))
        } else {
          callback()
        }
      }
      var validateUsername = (rule, value, callback) => {
        // 向服务器验证用户名是否已经存在
        userApi.checkValid(this, value, 'username').then(function (response) {
          console.log('validateUsername: ', response.data)
          if (response.data.status !== 0) {
            callback(new Error(response.data.msg))
          } else {
            callback()
          }
        })
      }
      var validateEmail = (rule, value, callback) => {
        // 向服务器验证用户名是否已经存在
        userApi.checkValid(this, value, 'email').then(function (response) {
          console.log('validateEmail: ', response.data)
          if (response.data.status !== 0) {
            callback(new Error(response.data.msg))
          } else {
            callback()
          }
        })
      }
      return {
        ruleForm: {
          username: '',
          email: '',
          password: '',
          checkPassword: ''
        },
        rules: {
          username: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
            {min: 4, max: 20, message: '长度在 4 到 20 个字符', trigger: 'blur'},
            {validator: validateUsername, trigger: 'blur'}
          ],
          email: [
            {required: true, message: '请输入邮箱地址', trigger: 'blur'},
            {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change'},
            {validator: validateEmail, trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {validator: validatePassword, trigger: 'blur'}
          ],
          checkPassword: [
            {required: true, message: '请输入确认密码', trigger: 'blur'},
            {validator: validatePassword2, trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      submitForm (formName) {
        console.log('submitForm')
        let self = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            userApi.register(self, self.ruleForm.username, self.ruleForm.email, self.ruleForm.password).then(function (response) {
              if (response.data.status === 0) {
                self.$message({
                  message: '注册成功',
                  type: 'success'
                })
                self.$router.push({name: 'Login'})
              }
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      cancel () {
        window.history.back()
//        this.$router.go(-1)
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

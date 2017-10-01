import * as types from '../types.js'
import * as Const from './../../common/Const.js'
import cartApi from './../../api/portal/cartapi.js'
import userApi from './../../api/portal/userapi.js'
import userSession from '../../util/usersession.js'

const state = {
  username: '',
  count: 0,
  isLogin: false
}

// 可以进行异步操作
// ction 类似于 mutation，不同在于：
// Action 提交的是 mutation，而不是直接变更状态。
// Action 可以包含任意异步操作。
const actions = {
  updateCartCount ({commit}, context) {
    console.log('---------- updateCartCount ----------')
    // 请求网络
    var updateData = {}
    cartApi.getQuantitySum(context).then(function (response) {
      if (response.data.status === 0) {
        updateData.count = response.data.data
        updateData.isLogin = true
        updateData.username = localStorage.getItem(Const.USERNAME)
      }
      console.log('updateData: ', updateData)
      commit(types.UPDATE_CART_COUNT, updateData)
    })
  },
  logout ({commit}, context) {
    // 请求网络
    userApi.logout(context).then(function (response) {
      if (response.data.status === 0) {
        userSession.removeSession()
        context.$message({
          message: '注销成功',
          type: 'success'
        })
        commit(types.LOGOUT)
      }
    })
  }
}

// 这个应该没有关系吧？ getter or getters
const getters = {
  username: state => state.username,
  isLogin: state => state.isLogin,
  count: state => state.count
}

// 同步操作
const mutations = {
  [types.UPDATE_CART_COUNT] (state, updateData) {
    state.isLogin = updateData.isLogin
    state.username = updateData.username
    state.count = updateData.count
  },
  [types.LOGOUT] (state, status) {
    state.isLogin = false
    state.username = ''
    state.count = 0
  }
}

export default {
  state,
  actions,
  getters,
  mutations
}

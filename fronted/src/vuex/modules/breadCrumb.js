import * as types from '../types.js'
import * as Const from '../../common/Const.js'

const state = {
  breadcrumbs: []
}

// 可以进行异步操作
// ction 类似于 mutation，不同在于：
// Action 提交的是 mutation，而不是直接变更状态。
// Action 可以包含任意异步操作。
const actions = {
  updateBreadcrumbs ({commit}, context) {
    console.log('---------- updateBreadcrumbs--------- ')
    // 可以根据router.path计算得出吧？
    var paths = context.$route.path.split('/')

    var breadcrumbs = []
    var i
    var breadcrumb
    for (i in paths) {
      breadcrumb = Const.BREADCRUMB_MAP[paths[i]]
      console.log('BREADCRUMB_MAP: ', breadcrumb)
      if (breadcrumb !== undefined) {
        breadcrumbs[i] = breadcrumb
      }
    }
    console.log('breadcrumbs: ', breadcrumbs)
    commit(types.UPDATE_BREADCRUMBS, breadcrumbs)
  }
}

// 这个应该没有关系吧？ getter or getters 肯定有，作为一个属性，名字要对号入座
const getters = {
  breadcrumbs: state => state.breadcrumbs
}

// 同步操作
const mutations = {
  [types.UPDATE_BREADCRUMBS] (state, breadcrumbs) {
    state.breadcrumbs = breadcrumbs
  }
}

export default {
  state,
  actions,
  getters,
  mutations
}

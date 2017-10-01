// const API_URL = 'http://127.0.0.1:8082/product'
const API_URL = 'http://backend.vuespringdemo.com/product'

export default {
  getList (context, params) {
    console.log('----getList-----: ', params)
    // return context.$axios.get(API_URL + '/list.do', {categoryId: 100001, keyword: '手机'})
    return context.$axios.get(API_URL + '/list.do', {params})
  },
  getDetail (context, params) {
    return context.$axios.get(API_URL + '/detail.do', {params})
  }
}


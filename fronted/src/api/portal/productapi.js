const API_URL = 'http://backend.vuespringdemo.com/product'
// const API_URL = 'http://127.0.0.1:8082/product'

export default {
  getList (context, category, keyword) {
    return context.$axios.get(API_URL + '/list.do')
  },
  getDetail (context, productId) {
    return context.$axios.get(API_URL + '/detail.do', {productId: productId})
  }
}


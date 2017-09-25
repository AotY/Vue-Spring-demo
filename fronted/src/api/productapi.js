const API_URL = 'http://www.vuespringdemo.com/product'

export default {
  getList (context, category, keyword) {
    // let self = this
    return context.$axios.get(API_URL + '/list.do', { crossdomain: true })
  }
}

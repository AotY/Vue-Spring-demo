const API_URL = 'http://www.vuespringdemo.com/product'

export default {
  getList (context, pageNum, pageSize) {
    // let self = this
    return context.$axios.get(API_URL + '/list.do', { crossdomain: true })
  }
}

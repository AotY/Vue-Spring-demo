// 获取搜索建议
const API_URL = 'http://backend.vuespringdemo.com/recommend'

export default {
  getKeywords (context) {
    return context.$axios.get(API_URL + '/keyword.do')
  },
  getCarousels (context) {
    return context.$axios.get(API_URL + '/carousel.do')
  }
}


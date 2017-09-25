// const API_URL = 'http://backend.vuespringdemo.com/category'
const API_URL = 'http://backend.vuespringdemo.com/category'

export default {
  getRecommendCategory (context, categoryId) {
    return context.$axios.get(API_URL + '/recommend.do', { categoryId: categoryId })
  },
  getAllChildrenCategory (context, categoryId) {
    return context.$axios.get(API_URL + '/all.do', { categoryId: categoryId })
  }

}


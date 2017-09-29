const API_URL = 'http://backend.vuespringdemo.com/cart'

export default {
  getList (context) {
    // return context.$axios.get(API_URL + '/list.do', {categoryId: 100001, keyword: '手机'})
    return context.$axios.get(API_URL + '/list.do')
  },
  getQuantitySum (context) {
    return context.$axios.get(API_URL + '/count.do')
  },
  add2Cart (context, productId, quantity, checked) {
    var config = {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      params: {
        productId: productId,
        quantity: quantity,
        checked: checked
      }
    }
    return context.$axios.post(API_URL + '/add.do', {}, config)
  },
  reverseAllSelect (context, allChecked) {
    var config = {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      params: {
        checked: allChecked
      }
    }
    // return context.$axios.get(API_URL + '/reverse_select_all.do', {checked: allChecked})
    return context.$axios.post(API_URL + '/reverse_select_all.do', {}, config)
  },
  reverseSelect (context, productId, productChecked) {
    var config = {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      params: {
        productId: productId,
        checked: productChecked
      }
    }
    return context.$axios.post(API_URL + '/reverse_select.do', {}, config)
    // return context.$axios.get(API_URL + '/reverse_select.do', {productId: productId, checked: productChecked})
  },
  changeQuantity (context, productId, quantity) {
    var config = {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      params: {
        productId: productId,
        quantity: quantity
      }
    }
    return context.$axios.post(API_URL + '/change_quantity.do', {}, config)
  }

}


const API_URL = 'http://backend.vuespringdemo.com/order'

export default {
  getList (context) {
    return context.$axios.get(API_URL + '/list.do')
  },
  create (context, shippingId) {
    var config = {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      params: {
        shippingId: shippingId
      }
    }
    return context.$axios.post(API_URL + '/create.do', {}, config)
  },
  cancel (context, orderNo) {
    return context.$axios.get(API_URL + '/cancel.do?orderNo=' + orderNo)
  },
  pay (context, orderNo) {
    return context.$axios.get(API_URL + '/pay.do?orderNo=' + orderNo)
  },
  getDetail (context, orderNo) {
    return context.$axios.get(API_URL + '/detail.do?orderNo=' + orderNo)
  },
  deleteOrder (context, orderNo) {
    return context.$axios.delete(API_URL + '/delete.do?orderNo=' + orderNo)
  }
}


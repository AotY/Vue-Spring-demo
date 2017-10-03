const API_URL = 'http://backend.vuespringdemo.com/shipping'

export default {
  getList (context) {
    return context.$axios.get(API_URL + '/list.do')
  },
  add (context, formData) {
    var config = {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      params: {
        receiverName: formData.receiverName,
        receiverPhone: formData.receiverPhone,
        receiverProvince: formData.receiverProvince,
        receiverDistrict: formData.receiverDistrict,
        receiverAddress: formData.receiverAddress,
        receiverZip: formData.receiverZip
      }
    }
    return context.$axios.post(API_URL + '/add.do', {}, config)
  },
  edit (context, formData) {
    var config = {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      params: {
        receiverName: formData.receiverName,
        receiverPhone: formData.receiverPhone,
        receiverProvince: formData.receiverProvince,
        receiverDistrict: formData.receiverDistrict,
        receiverAddress: formData.receiverAddress,
        receiverZip: formData.receiverZip,
        id: formData.id
      }
    }
    return context.$axios.post(API_URL + '/edit.do', {}, config)
  },
  delete (context, productId) {
    return context.$axios.delete(API_URL + '/delete.do?id=' + productId)
  },
  setDefault (context, id) {
    var config = {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      params: {
        id: id
      }
    }
    return context.$axios.post(API_URL + '/set_default.do', {}, config)
  }
}


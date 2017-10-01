const API_URL = 'http://backend.vuespringdemo.com/user'

export default {
  login (context, username, password) {
    console.log(username, password)
    // return context.$axios.post(API_URL + '/login.do', {username: 'admin', password: '1234'})
    var config = {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      params: {
        username: username,
        password: password
      }
    }
    // var config = {
    //   headers: {
    //     'Content-Type': 'application/x-www-form-urlencoded'
    //   },
    //   withCredentials: true
    // }
    // return context.$axios.post(API_URL + '/login.do', {username: username, password: password}, config)
    return context.$axios.post(API_URL + '/login.do', {}, config)
  },
  logout (context) {
    var config = {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    }
    return context.$axios.post(API_URL + '/logout.do', {}, config)
  },
  register (context, username, email, password) {
    var config = {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      params: {
        username: username,
        email: email,
        password: password
      }
    }
    return context.$axios.post(API_URL + '/register.do', {}, config)
  },
  getUserInfo (context) {
    return context.$axios.get(API_URL + '/get_user_info.do')
  },
  checkValid (context, value, field) {
    var config = {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      params: {
        value: value,
        field: field
      }
    }
    return context.$axios.post(API_URL + '/check_valid.do', {}, config)
  }

}


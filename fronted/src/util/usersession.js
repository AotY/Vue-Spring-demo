export default {
  saveSession (user) {
    // 记录用户登录信息
    localStorage.setItem('username', user.username)
    localStorage.setItem('email', user.email)
    localStorage.setItem('phone', user.phone)
    localStorage.setItem('id', user.id)
  },
  removeSession () {
    localStorage.removeItem('username')
    localStorage.removeItem('email')
    localStorage.removeItem('phone')
    localStorage.removeItem('id')
  }
}


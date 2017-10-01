<template>

  <div class="input-wrapper">
    <el-autocomplete
      class="inline-input"
      placeholder="商品搜索，共 4324 款好货"
      icon="search"
      v-model="keyword"
      size="large"
      :fetch-suggestions="querySearch"
      :on-icon-click="handleIconClick"
      @keyup.enter.native="handleIconClick"
      @select="handleSelect">
    </el-autocomplete>
  </div>

</template>

<script>
  import recommendApi from './../../api/portal/recommendapi.js'

  export default {
    data () {
      return {
        keyword: '',
        // [{value: '锤子', link: '#/list/keyword/'}]
        suggestionKeywords: []
      }
    },
    created () {
      // 获取搜索建议列表
      this.getSuggestionKeywords()
      console.log('search: ', this.$route.params)
      this.keyword = this.$route.params['keyword']
    },
    mounted () {
    },
    methods: {
      handleIconClick (ev) {
        console.log('handleIconClick')
        this.$router.push({name: 'ListKeyword', params: {keyword: this.keyword}})
      },
      querySearch (queryString, cb) {
        console.log('---------querySearch----------')
        var suggestionKeywords = this.suggestionKeywords
        var results = queryString ? suggestionKeywords.filter(this.createFilter(queryString)) : suggestionKeywords
        // 调用 callback 返回建议列表的数据
        cb(results)
      },
      createFilter (queryString) {
        console.log('---------createFilter----------')
        return (suggestionKeyword) => {
          return (suggestionKeyword.value.indexOf(queryString.toLowerCase()) === 0)
        }
      },
      getSuggestionKeywords () {
        let self = this
        recommendApi.getKeywords(self).then(function (response) {
          if (response.data.status === 0) {
            var i
            var suggestionKeywords = []
            for (i in response.data.data) {
              suggestionKeywords[i] = {
                value: response.data.data[i],
                link: '/list/keyword/' + response.data.data[i]
              }
            }
            self.suggestionKeywords = suggestionKeywords
            console.log('----self.suggestionKeywords---- ', self.suggestionKeywords)
          }
        })
      },
      handleSelect (item) {
        console.log('---------handleSelect----------:', item)
        this.$router.push(item.link)
      },
      watch: {
        '$route' (route) {
          console.log('search: ', route)
          this.keyword = route.params['keyword']
        }
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .input-wrapper {
    margin: 0 auto;
    margin-top: 20px;
    width: 800px;
  }

  .inline-input {
    width: 100%;
  }
</style>

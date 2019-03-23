<template>
  <div>
    <form>
      <div>
        <label>中文名称</label>
        <input type="text" class="form-control" v-model="entity.chinese">
      </div>
      <div>
        <label>英文名称</label>
        <input type="text" class="form-control" v-model="entity.english">
      </div>
      <h3>属性</h3>
      <div>
        <div v-for="p in entity.properties">
          <label>中文</label>
          <input type="text" v-model="p.chinese">
          <label>英文</label>
          <input type="text" v-model="p.english">
          <label>类型</label>
          <input type="text" v-model="p.type">
        </div>
        <button v-on:click="addProp"> + </button>
      </div>
      <button type="button" class="btn btn-default" v-on:click="submit()">提交</button>
    </form>
  </div>
</template>

<script>
import g from '../../global.js'
import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

export default {
  name: 'Index',
  data () {
    return {
      entity: {
        chinese: '',
        english: '',
        properties: []
      }
    }
  },
  methods: {
    submit: function () {
      this.$validator.validateAll().then(result => {
        if (result) {
          let entity = this.$data.entity
          Vue.http.post(g.t('/api/admin/code'), JSON.stringify(entity)).then(function (response) {
            alert('成功')
          }, function (response) {
            alert('失败')
          })
        }
      })
    },
    addProp () {
      this.entity.properties.push({'chinese': '', 'english': '', 'type': ''})
    }
  },
  created () {
  }
}
</script>

<style scoped>
.liveInput{
  width: 40%
}
</style>

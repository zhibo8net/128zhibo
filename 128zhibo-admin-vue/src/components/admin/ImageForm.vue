<template>
  <div>
    <form>
      <div :class="{'form-group':true, 'has-error':errors.has('项目')}">
        <label>项目</label>
        <input type="text" class="form-control" placeholder="项目" v-model="image.project" v-validate="'required'" name="项目">
        <span class="help-block" v-show="errors.has('项目')">{{ errors.first('项目') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('标题')}">
        <label>标题</label>
        <input type="text" class="form-control" placeholder="输入标题" v-model="image.title" v-validate="'required'" name="标题">
        <span class="help-block" v-show="errors.has('标题')">{{ errors.first('标题') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('来源')}">
        <label>来源</label>
        <input type="text" class="form-control" placeholder="输入来源" v-model="image.source" v-validate="'required'" name="来源">
        <span class="help-block" v-show="errors.has('来源')">{{ errors.first('来源') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('图片')}">
        <label>图片（数量：{{ image.images.length }}）</label>
        <div v-for="i in image.images" class="one-image">
          <img :src="i.name"/>
          <button v-on:click="remove(i)" class="del-image-btn">删除</button>
        </div>
        <input ref="files" type="file" name="image" id="image" v-on:change="upload">
        <span class="help-block" v-show="errors.has('图片')">{{ errors.first('图片') }}</span>
      </div>
      <button type="button" class="btn btn-default" v-on:click="submit()">提交</button>
    </form>
  </div>
</template>

<script>
import g from '../../global.js'
import Vue from 'vue'
import VueResource from 'vue-resource'
import zhcn from 'vee-validate/dist/locale/zh_CN'
import VeeValidate, { Validator } from 'vee-validate'
import VueKindEditor from 'vue-kindeditor'
import 'kindeditor/kindeditor-all-min.js'
import 'kindeditor/themes/default/default.css'

Vue.use(VueResource)

Validator.localize('zh_CN', zhcn)
Vue.use(VeeValidate)

Vue.config.productionTip = false
Vue.use(VueKindEditor)

export default {
  name: 'Index',
  data () {
    return {
      image: {
        images: []
      }
    }
  },
  methods: {
    submit: function () {
      this.$validator.validateAll().then(result => {
        if (result) {
          let image = this.$data.image
          if (!image.id) {
            Vue.http.post(g.t('/api/admin/imageBags'), JSON.stringify(image)).then(function (response) {
              location.href = '#/imageList'
            }, function (response) {
              g.toLogin()
            })
          } else {
            Vue.http.put(g.t('/api/admin/imageBags/' + image.id), JSON.stringify(image)).then(function (response) {
              location.href = '#/imageList'
            }, function (response) {
              g.toLogin()
            })
          }
        }
      })
    },
    upload: function (e) {
      let that = this

      e.preventDefault()
      var files = e.target.files
      var data = new FormData()
      data.append('image', files[0])

      Vue.http.post(g.t('/api/admin/file'), data).then(function (response) {
        that.$data.image.images.push({'name': response.bodyText})
      }, function (response) {
        console.log(2)
        console.log(response.bodyText)
      })
    },
    onContentChange (val) {
      this.image.content = val
    },
    remove: function (image) {
      this.$data.image.images.splice(this.$data.image.images.indexOf(image), 1)
    }
  },
  created () {
    let image = this.$data.image
    let imageId = this.$route.params.id
    if (imageId) {
      Vue.http.get(g.t('/api/admin/imageBags/' + imageId)).then((response) => {
        let imageEntity = response.data
        this.$set(image, 'id', imageEntity.id)
        this.$set(image, 'title', imageEntity.title)
        this.$set(image, 'source', imageEntity.source)
        this.$set(image, 'project', imageEntity.project)
        this.$set(image, 'images', imageEntity.images)
      }, function (response) {
        g.toLogin()
      })
    }
  }
}
</script>

<style scoped>
body{
  background-color: #CCC;
}
.one-image{
  padding: 20px;
}
.one-image img{
  height: 200px;
}
.del-image-btn{
  vertical-align: bottom;
}
</style>

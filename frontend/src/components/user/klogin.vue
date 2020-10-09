<template>
  <div>
  </div>
</template>

<script>
import { mapMutations, mapActions } from 'vuex'
const BACK_URL=process.env.VUE_APP_BACK_URL
export default {
    methods:{
        ...mapMutations(["SET_TOKEN"]),
        ...mapActions(["getUserData"])
    },
    created:function(){
        this.SET_TOKEN(this.$route.query.token)
        this.getUserData()
    }
}
</script>

<style>

</style>
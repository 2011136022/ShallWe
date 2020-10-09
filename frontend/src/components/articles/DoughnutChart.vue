<template>
  <div>
    <h3 class="mb-3">참가자 별 금액 현황</h3>
    <div>
      <chartjs-doughnut
        :bind="true"
        :data="this.articleData.scoreList"
        :labels="this.articleData.nameList"
        :backgroundColor="this.articleData.rgbCodeList"
        :hoverBackgroundColor="this.articleData.rgbCodeList"
        :option="option"
      />
    </div>
  </div>
</template>
<script>
  import { Doughnut } from 'vue-chartjs'
  import { mapState, mapActions } from "vuex";

  export default {
    name: "Doughnut",
    extends: Doughnut,
    data () {
      return {
        data: [],
        backgroundColor: [],
        hoverBackgroundColor: [],
        labels: [],
        option: {}
      }
    },
    mounted () {
      this.renderChart(this.chartdata, this.options)
    },
    methods: {
      ...mapActions(["getArticle"]),
    },
    computed: {
      ...mapState(["articleData"]),
    },
    created: function () {
      this.getArticle(this.$route.params.ID);
    },
  }
</script>
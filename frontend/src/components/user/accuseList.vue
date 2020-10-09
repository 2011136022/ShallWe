<template>
  <div class="mt-5">
    <h3 class="mb-3 our-main-font">신고관리</h3>
    <ul class="list-group">
      <li class="list-group-item">
        <accuseListItem v-for="accuse in accuses" :key="accuse.accuseId" :accuse="accuse" @get="getAccuses"/>
      </li>
    </ul>
  </div>
</template>

<script>
const BACK_URL = process.env.VUE_APP_BACK_URL;
import { mapState, mapActions } from "vuex";
import axios from "axios";
import accuseListItem from "@/components/user/accuseListItem";

export default {
  name: "accuseList",
  components: {
    accuseListItem,
  },
  data() {
    return {};
  },
  methods: {
    ...mapActions(["getAccuses"]),
  },
  created() {
    this.getAccuses();
  },
  computed: {
    ...mapState(["accuses"]),
  },
};
</script>

<style>
</style>
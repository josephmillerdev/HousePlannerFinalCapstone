<template>
  <div>
    <!-- <router-link
      tag="button"
      class="btn addNewCard"
      :to="{ name: 'addRoom', params: { homeId: this.$route.params.homeId }}"                          
      >Add New Room</router-link> -->
    <div>
      <house-details />
    </div>
  </div>
</template>

<script>
// import houseService from "@/services/HouseService";
import HouseDetails from "../components/HouseDetails";
export default {
  name: "room-list",
  components: {
   HouseDetails,
  },
  methods: {

  },
  computed: {
    floorArray() {
      return this.$store.state.houses.floors.filter( (curFloor) =>{
        return curFloor.homeId == this.$route.params.homeId;
      })
    }
  }
};
</script>

<style>
</style>
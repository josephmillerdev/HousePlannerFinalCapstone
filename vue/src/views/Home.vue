<template>
  <div class="home">
    
    <house-list/>
      
  </div>
</template>

<script>
import HouseList from "@/components/HouseList";


export default {
  name: "home",
  components: {
    HouseList
  }
};

</script>

<style>


.home {

  background-image: url("../assets/house_list_picture.png");
  background-size: 100%;
  background-position:center;
  background-image:linear-gradient();
  height: 70vh;
  margin-top: 4%;
  margin-left: 18%;
  


}
</style>
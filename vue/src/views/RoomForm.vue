<template>
  <div class="room-form">
    <h1>Create New Room</h1>
    <new-room-form v-bind:homeId="$route.params.homeId" v-bind:floorId="$route.params.floorId"/>
    <calculations />
  </div>
</template>

<script>

import NewRoomForm from "@/components/NewRoomForm";
import Calculations from "@/components/Calculations";

export default {
  name: "RoomForm",
  components: {
    NewRoomForm,
    Calculations
  }
};

</script>
<style>
.room-form{
  color: #240128;
  font-family: "Covered By Your Grace", cursive;
  
  
}

</style>
<template>
<div>
  <!-- <div>
     <h2>Can We See This </h2>
 </div>  -->
 
  <form v-on:submit.prevent="createRoom">
    <div>
      <label for="room-name">Room Type</label>
      <select class="room-input" v-model="room.roomName">
        <option>Kitchen</option>
        <option>Dining Room</option>
        <option>Living Room</option>
        <option>Bedroom</option>
        <option>Bathroom</option>
      </select>
    </div>

    <div>
    <label for="room-length">Room Length (Feet)</label>
    <input class="roomLength-input" type="number" v-model="room.roomLength" />
    </div>
   
   <div>
    <label for="room-width">Room Width (Feet)</label>
    <input class="roomWidth-input" type="number" v-model="room.roomWidth" />
    </div>
<!-- 
    <div>
    <label for="room-sqft">Total Square Footage</label>
    <input class="roomSqft-input" type="number" v-model="sqftTotal" />
    </div> -->

    <div>
      <label for="flooring-type">Flooring Type</label>
      <select class="flooringType-input" v-model="room.flooringType">
        <option>Hardwood</option>
        <option>Carpet</option>
        <option>Tile</option>
      </select>
    </div>

    <button type="submit" @click="$router.go(-1)">Add New Room</button>
 
    
  </form>

   <select v-model="roomSelected" @change="selectOption" >
    <option v-for="(item,index) in $store.state.itemList" v-bind:key="item.roomName" :value="index">{{ item.roomName }}</option>
  </select>

  <select v-model="itemsSelected" v-if="roomsSelected != -1">
      <option v-for="option in $store.state.itemList[roomsSelected]" 
      v-bind:key="option.itemId">{{ option.itemName }}</option>
    </select>


  <!-- <p v-if="itemsSelected">
    You selected a {{  }} and specifically {{ selectedOption }}.
  </p> -->
  
</div>
</template>

<script>
import houseService from "@/services/HouseService";
export default {
  name: "new-room-form",
  props: ["homeId", "floorId"],
  data() {
    return {
      room: {
        roomName: "",
        roomLength: "",
        roomWidth: "",
        flooringType: "",
        floorId: this.floorId
      },

      roomSelected: -1,
      itemsSelected: ''
    };
  },
  methods: {
      selectOption() {
        this.itemsSelected = '';
      },
      createRoom() {
          
          const roomParam = {
            room: this.room,
            homeId: this.homeId
          }
          houseService.createNewRoom(this.$store.state.user.id, this.$route.params.floorId, this.room).then((response) => {
              if (response.status === 201) {
              this.$store.commit("ADD_NEW_ROOM", roomParam);
              //this.$router.push()({name: "Details", params: { homeId: this.house.homeId }});//need to find houseId param
            }

          })
      }
    //method here to add the house using the service and committing to the store
  },
  computed: {
    // sqftTotal() {
    //   return this.roomLength * this.roomWidth;
    // }
      
  }
};
</script>

<style>
</style>
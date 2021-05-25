<template>
  <div class="container">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <div class="left-col">
      <h1>{{ $store.state.house.name }}</h1>
      <button class="delete-house" v-on:click="deleteHouse($store.state.house.homeId)">
        Delete Home
      </button>

      <div class="house-details">
        <!-- <h2>Home ID: {{ this.house.homeId }}</h2> -->
        <div class="region">Region: {{ this.house.region }}</div>
        <!-- <h2>Foundation Size: {{ this.house.foundationSize }}</h2> -->
        <div class="number">Number of Floors: {{ this.house.numberOfFloors }}</div>
        <div class="cost">House Cost : ${{totalCostOfHouse()}}</div>
        <button class="add-floor" v-on:click="addFloor()">Add A Floor</button>
      </div> <!-- house-details -->
    </div> <!-- left-col -->

    <div class="floor-col-container">
      <div
        class="floor-columns"
        v-for="floor in this.house.floors"
        v-bind:key="floor.floorId">
        <div class="flex-col">

           

          <p class="floor-number">Level {{floor.level}}
            <i class="fa fa-home"></i>
          </p>
        </div>
        <!-- flex col -->

        <div class="add-delete-flex">
          <router-link class="add-a-room-btn"
            :to="{ name: 'addRoom', params: { homeId: floor.homeId, floorId:
            floor.floorId }}"><i class="fa fa-plus" style="font-size: 28px"></i> Room</router-link>
          <button class="delete-floor" v-on:click="deleteFloor(floor.floorId)">
             <i class="fa fa-trash-o" style="font-size:28px"></i>
          </button>  
        </div>

        <div class="room-container" v-for="room in floor.rooms" v-bind:key="room.roomId">
            <button class="room-name">{{room.roomName}} ${{room.roomCost}}</button> 
            <button class="delete-room" v-on:click="deleteRoom(room.roomId)">
             Delete</button>
        </div>  

      </div>
        <!-- <div v-for="room in roomArray(floor.floorId)" v-bind:key="room.roomId">
          Room Id: {{ room.roomId }}
        </div> -->
    </div>
    <!-- floor-columns -->

  </div>
</template>

<script>
import houseService from "@/services/HouseService";
export default {
  name: "house-details",
  props: ["floorId", "roomId"],
  created() {
    this.retrieveHouse();
  },
  data() {
    return {
      house: {
        homeId: "",
        name: "",
        region: "",
        foundationSize: "",
        numberOfFloors: "",
        floors: [],
      },

      roomCostTotal: 0,

      floor: {
        floorId: "",
        floorLevel: "",
        numberOfRooms: "",
        rooms: [],
      },

      room: {
        roomId: "",
        floorId: "",
        flooringType: "",
        roomLength: "",
        roomWidth: "",
        roomName: "",
      },
      detail: {},
      //move to be a computed property - to return house from the store
      selectedFloor: {},

    };
  },
  methods: {

    

    totalCostOfRooms(){
      let total = 0;

      for(let i = 0; i < this.house.floors.length; i ++) {
           if(this.house.floors[i].rooms.length != -1){
           for(let z = 0; z < this.house.floors[i].rooms.length; z ++){
             total += parseInt(this.house.floors[i].rooms[z].roomCost);
           }
           } 
      }

      this.roomCostTotal = total;
      return total;
    },

    totalCostOfHouse(){
      this.totalCostOfRooms();
      let total = 0; 
      let initialHouseCost = 60000;
      total += (initialHouseCost + this.roomCostTotal);

      return total;
    },

    roomArray(floorId) {
      return this.$store.state.floor.rooms.filter((room) => {
        room.floorId == floorId;
      });
    },

    retrieveHouse() {
      houseService
        .getHomeDetails(this.$route.params.homeId)
        .then((response) => {
          this.$store.commit("LOAD_HOUSE_DETAILS", response.data);
          this.house = response.data;
        });
    },

    deleteHouse(homeId) {
      if (confirm("Are you sure you want to delete this home?")) {
        houseService
          .deleteHouse(this.$store.state.user.id, homeId)
          .then((response) => {
            if (response.status === 200) {
              this.$router.push({ name: "home" });
            }
          })
          .catch((error) => {
            if (error.response) {
              this.errorMsg =
                "Error deleting house. Response received was '" +
                error.response.statusText +
                "'.";
            } else if (error.request) {
              this.errorMsg =
                "Error deleting house. Server could not be reached.";
            } else {
              this.errorMsg =
                "Error deleting house. Request could not be created.";
            }
          });
      }
    },

    addFloor() {
      houseService
        .addNewFloor(this.$store.state.user.id, this.house.homeId)
        .then((response) => {
          if (response.status === 201) {
            this.$store.commit("UPDATE_HOUSE", response.data);
            
            // this.$router.push({
            //   name: "Details",
            //   params: { homeId: this.$store.state.house.homeId },
            // });
          }
        });
        // this.house = this.$store.state.house;
        this.retrieveHouse();
    },

    deleteFloor(floorId) {
      houseService.deleteFloor(floorId).then((response) => {
        if (response.status == 200) {
          this.$store.commit("DELETE_FLOOR", response.data);
        }
      });
      // this.house = this.$store.state.house;
      this.retrieveHouse();
    },

    deleteRoom(roomId) {
      houseService.deleteRoom(roomId).then((response) => {
        if (response.status == 200) {
          this.$store.commit("DELETE_ROOM", response.data);
        }
      })
      //make a better solution to this and where we do this in add floor and delete floor!!
    //  this.house = this.$store.state.house;
    //  this.retrieveHouse();
    }
    
    
  },
  computed: {
    // house() {
    //   return this.$store.state.house;
    // }
  },
};
</script>
<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Covered+By+Your+Grace&display=swap");

.container {
  background-image: url("../assets/living3.jpg");
  background-repeat:no-repeat;
  background-size: 75%;
  background-position-x: 80%;
  background-position-y: 60%;
  /* background-color: #F5DECC; */
  padding-bottom: 30%;
}
.left-col {
background-color: gainsboro;
width: 20%;
float: left;
font-family: "Annie Use Your Telescope", cursive;
color: #3a0b3f;
border: solid;
border-color: #240128;
border-radius: 10px;
padding-bottom: 25%;

}

.delete-house {
  background-color: rgb(214, 67, 67);
  font-family: "Covered By Your Grace", cursive;
  font-size: 20px;
  color: whitesmoke;
  border: whitesmoke;
  border: 5px dotted rgb(255, 255, 255);
  cursor: pointer;
  margin-left: 31%;
  margin-bottom: 10px;
}

.left-col > .house-details {
  padding-top: 25%;
  padding: 20px;
  font-size: 20px;

}

.left-col > h1 {
  font-family: "Covered By Your Grace", cursive;
  justify-items: center;
  font-size: 50px;
  border: 5px dashed;
  border-color: #240128;
  text-align: center;
  padding: 25px;
  background-color: mistyrose;

}

.house-details {
  background-color: rgb(255, 255, 255);
  margin-top: 20px;
  border-radius: 20%;
}

.region {
  border: 5px solid indigo;
  border: double;
  border-radius: 10%;
  padding: 3px;
  margin-bottom: 5%;
}

.number {
  border: 5px solid indigo;
  border: double;
  border-radius: 10%;
  padding: 3px;
  margin-bottom: 5%;
}

.cost {
  border: 5px solid indigo;
  border: double;
  border-radius: 10%;
  padding: 3px;
  margin-bottom: 5%;
}

.house-details#h2 {
  border-style: ridge;
}

.floor-columns {
  /* border-style:double;
  float: left;
  grid-template-columns: repeat(4, 1fr); */
  
  background-color: mistyrose; 
  width: 50%;
  margin: 20px;
  border: 5px ridge;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  /* flex-wrap: wrap; */
  border-radius: 5%;
  padding-bottom: 25%;
  /* border: 10px; */


}

/* .flex-col {
  
} */

.floor-number {
  /* border: dashed #240128; */
  border-radius: 20%;
  padding: 10px;
  font-family: "Covered By Your Grace", cursive;
  font-size: 40px;
  /* margin-left: 40%; */
  text-align: center;
  color: #5d1166;
  font-weight: bold;

}
.delete-floor {
  background-color: mistyrose;
  font-family: "Covered By Your Grace", cursive;
  font-size: 20px;
  color: black;
  border: none;
  /* border: 2px solid #240128; */
  margin-left: 20px;
  cursor: pointer;

}


.add-floor {
  display: flex;
  background-color: rgb(243, 242, 242);
  border-color:#240128;
  justify-content: center;
  padding: 5px;
  margin-top: 5%;
  margin-left: 32%;
  cursor: pointer;


}

.add-a-room-btn{
  color: #240128;
  font-family: "Annie Use Your Telescope", cursive;
  font-size: 20px;
  /* border: ridge; */
  cursor: pointer;
  margin-right: 20px;
  /* border: 2px solid #240128; */
  


  

}

.floor-col-container{
  display: flex;
}

.room-name {
  background-color: #240128;
  color: whitesmoke;
  font-size: 24px;
  font-family: "Annie Use Your Telescope", cursive;
  position: relative;
  border-radius: 20%;
  /* float:left; */
  margin-top: 10px;
  margin-bottom: 10px;

}
 .delete-room {

  /* background-color: white ; */
  font-family: "Annie Use Your Telescope", cursive;
  font-size: 20px;
  /* color: whitesmoke; */
  border: whitesmoke;
  /* border: 5px dotted rgb(255, 255, 255); */
  border-radius: 20px;
  cursor: pointer;
  margin-left: 20px;
  margin-top: 8px;
  box-shadow:5px 5px 5px #240128
  
  /* margin-bottom: 15px; */

  
 }

 .add-delete-flex{
   display: flex;
   justify-content:space-around;
 }


</style>
<template>
  <div class="grid-container">
    <link rel="preconnect" href="https://fonts.gstatic.com" />
    <link
      href="https://fonts.googleapis.com/css2?family=Covered+By+Your+Grace&display=swap"
      rel="stylesheet"
    />
    <form class="roomForm-container" v-on:submit.prevent="createRoom">
      <div>
        <label class="roomName" for="room-name">Room Name</label>
        <input
          id="roomName-input"
          type="text"
          name="roomName"
          v-model="room.roomName"
        />
      </div>
      <div>
        <label class="name" for="room-length">Room Length (Feet)</label>
        <input
          id="roomName-input"
          type="number"
          name="roomLength"
          v-model="room.roomLength"
        />
      </div>

      <div>
        <label class="name" for="room-width">Room Width (Feet)</label>
        <input
          id="roomName-input"
          type="number"
          name="roomWidth"
          v-model="room.roomWidth"
        />
      </div>
      <div>
        <label class="name" for="flooring-type">Flooring Type</label>
        <select id="roomName-input" v-model="room.flooringType">
          <option>Hardwood</option>
          <option>Carpet</option>
          <option>Tile</option>
        </select>
      </div>

      

      <!-- <button type="button" id="calculate" name="button">Calculate</button>   -->
<label class="name" >Room Type</label>
      <select id="roomName-input" v-model="roomSelected" @change="selectOption">
        <option
          v-for="(item, index) in $store.state.itemList"
          v-bind:key="item.roomName"
          :value="index"
        >
          {{ item.roomName }}
        </option>
      </select>
<br/>
      <select
        id="roomName-input-choice"
        v-model="selectedItem"
        v-if="roomSelected != -1"
      >
        <option
          v-for="option in $store.state.itemList[roomSelected].options"
          v-bind:key="option.itemId"
          v-bind:value="option.itemId"
        >
          {{ option.itemName }} ${{ option.itemPrice }}
        </option>
      </select>
      <br/>
      <button class="addMe" v-on:click="addItem2">ADD ME!</button>
    </form>

    <div class="calc-container">
      <div class="calc-name">ROOM CALCULATOR</div>
      <div>
        <div class="calcNamePrice" for="room-sqft"
          >Total Square Footage: {{ sqftTotal() }}
        </div>
      </div>
      <div>
        <div class="calcNamePrice" for="sqft-price"
          >Price of Total Flooring: ${{ totalFloorPrice() }}</div
        >
      </div>
      <div v-for="item in $store.state.selectedItems" v-bind:key="item.itemId">
        <span class="calcNamePrice">{{ item.itemName }}: ${{item.itemPrice}}</span> 
        <!-- <span class="calcPricePriceP">${{ item.itemPrice }}</span> -->
      </div>
      <br/>
      <br/>
      <span class="totalRoomCost">TOTAL ROOM COST: <span class="priceTotal"> ${{ totalRoomPrice() }}</span></span>
    </div>

    <div class="footer">
      <button
        class="btn btn-submit"
        type="submit"
        @click.prevent="$router.go(-1)"
      >
        ADD ROOM
      </button>
      <button
        class="btn btn-cancel"
        v-on:click.prevent="cancelForm"
        type="cancel"
      >
        CANCEL
      </button>
    </div>
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
        roomLength: 0,
        roomWidth: 0,
        // squareFeet: 0,
        flooringType: "",
        floorId: this.floorId,
        roomCost: 0,
        items: [],
      },

      floorSelected: -1,
      selectedFloor: "",

      roomSelected: -1,
      selectedItem: "",
    };
  },
  created() {
    let firstItem = {
      itemId: 0,
      itemName: "Item Name",
      itemPrice: 0,
    };
    //this.$store.state.selectedItems.push({ itemId: 0, itemName: 'Item Name', itemPrice: 10000.00})
    //this.$store.commit('ADD_ITEM', this.$store.state.selectedItems[0])
    this.$store.commit("ADD_ITEM", firstItem);
  },
  methods: {
    totalRoomPrice() {
      let roomTotal = 0;
      roomTotal = this.totalFloorPrice() + this.$store.state.currentRoomTotal;
      // return this.totalFloorPrice() + this.$store.state.currentRoomTotal;
      this.room.roomCost = roomTotal;
      return roomTotal;
    },

    sqftTotal() {
      return this.room.roomLength * this.room.roomWidth;
    },

    totalFloorPrice() {
      let matchedFloorPrice = 0;
      for (let i = 0; i < this.$store.state.floorList.length; i++) {
        if (
          this.$store.state.floorList[i].flooringType == this.room.flooringType
        ) {
          matchedFloorPrice = this.$store.state.floorList[i].floorPrice;
        }
      }
      return matchedFloorPrice * this.sqftTotal();
    },
    selectOption() {
      this.selectedItem = "";
    },
    selectedFloorOption() {
      this.selectedFloor = "";
    },
    addItem2() {
      let id = this.selectedItem;

      let itemToAdd = this.$store.state.itemList[
        this.roomSelected
      ].options.filter((curItem) => {
        return curItem.itemId == id;
      });
      this.room.items.push(itemToAdd[0]);

      this.$store.commit("ADD_ITEM", itemToAdd[0]);
    },

    addItem(id, name, price) {
      let newItem = {
        itemId: id,
        itemName: name,
        itemPrice: price,
      };
      //this.house.selectedItems.push(newItem);
      this.$store.commit("ADD_ITEM", newItem);
    },

    // find chosen item by item
    // finditem() {
    // let chosenItem = $store.state.itemList.filter( (curItem) => {
    //   return curItem.roomName.options.itemId
    //     })
    // },

    createRoom() {
      const roomParam = {
        room: this.room,
        homeId: this.homeId,
      };
      houseService
        .createNewRoom(
          this.$store.state.user.id,
          this.$route.params.floorId,
          this.room
        )
        .then((response) => {
          if (response.status === 201) {
            this.$store.commit("ADD_NEW_ROOM", roomParam);
            //this.$router.push()({name: "Details", params: { homeId: this.house.homeId }});//need to find houseId param
          }
        });
    },
    cancelForm() {
      this.$router.push({ name: "home" });
    },
    //method here to add the house using the service and committing to the store
  },
  computed: {
    // houseTotal() {
    //     return this.cart.map($store.state.itemList. => item.price).reduce((total, amount) => total + amount);
    // }
  },
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Covered+By+Your+Grace&display=swap");
.grid-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-areas:
    "roomForm-container calc-container "
    "footer footer ";
  background-color: #f5decc;
  background-image: url("../assets/bed2.jpg");
  /* background-repeat: no-repeat; */
  gap: 50px; 
  
}
.roomForm-container {
  grid-area: roomForm-container;
  background-color: #f5decc;
  color: black;
  border-radius: 50px;
  width: 450px;
  text-align: center;
  vertical-align: middle;
  margin-left: 35%;
  

}

.roomName {
  display: block;
  color: #240128;
  font-family: "Covered By Your Grace", cursive;
  /* font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
    "Lucida Sans Unicode", Geneva, Verdana, sans-serif; */
  font-size: 50px;
}
.name {
  display: block;
  color: #240128;
  font-family: "Annie Use Your Telescope", cursive;
  /* font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
    "Lucida Sans Unicode", Geneva, Verdana, sans-serif; */
  font-size: 20px;
}
#roomName-input {
  padding: 0.9em;
  background-color: white;
  color: #240128;
  font-family: "Annie Use Your Telescope", cursive;
  /* font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
    "Lucida Sans Unicode", Geneva, Verdana, sans-serif; */
  font-size: 18px;
  width: 250px;
  padding: 11px;
  margin: 10px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 15px;
  box-sizing: border-box;
  box-shadow: 0 0 15px 4px rgba(95, 94, 94, 0.06);
}
#roomName-input-choice {
  padding: 0.9em;
  background-color: white;
  color: #240128;
  font-family: "Annie Use Your Telescope", cursive;
  /* font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
    "Lucida Sans Unicode", Geneva, Verdana, sans-serif; */
  font-size: 15px;
  width: 320px;
  padding: 15px;
  margin: 10px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 15px;
  box-sizing: border-box;
  box-shadow: 0 0 15px 4px rgba(95, 94, 94, 0.06);
  overflow-wrap: break-word;
}

.addMe {
  background-color: #240128;
  color: #e0e1e1;
  font-size: 16px;
  width: 25%;
  border-radius: 15px;
  padding: 20px;
  margin: 10px 0;
  cursor: pointer;
}
.addMe:hover {
  background-color: #e0e1e1;
  color: #240128;
}
.btn-submit {
  color: #e4f1e5;
  font-family: "Annie Use Your Telescope", cursive;
  font-size: 16px;
  background-color: #4a576d;
  width: 25%;
  border-radius: 15px;
  padding: 15px;
  margin: 10px 0;
  cursor: pointer;
}

.btn-submit:hover {
  background-color: #e0e1e1;
  color: #240128;
}
.btn-cancel {
  color: #e4f1e5;
  font-family: "Annie Use Your Telescope", cursive;
  font-size: 16px;
  background-color: #4a576d;
  width: 25%;
  border-radius: 15px;
  padding: 15px;
  margin: 10px 0;
  cursor: pointer;
}
.btn-cancel:hover {
  background-color: #e0e1e1;
  color: #240128;
}
.calc-container {
  grid-area: calc-container;
  background-color: #e0e1e1;
  border: 15px solid #240128;
  border-radius: 50px;
  margin-left: 15%;
  width: 435px;
  
}
.calc-name {
  font-family: 'Hind Siliguri', sans-serif;
  font-weight: bold;
  color: #240128;
  text-align: center;
  font-size: 45px;
  border: 15px dashed #240128;
}
.calc-container-total {
  background-color: #f5decc;
  color: #f5decc;
}
.totalRoomCost {
  font-family: 'Hind Siliguri', sans-serif;
  color: #240128;
  font-size: 30px;
  bottom: 0%;
  justify-content: flex-end;
  padding: 3px;
}
.priceTotal {
  font-family: 'Hind Siliguri', sans-serif;
  color: #240128;
  font-size: 30px;
  /* bottom: 5%; */
  float: right;
  /* display: table-cell;
  vertical-align: bottom; */
  padding: 3px;
}
.calcPricePrice {
   font-family: 'Hind Siliguri', sans-serif;
  color: #240128;
  font-size: 20px;
  float: right;
}
.calcPricePriceP {
   font-family: 'Hind Siliguri', sans-serif;
  color: #240128;
  font-size: 20px;
  float: right;
  padding: 25px
}
.calcNamePrice {
font-family: 'Hind Siliguri', sans-serif;
  color: #240128;
  font-size: 20px;
  bottom: 0%;
  padding: 20px;
 
}


.footer {
  grid-area: footer;
  text-align: center;
}
</style>


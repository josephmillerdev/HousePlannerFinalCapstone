<template>
  <div id="sideNav">
    <nav class="title">Your Houses</nav>
    <div class="houses">
      <head class="house-list">
        <link rel="preconnect" href="https://fonts.gstatic.com" />
        <link
          href="https://fonts.googleapis.com/css2?family=Covered+By+Your+Grace&display=swap"
          rel="stylesheet"
        />
      </head>
      <!-- <nav class="under-title">Your Houses</nav> -->
      <body>
        <div
          class="single-house"
          v-for="house in this.$store.state.houses"
          v-bind:key="house.homeId"
        >
          <!-- <li> -->
            <router-link
              v-bind:to="{ name: 'Details', params: { homeId: house.homeId } }"
              class="house-names"
              v-bind:style="{ randomBackgroundColor }"
            >{{ house.name }}
            </router-link>
          <!-- </li> -->
        </div>
      </body>
    </div>
    <nav>
      <div class="create-home-btn">
        <router-link
          v-bind:to="{ name: 'Form', params: { id: $store.state.user.id } }"
          class="create-home"
          v-bind:style="{ randomBackgroundColor }"
          >Create New Home</router-link
        >
      </div>
    </nav>

  </div>
</template>

<script>
import houseService from "@/services/HouseService";

export default {
  // data() {
  //   return {
  //     newHouse: {
  //       name: '',
  //     }
  //   }
  // }
  name: "house-list",
  created() {
    houseService.getHomeList(this.$store.state.user.id).then((response) => {
      this.$store.commit("LOAD_HOUSES", response.data);
    });
  },
  methods: {
    getHouseDetails() {
      houseService
        .getHomeDetails(this.$store.state.home.homeId)
        .then((response) => {
          if (response.status === 201) {
            this.name = response.data.name;
            this.$store.commit("LOAD_HOUSE_DETAILS", response.data);
            this.$router.push({
              name: "Details",
              params: { homeId: this.$store.state.house.homeId },
            });
          }
        })
        .catch((error) => {
          if (error.response && error.response.status === 404) {
            alert("House is not available. This house may have been deleted.");
            this.$router.push("/");
          }
        });
    },
    randomBackgroundColor() {
      return "#" + this.generateHexCode();
    },
    generateHexCode() {
      var bg = Math.floor(Math.random() * 16777215).toString(16);
      if (bg.length !== 6) bg = this.generateHexCode();
      return bg;
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Covered+By+Your+Grace&display=swap");
div#sideNav {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 90%;
  width: 25%;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 30px;
  /* bottom: 10px; */
  /* padding-top: 100px;
  padding-bottom: 250px; */
  overflow-x: hidden;
  border-right: solid lightgrey 1px;
  background-color: #f5decc;
  background-image: linear-gradient(to right,#f5decc,#FCF4EE );
}

.title {
  font-family: 'Covered By Your Grace', cursive;
  text-align: left;
  font-size: 75px;
  color: #240128;
  padding-top: 1px;
  padding-bottom: 20px;
 
}
.under-title {
  font-family: 'Annie Use Your Telescope', cursive;
  text-align: left;
  font-size: 35px;
  color: #240128;

}
.houses {
  display: flex;
  flex: 1;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}

.single-house {
  display:flex;
  justify-content:center;
  align-items: center;
  margin: 35px;
  text-align: center;
  cursor: pointer;
  width: 250px;
  height: 65px;
  border-radius: 20px;
  border: solid #4a576d 2px;
  background-color: #e0e1e1;
}
a.house-names {
  color: #4a576d;
  text-decoration: none;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
    "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
  
  text-align: center;
  cursor: pointer;
  font-size: 25px;
  font-weight: bold;
  word-wrap:break-word;
  
  
}

a.house-names:hover{
  color: #240128; 
  transform: scale(1.1);
}



.form-control {
  margin-bottom: 10px;
}
.btn {
  margin-bottom: 35px;
}
.loading {
  flex: 3;
}

.board:hover:not(.router-link-active),
.addBoard:hover {
  font-weight: bold;
}

.create-home-btn:hover {
  color:#e0e1e1;
  transform:scale(1.1);
  background-color: #240128;
}

.create-home-btn {
  display:flex;
  justify-content:center;
  align-items: center;
  margin: 35px;
  text-align: center;
  cursor: pointer;
  width: 250px;
  height: 65px;
  border-radius: 20px;
  background-color: #4a576d;
  
}

.create-home {
    
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
    "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
  color: #e0e1e1;
  text-decoration: none;
  
  font-size: 20px;
  
  cursor: pointer;
 
  text-align: center;
  
}
 

</style>
 
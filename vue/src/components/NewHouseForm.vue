<template class="main-body">
  <div class="body" align="center">
    <link rel="preconnect" href="https://fonts.gstatic.com" />
    <link
      href="https://fonts.googleapis.com/css2?family=Covered+By+Your+Grace&display=swap"
      rel="stylesheet"
    />
    <form class="homeForm" v-on:submit.prevent="createHome">
      <div>
        <label class="name">House Name</label>
        <input class="name-input" type="text" v-model="home.name" />
      </div>

      <div>
        <label class="name">Region</label>
        <select class="name-input" v-model="home.region">
          <option>North</option>
          <option>South</option>
          <option>East</option>
          <option>West</option>
          <option>Central</option>
        </select>
      </div>

      <!-- <div class="foundation">
        <label class="name">Foundation Size</label>
        <input class="name-input" type="text" v-model="home.foundationSize" />
      </div> -->

      <div>
        <label class="name">Number of Floors</label>
        <input class="name-input" type="number" v-model="home.numberOfFloors" />
      </div>

      <button class="btn btn-submit-home">ADD HOME</button>

      <button
        class="btn btn-cancel-home"
        v-on:click.prevent="cancelForm"
        type="cancel"
      >
        CANCEL
      </button>
    </form>
  </div>
</template>

<script>
import houseService from "@/services/HouseService";

export default {
  name: "new-house-form",
  data() {
    return {
      home: {
        foundationSize: "",
        region: "",
        numberOfFloors: "",
        name: "",
      },
      houses: [],
    };
  },
  methods: {
    createHome() {
      houseService
        .createHome(this.$store.state.user.id, this.home)
        .then((response) => {
          if (confirm("save?")) {
            if (response.status === 201) {
              this.$store.commit("ADD_NEW_HOUSE", this.home);
              this.$router.push({ name: "home" });
            }
          }
        })
        .catch((error) => {
          this.errorMsg = "";
          if (error.response) {
            if (error.response.status === 500) {
              this.errorMsg =
                "Error submitting new house. An internal server error occurred.";
            } else if (error.response.status === 404) {
              this.errorMsg =
                "Error submitting new house. URL could not be found.";
            } else {
              this.errorMsg =
                "Error submitting new house. Response received was code " +
                error.response.status +
                " '" +
                error.response.statusText +
                "'.";
            }
          } else if (error.request) {
            this.errorMsg =
              "Error submitting new house. Server could not be reached.";
          } else {
            this.errorMsg =
              "Error submitting house. Request could not be created.";
          }
        });
    },
    cancelForm() {
      this.$router.push({ name: "home" });
    },
  },
};
</script>

<style scope>
@import url("https://fonts.googleapis.com/css2?family=Covered+By+Your+Grace&display=swap");



.homeForm {
  /* display: block; */
  position: relative;
  left: 10%;
  transform: translate(-50%);  
  width: 400px;
  text-align: center; 
  margin: 0 auto;
  border-radius: 20px;
  background-color: #e4f1e5;
  border: 50px solid #f5decc;
  border-image: linear-gradient(to bottom, #f5decc, #FCF4EE );
}
.name {
  /* display: block; */
  font-family: "Covered By Your Grace", cursive;
  font-size: 250px;
  font-weight: bold;
  color: #240128;
}
.name-input {
  background-color: white;
  font-family: "Annie Use Your Telescope", cursive;
  font-size: 20px;
  width: 200px;
  padding: 25px;
  margin: 10px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 15px;
  box-sizing: border-box;
  box-shadow: 0 0 15px 4px rgba(95, 94, 94, 0.06);
}

.btn-submit-home {
  font-family: "Annie Use Your Telescope", cursive;
  color: #e4f1e5;
  font-size: 16px;
  background-color: #240128;
  border-color: #d1dadc;
  width: 50%;
  border-radius: 15px;
  padding: 20px;
  margin: 10px 0;
  box-shadow: 0 0 15px 4px rgba(95, 94, 94, 0.06);
  cursor: pointer;
}

.btn-submit-home:hover {
  font-family: "Annie Use Your Telescope", cursive;
   background-color: #4a576d;
  color: #240128;
}
.btn-cancel-home {
  font-family: "Annie Use Your Telescope", cursive;
color: #e4f1e5;
  font-size: 16px;
  background-color: #240128;
  border-color: #d1dadc;
  width: 50%;
  border-radius: 15px;
  padding: 20px;
  margin: 10px 0;
  box-shadow: 0 0 15px 4px rgba(95, 94, 94, 0.06);
  cursor: pointer;
}
.btn-cancel-home:hover {
  font-family: "Annie Use Your Telescope", cursive;
  background-color: #4a576d;
  color: #240128;
}
</style>
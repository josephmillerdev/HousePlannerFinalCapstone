<template>
  <div id="login" class="text-center">
    
    <form class="form-signin" @submit.prevent="login">
      <h1 class="title">Da-Vine Grape Design</h1>
      <link rel="preconnect" href="https://fonts.gstatic.com" />
      <link
        href="https://fonts.googleapis.com/css2?family=Covered+By+Your+Grace&display=swap"
        rel="stylesheet"
     />
      <div
        class="alert alert-danger"
        role="alert"
        v-if="invalidCredentials"
      >Invalid username and password!</div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.</div>
      <label for="username" class="sr-only">Username</label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <label for="password" class="sr-only">Password</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <router-link :to="{ name: 'register' }" class="registerLink">Need an account?</router-link>
      <button class = "sign-in-button" type="submit">Sign in</button>
    </form>
    
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false,
      
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>
<style>
#login{
  background-image: url("../assets/inside-and-outside-ani-barmashi.jpg");
  background-size: 85%;
  background-repeat: no-repeat;
  background-position: center;
  background-image:linear-gradient();
  height: 100vh;
  
}

.sr-only{
  font-family: 'Annie Use Your Telescope', cursive;
  color: #35033A;
  font-weight: bold;
}

#nav{
  font-family: 'Covered By Your Grace', cursive;
  font-weight: bolder;
  font-size: 20px;
  background-image: url("../assets/grape_cartoon_1.gif");
  background-size: 17px;
  background-repeat: no-repeat;
  background-position:right ;
  height: 3vh;
  align-items: center;
 
}

label{
  padding: 8px;
}

.form-control {
  padding: 0;
  height: 30px;
  width: 12%;
  position:relative;
  left: 0;
  outline: none;
  border: 1px solid #E0E1E1;
  border-color: #E0E1E1;
  background-color: white;
  font-size: 16px;
  
}

.registerLink{
  font-family: 'Covered By Your Grace', cursive;
  padding: 5px;
  color:#240128;
  font-weight: bold;
}

.title {
  font-family: 'Covered By Your Grace', cursive;
  text-align: left;
  font-size: 75px;
  color: #240128;
  padding-top: 1px;
  padding-bottom: 1px;
  margin: 1px;
 
}

.sign-in-button{
  font-family: 'Annie Use Your Telescope', cursive;
  background-color: #F5DECC;
  border-radius: 15px;
  width: 6%;
}
.sign-in-button:hover {
  transform:scale(1.1);
  background-color: #E4F1E5;
}

</style>


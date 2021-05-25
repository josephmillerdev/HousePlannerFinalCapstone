<template>
  <div class="home-form">
    <h1>Create New Home</h1>
    <new-house-form/>
  </div>
</template>

<script>

import NewHouseForm from "@/components/NewHouseForm";


export default {
  name: "Form",
  components: {
    NewHouseForm
  }
};

</script>
<style>
.home-form {
  background-image: url("../assets/liv-2.jpg");
  background-position: center;
  font-family: 'Covered By Your Grace', cursive;
  font-size: 35px;
  color:#35033A;
}

</style>
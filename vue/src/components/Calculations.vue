<template>
  <div class="calc-container-total">
      Total Cost: {{ totalCost() }}
  </div>
</template>


<script>
export default {
name: 'calculations',
methods: {
    totalCost() {
            let total = 0;
            for(let i = 0; i < this.$store.state.selectedItems.length; i++) {
                total += parseInt(this.$store.state.selectedItems[i].itemPrice);
            }
            this.$store.commit('SET_ROOM_TOTAL', total);
            return total
        },
    
    
},
computed: {
    }
}

</script>

<style>

</style>


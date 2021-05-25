import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if (currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    houses: [],
    house: {
      homeId: '',
      name: '',
      region: '',
      foundationSize: '',
      numberOfFloors: '',
      floors: []
    },
    currentRoomTotal: 0,
    floor: {
      homeId: '',
      floorId: '',
      floorLevel: '',
      numberOfRooms: '',
      rooms: []
    },
    room: {
      roomId: '',
      floorId: '',
      flooringType: '',
      roomLength: '',
      roomWidth: '',
      roomName: '',
      roomCost: '',
      item: []
    },

    floorList: [

      {
        floorId: 1,
        flooringType: "Hardwood",
        floorPrice: 60
      },
      {
        floorId: 2,
        flooringType: "Carpet",
        floorPrice: 3
      },
      {
        floorId: 3,
        flooringType: "Tile",
        floorPrice: 10
      }

    ],


    selectedItems: [],

    itemList: [

      {
        roomName: "Bathroom",
        options: [
          {
            itemId: 1,
            itemName: 'Kohler Bathroom Sink',
            itemPrice: 1000
          },
          {
            itemId: 2,
            itemName: 'Ikea Bathroom Sink',
            itemPrice: 750
          },
          {
            itemId: 3,
            itemName: 'Pottery Barn Bathroom Sink',
            itemPrice: 2000
          }
        ]
      },
      {
        roomName: "Bedroom",
        options: [
          {
            itemId: 4,
            itemName: 'Target Bed Frame',
            itemPrice: 500
          },
          {
            itemId: 5,
            itemName: 'Bed Bath & Beyond Bed Frame',
            itemPrice: 750
          },
          {
            itemId: 6,
            itemName: 'Wayfair Bed Frame',
            itemPrice: 500
          }
        ]
      },
      {
        roomName: "Kitchen",
        options: [
          {
            itemId: 7,
            itemName: 'Granite Counter-top',
            itemPrice: 500
          },
          {
            itemId: 8,
            itemName: 'Laminate Counter-top',
            itemPrice: 750
          },
          {
            itemId: 9,
            itemName: 'Butchers Block Counter-top',
            itemPrice: 500
          }
        ]
      },
      {
        roomName: "Dining Room",
        options: [
          {
            itemId: 10,
            itemName: 'Target Dining Room Set',
            itemPrice: 500
          },
          {
            itemId: 11,
            itemName: 'Chandelier',
            itemPrice: 750
          },
          {
            itemId: 12,
            itemName: 'China Set',
            itemPrice: 500
          }
        ]
      },
      {
        roomName: "Living Room",
        options: [
          {
            itemId: 13,
            itemName: 'LazyBoy Lounge Chair',
            itemPrice: 500
          },
          {
            itemId: 14,
            itemName: 'Fluffy Futon',
            itemPrice: 750
          },
          {
            itemId: 15,
            itemName: 'Barker Lounger',
            itemPrice: 500
          }
        ]
      },
      


    ]
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user', JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    LOAD_HOUSES(state, data) {
      state.houses = data;

    },
    ADD_NEW_HOUSE(state, data) {
      state.houses.push(data);
    },
    LOAD_HOUSE_DETAILS(state, data) {
      state.house = data;
    },
    UPDATE_HOUSE(state, house) {
      for (let i = 0; i < state.houses.length; i++) {
        if (state.houses[i].homeId == house.homeId) {
          state.houses[i] = house;  //.homeId;
          break;
        }
      }
    },
    DELETE_FLOOR(state, floorId) {
      state.house.floors.splice(
        state.house.floors.findIndex(floor => floor.floorId === floorId),
        1
      )
    },

    DELETE_ROOM(state, roomId) {
      state.floor.rooms.splice(
        state.floor.rooms.findIndex(room => room.roomId === roomId),
        1
      )
    },

    ADD_NEW_ROOM(state, data) {
      //chosenHome below is a new variable that stores the result of filtering the houses array by the specific home id
      //coming from the object passed in the NewRoomForm
      //data here references the
      // let chosenHome = state.houses.filter( (curHouse) => {
      //   return curHouse.homeId == data.homeId;
      // })
      let chosenFloor = state.house.floors.filter((curFloor) => {
        return curFloor.floorId == data.room.floorId;
      })
      if (chosenFloor.rooms == undefined) {
        chosenFloor.rooms = [];
      }
      chosenFloor.rooms.push(data.room);
    },
    ADD_ITEM(state, item) {
      state.selectedItems.push(item);
    },
    SET_ROOM_TOTAL(state, data){
      state.currentRoomTotal = data;
    }
  }

});


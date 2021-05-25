import axios from 'axios';
const http = axios.create({
    baseURL: "http://localhost:8080"
});

export default {

    createHome(id, data) {
        return http.post(`/users/${id}`, data);
    },

    getHomeList(id) {
        return http.get(`/users/${id}`);
    },

    addNewFloor(id, homeId) {
        return http.post(`/users/${id}/home/${homeId}`);
    },
    
    deleteFloor(floorId){
        return http.delete(`/floors/${floorId}`);
    },

    deleteHouse(id, homeId) {
        return http.delete(`/users/${id}/home/${homeId}`);
    },

    getHomeDetails(homeId) {
        return http.get(`/home/${homeId}`);
    },
    createNewRoom(id, floorId, data) {
        return http.post(`/users/${id}/floors/${floorId}`, data);
    },

    deleteRoom(roomId){
        return http.delete(`/room/${roomId}`);
    }



}
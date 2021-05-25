package com.techelevator.service;
import java.util.List;

import org.springframework.stereotype.Service;
import com.techelevator.dao.HouseDAO;
import com.techelevator.model.House;
import com.techelevator.model.Room;

@Service
public class HouseService {
	private HouseDAO houseDAO;
	
	public HouseService (HouseDAO houseDAO) {
		this.houseDAO = houseDAO;
	}
	
	public void createHouse(House house, Long id) {
		houseDAO.createHouse(house, id);
	}
	
	public void addFloors(int homeId) {
		houseDAO.addFloors(homeId);
	}

	public void addRooms(Room room, int floorId) {
		houseDAO.addRooms(room, floorId);
	}
	
	public void deleteHouse(int homeId) {
		houseDAO.deleteHouse(homeId);
	}
	
	public void deleteRoom(int roomId) {
		houseDAO.deleteRoom(roomId);
	}
	
	public void deleteFloor(int floorId) {
		houseDAO.deleteFloor(floorId);
	}

	public List<House> getHouses(Long userId){
		return houseDAO.getHouses(userId);
	}
	
	public House getHouse(int homeId) {
		return houseDAO.getHouse(homeId);
	}
	
}

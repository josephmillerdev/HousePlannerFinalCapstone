package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Home;
import com.techelevator.model.House;
import com.techelevator.model.Room;

public interface HouseDAO {

	House getHouse(int homeId);
	List<House> getHouses(Long userId);
	Home getHomeId();
	void addFloors(int homeId);
	void deleteFloor(int floorId);
	void addRooms(Room room, int floorId);
	void deleteRoom(int roomId);
	void createHouse(House house, Long id);
	void deleteHouse(int homeId);
	
}

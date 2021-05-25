package com.techelevator.model;

import java.util.List;

public class Floor {

	private int floorId;
	private int homeId;
	private int floorLevel;
	private int numberOfRooms;
	private List<Room> rooms;

	public Floor(int floorId, int homeId, int floorLevel, int numberOfRooms, List<Room> rooms) {
		this.floorId = floorId;
		this.homeId = homeId;
		this.floorLevel = floorLevel;
		this.numberOfRooms = numberOfRooms;
		this.rooms = rooms;		
	}
	
	public Floor() {
		
	}

	public int getLevel() {
		return floorLevel;
	}

	public void setLevel(int level) {
		this.floorLevel = level;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public int getFloorId() {
		return floorId;
	}

	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}

	public int getHomeId() {
		return homeId;
	}

	public void setHomeId(int homeId) {
		this.homeId = homeId;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}
}

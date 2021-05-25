package com.techelevator.model;

import java.util.List;

public class Room {
	
	private int roomId;
	private int floorId;
	private String roomName;
	private int roomLength;
	private int roomWidth;
	private String flooringType;
	private int roomCost;
	private List<Item> items;
	
	public Room (int roomId, int floorId, String roomName, int roomLength, int roomWidth, String flooringType,
			int roomCost, List<Item> items) {
		
		this.roomId = roomId;
		this.floorId = floorId;
		this.roomName = roomName;
		this.roomLength = roomLength;
		this.roomWidth = roomWidth;
		this.flooringType = flooringType;
		this.roomCost = roomCost;
		this.items = items;
		
	}
	
	public Room() {
		
	}
	
	public String getRoomName() {
		return roomName;
	}
	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	
	public int getRoomId() {
		return roomId;
	}
	
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
	public String getFlooringType() {
		return flooringType;
	}
	
	public void setFlooringType(String flooringType) {
		this.flooringType = flooringType;
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public int getRoomLength() {
		return roomLength;
	}
	
	public void setRoomLength(int roomLength) {
		this.roomLength = roomLength;
	}
	
	public int getRoomWidth() {
		return roomWidth;
	}
	
	public void setRoomWidth(int roomWidth) {
		this.roomWidth = roomWidth;
	}

	public int getFloorId() {
		return floorId;
	}

	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}

	public int getRoomCost() {
		return roomCost;
	}

	public void setRoomCost(int roomCost) {
		this.roomCost = roomCost;
	}
}

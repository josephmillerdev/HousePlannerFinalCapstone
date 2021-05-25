package com.techelevator.model;

import java.util.List;

public class House {
	


private String name;
private int foundationSize;
private String region;
private int homeId;
private int numberOfFloors;
private int userId;
private List<Floor> floors;

	public House(String name, int foundationSize, String region, int homeId, List<Floor> floors) {
		this.setFloors(floors);
		this.name = name;
		this.foundationSize = foundationSize;
		this.region = region;
		this.homeId = homeId;
	}
	
	public House() {
		
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getFoundationSize() {
		return foundationSize;
	}


	public void setFoundationSize(int foundationSize) {
		this.foundationSize = foundationSize;
	}


	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public List<Floor> getFloors() {
		return floors;
	}


	public void setFloors(List<Floor> floors) {
		this.floors = floors;
	}


	public int getHomeId() {
		return homeId;
	}


	public void setHomeId(int homeId) {
		this.homeId = homeId;
	}

	public int getNumberOfFloors() {
		return numberOfFloors;
	}

	public void setNumberOfFloors(int numOfFloors) {
		this.numberOfFloors = numOfFloors;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}

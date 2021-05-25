package com.techelevator.model;

public class Home {
	
private House house;


	public Home(House house) {
		this.setHouse(house);
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}


}

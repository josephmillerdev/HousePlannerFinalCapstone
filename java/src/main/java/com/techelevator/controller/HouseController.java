package com.techelevator.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.model.House;
import com.techelevator.model.Room;
import com.techelevator.service.HouseService;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class HouseController {
	private HouseService houseService;
	
	public HouseController (HouseService houseService) {
		this.houseService = houseService;		
	}
	
	@PreAuthorize("permitAll")
	@ResponseStatus (HttpStatus.CREATED)
	@RequestMapping(path = "/users/{id}", method = RequestMethod.POST)
	public void createHouse (@RequestBody House house, @PathVariable Long id, Principal user) {
		houseService.createHouse(house, id);		
	}
	
	@PreAuthorize("permitAll")
	@RequestMapping(path = "/users/{id}/home/{homeId}", method = RequestMethod.POST)
	public void addFloor(@PathVariable int homeId) {
		houseService.addFloors(homeId);
	}
	
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "/users/{id}/floors/{floorId}", method = RequestMethod.POST)
	public void addRoom(@RequestBody Room room, @PathVariable int floorId) {
		houseService.addRooms(room, floorId);
	}
	
	@PreAuthorize("permitAll")
	@RequestMapping(path = "/users/{userId}", method = RequestMethod.GET)
	public List<House> returnHouses(@PathVariable Long userId){
		return houseService.getHouses(userId);
	}

	@PreAuthorize("permitAll")
	@RequestMapping(path = "/floors/{floorId}", method = RequestMethod.DELETE)
	public void deleteThisFloor(@PathVariable int floorId) {
		houseService.deleteFloor(floorId);
	}
	
	@PreAuthorize("permitAll")
	@RequestMapping(path = "/room/{roomId}", method = RequestMethod.DELETE)
	public void deleteThisRoom(@PathVariable int roomId) {
		houseService.deleteRoom(roomId);
	}
	
	@PreAuthorize("permitAll")
	@RequestMapping(path = "/users/{userId}/home/{homeId}", method = RequestMethod.DELETE)
	public void deleteHouse(@PathVariable int homeId) {
		houseService.deleteHouse(homeId);
	}
	
	@PreAuthorize("permitAll")
	@RequestMapping(path = "/home/{homeId}", method = RequestMethod.GET)
	public House returnHouse(@PathVariable int homeId) {
		return houseService.getHouse(homeId);
	}
}


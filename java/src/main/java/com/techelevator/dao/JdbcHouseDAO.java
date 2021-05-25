package com.techelevator.dao;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Floor;
import com.techelevator.model.Home;
import com.techelevator.model.House;
import com.techelevator.model.Item;

import com.techelevator.model.Room;


@Component
public class JdbcHouseDAO implements HouseDAO {
	
	private static final int DEFAULT_SINGLE_FLOOR = 1;
	private static final int DEFAULT_NUMBER_ROOMS = 0;
	private JdbcTemplate jdbcTemplate;
	
	public JdbcHouseDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public House getHouse(int homeId) {
		House result = new House();
		List<Floor> dbFloors = new ArrayList<Floor>();		
		
		String floorSql = "SELECT * FROM floors WHERE home_id = ?";
		SqlRowSet floorRow = jdbcTemplate.queryForRowSet(floorSql, homeId);
		while(floorRow.next()) {
			dbFloors.add(mapRowToFloor(floorRow));
		}
		//room query and item query
		String roomSql = "SELECT * FROM room WHERE floor_id = ?";
		String itemSql = "SELECT * FROM item WHERE room_id = ?";
		//loop through each floor and pass a list of rooms to it - ideally with the items in the rooms 
		for(Floor floor : dbFloors) {
			SqlRowSet roomSet = jdbcTemplate.queryForRowSet(roomSql, floor.getFloorId());
			
			//this empty list will be used to gather the rooms 
			List<Room> dbRooms = new ArrayList<Room>();
			
			while(roomSet.next()) {
				Room curRoom = new Room();
				curRoom = mapRowToRoom(roomSet);
				
				SqlRowSet itemSet = jdbcTemplate.queryForRowSet(itemSql, curRoom.getRoomId());
				List<Item> itemList = new ArrayList();
				while(itemSet.next()) {
					itemList.add(mapRowToItem(itemSet));
				}
				//dbRooms.add(mapRowToRoom(roomSet));
				curRoom.setItems(itemList);
				dbRooms.add(curRoom);
			}			
			floor.setRooms(dbRooms);
//			dbFloors.add(floor);			
		}		
		
		String sql = "SELECT * FROM house WHERE home_id = ?";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, homeId);
		while(rowSet.next()) {
			result = mapRowToHouseList(rowSet);
		}
		
		result.setFloors(dbFloors);
		return result;
	}
	
	@Override
	public List<House> getHouses(Long userId) {
		List<House> results = new ArrayList<House>(); 
		List<House> finalResults = new ArrayList<House>();
		String sql = "SELECT * FROM house WHERE user_id = ?";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
		
		while(rowSet.next()) {
			results.add(mapRowToHouseList(rowSet));
		}
		
		for(House house : results) {
			finalResults.add(getHouse(house.getHomeId()));
		}
		
		return finalResults;
	}

	@Override
	public Home getHomeId() {
		// TODO Auto-generated method stub
		return null;
	}

	// look into this logic, it should always return the correct floor level based on this logic right?
	@Override
	public void addFloors(int homeId) {
		String sql = "UPDATE house SET number_of_floors = number_of_floors + 1 WHERE home_id = ? RETURNING number_of_floors";
		
		int floorLevel = jdbcTemplate.queryForObject(sql, int.class, homeId);
		
		String floorsSql = "INSERT INTO floors (home_id, floor_level, number_of_rooms) VALUES (?, ?, ?)";
		
		jdbcTemplate.update(floorsSql, homeId, floorLevel, DEFAULT_NUMBER_ROOMS);
	}

	
	
	
	
	
	@Override
	public void addRooms(Room room, int floorId) {
				
		String sql = "UPDATE floors SET number_of_rooms = (number_of_rooms + 1) WHERE floor_id = ?";
		
		jdbcTemplate.update(sql, floorId);
		//int numOfRooms = jdbcTemplate.queryForObject(sql, int.class, floorId);
		
		String roomsSql = "INSERT INTO room (floor_id, flooring_type, room_length, room_width, room_name, room_cost) VALUES (?, ?, ?, ?, ?, ?) RETURNING room_id";
		
		int roomId = jdbcTemplate.queryForObject(roomsSql, int.class, floorId, room.getFlooringType(), room.getRoomLength(), room.getRoomWidth(), room.getRoomName(), room.getRoomCost());
		
		String itemSql = "INSERT INTO item (room_id, item_name, item_price) VALUES (?, ?, ?)";
		
		for(Item item : room.getItems()) {
			jdbcTemplate.update(itemSql, roomId, item.getItemName(), item.getItemPrice());
		}
		
	}

	

	@Override
	public void createHouse(House house, Long id) {

		String sql = "INSERT INTO house (user_id, foundation_size, region, house_name, number_of_floors) VALUES (?, ?, ?, ?, ?) RETURNING home_id";
		
		int homeId = jdbcTemplate.queryForObject(sql, int.class, id, house.getFoundationSize(), house.getRegion(), house.getName(), house.getNumberOfFloors());
		
		String sqlFloors = "INSERT INTO floors (home_id, floor_level, number_of_rooms) VALUES (?, ?, ?)";
		
		for(int i = 1; i <= house.getNumberOfFloors(); i ++) {
			jdbcTemplate.update(sqlFloors, homeId, DEFAULT_SINGLE_FLOOR, DEFAULT_NUMBER_ROOMS);
		}
				
		
	}

	private House mapRowToHouseList(SqlRowSet rowSet) {
		House house = new House();
		
		house.setName(rowSet.getString("house_name"));
		house.setFoundationSize(rowSet.getInt("foundation_size"));
		house.setRegion(rowSet.getString("region"));
		house.setHomeId(rowSet.getInt("home_id"));
		house.setNumberOfFloors(rowSet.getInt("number_of_floors"));
		house.setUserId(rowSet.getInt("user_id"));
		
		return house;
	}
	
	private Floor mapRowToFloor(SqlRowSet rowSet) {
		Floor floor = new Floor();
		
		floor.setFloorId(rowSet.getInt("floor_id"));
		floor.setHomeId(rowSet.getInt("home_id"));
		floor.setLevel(rowSet.getInt("floor_level"));
		floor.setNumberOfRooms(rowSet.getInt("number_of_rooms"));
		
		return floor;
		
	}

	// beefed this up, now updates number of floors as well
	@Override
	public void deleteFloor(int floorId) {
		String sql = "DELETE FROM floors WHERE floor_id = ? RETURNING home_id";
		int homeId = jdbcTemplate.queryForObject(sql, int.class, floorId); 
		
		//update number_of_floors in house table
		String houseSql = "UPDATE house SET number_of_floors = number_of_floors - 1 WHERE home_id = ?"; 
		jdbcTemplate.update(houseSql, homeId);
		
	}

	@Override
	public void deleteRoom(int roomId) {
		String sql = "DELETE FROM room WHERE room_id = ?";
		jdbcTemplate.update(sql, roomId);
	}

	@Override
	public void deleteHouse(int homeId) {
		String sql = "DELETE FROM house WHERE home_id = ?";
		jdbcTemplate.update(sql, homeId);
		
	}
	
	public Room mapRowToRoom(SqlRowSet rowSet) {
		Room room = new Room();
		room.setRoomId(rowSet.getInt("room_id"));
		room.setFloorId(rowSet.getInt("floor_id"));
		room.setFlooringType(rowSet.getString("flooring_type"));
		room.setRoomLength(rowSet.getInt("room_length"));
		room.setRoomWidth(rowSet.getInt("room_width"));
		room.setRoomName(rowSet.getString("room_name"));
		room.setRoomCost(rowSet.getInt("room_cost"));
		
		return room;
		
	}
	
	public Item mapRowToItem(SqlRowSet rowSet) {
		Item item = new Item();
		item.setItemId(rowSet.getInt("item_id"));
		item.setItemName(rowSet.getString("item_name"));
		item.setItemPrice(rowSet.getInt("item_price"));
		item.setRoomId(rowSet.getInt("room_id"));
		
		return item;
	}
	
	public List<Room> mapRowToRoomList(SqlRowSet rowSet) {
		List<Room> room = new ArrayList<Room>();
		while (rowSet.next()) {
			room.add(mapRowToRoom(rowSet));
		}
		return room;
		
	}

	

}

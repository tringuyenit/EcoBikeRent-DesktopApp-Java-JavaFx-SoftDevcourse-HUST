package se.project.model.station;

import javafx.scene.image.Image;

public class Station {

	private String name;
	private String address;
	private int available;
	private int rent;
	private int id;
	private String status;
	private Image store;

	private String availability;
	private String emptySlot; // yes - no

	public String getEmptySlot() {
		return emptySlot;
	}

	public void setEmptySlot(String emptySlot) {
		this.emptySlot = emptySlot;
	}


	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public void setImage(Image store) {
		this.store = store;
	}

	public Image getImage() {
		return store;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setId(Integer valueOf) {
		this.id = valueOf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}



}

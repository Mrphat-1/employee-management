package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Shift {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String employeeName;
	private String date;
	private String startTime;
	private String endTime;
	private String note;
	private String username;
	private String status = "pending";

	public Shift() {}

	public Long getId() { return id; }

	public String getEmployeeName() { return employeeName; }
	public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

	public String getDate() { return date; }
	public void setDate(String date) {this.date =date; }

	public String getStartTime() { return startTime; }
	public void setStartTime(String startTime) {this.startTime = startTime; }

	public String getEndTime() { return endTime; }
	public void setEndTime(String endTime) {this.endTime = endTime; }

	public String getNote() {return note; }
	public void setNote(String note) { this.note = note; }

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username;}

	public String getStatus() { return status; }
	public void setStatus(String status) {this.status = status; }
}


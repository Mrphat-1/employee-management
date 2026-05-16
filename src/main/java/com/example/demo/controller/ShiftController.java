package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Shift;
import com.example.demo.repository.ShiftRepository;

@RestController
@RequestMapping("/shifts")
@CrossOrigin
public class ShiftController {

	@Autowired
	private ShiftRepository repository;

	@GetMapping
	public List<Shift> getShift(@RequestParam String username) {

		if ("admin".equals(username)) {
			return repository.findAll();
		}

		return repository.findByUsername(username);
	}
	@PostMapping
	public Shift create(@RequestBody Shift shift) {
		if (shift.getUsername() == null || shift.getUsername().isEmpty()) {
			shift.setUsername(shift.getEmployeeName());
		}

		if (shift.getEmployeeName() == null || shift.getEmployeeName().isEmpty()) {
			shift.setEmployeeName(shift.getUsername());
		}

		if(shift.getStatus() == null || shift.getStatus().isEmpty()) {
			shift.setStatus("pending");
		}

		return repository.save(shift);
	}

	@PutMapping("/{id}")
	public Shift update(@PathVariable Long id, @RequestBody Shift newShift) {
		Shift shift = repository.findById(id).orElseThrow(() -> new RuntimeException("Shift not found"));

		shift.setEmployeeName(newShift.getEmployeeName());
		shift.setDate(newShift.getDate());
		shift.setStartTime(newShift.getStartTime());
		shift.setEndTime(newShift.getEndTime());
		shift.setNote(newShift.getNote());

		if (newShift.getUsername() != null && !newShift.getUsername().isEmpty()) {
			shift.setUsername(newShift.getUsername());
		}

		if (shift.getUsername() == null || shift.getUsername().isEmpty()) {
			shift.setUsername(shift.getEmployeeName());
		}

		shift.setStatus(newShift.getStatus());

		return repository.save(shift);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
}

package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Shift;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
	List<Shift> findByUsername(String username);

}

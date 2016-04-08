package com.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@SpringBootApplication
public class CpaReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CpaReservationServiceApplication.class, args);
	}
}

@RestResource
interface ReservationRepository extends JpaRepository<Reservation, Long>{
	
}

@Entity
class Reservation{
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Reservation(String name) {
		super();
		this.name = name;
	}
	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
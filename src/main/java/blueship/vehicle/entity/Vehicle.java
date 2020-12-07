package blueship.vehicle.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * The persistent class for the Vehicles database table.
 * 
 */
@Data
@Entity
@Table(name = "Vehicles")
public class Vehicle implements Serializable {

	private static final long serialVersionUID = 8190452425191354393L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicle_id")
	private Integer id;

	@Column(name = "engine_number")
	private String engineNumber;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "registration_date")
	private LocalDate registrationDate;
}
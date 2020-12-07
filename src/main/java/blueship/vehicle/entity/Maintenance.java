package blueship.vehicle.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * The persistent class for the Maintenance database table.
 * 
 */
@Data
@Entity
@Table(name = "Maintenance")
public class Maintenance implements Serializable {

	private static final long serialVersionUID = 8110031340029569069L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "vehicle_id")
	private Integer vehicleId;

	@Column(name = "price")
	private Float price;

	@Column(name = "booking_date")
	private LocalDate bookingDate;

	@Column(name = "note")
	private String note;
}
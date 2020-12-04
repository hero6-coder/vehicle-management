package blueship.vehicle.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * The persistent class for the WATCH_LIST database table.
 * 
 */
@Data
@Entity
@Table(name = "Holiday")
public class Holiday implements Serializable {

	private static final long serialVersionUID = -3160466871474676470L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "holiday_name")
	private String holidayName;

	@Column(name = "active")
	private Boolean active;

	@Column(name = "holiday_date")
	private LocalDate holidayDate;
}
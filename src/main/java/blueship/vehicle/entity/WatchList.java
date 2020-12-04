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
//@Table(name = "watch_list")
@NamedQuery(name = "WatchList.findAll", query = "SELECT wl FROM WatchList wl")
public class WatchList implements Serializable {

	private static final long serialVersionUID = -8455594677661418319L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "ticker")
	private String ticker;

	@Column(name = "active")
	private Boolean active;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;
}
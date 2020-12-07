package blueship.vehicle.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
/**
 * The persistent class for the Users database table.
 * 
 */
@Data
@Entity
@Table(name = "Users")
public class User implements Serializable {

	private static final long serialVersionUID = 8373095957525770894L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "status")
	private Integer status;

	@Column(name = "created_at")
	private LocalDate createdDate;
}
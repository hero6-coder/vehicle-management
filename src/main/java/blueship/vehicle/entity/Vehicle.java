package blueship.vehicle.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * The persistent class for the Vehicles database table.
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

  @Column(name = "engine_number", unique = true)
  private String engineNumber;

  @Column(name = "registration_date")
  @CreationTimestamp
  private LocalDate registrationDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @OneToMany(mappedBy = "vehicle")
  private Set<Maintenance> maintenances = new HashSet<Maintenance>();
}
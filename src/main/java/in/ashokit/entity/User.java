package in.ashokit.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "USER_MASTER")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String fname;
	private String lname;
	private String email;
	private Long phno;
	private LocalDate dob;
	private Integer countryId;
	private Integer stateId;
	private Integer cityId;
	private String userPwd;
	private String accStatus;

}

package in.ashokit.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.User;

public interface UserRepository extends JpaRepository<User, Serializable> {
	public User findByEmail(String email);
	
	public User findByEmailAndUserPwd (String email, String pwd);

}

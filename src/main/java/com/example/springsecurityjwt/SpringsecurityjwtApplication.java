package com.example.springsecurityjwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.springsecurityjwt.Entities.Role;
import com.example.springsecurityjwt.Entities.User;
import com.example.springsecurityjwt.Repositories.UserRepository;

@SpringBootApplication
public class SpringsecurityjwtApplication  {
	@Autowired
	private UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(SpringsecurityjwtApplication.class, args);
	}

	// public void run(String... args){
	// 	User adminAccount=userRepository.findByRole(Role.ADMIN);
	// 	if(null==adminAccount){
	// 		User user=new User();
	// 		user.setEmail("admin@gmail.com");
	// 		user.setfirstname("admin");
	// 		user.setlastname("123");
	// 		user.setPassword(new BCryptPasswordEncoder().encode("admin"));
	// 		user.setRole(Role.ADMIN);
	// 		userRepository.save(user);
	// 	}
	// }
}

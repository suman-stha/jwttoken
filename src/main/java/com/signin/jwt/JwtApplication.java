package com.signin.jwt;

import com.signin.jwt.entities.Role;
import com.signin.jwt.entities.User;
import com.signin.jwt.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKey;

@SpringBootApplication
public class JwtApplication  implements CommandLineRunner {
	@Autowired
private UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
		SecretKey key = Jwts.SIG.HS256.key().build();
		String base64Key = Encoders.BASE64.encode(key.getEncoded());
		System.out.println(base64Key);

		System.out.println("Hello from develop branch..");
		System.out.println("Hello comrades..");
		System.out.println("Try again..");
		System.out.println("Success!");
		System.out.println("Finally!");
	}

	@Override
	public void run(String... args) throws Exception {
		User adminAccount=userRepository.findByRole(Role.ADMIN);
		if (null==adminAccount){
			User user =new User();
			user.setEmail("admin@gmail.com");
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"))	;

			userRepository.save(user);
		}

	}
}

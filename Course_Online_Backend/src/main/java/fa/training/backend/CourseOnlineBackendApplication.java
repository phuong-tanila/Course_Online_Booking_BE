package fa.training.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CourseOnlineBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseOnlineBackendApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner insertSampleUser(UsersRepository repository) {
//		return args -> {
//			repository.save(new User("user1", "123", "88888881", "abc1@gmail.com", "ROLE_ADMIN"));
//			repository.save(new User("user2", "123", "88888882", "abc2@gmail.com", "ROLE_USER"));
//			repository.save(new User("user3", "123", "88888883", "abc3@gmail.com", "ROLE_OTHER"));
//		};
//	}
}

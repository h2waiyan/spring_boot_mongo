package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository repository, MongoTemplate mongoTemplate ) {
		return args -> {
			Address address = new Address(
					"Myanmar",
					"11001",
					"Yangon"
			);
			Student student = new Student(
					"John",
					"Doe",
					"johndoe@email.com",
					Gender.Male,
					address,
					List.of("Maths", "Physics"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);

			repository.findStudentByEmail(student.getEmail())
					.ifPresentOrElse(s -> {
						System.out.println(student + " already exists");
					}, () -> {
						System.out.println("Inserting student " + student);
						repository.insert(student);
					});

//			Query query = new Query();
//			query.addCriteria(Criteria.where("email").is("johndoe@email.com"));
//
//			List<Student> students = mongoTemplate.find(query, Student.class);
//
//			if (students.size() > 1) {
//				throw new IllegalStateException("Found many students with email " + student.getEmail());
//			}
//
//			if (students.isEmpty()) {
//				System.out.println("Inserting student " + student);
//				repository.insert(student);
//			} else {
//				System.out.println(student + " already exists");
//			}

		};
	}
}

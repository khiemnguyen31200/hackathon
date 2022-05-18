package vn.techmaster.job_hunt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vn.techmaster.job_hunt.model.City;
import vn.techmaster.job_hunt.model.Employer;
import vn.techmaster.job_hunt.model.Job;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.UUID;

@SpringBootApplication
public class JobHuntApplication implements CommandLineRunner  {
	@Autowired EntityManager em;
	public static void main(String[] args) {
		SpringApplication.run(JobHuntApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
			var employer1 = Employer.builder().id(UUID.randomUUID().toString()).name("FPT").website("https://fpt.com.vn").email("vmcuong2192@gmail.com").logo_path("fpt.png").build();
			var employer2 = Employer.builder().id(UUID.randomUUID().toString()).name("CMC").website("https://cmc.com.vn").email("vmcuong2192@gmail.com").logo_path("cmc.png").build();
			var employer3 = Employer.builder().id(UUID.randomUUID().toString()).name("AMAZON").website("https://amazon.com").email("vmcuong2192@gmail.com").logo_path("amazon.png").build();
			var employer4 = Employer.builder().id(UUID.randomUUID().toString()).name("GOOGLE").website("https://google.com").email("vmcuong2192@gmail.com").logo_path("google.png").build();
			var job1 = Job.builder().id(UUID.randomUUID().toString()).title("Java1").city(City.HaNoi).employer(employer1).build();
			var job2 = Job.builder().id(UUID.randomUUID().toString()).title("Java").city(City.HaNoi).employer(employer3).build();
			var job3 = Job.builder().id(UUID.randomUUID().toString()).title("Java").city(City.HaNoi).employer(employer3).build();
			em.persist(employer1);
			em.persist(employer2);
			em.persist(employer3);
			em.persist(employer4);
			em.persist(job1);
			em.persist(job2);
			em.persist(job3);
			System.out.println(job1.getId());
			em.flush();
	}
}

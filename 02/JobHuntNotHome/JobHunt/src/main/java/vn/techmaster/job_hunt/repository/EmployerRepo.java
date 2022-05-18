package vn.techmaster.job_hunt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.job_hunt.model.Employer;

import java.util.UUID;

@Repository
public interface EmployerRepo extends JpaRepository<Employer, String> {

}

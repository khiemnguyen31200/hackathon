package vn.techmaster.job_hunt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.techmaster.job_hunt.model.Job;

public interface JobRepo extends JpaRepository<Job,String> {
}

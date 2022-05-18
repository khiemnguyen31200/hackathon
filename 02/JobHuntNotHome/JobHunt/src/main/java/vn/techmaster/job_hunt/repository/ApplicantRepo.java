package vn.techmaster.job_hunt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.techmaster.job_hunt.model.Applicant;

public interface ApplicantRepo extends JpaRepository<Applicant, String> {
}

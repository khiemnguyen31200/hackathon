package vn.techmaster.job_hunt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.techmaster.job_hunt.model.Applicant;
import vn.techmaster.job_hunt.model.Job;
import vn.techmaster.job_hunt.repository.ApplicantRepo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicantService {
    @Autowired
    private ApplicantRepo applicantRepo;
    public Map<Job, Long> countApplicantTotal() {

        return applicantRepo.findAll()
                .stream()
                .collect(Collectors.groupingBy(Applicant::getJob,Collectors.counting()));
    }


    public Collection<Applicant> findByJobId(String id) {
        return applicantRepo.findAll().stream()
                .filter(u -> u.getJob().getId().equals(id)).toList();
    }
}

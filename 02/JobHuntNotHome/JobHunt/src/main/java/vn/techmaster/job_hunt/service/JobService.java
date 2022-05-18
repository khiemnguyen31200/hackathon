package vn.techmaster.job_hunt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.techmaster.job_hunt.model.Applicant;
import vn.techmaster.job_hunt.model.Job;
import vn.techmaster.job_hunt.repository.ApplicantRepo;
import vn.techmaster.job_hunt.repository.JobRepo;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;


@Service
public class JobService {
    @Autowired private JobRepo jobRepo;
    @Autowired private ApplicantRepo applicantRepo;

    public Collection<Job> findByEmpId(String id) {
        return jobRepo.findAll().stream().filter(u -> u.getEmployer().getId().equals(id)).toList();
    }
    public Collection<Applicant>  findByJobId(String id) {
        Optional<Job> applicant = jobRepo.findById(id);
        return applicantRepo.findAll().stream()
                .filter(u -> u.getJob().getId().equals(id)).toList();
    }

    public List<Job> SearchByKeyword(String search, String city) {
        if(search.equals("")&&city.equals("----City----")){
            return new ArrayList<>(jobRepo.findAll());
        }
        else if(search.equals("")){
            return jobRepo.findAll().stream().filter(job -> job.SearchByCity(city)).collect(Collectors.toList());
        }else if(city.equals("----City----")){
            return jobRepo.findAll().stream().filter(job -> job.SearchByOnlyKeyword(search)).collect(Collectors.toList());
        }
        return jobRepo.findAll().stream().filter(job -> job.SearchByKeyword(search,city)).collect(Collectors.toList());
    }
}



package vn.techmasterr.jobhunt.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


import org.springframework.stereotype.Repository;

import vn.techmasterr.jobhunt.model.Job;

@Repository
public class JobRepository {
    private ConcurrentHashMap<String, Job> jobs = new ConcurrentHashMap<>();

    public List<Job> getAll(String empid) {
        return jobs.values().stream().filter(u -> u.getEmpId().contains(empid)).toList();
      }

      public List<Job> getAllfotApplicant() {
        return jobs.values().stream().toList();
      }
    
      public Optional<Job> get(String id) {
        return jobs.values().stream().filter(u -> u.getId().contains(id)).findFirst();
      }

      public Job add(Job job) {
        String id = UUID.randomUUID().toString();
        job.setId(id);
        jobs.put(id,job);
        return job;
      }

      public void update(Job job) {
        get(job.getId()).ifPresent(existemployee -> {
          existemployee.setTitle(job.getTitle());
          existemployee.setCity(job.getCity());
          existemployee.setCompany(job.getCompany());
          existemployee.setJobDescription(job.getJobDescription());
        });
      }

      public void deleteByID(String id) {
        get(id).ifPresent(existemployer -> jobs.remove(id));
      }

      public void delete(Job employer) {
        deleteByID(employer.getId());
      }

}

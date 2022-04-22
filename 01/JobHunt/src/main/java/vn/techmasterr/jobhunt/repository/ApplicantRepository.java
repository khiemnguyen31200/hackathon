package vn.techmasterr.jobhunt.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import vn.techmasterr.jobhunt.model.Applicant;


@Repository
public class ApplicantRepository {
    private ConcurrentHashMap<String, Applicant> applicants = new ConcurrentHashMap<>();

    public List<Applicant> getAll(String jobid) {
        return applicants.values().stream().filter(u -> u.getJobId().contains(jobid)).toList();
      }
    
      public Optional<Applicant> get(String id) {
        return applicants.values().stream().filter(u -> u.getId().contains(id)).findFirst();
      }
    
      public Applicant add(Applicant applicant) {
        String id = UUID.randomUUID().toString();
        applicant.setId(id);
        applicants.put(id,applicant);
        return applicant;
      }

      public void update(Applicant applicant) {
        get(applicant.getId()).ifPresent(exist-> {
          exist.setFullName(applicant.getFullName());
          exist.setEmail(applicant.getEmail());
          exist.setPhoneNumber(applicant.getPhoneNumber());
          exist.setAppliedJob(applicant.getAppliedJob());
          exist.setTalentDescription(applicant.getTalentDescription());;
        });
      }

      public void deleteByID(String id) {
        get(id).ifPresent(existemployer -> applicants.remove(id));
      }

      public void delete(Applicant employer) {
        deleteByID(employer.getId());
      }

}

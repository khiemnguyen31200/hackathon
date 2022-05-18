package vn.techmaster.job_hunt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.techmaster.job_hunt.model.Employer;
import vn.techmaster.job_hunt.repository.EmployerRepo;

import java.util.Optional;
@Service
public class EmployerService {
    @Autowired
    private EmployerRepo employerRepo;
    public void updateLogo(String id, String logoFileName) {

        Optional<Employer> employerPut = employerRepo.findById(id);
        if(employerPut.isPresent()) {
            employerPut.get().setLogo_path(logoFileName);
            employerRepo.save(employerPut.get());
        }
    }
}

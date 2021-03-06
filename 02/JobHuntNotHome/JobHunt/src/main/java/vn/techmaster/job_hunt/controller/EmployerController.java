package vn.techmaster.job_hunt.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import vn.techmaster.job_hunt.model.Employer;
import vn.techmaster.job_hunt.repository.EmployerRepo;
import vn.techmaster.job_hunt.repository.JobRepo;
import vn.techmaster.job_hunt.request.EmployerRequest;
import vn.techmaster.job_hunt.service.EmployerService;
import vn.techmaster.job_hunt.service.JobService;
import vn.techmaster.job_hunt.service.StorageService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@Controller
@RequestMapping("/employer")
public class EmployerController {
    @Autowired
    private EmployerRepo employerRepo;
    @Autowired
    private StorageService storageService;
    @Autowired
    private JobService jobService;

    @Autowired
    private EmployerService employerService;

    @GetMapping
    public String listAllEmployers(Model model) {
        model.addAttribute("employers", employerRepo.findAll());
        return "employers";
    }

    @GetMapping(value = "/{id}")
    public String showEmployerDetailByID(Model model, @PathVariable String id) {
        model.addAttribute("employer", employerRepo.findById(id).get());
        model.addAttribute("jobs", jobService.findByEmpId(id));
        return "employer";
    }

    @GetMapping(value = "/add")
    public String addEmployerForm(Model model) {
        model.addAttribute("employer", new EmployerRequest("", "", "", "", "", null));
        return "employer_add";
    }

    @PostMapping(value = "/add", consumes = {"multipart/form-data"})
    public String addEmployer(@Valid @ModelAttribute("employer") EmployerRequest employerRequest,
                              BindingResult result,
                              Model model) {
        if (employerRequest.logo().getOriginalFilename().isEmpty()) {
            result.addError(new FieldError("employer", "logo", "Logo is required"));
        }

        // N???? c?? l???i th?? tr??? v??? tr??nh duy???t
        if (result.hasErrors()) {
            return "employer_add";
        }

        // Th??m v??o c?? s??? d??? li???u
        Employer emp = employerRepo.save(Employer.builder()
                .id(UUID.randomUUID().toString())
                .name(employerRequest.name())
                .website(employerRequest.website())
                .email(employerRequest.email())
                .build());

        // L??u logo v??o ??? c???ng
        try {
            String logoFileName = storageService.saveFile(employerRequest.logo(), emp.getId());
            employerService.updateLogo(emp.getId(), logoFileName);
        } catch (IOException e) {
            // N???u l??u file b??? l???i th?? h??y xo?? b???n ghi Employer
            employerRepo.deleteById(emp.getId());
            e.printStackTrace();
            return "employer_add";
        }
        return "redirect:/employer";
    }



    @GetMapping(value = "/edit/{id}")
    public String editEmpId(Model model, @PathVariable("id") String id) {
        Optional<Employer> employer = employerRepo.findById(id);
        if (employer.isPresent()) {
            Employer currentEmp = employer.get();
            model.addAttribute("employerReq", new EmployerRequest(currentEmp.getId(),
                    currentEmp.getName(),
                    currentEmp.getWebsite(),
                    currentEmp.getEmail(),
                    currentEmp.getLogo_path(),
                    null));
            model.addAttribute("employer", currentEmp);
        }
        return "employer_edit";
    }


    @PostMapping(value = "/edit", consumes = {"multipart/form-data"})
    public String editEmployer(@Valid @ModelAttribute("employerReq") EmployerRequest employerRequest,
                               BindingResult result,
                               Model model) {

        // N???? c?? l???i th?? tr??? v??? tr??nh duy???t
        if (result.hasErrors()) {
            return "employer_edit";
        }

        String logoFileName = null;
        // C???p nh???t logo v??o ??? c???ng
        if (!employerRequest.logo().getOriginalFilename().isEmpty()) {
            try {
                logoFileName = storageService.saveFile(employerRequest.logo(), employerRequest.id());
                employerService.updateLogo(employerRequest.id(), logoFileName);
            } catch (IOException e) {
                // N???u l??u file b??? l???i th?? h??y xo?? b???n ghi Employer
                employerRepo.deleteById(employerRequest.id());
                e.printStackTrace();
                return "employer_edit";
            }
        }
        // C???p nh???t l???i Employer
        employerRepo.save(Employer.builder()
                .id(employerRequest.id())
                .name(employerRequest.name())
                .website(employerRequest.website())
                .email(employerRequest.email())
                .logo_path(logoFileName == null ? employerRequest.logo_path() : logoFileName)
                .build());

        return "redirect:/employer";
    }

    //
    @GetMapping(value = "/delete/{id}")
    public String deleteEmployerByID(@PathVariable String id) {
        employerRepo.deleteById(id);
        storageService.deleteFile(employerRepo.findById(id).get().getLogo_path());
        return "redirect:/employer";
    }
}
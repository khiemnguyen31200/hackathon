package vn.techmaster.job_hunt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.techmaster.job_hunt.model.Applicant;
import vn.techmaster.job_hunt.model.Employer;
import vn.techmaster.job_hunt.model.Job;
import vn.techmaster.job_hunt.repository.ApplicantRepo;
import vn.techmaster.job_hunt.repository.EmployerRepo;
import vn.techmaster.job_hunt.repository.JobRepo;
import vn.techmaster.job_hunt.request.ApplicantRequest;
import vn.techmaster.job_hunt.service.ApplicantService;
import vn.techmaster.job_hunt.service.EmployerService;
import vn.techmaster.job_hunt.service.MailService;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/applicant")
public class ApplicantController {
    @Autowired
    private JobRepo jobRepo;
    @Autowired
    private EmployerRepo empRepo;
    @Autowired
    private ApplicantRepo applicantRepo;
    @Autowired
    private MailService mailService;




    @GetMapping(value = "/apply/{job_id}")
    public String applyForm(Model model, @PathVariable String job_id) {
        model.addAttribute("applicantReq", new ApplicantRequest(null, job_id, null, null, null, null));
        return "applicant_apply";
    }

    @PostMapping(value = "/add")
    public String addApplicant(@Valid @ModelAttribute("applicantReq") ApplicantRequest applicantRequest,
                               BindingResult result,
                               Model model) {

        // Nêú có lỗi thì trả về trình duyệt
        if (result.hasErrors()) {
            return "applicant_apply";
        }

        Optional<Job> jobNew = jobRepo.findById(applicantRequest.job_id());
        // Thêm vào cơ sở dữ liệu
        applicantRepo.save(Applicant.builder()
                .id(UUID.randomUUID().toString())
                .job(jobNew.get())
                .name(applicantRequest.name())
                .email(applicantRequest.email())
                .phone(applicantRequest.phone())
                .skills(applicantRequest.skills())
                .build());
        // send email
//        Job job = jobRepo.findById(applicantRequest.job_id());
        try {
            mailService.sendEmail(String.valueOf(empRepo.findById(jobNew.get().getEmployer().getEmail())), jobNew.get().getTitle());
        } catch (Exception e) {
            return "error_page";
        }

        return "redirect:/job/" + applicantRequest.job_id();
    }

    @GetMapping(value = "/{id}")
    public String editId(Model model, @PathVariable("id") String id) {
        Optional<Applicant> applicantOpt = applicantRepo.findById(id);
        if (applicantOpt.isPresent()) {
            Applicant currentApplicant = applicantOpt.get();
            model.addAttribute("applicantReq", new ApplicantRequest(
                    currentApplicant.getId(),
                    currentApplicant.getJob().getId(),
                    currentApplicant.getName(),
                    currentApplicant.getEmail(),
                    currentApplicant.getPhone(),
                    currentApplicant.getSkills()));
            // model.addAttribute("job", currentJob);
            // model.addAttribute("employer", empRepo.findById(currentJob.getEmp_id()));
        }
        return "applicant_edit";
    }

    @PostMapping(value = "/edit", params = "action=save")
    public String edit(@Valid @ModelAttribute("applicantReq") ApplicantRequest applicantRequest,
                       BindingResult result,
                       Model model) {

        // Nêú có lỗi thì trả về trình duyệt
        if (result.hasErrors()) {
            return "applicant_edit";
        }
        Optional<Job> currentJob = jobRepo.findById(applicantRequest.job_id());
        // Thêm vào cơ sở dữ liệu
        applicantRepo.save(Applicant.builder()
                .email(applicantRequest.email())
                .id(applicantRequest.id())
                .job(currentJob.get())
                .name(applicantRequest.name())
                .phone(applicantRequest.phone())
                .skills(applicantRequest.skills())
                .build());

        // http://localhost:8080/employer/2f3fa6ef-77f1-460a-8fcb-3ac08219bb81
        return "redirect:/job/" + applicantRequest.job_id();
    }

    @PostMapping(value = "/edit", params = "action=delete")
    public String delete(@ModelAttribute("applicant") ApplicantRequest applicantRequest) {
        applicantRepo.deleteById(applicantRequest.id());
        return "redirect:/job/" + applicantRequest.job_id();
    }
}

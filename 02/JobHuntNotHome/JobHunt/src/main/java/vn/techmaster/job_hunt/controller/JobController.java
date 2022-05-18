package vn.techmaster.job_hunt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.techmaster.job_hunt.model.Employer;
import vn.techmaster.job_hunt.model.Job;
import vn.techmaster.job_hunt.repository.EmployerRepo;
import vn.techmaster.job_hunt.repository.JobRepo;
import vn.techmaster.job_hunt.request.JobRequest;
import vn.techmaster.job_hunt.service.ApplicantService;
import vn.techmaster.job_hunt.service.JobService;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(value = "/job")
public class JobController {
    @Autowired private JobRepo jobRepo;
    @Autowired private EmployerRepo empRepo;
    @Autowired private ApplicantService applicantService;
    @Autowired private JobService jobService;



    @GetMapping
    public String listAllJob(Model model) {
        model.addAttribute("jobs", jobRepo.findAll());
        model.addAttribute("employers",empRepo);
        model.addAttribute("totalApplicant", applicantService.countApplicantTotal());
        return "jobhome";
    }

    @GetMapping(value = "/{id}/dashboard")
    public String showJobDetailByEmID(Model model, @PathVariable String id) {
        Optional<Job> job = jobRepo.findById(id);
        Optional<Employer> employer = empRepo.findById(job.get().getEmployer().getId());
        model.addAttribute("job", job.get());
        model.addAttribute("employer",employer.get());
        model.addAttribute("applicants", jobService.findByJobId(id));
        model.addAttribute("jobs", jobService.findByEmpId(job.get().getEmployer().getId()));
        return "jobShowForApplicant";
    }

    @GetMapping(value = "/{id}")
    public String showJobDetailByID(Model model, @PathVariable String id) {
        Optional<Job> job = jobRepo.findById(id);
        Optional<Employer> employer = empRepo.findById(job.get().getEmployer().getId());
        model.addAttribute("job", job.get());
        model.addAttribute("employer", employer.get());
        model.addAttribute("applicants", jobService.findByJobId(id));
        return "job";
    }

    @GetMapping(value = "/add/{emp_id}")
    public String addEmployerForm(Model model, @PathVariable String emp_id) {
        model.addAttribute("job", new JobRequest("", emp_id, "", "", null));
        return "job_add";
    }

    @PostMapping(value = "/add")
    public String addEmployer(@Valid @ModelAttribute("job") JobRequest jobRequest,
                              BindingResult result,
                              Model model) {

        // Nêú có lỗi thì trả về trình duyệt
        if (result.hasErrors()) {
            return "job_add";
        }

        //Lay ra employer
        Optional<Employer> employerNew = empRepo.findById(jobRequest.emp_id());
        // Thêm vào cơ sở dữ liệu
        jobRepo.save(Job.builder()
                .id(UUID.randomUUID().toString())
                .employer(employerNew.get())
                .title(jobRequest.title())
                .description(jobRequest.description())
                .city(jobRequest.city()).build());

        // http://localhost:8080/employer/2f3fa6ef-77f1-460a-8fcb-3ac08219bb81
        return "redirect:/employer/" + jobRequest.emp_id();
    }

    @GetMapping(value = "/edit/{id}")
    public String editJobId(Model model, @PathVariable("id") String id) {
        Optional<Job> job = jobRepo.findById(id);
        if (job.isPresent()) {
            Job currentJob = job.get();
            model.addAttribute("jobReq", new JobRequest(
                    currentJob.getId(),
                    currentJob.getEmployer().getId(),
                    currentJob.getTitle(),
                    currentJob.getDescription(),
                    currentJob.getCity()));
            // model.addAttribute("job", currentJob);
            model.addAttribute("employer", empRepo.findById(currentJob.getEmployer().getId()));
        }
        return "job_edit";
    }

    @PostMapping(value = "/edit")
    public String edit(@Valid @ModelAttribute("jobReq") JobRequest jobRequest,
                       BindingResult result,
                       Model model) {

        // Nêú có lỗi thì trả về trình duyệt
        if (result.hasErrors()) {
            return "job_edit";
        }
        Optional<Employer> employerOld = empRepo.findById(jobRequest.emp_id());
        // Thêm vào cơ sở dữ liệu
        jobRepo.save(Job.builder()
                .id(jobRequest.id())
                .employer(employerOld.get())
                .title(jobRequest.title())
                .description(jobRequest.description())
                .city(jobRequest.city()).build());

        // http://localhost:8080/employer/2f3fa6ef-77f1-460a-8fcb-3ac08219bb81
        return "redirect:/employer/" + jobRequest.emp_id();
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteJobByID(@PathVariable String id) {
        jobRepo.deleteById(id);
        return "redirect:/employer/" + jobRepo.getById(id).getEmployer().getId();
    }

    @PostMapping ("/search")
    public String Search(@ModelAttribute("city") String city, @ModelAttribute("keyword") String keyword, Model model){
        model.addAttribute("jobs",  jobService.SearchByKeyword(keyword, city));
        model.addAttribute("employers",empRepo);
        model.addAttribute("totalApplicant", applicantService.countApplicantTotal());
        return"jobhome";
    }
}

package vn.techmasterr.jobhunt.controller;

import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.techmasterr.jobhunt.repository.EmployerRepository;
import vn.techmasterr.jobhunt.repository.JobRepository;
import vn.techmasterr.jobhunt.model.CityEnum;
import vn.techmasterr.jobhunt.model.Job;


@Controller
@RequestMapping("/job")
public class JobController {
  @Autowired
  JobRepository jobRepo;
  @Autowired
  EmployerRepository empRepo;

  @GetMapping("/list")
  public String listAll(Model model, @RequestParam("empId") String empId) { 
    model.addAttribute("jobs", jobRepo.getAll(empId));
    return "alljob";
  }
  @GetMapping("/lists")
  public String listAllforApplicant(Model model) { 
    model.addAttribute("jobs", jobRepo.getAllfotApplicant());
    return "alljobforapplicant";
  }

  

  @GetMapping(value = "/{id}")
  public String getByID(@PathVariable("id") String id, Model model) {
    Optional<Job> job = jobRepo.get(id);
    if (job.isPresent()) {
      model.addAttribute("job", job.get());
    }
    return "jobdetail";
  }

  @GetMapping("/add")
  public String add(Model model,@RequestParam("empId") String empId) {
    Job job = new Job();
    job.setEmpId(empId);
    model.addAttribute("job", job);
    model.addAttribute("citycode", CityEnum.values());
    return "jobform";
  }

  @PostMapping(value = "/save") // ,consumes = {"multipart/form-data}"}
  public String save(@Valid @ModelAttribute("job") Job job, BindingResult result, Model model) {// Kết quả nếu thành
                                                                                                // công trả về success
    if (result.hasErrors()) {
      model.addAttribute("job", job);// Để hứng lỗi sai
      return "jobform";
    }
    if (job.getId() != "") {
      jobRepo.update(job);
    } else {
      jobRepo.add(job);
    }
    return "redirect:/employer/list";
  }
 
  @GetMapping(value = "/edit/{id}")
  public String editEmployerById(@PathVariable("id") String id, Model model) {
    Optional<Job> job = jobRepo.get(id);
    if (job.isPresent()) {
      model.addAttribute("job", job.get());
      model.addAttribute("companycode", empRepo.getAll());
      model.addAttribute("citycode", CityEnum.values());
    }
    return "jobform";
  }

  @GetMapping(value = "/delete/{id}")
  public String deleteByID(@PathVariable("id") String id) {
    jobRepo.deleteByID(id);
    return "redirect:/employer/list";
  }

}

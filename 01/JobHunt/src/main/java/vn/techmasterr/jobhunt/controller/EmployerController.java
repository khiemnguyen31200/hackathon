package vn.techmasterr.jobhunt.controller;

// import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmasterr.jobhunt.model.Employer;
import vn.techmasterr.jobhunt.repository.EmployerRepository;
// import vn.techmasterr.jobhunt.request.EmployerRequest;
import vn.techmasterr.jobhunt.service.StorageService;

import javax.validation.Valid;


@Controller
@RequestMapping("/employer")
public class EmployerController {
  @Autowired EmployerRepository empRepo;
  @Autowired StorageService storageService;


  @GetMapping("/list")
  public String listAll(Model model) {
    model.addAttribute("employers", empRepo.getAll());
    return "allemployer";
  }
  @GetMapping("/add")
  public String add(Model model) {
    model.addAttribute("employer", new Employer());
    return "employerform";
  }
  @GetMapping(value = "/{id}")
  public String getByID(@PathVariable("id") String id, Model model) {
    Optional<Employer> employer = empRepo.get(id);
    if (employer.isPresent()) {
      model.addAttribute("employer", employer.get());
    }
    return "employerdetail";
  }
  @PostMapping(value = "/save") //,consumes = {"multipart/form-data}"}
  public String save(@Valid @ModelAttribute("employer") Employer employer, BindingResult result, Model model) {//Kết quả nếu thành công trả về success
    if (result.hasErrors()) {
      model.addAttribute("employer", employer);// Để hứng lỗi sai
      return "employerform";
    }
    //Thêm vào CSDL
    // Employer emp= empRepo.add(Employer.builder()
    //         .name(employerRequest.name())
    //         .website(employerRequest.website())
    //         .email(employerRequest.email())
    //         .build());
    //Lưu logo vào ô cứng
  // try{
  //   String fileName = storageService.saveFile(employerRequest.photo(),emp.getId());
  //   empRepo.UpdateLogo(emp.getId(),fileName);
  // }
  // catch (IOException e){
  //  e.printStackTrace();
  // }

   if (employer.getId()!="") {
       empRepo.update(employer);
   } else {
       empRepo.add(employer);
   }
    return "redirect:/employer/list";
  }
  @GetMapping(value = "/edit/{id}")
  public String editEmployerById(@PathVariable("id") String id, Model model) {
    Optional<Employer> employer = empRepo.get(id);
    if (employer.isPresent()) {
      model.addAttribute("employer", employer.get());
    }
    return "employerform";
  }

  @GetMapping(value = "/delete/{id}")
  public String deleteByID(@PathVariable("id") String id) {
    empRepo.deleteByID(id);
    return "redirect:/employer/list";
  }
}

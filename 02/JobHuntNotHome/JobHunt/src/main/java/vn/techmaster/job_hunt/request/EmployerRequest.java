package vn.techmaster.job_hunt.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record EmployerRequest(
        String id,

        @NotBlank(message = "Name cannot null") String name,

        @NotBlank(message = "Web site cannot null") String website,

        @NotBlank(message = "Email cannot null") @Email(message = "Invalid email") String email,
        
        String logo_path,

        MultipartFile logo) {
}
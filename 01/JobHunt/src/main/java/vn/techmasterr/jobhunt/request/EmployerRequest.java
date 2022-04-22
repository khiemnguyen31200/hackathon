package vn.techmasterr.jobhunt.request;

import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.*;


public record EmployerRequest (String id,
                               @NotBlank(message= "Không được để trống")
                               String name,
                               @NotBlank(message= "Không được để trống")
                               String website,
                               @NotBlank(message= "Không được để trống")
                               @Email(message ="Email không đúng định dạng")
                               String email,
                               String address,
                               MultipartFile photo){

}

package vn.techmaster.job_hunt.request;


import vn.techmaster.job_hunt.model.City;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
  private String id;
  private String emp_id;
  private String title;
  private String description;
  private City city;
 */

public record JobRequest(
                String id,

                String emp_id,

                @NotBlank(message = "Title cannot null") String title,

                @NotBlank(message = "Description cannot null") String description,

                @NotNull(message = "City cannot null") City city) {
}
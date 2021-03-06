package vn.techmasterr.jobhunt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Applicant{
  private String id;
  private String jobId;
  private String fullName;
  private String email;
  private String phoneNumber;
  private String appliedJob;
  private String talentDescription;
}

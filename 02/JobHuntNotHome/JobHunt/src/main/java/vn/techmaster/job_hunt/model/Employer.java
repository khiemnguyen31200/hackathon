package vn.techmaster.job_hunt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
@Entity
public class Employer {
  @Id
  private String id;
  private String name;
  private String logo_path;
  private String website;
  private String email;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Job> jobs ;
}

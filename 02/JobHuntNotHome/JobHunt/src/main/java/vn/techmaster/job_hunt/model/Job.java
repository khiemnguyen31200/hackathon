package vn.techmaster.job_hunt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
@Entity
public class Job {
  @Id
  private String id;
//  private String emp_id;
  private String title;
  private String description;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  List<Applicant> applicants;
  @Enumerated
  private City city;
  private LocalDateTime updateTime;
  private LocalDateTime createTime;

  @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
  @JsonIgnore
  private Employer employer;

  public boolean SearchByKeyword(String search, String citySearch) {
    return (title.toLowerCase().contains(search.toLowerCase())
            &&citySearch.contains(city.label));
  }
  public boolean SearchByCity(String citySearch) {
    return citySearch.contains(city.label);
  }
  public boolean SearchByOnlyKeyword(String search) {
    return (title.toLowerCase().contains(search.toLowerCase()));
  }
}

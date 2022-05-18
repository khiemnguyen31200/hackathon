package vn.techmaster.job_hunt.model;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Applicant {
    @Id
    private String id;
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private Job job;
    private String name;
    private String email;
    private String phone;

    @Enumerated
    private Skill skills;
     
}

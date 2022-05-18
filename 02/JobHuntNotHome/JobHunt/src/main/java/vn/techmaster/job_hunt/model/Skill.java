package vn.techmaster.job_hunt.model;

import javax.persistence.Entity;
import javax.persistence.Table;


public enum Skill {
    Java("Java"),
    CSharp("Csharp"),
    AWS("AWS"),
    SQL("SQL");
  
    public final String label;
    private Skill(String label) {
      this.label = label;
    }
}

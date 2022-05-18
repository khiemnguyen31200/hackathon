package vn.techmaster.job_hunt.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.techmaster.job_hunt.model.City;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest{
    private String keyword;
    private City city;
}
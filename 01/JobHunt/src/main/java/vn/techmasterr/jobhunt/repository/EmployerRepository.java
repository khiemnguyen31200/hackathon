package vn.techmasterr.jobhunt.repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import vn.techmasterr.jobhunt.model.Employer;

@Repository
public class EmployerRepository {  

  private ConcurrentHashMap<String, Employer> employers = new ConcurrentHashMap<>();
  public EmployerRepository(){
    employers.put(UUID.randomUUID().toString(),new Employer(UUID.randomUUID().toString(),"CMC"," https://fpt.com.vn","apply@fpt.com","Hà Nội","cmc.png"));
    employers.put(UUID.randomUUID().toString(),new Employer(UUID.randomUUID().toString(),"FPT"," https://cmc.com.vn","apply@cmc.com","Hồ Chí Minh","fpt.png"));
    employers.put(UUID.randomUUID().toString(),new Employer(UUID.randomUUID().toString(),"Amazon"," https://amazon.com","apply@amazon.com","Seattle","amazon.png"));
    employers.put(UUID.randomUUID().toString(),new Employer(UUID.randomUUID().toString(),"Google"," https://google.com","apply@google.com","San Jose","google.png"));
  }

  public List<Employer> getAll() {
    return employers.values().stream().toList();
  }

  public Optional<Employer> get(String id) {
    return employers.values().stream().filter(u -> u.getId().contains(id)).findFirst();
  }

  public Employer add(Employer employer) {
    String id = UUID.randomUUID().toString();
    employer.setId(id);
    employers.put(id,employer);
    return employer;
  }
  //Cập nhật logo của employer
  public void UpdateLogo(String id,String logoPath){
    var emp = employers.get(id);
    emp.setLogoPath(logoPath);
    employers.put(id,emp);
  }


  public void update(Employer employer) {
    get(employer.getId()).ifPresent(existemployee -> {
      existemployee.setName(employer.getName());
      existemployee.setWebsite(employer.getWebsite());
      existemployee.setEmail(employer.getEmail());
    });
  }

  public void deleteByID(String id) {
    get(id).ifPresent(existemployer -> employers.remove(id));
  }

  public void delete(Employer employer) {
    deleteByID(employer.getId());
  }
}

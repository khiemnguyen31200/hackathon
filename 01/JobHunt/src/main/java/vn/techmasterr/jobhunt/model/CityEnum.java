package vn.techmasterr.jobhunt.model;


    public enum CityEnum {
        HANOI("Hà Nội"),
        HAIPHONG("Hải Phòng"),
        DANANG("Đà Nẵng"),
        HUE("Huế"),
        HOCHIMINH("Hồ Chí Minh");
      
        private String value;
       
        CityEnum(String value) {
          this.value = value;
        }
       
        public String getValue() {
          return value;
        }
      }


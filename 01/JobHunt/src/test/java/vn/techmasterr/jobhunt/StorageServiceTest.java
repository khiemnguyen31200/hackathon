package vn.techmasterr.jobhunt;

import org.junit.jupiter.api.Test;
import vn.techmasterr.jobhunt.service.StorageService;
import static org.assertj.core.api.Assertions.*;

public class StorageServiceTest {
    @Test
    public void Test(){
        StorageService s = new StorageService();
        String extendsion = s.getFileExtendsion("abc.jpg");
        assertThat(extendsion).isEqualTo("jpg");

        extendsion=s.getFileExtendsion("abc.png");
        assertThat(extendsion).isEqualTo("png");

    }
}

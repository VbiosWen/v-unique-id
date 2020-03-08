import org.geeksword.Application;
import org.geekswrod.api.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = Application.class)
public class Test {

    @Autowired
    private IdService idService;

    @org.junit.jupiter.api.Test
    public void testGenId(){
        for (int i = 0 ; i< 10000; i++){
            long l = idService.genId();
            System.out.println(l);
        }
    }
}

import org.geeksword.Application;
import org.geekswrod.api.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootTest(classes = Application.class)
public class Test {

    @Autowired
    private IdService idService;

    @org.junit.jupiter.api.Test
    public void testGenId() {
        long start = Instant.now().toEpochMilli();
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            long l = idService.genId();
            System.out.println(l);
            list.add(l);
        }
        System.out.println(list.size());
        Set<Long> hashSet = new HashSet<>();
        for (Long aLong : list) {
            boolean add = hashSet.add(aLong);
            if(!add)
            System.out.println(add + ":" + aLong);
        }
        System.out.println(Instant.now().toEpochMilli() - start);
    }
}

import com.scraping.Main;
import com.scraping.entity.ProductDTO;
import com.scraping.repository.RedisDB;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(classes = Main.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LocalRedisTest {

    @Autowired
    RedisDB redisDB;

    List<String> changes;

    @AfterAll
    void reset() {
        redisDB.clean();
    }

    @Test
    public void testRedis() {
        ProductDTO productDTO = new ProductDTO("celular1002", "celular", "1000", "Americanas");
        redisDB.update(productDTO);
    }

    @Test
    public void testUpdate() {
        ProductDTO productDTO = new ProductDTO("celular1002", "celular", "1005", "Americanas");
        redisDB.update(productDTO);
        ProductDTO result = redisDB.getItem("celular1002");
        assertNotEquals(result, productDTO);
    }
}

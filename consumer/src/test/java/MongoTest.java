import com.scraping.Main;
import com.scraping.entity.ProductDTO;
import com.scraping.repository.MongoDB;
import com.scraping.repository.RedisDB;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = Main.class)
public class MongoTest {

    @Autowired
    MongoDB mongoDB;

    @Test
    public void testRedis() {
        ProductDTO produto = mongoDB.getItem("celular");
        assertNotNull(produto);
    }
}

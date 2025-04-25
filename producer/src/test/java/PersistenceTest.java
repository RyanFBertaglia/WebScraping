import com.scraping.DemoApplication;
import com.scraping.RedisCache.ProductPublisher;
import com.scraping.entities.ProductDTO;
import com.scraping.services.ChocolateAtacadao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = DemoApplication.class)
@TestPropertySource(properties = {"driver.url=http://localhost:4444/wd/hub"})
public class PersistenceTest {

    @Autowired
    ProductPublisher productPublisher;

    @Autowired
    ChocolateAtacadao chocolateAtacadao;

    @Test
    void testPersistence() {
        ProductDTO chocolate = chocolateAtacadao.buscaProduto();
        boolean teste = productPublisher.publishProductEvent(chocolate);
        assertTrue(teste);
    }
}

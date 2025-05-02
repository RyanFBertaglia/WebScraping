import com.scraping.DemoApplication;
import com.scraping.repository.ProductPublisher;
import com.scraping.entities.ProductDTO;
import com.scraping.services.ChocolateAmericanas;
import com.scraping.services.ChocolateAtacadao;
import com.scraping.services.ChocolateSpani;
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

    @Autowired
    ChocolateSpani chocolateSpani;

    @Autowired
    ChocolateAmericanas chocolateAmericanas;

    @Test
    void testPersistence() {

        ProductDTO atacadao = chocolateAtacadao.buscaProduto();
        boolean testeATC = productPublisher.publishProductEvent(atacadao);

        ProductDTO spani = chocolateSpani.buscaProduto();
        boolean testeSPN = productPublisher.publishProductEvent(spani);

        ProductDTO americanas = chocolateAmericanas.buscaProduto();
        boolean testeAMC = productPublisher.publishProductEvent(americanas);

        assertTrue(testeATC);
        assertTrue(testeSPN);
        assertTrue(testeAMC);

    }
}

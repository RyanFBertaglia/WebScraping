import com.scraping.config.MongoDBConnection;
import com.scraping.entity.ProductDTO;
import com.scraping.repository.MongoDB;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {
        MongoDBConnection.class,
        MongoDB.class
})
@TestPropertySource("classpath:application-test.properties")
public class MongoTest {

    @Autowired
    private MongoDB mongoDB;

    @Value("${spring.mongodb.url}")
    private String url;

    @Test
    public void testWrite() {
        ProductDTO productDTO = new ProductDTO("celular1002", "celular", "1000", "Americanas");
        assertDoesNotThrow(() -> {
            mongoDB.update(productDTO);
        });
    }

    @Test
    public void testReadFalse() {
        ProductDTO produto = mongoDB.getItem("celular");
        assertNull(produto);
    }

    @Test
    public void testReadTrue() {
        ProductDTO produto = mongoDB.getItem("celular1002");
        assertNotNull(produto);
    }

    @Test
    public void testReadAll() {
        ArrayList<ProductDTO> produto = mongoDB.getAllProducts();
        assertNotNull(produto);
    }
}

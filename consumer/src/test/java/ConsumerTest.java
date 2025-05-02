import com.scraping.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
@AutoConfigureMockMvc
public class ConsumerTest {

    @Autowired
    private MockMvc mockMvc;

    //Todo: Adicionar driver mongodb para java 

    @Test
    void returnAllProducts() throws Exception {
        this.mockMvc.perform(get("/prices/getAllProducts"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    void returnProductgetSpecific() throws Exception {
        this.mockMvc.perform(get("/getSpecifc/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
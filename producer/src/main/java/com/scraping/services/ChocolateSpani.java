package com.scraping.services;

import com.scraping.entities.ProductDTO;
import com.scraping.exceptions.LoadPageError;
import com.scraping.exceptions.NotFoundItem;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service(ChocolateSpani.BEAN_ID)
public class ChocolateSpani implements BuscaProduto {

    @Autowired
    private ConnectionFactory connectionFactory;

    public static final String BEAN_ID = "BarraChocolateSpani";

    String url = "https://www.spanionline.com.br/produto/13383/chocolate-tablete-lacta-145g-leite";

    @Override
    public ProductDTO buscaProduto() {
        WebDriver driver = connectionFactory.conexao();
        String name = "", price = "";

        try {

            driver.get(url);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement body = driver.findElement(By.tagName("body"));
            WebElement containerPrice = driver.findElement(
                    By.xpath("//span[@data-cy='preco']")
            );
            price = containerPrice.getText();
            name = "Barra de Chocolate Lacta 145g";
        } catch (TimeoutException e) {
            throw new LoadPageError("Error loading page");
        } catch(NoSuchElementException e){
            throw new NotFoundItem();
        }finally {
            driver.quit();
        }
        return new ProductDTO("3", name, price, "Spani");
    }
}

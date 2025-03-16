package com.scraping.demo.services;

import com.scraping.demo.entities.Product;
import com.scraping.demo.exceptions.LoadPageError;
import com.scraping.demo.exceptions.NotFoundItem;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Duration;

@Service(ChocolateSpani.BEAN_ID)
public class ChocolateSpani implements BuscaProduto {

    @Autowired
    private ConectionFactory conectionFactory;

    public static final String BEAN_ID = "BarraChocolateSpani";

    String url = "https://www.spanionline.com.br/produto/13383/chocolate-tablete-lacta-145g-leite";

    @Override
    public Product buscaProduto() {
        WebDriver driver = conectionFactory.conexao();
        String name = "", price = "";
        
        try {
            driver.get(url);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement productPrice = wait.until(d ->
                    d.findElement(By.xpath("//span[@data-cy='preco']")));

            price = productPrice.getText();
            name = "Barra de Chocolate Lacta 145g";

        } catch (TimeoutException e) {
            throw new LoadPageError("Error loading page");
        } catch(NoSuchElementException e){
            throw new NotFoundItem();
        }finally {
            driver.quit();
        }
        return new Product(name, price, "Atacadao");
    }
}

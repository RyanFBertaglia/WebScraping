package com.scraping.services;

import com.scraping.entities.Product;
import com.scraping.exceptions.LoadPageError;
import com.scraping.exceptions.NotFoundItem;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service(ChocolateAtacadao.BEAN_ID)
public class ChocolateAtacadao implements BuscaProduto{

    @Autowired
    private ConectionFactory conectionFactory;

    public static final String BEAN_ID = "BarraChocolateAtacadao";

    String url = "https://www.atacadao.com.br/chocolate-lacta-ao-leite-94507-44721/p";

    @Override
    public Product buscaProduto() {
        WebDriver driver = conectionFactory.conexao();
        String name = "", price = "";

        try {
            driver.get(url);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement body = driver.findElement(By.tagName("body"));
            body.click();

            WebElement containerPrice = driver.findElement(
                    By.xpath("//p[contains(@class, 'text-2xl') and contains(@class, 'font-bold')]")
            );
            price = containerPrice.getText();
            name = "Barra de Chocolate Lacta 145g";
        } catch (TimeoutException e) {
            throw new LoadPageError("Error loading page");
        } catch(NoSuchElementException e){
            System.out.println("Item não encontrado");
            throw new NotFoundItem();
        }finally {
            driver.quit();
        }
        return new Product(name, price, "Atacadao");
    }
}

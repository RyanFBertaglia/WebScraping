package com.scraping.services;

import com.scraping.entities.ProductDTO;
import com.scraping.exceptions.LoadPageError;
import com.scraping.exceptions.NotFoundItem;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service(ChocolateAmericanas.BEAN_ID)
public class ChocolateAmericanas implements BuscaProduto{

    @Autowired
    private ConnectionFactory connectionFactory;

    public static final String BEAN_ID = "BarraChocolateAmericanas";

    String url = "https://www.americanas.com.br/lojas-proximas/33014556000196/5310?c_legionRegion=202235109001&c_macroRegion=SP_INTERIOR&c_mesoRegion=3510&context=lojas_proximas&filter=%7B%22id%22%3A%22variation.sellerID%22%2C%22value%22%3A%2233014556000196%22%2C%22fixed%22%3Atrue%7D&filter=%7B%22id%22%3A%22variation.warehouse%22%2C%22value%22%3A%225310%22%2C%22fixed%22%3Atrue%7D&filter=%7B%22id%22%3A%22wit%22%2C%22value%22%3A%22barra%20de%20chocolate%22%2C%22fixed%22%3Afalse%7D&lat=-22.9406129&lon=-47.111549&sortBy=relevance&source=nanook&page=2&limit=24&offset=24";

    @Override
    public ProductDTO buscaProduto() {
        WebDriver driver = connectionFactory.conexao();
        String name = "", price = "";

        try {
            driver.get(url);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("div[class*='styles__Wrapper-sc-akc962-0 iVmOpn']")
            ));

            List<WebElement> productContainers = driver.findElements(
                    By.cssSelector("div[class*='styles__Wrapper-sc-akc962-0 iVmOpn']")
            );

            for (WebElement container : productContainers) {

                try {
                    WebElement priceElement = container.findElement(
                            By.cssSelector("span.src__Text-sc-154pg0p-0.styles__PromotionalPrice-sc-akc962-4.jbedzg")
                    );

                    name = "Barra de Chocolate Lacta 145g"; //nameElement.getText();
                    price = priceElement.getText();

                } catch (NoSuchElementException e) {
                    System.out.println("Item não encontrado");
                    throw new NotFoundItem();
                }
            }
        } catch (TimeoutException e) {
            throw new LoadPageError();
        } finally {
            driver.quit();
        }
        return new ProductDTO("1", name, price, "Americanas");
    }
    //Barra de Chocolate Branco Lacta Laka 145g
}

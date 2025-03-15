package com.scraping.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}
	/*package com.scraping.demo;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName("chrome");
		String url = "https://www.americanas.com.br/lojas-proximas/33014556000196/5310?c_legionRegion=202235109001&c_macroRegion=SP_INTERIOR&c_mesoRegion=3510&context=lojas_proximas&filter=%7B%22id%22%3A%22variation.sellerID%22%2C%22value%22%3A%2233014556000196%22%2C%22fixed%22%3Atrue%7D&filter=%7B%22id%22%3A%22variation.warehouse%22%2C%22value%22%3A%225310%22%2C%22fixed%22%3Atrue%7D&filter=%7B%22id%22%3A%22wit%22%2C%22value%22%3A%22barra%20de%20chocolate%22%2C%22fixed%22%3Afalse%7D&lat=-22.9406129&lon=-47.111549&sortBy=relevance&source=nanook&page=2&limit=24&offset=24";

		WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

		HashMap<String, String> produtos = new HashMap<>();

		try {
			driver.get(url);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			System.out.println("Encontrou a url");

			// Passo 1: Esperar pelo carregamento dos containers de produtos
			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.cssSelector("div[class*='styles__Wrapper-sc-akc962-0 iVmOpn']")
			));
			System.out.println("Achou a lista");

			// Passo 2: Coletar todos os containers de produtos
			List<WebElement> productContainers = driver.findElements(
					By.cssSelector("div[class*='styles__Wrapper-sc-akc962-0 iVmOpn']")
			);

			// Passo 3: Extrair nome e preço de cada produto
			for (WebElement container : productContainers) {
				System.out.println(container.getText());

				try {
//					WebElement subContainer = container.findElement(
//							By.cssSelector("styles__WrapperInfo-sc-akc962-1 bpEMVP"));
//
					WebElement nameElement = container.findElement(
							By.cssSelector("span.src__Text-sc-154pg8p-0.styles__Name-sc-akc962-2.buHDRn")
					);
					WebElement priceElement = container.findElement(
							By.cssSelector("span.src__Text-sc-154pg8p-0.styles__PromotionalPrice-sc-akc962-4.jbedzg")
					);

					produtos.put(nameElement.getText(), priceElement.getText());

				} catch (NoSuchElementException e) {
					System.out.println("Dados incompletos em um produto");
				}
			}

			// Exibir resultados
			System.out.println("=== PRODUTOS ===");
			produtos.forEach((nome, preco) ->
					System.out.println(nome + " | Preço: " + preco)
			);

		} catch (TimeoutException e) {
			System.err.println("Erro: Página não carregou corretamente!");
		} finally {
			driver.quit();
		}
	}
}
//body > id = "root" > id="rsyswpsdk" > class="src__SkinUI-sc-1n4955m-0 kVILVJ" > main > class="styles__Background-sc-qudgty-2 ieLLEc" > class="grid__StyledGrid-sc-1man2hx-0 imOqUG styles__GridUI-sc-qudgty-0 gJBFDo" > class="col__StyledCol-sc-1snw5v3-0 gGsvRl theme-grid-col"
//class="grid__StyledGrid-sc-1man2hx-0 iFeuoP" > class="col__StyledCol-sc-1snw5v3-0 kABhu theme-grid-col styles__ColGridItem-sc-173klk1-2 YfPWB" > class="styles__Wrapper-sc-akc962-0 iVmOpn" > class="styles__WrapperInfo-sc-akc962-1 bpEMVP" > class="src__Text-sc-154pg0p-0 styles__PromotionalPrice-sc-akc962-4 jbedzg" > 11,99
*/

}

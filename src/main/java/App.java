import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import org.hamcrest.MatcherAssert;

// Класс страницы авторизации
class App {
    public static void main(String[] args) {

    }

    class LoginPageMesto {
        private WebDriver driver;
        private By emailField = By.id("email");
        private By passwordField = By.id("password");
        private By signInButton = By.className("auth-form__button");

        public LoginPageMesto(WebDriver driver) {
            this.driver = driver;
        }

        public void setUsername(String username) {
            driver.findElement(emailField).sendKeys(username);
        }

        public void setPassword(String password) {
            driver.findElement(passwordField).sendKeys(password);
        }

        public void clickSignInButton() {
            driver.findElement(signInButton).click();
        }

        public void login(String username, String password) {
            setUsername(username);
            setPassword(password);
            clickSignInButton();
        }
    }

    // Класс заголовка
    class HeaderPageMesto {

        private WebDriver driver;
        // создай локатор для элемента c email в заголовке страницы
        private By headerUser = By.className("header__user");

        public HeaderPageMesto(WebDriver driver) {
            this.driver = driver;
        }

        // метод ожидания загрузки страницы
        public void waitForLoadHeader() {
            new WebDriverWait(driver, 3)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.className("header__user")));
        }
        // метод для получения текста элемента в заголовке
        public void emailInHeader(){
            driver.findElement(headerUser).getText();
        }
    }

    // Класс с автотестом
    public class Praktikum {

        private WebDriver driver;

        @Test
        public void checkEmailInHeader() {
            // создали драйвер для браузера Chrome
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
            driver = new ChromeDriver(options);
            // перешли на страницу тестового приложения
            driver.get("https://qa-mesto.praktikum-services.ru/");

            // создай объект класса страницы авторизации
            LoginPageMesto objLoginPage = new LoginPageMesto(driver);
            // выполни авторизацию
            String email = "testik285555@gmail.com";
            String password = "123";
            // передавай эти переменные внутрь метода
            objLoginPage.login(email, password);

            // создай объект класса заголовка приложения
            HeaderPageMesto objHeaderMesto = new HeaderPageMesto(driver);
            // дождись загрузки заголовка
            objHeaderMesto.waitForLoadHeader();
            // получи текст элемента в заголовке
            objHeaderMesto.emailInHeader();
            // сделай проверку, что полученное значение совпадает с email
            //MatcherAssert.assertThat(objHeaderMesto.emailInHeader(), is(true));
            //assertThat(objHeaderMesto.emailInHeader(), equalTo(email));

        }

        @After
        public void tearDown() {
            // Закрой браузер
        driver.quit();
        }
    }
}
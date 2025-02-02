package dika.springbootstarter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static java.lang.Integer.parseInt;

public class IncomeClient {

    private static final Logger log = LoggerFactory.getLogger(IncomeClient.class);
    private final RestTemplate restTemplate;

    @Autowired
    public IncomeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public int getData(String baseUrl, Long userId) {

            ResponseEntity<String> response = restTemplate.getForEntity(baseUrl, String.class);
            String responseBody = response.getBody();
            log.info("Response body: " + responseBody);
            log.info("User id: " + userId.toString());
            // Проверка наличия пользователя в ответе
            if (responseBody != null && responseBody.contains("\"id\":" + userId.toString() + ",")) {
                try {
                    // Ищем начало зарплаты, предполагаем, что она идет после "salary: "
                    int salaryIndex = responseBody.indexOf("\"income\":", responseBody.indexOf("\"id\":" + userId.toString() + ","));
                    if (salaryIndex != -1) {
                        // Извлекаем зарплату после "salary: "
                        String salaryStr = responseBody.substring(salaryIndex + 9).split("}")[0];
                        return Integer.parseInt(salaryStr);
                    }
                } catch (Exception e) {
                    e.printStackTrace();  // Выводим стек вызовов для отладки
                }
            }

            return 0;  // Если не нашли или произошла ошибка
        }

    }


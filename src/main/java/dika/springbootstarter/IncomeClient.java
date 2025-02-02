package dika.springbootstarter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class IncomeClient {

    private static final Logger log = LoggerFactory.getLogger(IncomeClient.class);
    private final RestTemplate restTemplate;

    @Autowired
    public IncomeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public int getData(String baseUrl, Long userId) {

        String responseBody = restTemplate.getForEntity(baseUrl, String.class).getBody();

        if (responseBody != null && responseBody.contains("\"id\":" + userId.toString() + ",")) {
            try {

                int salaryIndex = responseBody.indexOf("\"income\":", responseBody.indexOf("\"id\":" + userId.toString() + ","));
                if (salaryIndex != -1) {

                    String salaryStr = responseBody.substring(salaryIndex + 9).split("}")[0];
                    return Integer.parseInt(salaryStr);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

}


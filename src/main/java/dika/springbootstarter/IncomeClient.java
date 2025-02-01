package dika.springbootstarter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class IncomeClient {

    private final RestTemplate restTemplate;

    @Autowired
    public IncomeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> List<T> getData(ParameterizedTypeReference<List<T>> typeReference, String baseUrl) {
        return restTemplate.exchange(
                        baseUrl, HttpMethod.GET,
                        null,
                        typeReference)
                .getBody();
    }

}

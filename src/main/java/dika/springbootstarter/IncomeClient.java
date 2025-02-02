package dika.springbootstarter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class IncomeClient {

    private final RestTemplate restTemplate;

    @Autowired
    public IncomeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public int getData(String baseUrl, Long userId) {

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("id", userId)
                .toUriString();

        ResponseEntity<List<UserDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDTO>>() {
                }
        );

        List<UserDTO> list = response.getBody();
        for (UserDTO userDTO : list) {
            if (userDTO.getId() == (userId)) {
                if (userDTO.getIncome() != null) {
                    return userDTO.getIncome().intValue();
                }
            }
        }
        return 0;
    }
}


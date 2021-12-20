package com.pos.integration.service.client;

import com.pos.integration.model.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class RestClient {

    private final RestTemplate rt;

    public <T> PostResponse<T> post(String url, Object request, ParameterizedTypeReference<PostResponse<T>> typeReference) {

        HttpEntity<Object> httpEntity = new HttpEntity<>(request);
        ResponseEntity<PostResponse<T>> response = rt.exchange(url, HttpMethod.POST, httpEntity, typeReference);
        return response.getBody();
    }
}

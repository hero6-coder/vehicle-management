package blueship.vehicle.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class TcbsRestTemplateInterceptor implements ClientHttpRequestInterceptor {
    private String apiKey;
    private static final String AGENT = "X-User-Agent";
    private static final String AGENT_ID = "Bond trading v1.0-Service code 204";

    public TcbsRestTemplateInterceptor(String apiKey) {
        super();
        this.apiKey = apiKey;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        HttpHeaders headers = request.getHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AGENT, AGENT_ID);
//        headers.add(TcbsJWT.API_KEY, apiKey);
        return execution.execute(request, body);
    }
}
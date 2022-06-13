package guru.springframework.msscbreweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jt on 2019-08-08.
 *
 * Blocking IO
 */
@Component // Comment this out to get the default
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

    /**
     * Sets up Apache-specific client information
     * @return
     */
    public ClientHttpRequestFactory clientHttpRequestFactory(){
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(100);
        connectionManager.setDefaultMaxPerRoute(20);

        // Set custom request configuration
        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(3000)
                .setSocketTimeout(3000)
                .build();

        // Simple closable HTTP Client
        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();

        // Return a new instance of the client
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    /**
     * Spring configured to use Apache-flavored HTTP client
     * @param restTemplate
     */
    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(this.clientHttpRequestFactory());
    }
}
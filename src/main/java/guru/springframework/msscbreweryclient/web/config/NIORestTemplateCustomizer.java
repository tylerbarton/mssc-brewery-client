package guru.springframework.msscbreweryclient.web.config;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jt on 2019-08-07.
 *
 * Non-Blocking IO template: Sets of sockets can be used by a single thread
 */
//@Component // Uncomment this to use this
public class NIORestTemplateCustomizer implements RestTemplateCustomizer {

    /**
     * Apache configuration
     * @return
     * @throws IOReactorException
     */
    public ClientHttpRequestFactory clientHttpRequestFactory() throws IOReactorException {
        final DefaultConnectingIOReactor ioreactor = new DefaultConnectingIOReactor(IOReactorConfig.custom().
                setConnectTimeout(3000).
                setIoThreadCount(4).
                setSoTimeout(3000).
                build());

        final PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(ioreactor);
        connectionManager.setDefaultMaxPerRoute(100);
        connectionManager.setMaxTotal(1000);

        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        // Deprecated in Spring 5 - probably to favor reactive client
        return new HttpComponentsAsyncClientHttpRequestFactory(httpAsyncClient);
    }

    /**
     * Spring customization
     * @param restTemplate
     */
    @Override
    public void customize(RestTemplate restTemplate) {
        try {
            // Sets up requestFactory with non-blocking IO
            restTemplate.setRequestFactory(clientHttpRequestFactory());
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
    }
}
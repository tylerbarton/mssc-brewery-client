package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

/**
 * Creates a client that will internet with the web api
 */
@Component
@ConfigurationProperties(value = "sfg.brewery", ignoreUnknownFields = false)    // Creates a variable in application.properties
public class BreweryClient {

    public final String BEER_PATH_V1 = "/api/v1/beer/";      // Path is a constant
    private String apihost;                                 // Host is variable across systems
    private final RestTemplate restTemplate;

    /**
     *
     * @param restTemplateBuilder creates a restTemplate
     */
    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * Client GET method
     * @param uuid The uuid of the beer itself
     */
    public BeerDto getBeerById(UUID uuid){
        return restTemplate.getForObject(apihost + BEER_PATH_V1 + uuid.toString(), BeerDto.class);
    }

    /**
     * Saves a new beer on the web service
     * @param beerDto beer to save on the service
     * @return URI: Resource locator
     */
    public URI saveNewBeer(BeerDto beerDto){
        return restTemplate.postForLocation(apihost + BEER_PATH_V1, beerDto);
    }

    /**
     * PUT method to update a given beer
     * @param uuid Id of the beer to update
     * @param beerDto Data
     */
    public void updateBeer(UUID uuid, BeerDto beerDto){
        restTemplate.put(apihost + BEER_PATH_V1 + uuid.toString(), beerDto);
    }

    /**
     * DELETE method sent to web service
     * @param uuid Id of beer to delete
     */
    public void deleteBeer(UUID uuid){
        restTemplate.delete(apihost + BEER_PATH_V1 + uuid.toString());
    }

    public void setApihost(String apihost) {
        this.apihost = apihost;
    }

    /**
     * GET: Gets the customer information from the web service
     * @param uuid Id of the customer
     * @return customer information
     */
    public CustomerDto getCustomerById(UUID uuid){
        return restTemplate.getForObject(apihost + BEER_PATH_V1 + uuid.toString(), CustomerDto.class);
    }

    /**
     * POST: Saves a new customer to the web service
     * @param customerDto customer data to save
     * @return URI of the saved data
     */
    public URI saveNewCustomer(CustomerDto customerDto){
        return restTemplate.postForLocation(apihost+BEER_PATH_V1, customerDto);
    }

    /**
     * PUT: Update a customer
     * @param uuid Id of the customer to update
     * @param customerDto Data of the customer
     */
    public void updateCustomer(UUID uuid, CustomerDto customerDto){
        restTemplate.put(apihost + BEER_PATH_V1 + uuid.toString(), customerDto);
    }

    /**
     * DELETE: delete a customer from the web service
     * @param uuid Id of the customer to delete
     */
    public void deleteCustomer(UUID uuid){
        restTemplate.delete(apihost + BEER_PATH_V1 + uuid.toString());
    }
}

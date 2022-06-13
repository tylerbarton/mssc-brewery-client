package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests BreweryClient methods
 * mssc-brewery service must be active for tests to pass
 */
@SpringBootTest
class BreweryClientTest {

    @Autowired              // Null is you don't include @SpringBootTest
    BreweryClient client;

    /**
     * Tests GET method
     * Fails with "I/O error on GET request" if brewery service is not running
     * "404: not found" caused by incorrect api path
     */
    @Test
    void testGetBeerById() {
        BeerDto dto = client.getBeerById(UUID.randomUUID());
        assertNotNull(dto);
    }

    /**
     * Attempts to save a new beer on the web service and get back a URI
     */
    @Test
    void testSaveNewBeer(){
        BeerDto beerDto = BeerDto.builder().beerName("TestBeer").build();
        URI uri = client.saveNewBeer(beerDto);

        assertNotNull(uri);
        System.out.println(uri);
    }

    /**
     * Handles the PUT request to the web service
     * "I/O error on PUT request" is brewery service is not active
     */
    @Test
    void testUpdateBeer(){
        BeerDto beerDto = BeerDto.builder().beerName("TestBeer").build();
        // There are methods to get a uuid in a real environment
        UUID uuid = UUID.randomUUID();
        client.updateBeer(uuid, beerDto);
        // updateBeer is void - no return value to test
    }

    /**
     * Tests DELETE method call to service
     * "I/O error on DELETE request" is brewery service is not active
     */
    @Test
    void testDeleteBeer(){
        client.deleteBeer(UUID.randomUUID());
    }

    /**
     * Tests GETS customer method call to service
     */
    @Test
    void testGetCustomerById(){
        UUID uuid = UUID.randomUUID();
        CustomerDto customerDto = client.getCustomerById(uuid);
        assertNotNull(customerDto);
    }

    /**
     * Tests POST method call to service
     */
    @Test
    void testSaveNewCustomer(){
        CustomerDto customerDto = CustomerDto.builder().name("testPOSTcustomer").build();
        URI uri = client.saveNewCustomer(customerDto);

        assertNotNull(uri);
        System.out.println(uri);
    }

    /**
     * Tests PUT method call to service
     */
    @Test
    void testUpdateCustomer(){
        CustomerDto customerDto = CustomerDto.builder().name("testcustomer").build();
        UUID uuid = UUID.randomUUID();
        client.updateCustomer(uuid, customerDto);
        // Test passes if not error
    }

    /**
     * Tests DELETE method for customer
     */
    @Test
    void testDeleteCustomer(){
        client.deleteCustomer(UUID.randomUUID());
    }

}
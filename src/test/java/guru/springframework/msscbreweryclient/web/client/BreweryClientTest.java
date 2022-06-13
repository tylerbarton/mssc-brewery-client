package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests BreweryClient methods
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
    void getBeerById() {
        BeerDto dto = client.getBeerById(UUID.randomUUID());
        assertNotNull(dto);
    }
}
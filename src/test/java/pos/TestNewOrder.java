package pos;

import POS.Model.Order;
import cucumber.api.CucumberOptions;

import cucumber.api.junit.Cucumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"pretty", "html:target/html"},
        features = {"classpath:pos/TestNewOrder.feature"})

public class TestNewOrder {
    Order order;

    @BeforeEach
    void init(){
        order = new Order ();
    }

}

package pos;
import POS.Model.Order;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.jupiter.api.BeforeEach;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class StepDefIncome {
    Order order = new Order();

    @BeforeEach
    void init(){
        order = Order.getInstance();
    }

    @Given("^Customer want main : (\\d+)$")
    public void customer_want_main(int arg1) throws Throwable {
        order.setMainProduct(String.valueOf(arg1));
    }

    @And("^Customer want option : (\\d+)$")
    public void customer_option(Integer option) throws Throwable{
        order.setOption(option);
    }

    @And("^Customer want topping : (.*)")
    public void customer_topping(String topping) throws Throwable{
        order.setTopping(Collections.singletonList(topping));
    }

    @And("^Price is (.*)")
    public void price_is(Float price) throws Throwable{
        order.setTotalPrice(price);
    }

    @When("^Customer confirm")
    public void customer_confirm() throws Throwable{
        Date d = new Date();
        order.setDateOrder(d);
        order.setWho("CucumberTester");
        order.orderHere();
    }

    @Then("^Order has record to database")
    public void record_confirm() throws Throwable{
        Integer check = 1;
        assertEquals(check,order.getStatus());
    }

    @Then("^Order has not record to database")
    public void record_no_confirm() throws Throwable{
        Integer check = 0;
        assertEquals(check,order.getStatus());
    }
}

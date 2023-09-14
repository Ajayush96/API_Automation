package stepDefinitions;

import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;
import io.cucumber.java.en.*;

@RunWith(Cucumber.class)
public class stepDefinition {
	
	@Given("User is on landing page")
	public void user_is_on_landing_page() {
	   System.out.println("This is test");
	}

	@When("User login application with userName and password")
	public void user_login_application_with_user_name_and_password() {
	   
	}

	@Then("Home page is populated")
	public void home_page_is_populated() {
	  
	}

	@Then("Cards are displayed")
	public void cards_are_displayed() {
	   
	}



}

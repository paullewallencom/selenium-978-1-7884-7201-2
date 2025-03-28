package com.packt.tests;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.herokuapp.LogInPage;
import com.herokuapp.SecurePage;
import com.packt.base.BaseTest;

public class FirstTest extends BaseTest {

	@Test
	@Parameters({ "username", "password" })
	public void firstTest(String username, String password) {
		SoftAssert softAssert = new SoftAssert();
		LogInPage logInPage = new LogInPage(driver);
		logInPage.open();

		// Clicking LogIn button
		SecurePage securePage = logInPage.logIn(username, password);
		securePage.waitForSecurePage(10000);

		// Verifications
		softAssert.assertTrue(securePage.isLogOutButtonVisible(), "LogOut button is not displayed.");
		softAssert.assertTrue(securePage.getPageSource().contains("You logged into a secure area!"),
				"Page source doesn't contain expected text: 'You logged into a secure area!'");

		softAssert.assertAll();
	}


	@Test(dataProvider = "negativeLogInData")
	public void negativeLogInTest(String username, String password, String expectedError) {
		LogInPage logInPage = new LogInPage(driver);
		logInPage.open();

		// Clicking LogIn button
		logInPage.negativeLogIn(username, password);

		//Verification
		String actualError = logInPage.getErrorMessageText();
		Assert.assertTrue(actualError.contains(expectedError),
				"Actual and expected error messages are different. \nExpected: " + expectedError + "\nActual: " + actualError);
	}
}

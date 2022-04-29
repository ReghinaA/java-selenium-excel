package com.mercury.tour.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignInPage {
    @FindBy(name = "userName")
    public WebElement userNameField;

    @FindBy(name = "password")
    public WebElement passwordField;

    @FindBy(name = "submit")
    public WebElement submitButton;

    @FindBy(tagName = "h3")
    public WebElement successLoginMessage;
}

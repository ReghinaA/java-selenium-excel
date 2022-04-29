package com.mercury.tour.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {

    @FindBy(linkText = "SIGN-ON")
    public WebElement signOnButton;
}

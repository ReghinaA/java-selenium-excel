Feature: Testing Login

  Background: Navigate to Mercury Tours Home page and Verify that Sign On button is working
    Given I navigate to Mercury Tours Home page
    And I select Sign On at the top menu

  ## Comment for not closing / uncomment for autoclosing browser
  @close
  Scenario Template: On Sign On page enter User Name and Password from Excel Row <row index>
    When I enter credentials from Excel row <row index> and click Submit
    Then I see the message '<expected message>'
    And I save excel results
    Examples:
      | row index | expected message   |
      | 1         | Login Successfully |
      | 2         | Login Successfully |
      | 3         | Login Successfully |
      | 4         | Login Successfully |
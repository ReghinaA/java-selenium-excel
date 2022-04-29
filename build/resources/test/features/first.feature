Feature: Testing Login

  # @close - close browser after test
  # @close
  Background: Navigate to Mercury Tours Home page and Verify that Sign On button is working
    Given I navigate to Mercury Tours Home page
    And I select Sign On at the top menu

  Scenario Template: On Sign On page enter User Name and Password
    When I enter credentials from Excel row <row index> and click Submit
    Then I see the message '<expected message>'
    Examples:
      | row index | expected message   |
      | 1         | Login Successfully |
#      | 2         | Login Successfully |
#      | 3         | Login Successfully |
#      | 4         | Login Successfully |

  Scenario: Save results
    Given I save excel results
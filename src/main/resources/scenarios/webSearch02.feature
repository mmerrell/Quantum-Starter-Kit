@Web02
Feature: Google Search

  @WebSearch
  Scenario: Search New York
    Given I am on Google Search Page, looking for two:one
    When I search for "new york"
    Then it should have "new york" in search results

  @WebResultsList
  Scenario: Search Quantum with results
    Given I am on Google Search Page, looking for two:two
    When I search for "Project-Quantum Quantum-Starter-Kit"
    Then it should have following search results:
      | Quantum-Starter-Kit |
      | Project-Quantum |

  @WebDD
  Scenario Outline: Search Keyword Inline Data
    Given I am on Google Search Page, looking for two:three
    When I search for "<searchKey>"
    Then it should have "<searchResult>" in search results

    Examples:
      | recId | recDescription 	| searchKey               | searchResult                  |
      | 1     | First Data Set	| quantum leap | Quantum Leap|
      | 2     | Second Data Set 	|Project-Quantum Quantum-Starter-Kit | GitHub |

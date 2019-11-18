@Web04
Feature: Google Search

  @WebSearch
  Scenario: Search Quantum
    Given I am on Google Search Page, looking for four:one
    When I search for "quantum leap"
    Then it should have "Quantum Leap" in search results

  @WebResultsList
  Scenario: Search Quantum with results
    Given I am on Google Search Page, looking for four:two
    When I search for "Project-Quantum Quantum-Starter-Kit"
    Then it should have following search results:
      | Quantum-Starter-Kit |
      | Project-Quantum |

  @WebDD
  Scenario Outline: Search Keyword Inline Data
    Given I am on Google Search Page, looking for four:three
    When I search for "<searchKey>"
    Then it should have "<searchResult>" in search results

    Examples:
      | recId | recDescription 	| searchKey               | searchResult                  |
      | 1     | First Data Set	| quantum leap        | Quantum Leap |
      | 2     | Second Data Set 	|Project-Quantum Quantum-Starter-Kit | GitHub |

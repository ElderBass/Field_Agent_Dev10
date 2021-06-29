# Field Agent Assessment

## Overview
### High Level Requirments:
* Create full HTTP CRUD for security clearance.
* Create full HTTP CRUD for agent aliases.
* Implement global error handling.

## Tasks

### 1. Fleshing Out Security Clearance

#### Task: Creating SecurityJdbcClearanceRepository
* First create interface that SecurityJdbcClearanceRepository will implement
* Need to write CRUD methods for the following operations:
    * Find all security clearances.
    *  Find a security clearance by its identifier.
    *  Add a security clearance.
    *  Update an existing security clearance.
    *  Delete a security clearance (only if it isn't referenced!)
    ####
    **Total Time** = ~2 hours
#### Task: Testing SecurityJdbcClearanceRepository
* Test all the methods from above section
    * Do one "happy" and one "unhappy" test for each method
    ####
    **Total Time** = 1 hour
    
#### Task: Creating SecurityClearanceService
* Need to write CRUD Service methods for the following operations:
    * Find all security clearances.
    *  Find a security clearance by its identifier.
    *  Add a security clearance.
    *  Update an existing security clearance.
    *  Delete a security clearance (only if it isn't referenced!)
* Will also need validation methods to be used for adding/updating Clearances
    ####
  **Total Time** = 1.5 hours
#### Task: Testing SecurityClearanceService
* Test all above methods with positive and negative cases
    ####
    **Total Time** = 2 hours
#### Task: Creating SecurityController
* Need to create a @RestController class for SecurityClearance
    * This will have routes for all the aforementioned CRUD methods
    * Will follow examples in PetController from LMS to write this
    ####
    **Total Time** = 1 hour
#### Task: Testing SecurityController
* Have to test all the routes from SecurityClearanceController using a MockService
    * This might be tricky since it's relatively new territory
    * Will consult LMS examples for how to test using Mockito format
    * This will require research and trial and error
    ####
    **Total Time** = 2.5 hours
  
#### Total Time for this Step = 10 hours

### 2. Fleshing Out Alias

#### Task: Create Alias Model
* Fields:
  * int aliasId
  * String alias ("name" in the model, but alias seems better to me)
  * String persona
  * Agent agent (for referencing the agent with that alias)
* Methods:
  * Empty constructor
  * Constructor for taking in all fields
  * Getters and Setters for all fields
* Will likely also have to alter Agent model to have a field for a List of aliases
  * This in turn might require further alterations, e.g. getter/setter for aliases list, alteration to certain methods to account for aliases
  ####
  **Total Time** = 2 hours

#### Task: Creating AliasJdbcTemplateRepository
* First create interface that AliasJdbcTemplateRepository will implement
* Need to write CRUD methods for the following operations:
    * Fetch an individual agent with aliases attached.
    * Add an alias.
    * Update an alias.
    * Delete an alias. (No strategy required. An alias is never referenced elsewhere.)
  ####
  **Total Time** = 2 hours
#### Task: Testing AliasJdbcTemplateRepository
* Test all the methods from above section
  * Do one "happy" and one "unhappy" test for each method
  ####
  **Total Time** = 1 hour

#### Task: Creating AliasService
* Need to write CRUD Service methods for the following operations:
  * Fetch an individual agent with aliases attached.
  * Add an alias.
  * Update an alias.
  * Delete an alias.
* Will also need validation methods to be used for adding/updating Clearances
  ####
  **Total Time** = 1.5 hours
#### Task: Testing AliasService
* Test all above methods with positive and negative cases
  ####
  **Total Time** = 2.5 hours
#### Task: Creating AliasController
* Need to create a @RestController class for Alias
  * This will have routes for all the aforementioned CRUD methods
  * Will follow examples in PetController from LMS to write this
  ####
  **Total Time** = 1 hour
#### Task: Testing AliasController
* Hopefully I have the hang of this from doing the SecurityClearance tests so it won't take as long
  ####
  **Total Time** = 1.5 hours
#### Total Time for this Step = 11.5 hours


### 3. Global Error Handling

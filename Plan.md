# Field Agent Assessment

## Overview
### High Level Requirements:
* Create full HTTP CRUD for security clearance.
* Create full HTTP CRUD for agent aliases.
* Implement global error handling.

### Plan Outline
* Will likely tackle the high level requirements in order
  * Start with SecurityClearanceJdbcRepo class and write out CRUD methods
    * Test the CRUD methods
  * Move on to SecurityClearanceService (which needs to be created)
    * Write out CRUD methods in class
    * Write out validation methods as needed
    * Test CRUD methods
  * Finally move on to creating a SecurityController class
    * Write routes for the necessary CRUD operations
    * Test routes using MockMVC
  * Once SecurityClearance is done, do the same for Alias
    * Only difference here is that Alias still needs a model and JdbcTemplateRepo class, unlike SecurityClearance which had code for both
    * Repeat process after model is created - JdbcTemplateRepo/Test, Service/Test, Controller/Test
  * Move on to final task which should be relatively simple - Global Error Handling
    * Should only involve two new classes which will closely mirror examples in LMS

## Tasks

### 1. Fleshing Out Security Clearance

#### TODO: Creating SecurityJdbcClearanceRepository
* First create interface that SecurityJdbcClearanceRepository will implement
* Need to write CRUD methods for the following operations:
    * Find all security clearances.
    *  Find a security clearance by its identifier.
    *  Add a security clearance.
    *  Update an existing security clearance.
    *  Delete a security clearance (only if it isn't referenced!)
    ####
    **Estimated Time** = ~2 hours
#### TODO: Testing SecurityJdbcClearanceRepository
* Test all the methods from above section
    * Do one "happy" and one "unhappy" test for each method
    ####
    **Estimated Time** = 1 hour
    
#### TODO: Creating SecurityClearanceService
* Need to write CRUD Service methods for the following operations:
    * Find all security clearances.
    *  Find a security clearance by its identifier.
    *  Add a security clearance.
    *  Update an existing security clearance.
    *  Delete a security clearance (only if it isn't referenced!)
* Will also need validation methods to be used for adding/updating Clearances
    ####
  **Estimated Time** = 1.5 hours
#### TODO: Testing SecurityClearanceService
* Test all above methods with positive and negative cases
    ####
    **Estimated Time** = 2 hours
#### TODO: Creating SecurityController
* Need to create a @RestController class for SecurityClearance
    * This will have routes for all the aforementioned CRUD methods
    * Will follow examples in PetController from LMS to write this
    ####
    **Estimated Time** = 1 hour
#### TODO: Testing SecurityController
* Have to test all the routes from SecurityClearanceController using a MockService
    * This might be tricky since it's relatively new territory
    * Will consult LMS examples for how to test using Mockito format
    * This will require research and trial and error
    ####
    **Estimated Time** = 2.5 hours
  
#### Total Time for this Step = 10 hours

### 2. Fleshing Out Alias

#### TODO: Create Alias Model
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
  **Estimated Time** = 2 hours

#### TODO: Create AliasMapper class
* Should be pretty straightforward, just follow examples of other mappers to make this work
  ####
  **Estimated Time** = 15 minutes


#### TODO: Creating AliasJdbcTemplateRepository
* First create interface that AliasJdbcTemplateRepository will implement
* Need to write CRUD methods for the following operations:
    * Fetch an individual agent with aliases attached.
    * Add an alias.
    * Update an alias.
    * Delete an alias. (No strategy required. An alias is never referenced elsewhere.)
  ####
  **Estimated Time** = 2 hours
#### TODO: Testing AliasJdbcTemplateRepository
* Test all the methods from above section
  * Do one "happy" and one "unhappy" test for each method
  ####
  **Estimated Time** = 1 hour

#### TODO: Creating AliasService
* Need to write CRUD Service methods for the following operations:
  * Fetch an individual agent with aliases attached.
  * Add an alias.
  * Update an alias.
  * Delete an alias.
* Will also need validation methods to be used for adding/updating Clearances
  ####
  **Estimated Time** = 1.5 hours
#### TODO: Testing AliasService
* Test all above methods with positive and negative cases
  ####
  **Estimated Time** = 2.5 hours
#### TODO: Creating AliasController
* Need to create a @RestController class for Alias
  * This will have routes for all the aforementioned CRUD methods
  * Will follow examples in PetController from LMS to write this
  ####
  **Estimated Time** = 1 hour
#### TODO: Testing AliasController
* Hopefully I have the hang of this from doing the SecurityClearance tests so it won't take as long
  ####
  **Estimated Time** = 1.5 hours
#### Total Time for this Step = 11.75 hours


### 3. Global Error Handling

#### TODO: Create Error Response Class
* Will follow Pets example from LMS to implement this as a way to print custom messages for exceptions
* This will act as a dependency in the next class we create, GlobalExceptionHandler
  ####
  **Estimated Time** = 15 minutes

#### TODO: Create GlobalExceptionHandler
* Again, will follow the Pet example from LMS to inject Error Response into this class and set up needed methods
* This should be a fairly straightforward process and no other code will need be altered
  ####
  **Estimated Time** = 30 minutes

#### Total Time for this Step = .75 hours

## Total Estimated Time to Completion = 22.5 hours
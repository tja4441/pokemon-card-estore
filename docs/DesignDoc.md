---
geometry: margin=1in
---
# PROJECT Design Documentation

## Team Information
* Team name: Engenuity
* Team members
  * Zachary Wagner
  * Daniel Pittman 
  * Jensen DeRosier
  * Timothy Avila
  * Gabriel Buxo
  
## Executive Summary

<p style='text-align: justify;'>
This is an E-Store designed to sell Pokémon Cards to customers. There are two types of users, customers and the admin. The Customers can register and login to add cards to their shopping cart before checking out. Checking out removes the indicated quantity from each product and charges the customer accordingly. The admin is unique user that is allowed to change the inventory of products directly. This includes decreasing the quantity in stock, changing the price, and adding/removing products.
</p>

<p style='text-align: justify;'>
We have three different objects we are keeping track of, the users, the products, and the shopping carts. Each object has a Data Access Object (DAO) and a controller. The DAO accesses a file in the repository that keeps all of the objects listed in JSON format. The DAO can add objects, read objects, and update objects. The Controller handles http requests and calls the DAO to tell it what to do.
</p>

<p style='text-align: justify;'>
The UI calls functions that ask the controller to get, update, or delete things. The controller returns the designated infromation and the UI accepts the information and displays it.
</p>

### Purpose

<p style='text-align: justify;'>
The purpose of this project was to create an online store application utilizing a programming stack that contains a Spring API and an Angular UI. The online store was made with the design concept of being to sell pokémon trading cards.
</p>
    
### Glossary and Acronyms

| Term  | Definition                        |
| ----- | --------------------------------- |
| SPA   | Single Page                       |
| UI    | User Interface                    |
| API   | Application Programming Interface |
| Stack | Spring API + Angular UI           |
| DAO   | data access object                |
| UML   | Unified Modeling Language         |


## Requirements

<p style='text-align: justify;'>
This section describes the features of the application.
</p>
<p style='text-align: justify;'>
Our program can handle arbitrary amounts of products and users with minor performance loss.
</p>
<p style='text-align: justify;'>
The data can be retreived and interpreted through the UI
</p>
<p style='text-align: justify;'>
Admins can edit the inventory directly while users can only edit through checking out thier cart
</p>

### Definition of MVP

* Customers and e-Store Owner can login and logout with a username
* Admin has a unique view that can control the inventory
        - Admin can Add a product, can Delete a product, can Update a product
        - The admin does not have a shopping cart 
* Customer can see a list of products, can search for a product, can add/remove an item to their shopping cart, and can checkout their items in their cart
* Customers items should save in their cart on logout

### MVP Features

* Admins can edit the inventory directly, but duplicate products are not allowed
* Each product has it's own id, name, price, and quantity
* These products can be added to customer's shopping carts by the sustomer, and then checked out
* When the shopping cart is checked out, the quantity of the product in the inventory is reduced by the appropriate amount
* Users should not be able to have duplicate usernames, and each has their own shopping cart that only they can change.
* The shopping cart is refreshed whenever the user logs in and before checking out
* Refreshing the cart checks the quantity in the cart against the quantity in the inventory to verify that the transaction can proceed, and updates the price if necessary
   
### Roadmap of Enhancements

* Add images and type to each Pokémon Card
* Add the ability to search by type
* Add a password to login that is hashed
* Add the ability to see Order history on a user's unique page
* Add a logout button on user & admin pages
* Add Sales Statistics to the admin's page

## Application Domain

This section describes the application domain.

![Domain Model](DomainModel.png)
<p style='text-align: justify;'>
There are three main entities interacting in our project. There is the customer, the admin, and the inventory. The admin directly edits the inventory, the inventory keeps track of products and users. The customer views the product side of the inventory, and adds the products to their shopping cart, which then removes the products from the inventory.
</p>


## Architecture and Design

<p style='text-align: justify;'>
The Architecture is split into 2 main sections. The API that handles all logic surrounding the persistance of data and the retrieval of data, and the UI, which handles the display of information. The API is further split into three sections, Models, DAOs, and Controllers. The Models are the representations of what we are keeping track. The DAOs are responsible for storing, retrieving, and altering the persistant data that represents the Models in our system. The Controllers are responsible for handling http requests, calling the respective DAO functions, and then returning the information.
</p>

### Summary

<p style='text-align: justify;'>
The following Tiers/Layers model shows a high-level view of the webapp's architecture.
</p>

![Tiers and Layers of the Architecture](architecture-tiers-and-layers.png)

<p style='text-align: justify;'>
The e-store web application, is built using the Model–View–ViewModel (MVVM) architecture pattern. 
</p>
<p style='text-align: justify;'>
The Model stores the application data objects including any functionality to provide persistance. 
</p>
<p style='text-align: justify;'>
The View is the client-side SPA built with Angular utilizing HTML, CSS and TypeScript. The ViewModel provides RESTful APIs to the client (View) as well as any logic required to manipulate the data objects from the Model.
</p>
<p style='text-align: justify;'>
Both the ViewModel and Model are built using Java and Spring Framework. Details of the components within these tiers are supplied below.
</p>


### Overview of User Interface
<p style='text-align: justify;'>
This section describes the web interface flow; this is how the user views and interacts
with the e-store application.
</p>
<p style='text-align: justify;'>
The User vists the web application and automatically visits the home page. On the home page the user can see the products in the store, can search for a product and press a button to go to the login page. The login page allows a new user to register an account and login if the user is an existing user. On login the user is directed back to the home page. On login the homepage also has a button for the user to visit their own User page. The user can also click the login button when they are logged in to be redirected to a page to logout. 
</p>

<p style='text-align: justify;'>
The User also has access to a user page that displays their shopping cart. The user can reach this page from the home page by clicking on a button in the top right corner of the application. This button will take them to their user page and they will be able to see their shopping cart. The shopping cart has the price of each individual item, the quantity of the item in the user's cart, and the total price of all items in the user's cart. The user can modify the quantity of each item in their cart by using the plus and minus buttons. The minus button is located on the left side of the quantity amount and when pressed will decrement the quantity of the product in the cart. When the quantity falls below 1, the item is removed from the user's cart. The plus button is located to the right of the shown quantity and when pressed will increment the quantity of the product in the cart. When the quantity in the user's cart is the same as the quantity of the product in the inventory, the quantity amount is capped and cannot be incremented further. As the user modifies the quantity of products in their cart, the total price will dynamically change without requiring the user to do any additional routing in the application for the changes to take effect.
</p>
    
### View Tier
<p style='text-align: justify;'>
Our view tier is our user's way of interacting with our backend and our products. A user's journey starts on our home page. It fetches information on the user and inventory from our User and Inventory services in angular which use http services to grab information from the backend. These are singletons for fetching information. The userService holds information on the user for access accross multiple different routes.
</p>
<p style='text-align: justify;'>
    After logging in a user is brought back to the home page in order to view cards in the store. If a user or admin is logged in  there is also a button in order to go to their personal page.
</p>
<p style='text-align: justify;'>
    In the case of the admin they see on their page a ui for adding, editing, and deleteing cards in the store. On the other hand, the user sees functionality for adding cards to their shopping cart, and checking out cards, which deletes said cards from the database.
</p>


### ViewModel Tier
<p style='text-align: justify;'>
The architecture for our project consists of three seperate Controllers that each have their own DAO and Model. The three controllers deal with requests that manipulate Product, User, and Shopping Cart data that are stored in JSON in a file. Each controller calls the designated DAO, which views the infromation in the files. The controllers return the data that the DAOs obtained, which allows anything with a connection to the controller to view the information in the DAO, and thus the models. 
</p>

<p style='text-align: justify;'>
The three controllers are connected as follows: The UserController can see and use the ShoppingCartController and the ShoppingCartController can see and use the InventoryController, however, the InventoryController cannot see or use the ShoppingCartController and the ShoppingCartController cannot see or use the UserController. This implementation is done to separate the logic and storage of shopping carts from users while also allowing the creation of shopping carts with the creation of users and the ability for shopping carts to do the logic necessary to refresh and have products added/removed from. This ViewModel Tier has been designed to ensure that single responsibility and restful behavior is of utmost concern.
</p>


### Model Tier
<p style='text-align: justify;'>
The model tier is the 'heart and soul' of the program. We have four different models that our program is keeping track of. 
</p>
<p style='text-align: justify;'>
The most important is the Product. The product is a template for the cards we are selling. Each card has a number of attributes, including a name, an id that increases sequentially with every product added, the quantity in inventory, the type of the pokemon, and the price of the card. The ids do back fill, if ID 1 is deleted then the next product added takes it's id.
</p>
<p style='text-align: justify;'>
Next, we have a model representing a user. The user has an id that also increasess sequentially, a username which is provided by on creation, and a hashcode for their password, also provided on creation. 
</p>
<p style='text-align: justify;'>
 We also have the shopping cart, which has an id that matches the user it is connected with, a total price that is dynamically calculated when products are added, and a set that contains the products the customer has added to their cart.
</p>
<p style='text-align: justify;'>
  Finally, we have a model which represents order history. An order history object contains an ID which corresponds to the user that placed the order, the shopping cart that was purchased by the user, a unique order number, which increases sequentially and acts as an order ID, separate from the user ID, and a timestamp, representing the date and time that the order was placed.
</p>

### Static Code Analysis/Design Improvements
> _Discuss design improvements that you would make if the project were
> to continue. These improvement should be based on your direct
> analysis of where there are problems in the code base which could be
> addressed with design changes, and describe those suggested design
> improvements._

> _With the results from the Static Code Analysis exercise, 
> discuss the resulting issues/metrics measurements along with your analysis
> and recommendations for further improvements. Where relevant, include 
> screenshots from the tool and/or corresponding source code that was flagged._

## Testing

<p style='text-align: justify;'>
The back end (API) was tested by creating and running JUnit tests. The front end (UI) was tested by manually manipulating the UI. These tests were used to find any bugs that existed in the stack. The tests also verified that the program has the correct operation at each tier of the stack.
</p>

### Acceptance Testing

  * Nine User stories have passed all of their acceptance criteria tests
  * Zero User stories have failing acceptance criteria tests
  * All tests in the sprint have been tested and checked

<p style='text-align: justify;'>
While acceptance testing, we discovered we used a hash set for the shopping cart model but did not override the hashcode in the product model. This flaw caused issues when attempting to delete an item from a shopping cart, as the item would not delete. This flaw was not, however, caught in the Junit test. Even though a test was written to test this functionality, the test still passed. We are puzzled about why the test passed without implementing the hashcode in the product model, but we are pleased that this flaw has been corrected.
</p>

### Unit Testing and Code Coverage

<p style='text-align: justify;'>
Before testing, we agreed on a code coverage target to meet while testing—this target aimed for 90% coverage. We set the target at 90% because it would ensure that the classes would be tested thoroughly for correct operation without wasting time on parts that are not significant.
</p>

<p style='text-align: justify;'>
We created and ran unit tests for each class we made on the back end (API). After running the tests, we ran a code coverage tool to generate a report on how well the JUnit tests covered our code. The code coverage report gave an average of 97% across each tier (Controller, Model, Persistence). This report shows that we exceeded our target and expectations. The breakdown of the report is shown below.
</p>

  * The code coverage for the Controller tier is 96%. 

    ![Controller Tier Coverage](ControllerTier.png)

  * The code coverage for the Model tier is 95%.

    ![Persistence Tier Coverage](PersistenceTier.png)

  * The code coverage for the Persistence tier is 99%

    ![Model Tier Coverage](ModelTier.png)

<p style='text-align: justify;'>
Further testing may be needed to cover more branches. However, this may be unnecessary as the missed branches do not seem essential and would be a waste of our limited time.
</p>
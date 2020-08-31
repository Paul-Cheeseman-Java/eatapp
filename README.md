
## General info
This is a demo project to help me develop my web development skills. It is a website that consumes data via a REST API, allows users to store data they select, and then uses that data stored to provide its own HATEOAS based REST API. 

### Application
Eatapp enables users to find out, and store, the Food Standards Agency (FSA) hygiene ratings consumed using the  [Food Standards Agency (FSA) hygiene ratings API](https://www.food.gov.uk/uk-food-hygiene-rating-data-api).

**Please Note:** 
1) The amount of results returned have been limited by EatApp to 40. This is to reduce the risk of the data plan for the EatApp database being exceed by one very large/general search.  
2) Unfortunately the FSA website can return strange results, so any searches performed on EatApp can be verified using the [FSA hygiene ratings search](https://ratings.food.gov.uk/) on the FSA site. That way its clear that EatApp is working fine, it's just responding with the information provided by the FSA API :-)

EatApp can be found on Heroku via this link:  [EatApp](https://demo-eatapp.herokuapp.com/)

You can either register as a user (users need to be 16+) or use the test account with has a username Test and password of T3s7ing#.
 
#### Features
- The site is themed and device responsive.
- Consumes from the FSA API so the search data is always up to date.
- Search results pre-select establishments already in users stored list.
- Provides a HATEOAS based API (details below).
- Pagination implemented to ensure smooth layout where required. 
- The site uses Spring Security.


**EatApp API**  
_/eatapp/users_ - A _GET_ request which returns a list of all of the applications user ids. 
    It provides HATEOAS links for:
        - user details. 
        - users establishment list.
            
_/eatapp/user/{user-id}_ - A _GET_ request which returns specific users details. 
    It provides HATEOAS links for:
        - returning all users
        - returning specific users establishment list. 

_/eatapp/establishments/{user-id}_ - A _GET_ request which returns a users establishment list. 
    It provides HATEOAS links for:
        - listing specific user details. 
        - returning specific establishment details. 

## Technologies Used
Project is created with:
* Java
* Spring Boot
* Postman
* MySQL
* Bootstrap
* CSS
* HTML
* Eclipse IDE

##### Credits
I built this application using the following resources which were developed by other people:
-   [Bootstrapmade](https://bootstrapmade.com/)  - Bootstrap theme site.
-   [Webapp Runner](https://github.com/heroku/webapp-runner)  - Enables easier deployment of Java Web Applications to Heroku.
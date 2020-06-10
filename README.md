## General info
This project is a small Java banking application to help me understand/practice/implement some of the fundamentals of API consumption and provision.

It can be found on Heroku via this link:  


## Technologies Used
Project is created with:
* Java (v11)
* Spring Boot
* Postman
* MySQL
* Bootstrap
* CSS
* HTML
* Eclipse IDE


## Application
Eatapp is a 



**API Description**  
_/users_ - A _GET_ request which returns a list of all of the applications user ids. 
    It provides HATEOAS links:
        - user details. 
            
_/user{user-id\}_ - A _GET_ request which returns a specific users details. 
    It provides HATEOAS links
        - returning all users
        - returning specific users establishment list. 


_/establishments{user-id\}_ - A _GET_ request which returns a users establishment list. 
    It provides HATEOAS links
        - listing specific user details. 
        - returning specific establishment details. 

_/establishment{establishment_d\}_ - A _GET_ request which returns all establishments with the given name. 
    It provides HATEOAS links
        - returning specific users establishment list. 


update username, age, postcode (look out for blanks or invalid fields)
Could also have searchs on ages range etc for whole db? Most popular type of food for 18to25
delete user's list (password in header??)
delete user (password in header??)
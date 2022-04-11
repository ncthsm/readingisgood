-------------------------------------
#### **Project Definition**

ReadingIsGood is an online books retail firm which operates only on the Internet. Main
target of ReadingIsGood is to deliver books from its one centralized warehouse to their
customers within the same day. That is why stock consistency is the first priority for their
vision operations.

#### Firstly, you need to create Bearer token to send request ```/authenticate``` endpoint, then use created Bearer token to send request other controller endpoints. Otherwise, UnAuthorized exception is returned

-------------------------------------

#### **Used Technologies**

-Java 11

-Spring-Boot

-MongoDB

-JWT

-Maven

-Docker

-Swagger

-JUnit 

-------------------------------------
## Controllers
* AuthenticationController (Create Bearer token)
* CustomerController (Create new customer, Query customer's orders)
* BookController (Create new book, Update book stock)
* OrderController (Create new Order, Query order detail, Query orders by date interval)
* StaticsController (Query customer's monthly statics data)

-------------------------------------

#### **Swagger**


http://localhost:8092/swagger-ui.html#/


-------------------------------------



#### **Authentication**

curl --location --request POST 'http://localhost:8092/authenticate' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username":"readingisgood",
    "password":"12345"
}'


-------------------------------------

#### **Postman Collection Directory**

reading-is-good\postman-collection

-------------------------------------



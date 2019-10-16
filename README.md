This project contains working functionality of a shopping cart allowing user to add, update and delete products in the cart. It also provide facility to user to place order and fetch the details, considering some of the exceptions that may occur during the whole process such as ResourceNotFoundException, OutOfStockException and ItemCannotUpdateException.

Features
-Implemented Oops concept
-Build the spring boot project which is interacting with the MySql DB
-Smoothly haandled the exception

Technical Description and Workflow:
This project contains 3 entities - Stock, Cart and Order.
Stock - It is like a warehouse which contains all information of the products
Cart - User specific collection of products
Order - Details of products purchased by user
User need to first add items into the cart. User can modify the cart as per requirement. Then finally user can place the order. During the complete process, user can get any of the 3 exceptions - ResourceNotFoundException, OutOfStockException and ItemCannotUpdateException.

Steps to run:
-The code is available on Github: https://github.com/pd100rab/easy-shopping
-Please execute the DB queries as specified below
-Run the spring boot application
-The APIs/Endpoints are present inside easy-shopping.postman_collection.json file. Please import it into any rest client like Postman.

DB queries:
Create tables stock, order

create table if not exists `mydb`.`stock` (
`id` INT NOT NULL,
`name` VARCHAR(50) NOT NULL,
`quantity` INT NOT NULL,
`total_price` DOUBLE NOT NULL
);
create table if not exists `mydb`.`order` (
`id` INT NOT NULL,
`name` VARCHAR(50) NOT NULL,
`quantity` INT NOT NULL,
`total_price` DOUBLE NOT NULL,
`email_id` VARCHAR(50) NOT NULL,
`booking_date` DATE NOT NULL,
`delivery_date` DATE
);

Insert few records into stock table:
INSERT INTO `mydb`.`stock` (`id`,`name`,`quantity`,`total_price`,`email_id`,`booking_date`,`delivery_date`) 
VALUES ('1','book', '2', '100');
INSERT INTO `mydb`.`stock` (`id`,`name`,`quantity`,`total_price`) VALUES ('2','computer', '2', '20000'); 
INSERT INTO `mydb`.`stock` (`id`,`name`,`quantity`,`total_price`) VALUES ('3','mobile', '4', '10000');

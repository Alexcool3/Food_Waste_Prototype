<?php
require "connection.php";

$user_name  = $_POST["identifier_username"];

$user_name_category = $user_name.'Category';
$user_name_input = $user_name.'Input';

$query = "DROP TABLE $user_name_category";
mysqli_query($conn, $query);

$query = "DROP TABLE $user_name_input";
mysqli_query($conn, $query);

$query_category = "CREATE TABLE $user_name_category(
    id INT(255) NOT NULL PRIMARY KEY UNIQUE AUTO_INCREMENT, 
    category VARCHAR(20) NOT NULL, 
    price FLOAT(24) NOT NULL  
)";

mysqli_query($conn, $query_category);

$query_input = "CREATE TABLE $user_name_input(
    id INT(255) NOT NULL PRIMARY KEY UNIQUE AUTO_INCREMENT, 
    category VARCHAR(20) NOT NULL, 
    weight FLOAT(24) NOT NULL,  
    type INT(255) NOT NULL, 
    time FLOAT(25) NOT NULL,
    date varchar(20) NOT NULL
)";

mysqli_query($conn, $query_input);

?>


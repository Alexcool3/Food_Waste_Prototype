<?php
require "connection.php";

$user_name  = $_POST["identifier_username"];
$category   = $_POST["identifier_category"];
$price      = $_POST["identifier_price"];


$user_name_category = $user_name.'Category';

$query = "INSERT INTO $user_name_category (category, price) VALUES ('$category', '$price')";
//$query = "INSERT INTO miaCategory(category, price) VALUES ('bbdjr', '5')";
//mysqli_query($conn, $query);

if(mysqli_query($conn, $query)){
    $latest_id =  mysqli_insert_id($conn);
    echo "true,".$latest_id;
} else {
    echo "false, Did not work! :(";
}

?>
<?php
require "connection.php";

$user_name  = $_POST["identifier_username"];
$weight     = $_POST["identifier_weight"];
$category   = $_POST["identifier_category"];
$type       = $_POST["identifier_type"];
$time       = $_POST["identifier_time"];
$date       = $_POST["identifier_date"];
    
$user_name_input = $user_name.'Input';

$query = "INSERT INTO $user_name_input(category, weight, type, time, date) VALUES ('$category', '$weight', '$type', '$time', '$date')";
//$query = "INSERT INTO table_name(category, weight) VALUES ('category_value', 'weight_value')";
if(mysqli_query($conn, $query)){
    $latest_id =  mysqli_insert_id($conn);
    echo "true,".$latest_id;
} else {
    echo "false, Did not work! :(";
}

?>
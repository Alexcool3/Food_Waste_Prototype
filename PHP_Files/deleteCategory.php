<?php
require "connection.php";

$user_name  = $_POST["identifier_username"];
$category   = $_POST["identifier_category"];

$user_name_category = $user_name.'Category';
$user_name_inputs = $user_name.'Input';

$query = "DELETE FROM $user_name_category WHERE category = '$category'";
mysqli_query($conn, $query);

$query = "DELETE FROM $user_name_inputs WHERE category = '$category'";
mysqli_query($conn, $query);
?>


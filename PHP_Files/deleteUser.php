<?php
require "connection.php";

$user_name  = $_POST["identifier_username"];

$user_name_category = $user_name.'Category';
$user_name_inputs = $user_name.'Input';

$query = "DROP TABLE $user_name_category";
mysqli_query($conn, $query);

$query = "DROP TABLE $user_name_inputs";
mysqli_query($conn, $query);

$query = "DELETE FROM users WHERE username = '$user_name'";
mysqli_query($conn, $query);

?>
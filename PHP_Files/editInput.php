<?php
require "connection.php";

$user_name  = $_POST["identifier_username"];
$weight      = $_POST["identifier_weight"];
$id = $_POST["identifier_id"];
$type = $_POST["identifier_type"];

$user_name_input = $user_name.'Input';

$query = "UPDATE $user_name_input SET weight = '$weight', type = '$type' WHERE id = '$id'";
//$query = "UPDATE jesInput SET weight = '57' WHERE id = '39'";
mysqli_query($conn, $query);

?>
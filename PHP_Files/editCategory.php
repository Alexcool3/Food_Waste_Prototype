<?php
require "connection.php";

$user_name  = $_POST["identifier_username"];
$id = $_POST["identifier_id"];
$category = $_POST["identifier_category"];
$price = $_POST["identifier_price"];

$current_category = "";
$user_name_category = $user_name.'Category';
$user_name_input = $user_name.'Input';

$mysql_query = "SELECT * FROM $user_name_category WHERE id = '$id'";

$result = mysqli_query($conn, $mysql_query);

if(mysqli_num_rows($result)>0){
	$row = mysqli_fetch_assoc($result);
    $current_category = $row["category"];
    
}


$query = "UPDATE $user_name_category SET category = '$category', price = '$price' WHERE id = '$id'";
//$query = "UPDATE miaCategory SET category = 'hest', price = '48' WHERE id = '25'";
mysqli_query($conn, $query);

$query = "UPDATE $user_name_input SET category = '$category' WHERE category = '$current_category'";
//$query = "UPDATE miaInput SET category = 'hest' WHERE category = 'jdjd'";
mysqli_query($conn, $query);

?>




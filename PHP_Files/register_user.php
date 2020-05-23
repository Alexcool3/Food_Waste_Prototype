<?php
require "connection.php";

$user_name = $_POST["identifier_username"];
$user_pass = $_POST["identifier_password"];

$query = "INSERT INTO users(username, password) VALUES ('$user_name','$user_pass')";
mysqli_query($conn, $query);

$user_name_category = $user_name.'Category';
$query_category = "CREATE TABLE $user_name_category(
    id INT(255) NOT NULL PRIMARY KEY UNIQUE AUTO_INCREMENT, 
    category VARCHAR(20) NOT NULL, 
    price FLOAT(24) NOT NULL  
)";

mysqli_query($conn, $query_category);

$user_name_input = $user_name.'Input';
$query_input = "CREATE TABLE $user_name_input(
    id INT(255) NOT NULL PRIMARY KEY UNIQUE AUTO_INCREMENT, 
    category VARCHAR(20) NOT NULL, 
    weight FLOAT(24) NOT NULL,  
    type INT(255) NOT NULL,
    time FLOAT(25) NOT NULL,
    date varchar(20) NOT NULL
)";

mysqli_query($conn, $query_input);

$mysql_query = "SELECT * FROM users WHERE username = '$user_name' AND password = '$user_pass'";

$result = mysqli_query($conn, $mysql_query);

if(mysqli_num_rows($result)>0){
	$row = mysqli_fetch_assoc($result);
    $id = $row["id"];
	echo "true,".$id;
}
else{
	echo "false, Did not work! :(";
}

?>
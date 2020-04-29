<?php
require "connection.php";

$user_username = $_POST["identifier_username"];
$user_password = $_POST["identifier_password"];

$mysql_query = "SELECT * FROM users WHERE username = '$user_username' AND password = '$user_password'";

$result = mysqli_query($db_con, $mysql_query);

if(mysqli_num_rows($result)>0){
	$row = mysqli_fetch_assoc($result);
	$username = $row["username"];
	$password = $row["password"];

	//Here is the specially formatted string response.. ie: "ServerResponse".
	//It is of the form: "boolean,email,name"
	echo "true,".$username.",".$password;
}
else{
	echo "Login was not successful... :(";
}

?>

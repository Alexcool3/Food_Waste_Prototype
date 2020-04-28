<?php
  require "connection.php";

$user_name = $_POST["identifier_name"];
$user_pass = $_POST["identifier_password"];

$query = "INSERT INTO users(name, password) VALUES ('$user_name','$user_pass')";

if (mysqli_query($db_con, $query)) {
  echo"<h2>Data Successfully Inserted!</h2>";
}else {
  echo "<h2>Data was unable to be inserted into database :(.</h2>"
}

?>

<?php

require"connection.php";

$user_name  = $_POST["identifier_username"];
$id   = $_POST["identifier_id"];

$user_name_input = $user_name.'Input';

$query = "DELETE FROM $user_name_input WHERE id = '$id'";

mysqli_query($conn, $query);

?>
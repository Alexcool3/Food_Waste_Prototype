<?php
// serverName, username, password, database name
$conn=mysqli_connect("localhost", "id13475849_nikofisk", "NikolajNikolaj95;", "id13475849_androiddb");

if (!$conn) {
die("Connection failed!" . mysqli_connect_error());
}else {
    //echo "Connect success!";
}
?>
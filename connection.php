<?php
  // serverName, username, password, database name
  $db_con=mysqli_connect("mysql4.gear.host", "foodwaste1", "Sd8KdgwtY~_4", "foodwaste1");

  if (!$db_con) {
    die("Connection failed!" . nysqli_connect_error());
  }else {
    echo "Connect success!";
  }
?>

	<?php
	
	session_start();	
	//Incluindo a conexão com banco de dados
	include_once("DatabaseConnection.php");	
	
	$url="127.0.0.1";
	$database="culturamysql"; // Alterar nome da BD se necessario
    $conn = mysqli_connect($url,$_POST['username'],$_POST['password'],$database);
	$query_select = "SELECT current_role()";
	$result_query_select = mysqli_query($conn, $query_select); 
	$conn->next_result();
	$role = mysqli_fetch_assoc($result_query_select)['current_role()'];
	if($role == "Investigador") { // Alterar role se necessario
		$response["valid"] = array();
		$json = json_encode($response["valid"]);
		echo $json;
	}
	header("Location: Home.php");
	$result_query_select->close();
	mysqli_close ($conn);
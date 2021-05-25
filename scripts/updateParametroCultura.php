<?php
	$url="127.0.0.1";
	$database="culturamysql"; // Alterar nome da BD se necessario
    $conn = mysqli_connect($url,$_POST['username'],$_POST['password'],$database);
	$sql = "call Alterar_ParametroCultura_Investigador('". $_POST['username'] ."',
	'". $_POST['username'] ."',
	'". $_POST['culturaID'] ."',
	'". $_POST['MinHumidade'] ."',
	'". $_POST['MaxHumidade'] ."',
	'". $_POST['MinTemperatura'] ."',
	'". $_POST['MaxTemperatura'] ."',
	'". $_POST['MinLuz'] ."',
	'". $_POST['MaxLuz'] ."',
	'". $_POST['DangerZoneMinHumidade'] ."',
	'". $_POST['DangerZoneMaxHumidade'] ."',
	'". $_POST['DangerZoneMinTemperatura'] ."',
	'". $_POST['DangerZoneMaxTemperatura'] ."',
	'". $_POST['DangerZoneMinLuz'] ."',
	'". $_POST['DangerZoneMaxLuz'] ."'

	)";
	$result = mysqli_query($conn, $sql);


	mysqli_close ($conn);
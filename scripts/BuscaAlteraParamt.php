<?php
	
session_start();	
	//Incluindo a conexão com banco de dados
	include_once("DatabaseConnection.php");

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "culturamysql";

//conecta a db
$conn = mysqli_connect($servidor, $usuario, $senha, $dbname);
	$IdPara = $_POST['IdParametroCultura '];
	$IdCult = $_POST['IdCultura'];
	$MinH = $_POST['MinHumidade'];
	$MaxH = $_POST['MaxHumidade'];
	$MinT = $_POST['MinTemperatura'];
	$MaxT = $_POST['MaxTemperatura'];
	$MinL = $_POST['MinLuz'];
	$MaxL = $_POST['MaxLuz'];
	$DMinH = $_POST['DangerZoneMinHumidade'];
	$DMaxH = $_POST['DangerZoneMaxHumidade'];
	$DMinT = $_POST['DangerZoneMinTemperatura'];
	$DMaxT = $_POST['DangerZoneMaxTemperatura'];
	$DMinL = $_POST['DangerZoneMinLuz'];
	$DMaxL = $_POST['DangerZoneMaxLuz'];
	
	
	
	$result = "INSERT INTO parametrocultura(IdParametroCultura, IdCultura, MinHumidade, MaxHumidade, 
	MinTemperatura, MaxTemperatura,MinLuz,MaxLuz,DangerZoneMinHumidade,DangerZoneMaxHumidade,DangerZoneMinTemperatura,
	DangerZoneMaxTemperatura,DangerZoneMinLuz,DangerZoneMaxLuz created) VALUES ('$IdPara', '$IdCult', '$MinH', '$MaxH'
	,'$MinT','$MaxT','$MinL','$MaxL','$DMinH','$DMaxH','$DMinT','$DMaxT','$DMinL','$DMaxL', NOW())";
	$resultado= mysqli_query($conn, $result)
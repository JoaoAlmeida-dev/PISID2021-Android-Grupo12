<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "culturamysql";

$conn = mysqli_connect($servername, $username, $password, $dbname);

//Receber a requisão da pesquisa 


/*
	$IdPara = filter_input(INPUT_POST, 'IdParametroCultura', FILTER_SANITIZE_NUMBER_INT);
	$IdCult = filter_input(INPUT_POST, 'IdCultura', FILTER_SANITIZE_NUMBER_INT);
	$MinH = filter_input(INPUT_POST, 'MinHumidade', FILTER_SANITIZE_NUMBER_INT);
	$MaxH = filter_input(INPUT_POST, 'MaxHumidade', FILTER_SANITIZE_NUMBER_INT);
	$MinT = filter_input(INPUT_POST, 'MinTemperatura', FILTER_SANITIZE_NUMBER_INT);
	$MaxT = filter_input(INPUT_POST, 'MaxTemperatura', FILTER_SANITIZE_NUMBER_INT);
	$MinL = filter_input(INPUT_POST, 'MinLuz', FILTER_SANITIZE_NUMBER_INT);
	$MaxL = filter_input(INPUT_POST, 'MaxLuz', FILTER_SANITIZE_NUMBER_INT);
	$DMinH = filter_input(INPUT_POST, 'DangerZoneMinHumidade', FILTER_SANITIZE_NUMBER_INT);
	$DMaxH = filter_input(INPUT_POST, 'DangerZoneMaxHumidade', FILTER_SANITIZE_NUMBER_INT);
	$DMinT = filter_input(INPUT_POST, 'DangerZoneMinTemperatura', FILTER_SANITIZE_NUMBER_INT);
	$DMaxT = filter_input(INPUT_POST, 'DangerZoneMaxTemperatura', FILTER_SANITIZE_NUMBER_INT);
	$DMinL = filter_input(INPUT_POST, 'DangerZoneMinLuz', FILTER_SANITIZE_NUMBER_INT);
	$DMaxL = filter_input(INPUT_POST, 'DangerZoneMaxLuz', FILTER_SANITIZE_NUMBER_INT);*/
	
	
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
	
		
	
	$result = "UPDATE parametrocultura SET MinHumidade=".$MinH.", MaxHumidade=".$MaxH.", 
	MinTemperatura=".$MinT.", MaxTemperatura=".$MaxT.",MinLuz=".$MinL.",MaxLuz=".$MaxL.",DangerZoneMinHumidade=".$DMinH.",DangerZoneMaxHumidade=".$DMaxH.",
	DangerZoneMinTemperatura=".$DMinT.",
	DangerZoneMaxTemperatura=".$DMaxT.",DangerZoneMinLuz=".$DMinL.",DangerZoneMaxLuz=".$DMaxL." WHERE IdCultura=".$IdCult.";";
	
	/*$result = "INSERT INTO parametrocultura(IdParametroCultura, IdCultura, MinHumidade, MaxHumidade, 
	MinTemperatura, MaxTemperatura,MinLuz,MaxLuz,DangerZoneMinHumidade,DangerZoneMaxHumidade,DangerZoneMinTemperatura,
	DangerZoneMaxTemperatura,DangerZoneMinLuz,DangerZoneMaxLuz) VALUES ('$IdPara', '$IdCult', '$MinH', '$MaxH'
	,'$MinT','$MaxT','$MinL','$MaxL','$DMinH','$DMaxH','$DMinT','$DMaxT','$DMinL','$DMaxL')";*/
	
	$resultado= mysqli_query($conn, $result);
	
	header("Location: AlterarParametro.php");
	

	/*
	$result = "INSERT INTO parametrocultura(IdParametroCultura, IdCultura, MinHumidade, MaxHumidade, 
	MinTemperatura, MaxTemperatura,MinLuz,MaxLuz,DangerZoneMinHumidade,DangerZoneMaxHumidade,DangerZoneMinTemperatura,
	DangerZoneMaxTemperatura,DangerZoneMinLuz,DangerZoneMaxLuz created) VALUES ('$IdPara', '$IdCult', '$MinH', '$MaxH'
	,'$MinT','$MaxT','$MinL','$MaxL','$DMinH','$DMaxH','$DMinT','$DMaxT','$DMinL','$DMaxL')";
	
	$resultado= mysqli_query($conn, $result);*/
?>	
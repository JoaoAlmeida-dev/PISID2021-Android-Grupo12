<?php
	$url="127.0.0.1";
	$database="culturamysql"; // Alterar nome da BD se necessario
    $conn = mysqli_connect($url,$_POST['username'],$_POST['password'],$database);
	$sql = "call Alterar_ParametroCultura_Investigador('". $_POST['username'] ."',
	'". $_POST['IdParametroCultura'] ."',
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
	
	$response["idparametro"] = array();
	if ($result){
		if (mysqli_num_rows($result)>0){
			while($r=mysqli_fetch_assoc($result)){
				$ad = array();
				$ad["IdParametroCultura"] = $r['IdParametroCultura'];
				array_push($response["idparametro"], $ad);
			}
		}	
	}
	$json = json_encode($response["idparametro"]);
	echo $json;

	mysqli_close ($conn);
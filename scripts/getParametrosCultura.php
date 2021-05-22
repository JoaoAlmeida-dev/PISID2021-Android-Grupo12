	<?php
	$url="127.0.0.1";
	$database="culturamysql"; // Alterar nome da BD se necessario
    $conn = mysqli_connect($url,$_POST['username'],$_POST['password'],$database);

	$sql = "SELECT IdParametroCultura,IdCultura,
	MinHumidade,MaxHumidade,MinLuz,MaxLuz,MinTemperatura,MaxTemperatura,
	DangerZoneMinHumidade,DangerZoneMaxHumidade,DangerZoneMinLuz,DangerZoneMaxLuz,DangerZoneMinTemperatura,DangerZoneMaxTemperatura
	FROM parametrocultura,cultura,utililizador
	WHERE parametrocultura.IdCultura = '". $_POST['culturaID'] ."'
	AND parametrocultura.IdCultura = cultura.IdCultura
	AND cultura.idUtilizador = utilizador.IdUtilizador
	AND utilizador.NomeUtilizador = '". $_POST['username'] ."'";
	
	//$sql = "call Selecionar_ParametrosCultura('". $_POST['username'] ."','". $_POST['culturaID'] ."')";
	
	$result = mysqli_query($conn, $sql);	
	$response["medicoes"] = array();
	if ($result){
		if (mysqli_num_rows($result)>0){
			while($r=mysqli_fetch_assoc($result)){
				$ad = array();
				
				$ad["IdParametroCultura"] = $r['IdParametroCultura'];
				$ad["IdCultura"] = $r['IdCultura'];
				$ad["MinHumidade"] = $r['MinHumidade'];
				$ad["MaxHumidade"] = $r['MaxHumidade'];
				$ad["MinTemperatura"] = $r['MinTemperatura'];
				$ad["MaxTemperatura"] = $r['MaxTemperatura'];
				$ad["MinLuz"] = $r['MinLuz'];
				$ad["MaxLuz"] = $r['MaxLuz'];
				
				$ad["DangerZoneMinHumidade"] = $r['DangerZoneMinHumidade'];
				$ad["DangerZoneMaxHumidade"] = $r['DangerZoneMaxHumidade'];
				$ad["DangerZoneMinTemperatura"] = $r['DangerZoneMinTemperatura'];
				$ad["DangerZoneMaxTemperatura"] = $r['DangerZoneMaxTemperatura'];
				$ad["DangerZoneMinLuz"] = $r['DangerZoneMinLuz'];
				$ad["DangerZoneMaxLuz"] = $r['DangerZoneMaxLuz'];
				
				array_push($response["medicoes"], $ad);
			}
		}	
	}
	$json = json_encode($response["medicoes"]);
	echo $json;
	mysqli_close ($conn);
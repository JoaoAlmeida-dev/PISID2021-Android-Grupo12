<?php
	$url="127.0.0.1";
	$database="culturamysql"; // Alterar nome da BD se necessario
    $conn = mysqli_connect($url,$_POST['username'],$_POST['password'],$database);
	$sql = "call Selecionar_Cultura()";
	$result = mysqli_query($conn, $sql);

	$response["avisos"] = array();

	if ($result){
		if (mysqli_num_rows($result)>0){
			while($r=mysqli_fetch_assoc($result)){
				//echo $r[0];
				$ad = array();
				// Completar com todos os campos da tabela cultura
				$ad["IdCultura"]             = $r['IdCultura'];
				$ad["NomeCultura"]           = $r['NomeCultura'];
				$ad["IdUtilizador"]          = $r['IdUtilizador'];
				$ad["Estado"]                = $r['Estado'];
				$ad["IdZona"]                = $r['IdZona'];

				array_push($response["avisos"], $ad);
			}
		}
	}
	$json = json_encode($response["avisos"]);
	echo $json;
	mysqli_close ($conn);
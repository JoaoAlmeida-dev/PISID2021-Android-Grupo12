	<?php
	$url="127.0.0.1";
	$database="culturamysql"; // Alterar nome da BD se necessario	
    $conn = mysqli_connect($url,$_POST['username'],$_POST['password'],$database);	
	// Alterar nome da tabela Alerta e nome do campo Hora se necessario
	//$sql = "SELECT * from Alerta where DATE(Alerta.Hora) = '" . $_POST['date'] . "';";
	//$sql = "call Selecionar_Alerta("+$_POST['idUtilizador']+")";
	$sql = "call Selecionar_Alerta";
	$result = mysqli_query($conn, "call Selecionar_Alerta");

	$response["avisos"] = array();

	if ($result){
		if (mysqli_num_rows($result)>0){
			while($r=mysqli_fetch_assoc($result)){
				//echo $r[0];
				$ad = array();
				// Completar com todos os campos da tabela alerta
				$ad["IdAlerta"]             = $r['IdAlerta'];
				$ad["IdZona"]               = $r['IdZona'];
				$ad["IdSensor"]             = $r['IdSensor'];
				$ad["Hora"]                 = $r['Hora'];
				$ad["Leitura"]              = $r['Leitura'];
				$ad["TipoAlerta"]           = $r['TipoAlerta'];
				$ad["Cultura"]              = $r['Cultura'];
				$ad["IdUtilizador"]         = $r['IdUtilizador'];
				$ad["HoraEscrita"]          = $r['HoraEscrita'];
				$ad["NivelAlerta"]          = $r['NivelAlerta'];
				$ad["IdParametroCultura"]   = $r['IdParametroCultura'];

				array_push($response["avisos"], $ad);
			}
		}	
	}
	$json = json_encode($response["avisos"]);
	echo $json;
	mysqli_close ($conn);
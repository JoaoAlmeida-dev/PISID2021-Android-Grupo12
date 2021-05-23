<?php
session_start();
//Incluindo a conexão com banco de dados
include_once("DatabaseConnection.php");

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>AlterarParametroCultura</title>
    <style type="text/css">
        * { margin: 0; padding: 0; font-family:Tahoma; font-size:;}
        #divCenter {
                background-color: #e1e1e1;
                width: 400px;
                height: 850px;
                left: 50%;
                margin: -130px 0 0 -210px;
                padding:10px;
                position: absolute;
                top: 20%; }
    </style>
</head>
<body>
	
<div id="divCenter">
    <h1>AlterarParametroCultura</h1>
	<form method="POST" action="BuscaAlteraParamt.php">
        <br/>
        <br/>
		IdCultura: <input type="IdCultura" name="IdCultura"  pattern="[0-9]+"><br/>
		<br/>
        <br/>
        MinHumidade: <input type="MinHumidade" name="MinHumidade"  step="0.010"><br/>
        <br/>
        <br/>
        MaxHumidade: <input type="MaxHumidade" name="MaxHumidade"  step="0.010"><br/>
        <br/>
        <br/>
        MinTemperatura: <input type="MinTemperatura" name="MinTemperatura" step="0.010"><br/>
        <br/>
        <br/>
        MaxTemperatura: <input type="MaxTemperatura" name="MaxTemperatura" step="0.010"><br/>
        <br/>
        <br/>
        MinLuz: <input type="MinLuz" name="MinLuz"  step="0.010"><br/>
        <br/>
        <br/>
        MaxLuz: <input type="MaxLuz" name="MaxLuz"  step="0.010"><br/>
        <br/>
        <br/>
        DangerZoneMinHumidade: <input type="DangerZoneMinHumidade" name="DangerZoneMinHumidade"  step="0.010"><br/>
        <br/>
        <br/>
        DangerZoneMaxHumidade: <input type="DangerZoneMaxHumidade" name="DangerZoneMaxHumidade" step="0.010"><br/>
        <br/>
        <br/>
        DangerZoneMinTemperatura: <input type="DangerZoneMinTemperatura" name="DangerZoneMinTemperatura" step="0.010"><br/>
        <br/>
        <br/>
        DangerZoneMaxTemperatura: <input type="DangerZoneMaxTemperatura" name="DangerZoneMaxTemperatura" step="0.010"><br/>
        <br/>
        <br/>
        DangerZoneMinLuz: <input type="DangerZoneMinLuz" name="DangerZoneMinLuz"  step="0.010"><br/>
        <br/>
        <br/>
        DangerZoneMaxLuz: <input type="DangerZoneMaxLuz" name="DangerZoneMaxLuz"  step="0.010"><br/>
        <br/>
		<input type="submit" value="Salvar">
		<center><a href="Home.php"> <input type="button" value="Voltar para pagina inicial"></a></br></center>
    </form>
</div>
</body>
</html>
<?php
	
session_start();	
	//Incluindo a conexão com banco de dados
	include_once("DatabaseConnection.php");
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Listar Culturas</title>

  <link href="//cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet">
  
	
</head>
	<center><h1>Lista das Culturas</h1></center>
<body>
	<table id="minhaTabela" class="display" style="width:100%">
		<thead>
		  <tr>
			<th>NomeCultura</th>
			<th>IdUtilizador</th>
			<th>Estado</th>
			<th>IdZona</th>
		  </tr>
		</thead>

    </table>
	
	<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
	  <script src="//cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
	
	  <script type="text/javascript" language="javascript">
		$(document).ready(function() {
			$('#minhaTabela').DataTable({			
				"processing": true,
				"serverSide": true,
				"ajax": {
					"url": "BuscarCultura_pesq_user.php",
					"type": "POST"
				}
			});
		} );
		</script>
		
		<center><a href="Home.php"> <input type="button" value="Voltar para pagina inicial"></a></br></center>
</body>
</html>

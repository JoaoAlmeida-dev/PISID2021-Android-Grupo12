<?php
//Start session
	session_start();
	
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<META content="text/html; charset=utf-8" http-equiv="content-type">
<link rel="shortcut icon" type="image/x-icon" href="../favicon.ico" />
<script src="js/1.11.0/jquery.min.js"></script>
<script src="ws-lightbox.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="ws-lightbox.css">
<SCRIPT language=JavaScript>
<!--
var message="Unautorized.";

///////////////////////////////////
function clickIE4(){
if (event.button==2){
alert(message);
return false;
}
}

function clickNS4(e){
if (document.layers||document.getElementById&&!document.all){
if (e.which==2||e.which==3){
alert(message);
return false;
}
}
}

if (document.layers){
document.captureEvents(Event.MOUSEDOWN);
document.onmousedown=clickNS4;
}
else if (document.all&&!document.getElementById){
document.onmousedown=clickIE4;
}

document.oncontextmenu=new Function("alert(message);return false")

// --> 
</SCRIPT>
</head>
<body bgcolor="##cce6ff">
  
<!-- <br><br><br><br><br><br> -->
<table align = "center"  width ="500">
   <tr>
     <td HEIGHT="150">
       <div>
        <font color="#cce6ff">
        <?php
          if( isset($_SESSION['ERRMSG_ARR']) && is_array($_SESSION['ERRMSG_ARR']) && count($_SESSION['ERRMSG_ARR']) >0 ) {
          echo '<ul class="err">';
          foreach($_SESSION['ERRMSG_ARR'] as $msg) {
          echo '<li>',$msg,'</li>';
          }
          echo '</ul>';
          unset($_SESSION['ERRMSG_ARR']);
          }
        ?>
        </font>
	   </div>
     </td>
	</tr>
	<tr>
	  <td>
       <br><br><br>
       <form action="validateLogin.php" method="post" name="login">
       <center>
	   <table width="250">
            <tr>
                <td width="50%">
					<b>Username</b><label for="username"></label>
                </td>
				<td width="50%">
					<input type="userName" name="username" id="inputUsername" /><br>
                </td>
            </tr>
			<tr>
                <td width="50%">
					<b>Password</b><label for="pass"></label>
                </td>
				<td width="50%">
					<input type="password" name="password" id="inputPassword"/><br> <!--   password -->
                 </td>
			</tr>
				<table>
				  <tr>
				    <td>
                     <center>					
					 <input type="submit" value="Login" id="but"/><br>
                     </center><br>
					</td>
				 </TR>
				</table>
			<tr>
		</table>						   
		</form>
		</center>
		<br><br>				
	</td>    
 </tr>
</table>

<center>		
	<table><tr><td>
                <script type="text/javascript">
	$(document).ready(function(){
		$("#lost").click(function(){
			var params = {};
			params.type = 'iframe';
			params.src = './lost_pw.php';
			params.height = 200;
			params.width = 800;
			params.overflow_hidden = false;
			// Opens the lightbox
			ws_lightbox.open(params);
		});
	});
	</script>
    </td></a></tr>
	</table>
</center>		
		
</body>
</html>
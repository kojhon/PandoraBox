<?php session_start(); ?>
<html>
<head>
<meta charset="UTF-8" />
</head>
<body>
<?php
include_once("./PandoraBox/PandoraBoxClient.php");
if (!isset($_GET['ip'])){
	$ip = "127.0.0.1";
}else{
	$ip = $_GET['ip'];
}
if (!isset($_GET['port'])){
	$port = 10001;
}else{
	$port = $_GET['port'];
}
if (!isset($_SESSION['cityList'])){
	$client = new PandoraBoxClient($ip,$port);
	$_SESSION['cityList'] = $client->getResult(iconv("UTF-8","UTF-16BE","getCities"));
}
if (isset($_POST['smb2'])){
	$client = new PandoraBoxClient($ip,$port);
	$_SESSION['cityList'] = $client->getResult(iconv("UTF-8","UTF-16BE","getCities"));
}
$result = $_SESSION['cityList'];
	if ($result[0] === false){
?>
<form action="" method="POST">
<table>
<tr>
<td>
Выберите город
</td>
<td>
<select name="city">
<?php
		$len = count($result[1]);
		for ($i = 0; $i < $len; $i++){
			$val = iconv("UTF-16BE","UTF-8",$result[1][$i]);
?>
<option value="<?php echo $val;?>"><?php echo $val;?></option>
<?php
		}
?>
</select>
</td>
</tr>
<tr>
<td>
	<input type="submit" name="smb" value="Узнать погоду"/>
</td>
<td>
	<input type="submit" name="smb2" value="Обновить"/>
</td>
</tr>
</table>
</form>
<?php }else {?>
	<h2>
<?php 
	$error = iconv("UTF-16BE","UTF-8",$result[1]);
	echo $error;
?>
	</h2>
<?php }
if (isset($_POST['smb'])){
	$client = new PandoraBoxClient($ip,$port);
	$result = $client->getResult(iconv("UTF-8","UTF-16BE","getWeather"),iconv("UTF-8","UTF-16BE",$_POST['city']));
//	var_dump($_POST);
//	$result = $client->getResult("getWeather",$_POST['city']);
	echo "Город:".$_POST['city'].".Температура:".iconv("UTF-16BE","UTF-8",$result['1']);
}
?>
</body>
</html>

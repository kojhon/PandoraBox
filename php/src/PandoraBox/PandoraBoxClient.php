<?php
class PandoraBoxClient{
	var $serverIp;
	var $serverPort;
	function PandoraBoxClient($_serverIP,$_serverPort){
		$this->serverIp = $_serverIP;
		$this->serverPort = $_serverPort;
	}
	
	function getResult(){
		include_once("Decoder.php");
		include_once("Encoder.php");
		$result;
		$argc = func_num_args();
		
		if ($argc == 0){
			$result[0] = true;
			$result[1] = iconv("UTF-8","UTF-16BE","Wrong number of parameters for PandoraBoxClient");
			return $result;
		}
		
		for ($i = 0; $i < $argc; $i++){
			$data[$i] = func_get_arg($i);
		}
		
		$protocolEncoder = new Encoder();
		$protocolDecoder = new Decoder();
		$msg = $protocolEncoder->encodeRequest($data);
		$strlen = strlen($msg);
		try {
     		$socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
			if ($socket < 0) {
	        	throw new Exception(iconv("UTF-8","UTF-16BE",'socket_create() failed: '.socket_strerror(socket_last_error())."\n"));
	        }
			
			$connection = socket_connect($socket, $this->serverIp, $this->serverPort);
	        if ($connection === false) {
	            throw new Exception(iconv("UTF-8","UTF-16BE",'socket_connect() failed: '.socket_strerror(socket_last_error())."\n"));
	        }
			socket_write($socket,$msg,strlen($msg));
			
			$resmsg = socket_read($socket,1024,PHP_BINARY_READ);	
			$len = strlen($resmsg);
			
			$result = $protocolDecoder->encodeResult($resmsg);
		}catch(Exception $e){
	         $result[0] = true;
			 $result[1] = $e->getMessage();
     	}
		
		return $result;
	}
	
}
?>
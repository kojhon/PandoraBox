<?php
class PandoraBoxServer{
	private $functions;
	private $adress;
	private $port;
	
	public function PandoraBoxServer($_adress,$_port){
		$this->adress = $_adress;
		$this->port = $_port;
	}
		
	public function publicateFunction($fullname,$shortname){
		$this->functions[$shortname][0] = $fullname;
	}
	
	public function publicateMethod($fullname,$shortname,$object){
		$this->functions[$shortname][0] = $fullname;
		$this->functions[$shortname][1] = $object;
	}
	
	public function start(){
		include_once("Decoder.php");
		include_once("Encoder.php");
		error_reporting(E_ALL ^ E_WARNING);
		set_time_limit(0);
		ob_implicit_flush();

		try {
			if (($sock = socket_create(AF_INET, SOCK_STREAM, SOL_TCP)) < 0) {
				throw new Exception('socket_create() failed: '.socket_strerror(socket_last_error())."\n");
			}

			
			if (($ret = socket_bind($sock, $this->adress, $this->port)) < 0) {
				throw new Exception('socket_bind() failed: '.socket_strerror(socket_last_error())."\n");
			}

			if (($ret = socket_listen($sock, 5)) < 0) {
				throw new Exception('socket_listen() failed: '.socket_strerror(socket_last_error())."\n");
			}

			do {
				
				if (($msgsock = socket_accept($sock)) < 0) {
					throw new Exception('socket_accept() failed: '.socket_strerror(socket_last_error())."\n");
				}
				
				$msg = socket_read($msgsock,1024);
				
				$protocolEncoder = new Encoder();
				$protocolDecoder = new Decoder();
				$argv = $protocolDecoder->encodeRequest($msg);
				//debug($argv);
				$msg2 = $protocolEncoder->encodeResult($argv);
				socket_write($msgsock,$msg2,strlen($msg2));
				socket_close($msgsock);
				 
			} while (true);

		} catch (Exception $e) {
			echo "\nError: ".$e->getMessage();
		}
		 
		if (isset($sock)) {
		 
			
			socket_close($sock);
			
		 
		}
		
		
	}
	
	private function invoke($recivedInf){
		$result;
		if (isset($this->functions[$recivedInf['function_name']])){
			$functionInf = $this->functions[$recivedInf['function_name']];
			
			if (count($functionInf) == 1){
				$function = $functionInf[0];
			}else{
				$function = array($functionInf[1],$functionInf[0]);
			}	
			
			
			$argc = $recivedInf['count'];
			switch($argc){
				case(0):
					$result[0] = false;
					$result[1] = call_user_func($function);
					return $result;
				case(1):
					$result[0] = false;
					$result[1] = call_user_func($function,$recivedInf[0]);
					return $result;
				default:
					$result[0] = false;
					for($i = 0; $i < count; $i++){
						$arguments[$i] = $recivedInf[$i];
					}
					$result[1] = call_user_func_array($function,$arguments);
					return $result;
			}	
		}else{
			$result[0] = true;
			$result[1] = iconv("UTF-8","UTF-16BE","No such publicated function");
		}
		
		
	}
}

?>
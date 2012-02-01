<?php 
function debug($data){
	echo "<br>";
	var_dump($data);
	echo "<br>";
}

class protocol_encoder{
	var $data;
	var $pointer;
	
	public function encode($string){
		$this->pointer = 0;
		$this->data = $string;
		$tmp = ord($this->data[$this->pointer]);
		$this->pointer++;
		$a[0] = substr($this->data,$this->pointer,$tmp);
		$this->pointer += $tmp;
		$length = ord($this->data[$this->pointer]);
		$this->pointer++;
		//debug($length);
		for ($i = 1; $i < $length+1; $i++){
			$inf[0] = strlen($this->data);
			$inf[1] = $this->pointer;
			$inf[2] = $i;
			//debug($inf);
			
			$tmp = ord($this->data[$this->pointer]);
			$this->pointer++;
			switch ($tmp){
				case (1):
					$ntmp = unpack("c",substr($this->data,$this->pointer,1));
					$this->pointer++;
					$a[$i] = $ntmp[1];
				break;
			
				case (2)://bool block
					$a[$i] = (bool)ord($this->data[$this->pointer]);
					$this->pointer++;
				break;
				
				case(3):
					$ntmp = unpack("s",substr($this->data,$this->pointer,2));
					$this->pointer+=2;
					$a[$i] = $ntmp[1];
				break;
				
				case (5)://integer block
					$ntmp = unpack("i",substr($this->data,$this->pointer,4));
					//var_dump($ntmp);
					$a[$i] = (int)$ntmp[1];
					$this->pointer += 4;
				break;
				
				case(7):
					$ntmp = unpack("l",substr($this->data,$this->pointer,4));
					$this->pointer+=4;
					$a[$i] = $ntmp[1];
				break;
				
				case (9)://float block
					$ntmp = unpack("f",substr($this->data,$this->pointer,4));
					//var_dump($ntmp);
					$a[$i] = (float)$ntmp[1];
					$this->pointer += 4;
				break;
				
				case(10):
					$ntmp = unpack("d",substr($this->data,$this->pointer,8));
					$this->pointer+=8;
					$a[$i] = $ntmp[1];
				break;
				
				case (11)://string block
					$lengthstr =  ord($this->data[$this->pointer]);
					$this->pointer++;
					$a[$i] = substr($this->data,$this->pointer,$lengthstr);
					$this->pointer+=$lengthstr;
				break;
				
				case (12):
					$a[$i] = $this->parse_to_array();
					
				break;
			}
		}
		
		return $a;
	}
	
	private function parse_to_array(){
		//$this->pointer++;
		$dimention = ord($this->data[$this->pointer]);
		$this->pointer++;
		$type = ord($this->data[$this->pointer]);
		$this->pointer++;
		switch($type){
				case (1):
					return $this->byte_parse($dimention);
				break;
			
				case (2)://bool block
					return $this->bool_parse($dimention);
				break;
				
				case(3):
					return $this->short_parse($dimention);
				break;
				
				case (5)://integer block
					return $this->int_parse($dimention);
				break;
				
				case(7):
					return $this->long_parse($dimention);
				break;
				
				case (9)://float block
					return $this->float_parse($dimention);
				break;
				
				case(10):
					return $this->double_parse($dimention);
				break;
				
				case (11)://string block
					return $this->string_parse($dimention);
				break;
		}
	}
	
	private function byte_parse($dimention){
		$length = ord($this->data[$this->pointer]);
		$this->pointer++;
		
		if ($dimention == 1){
			for ($i = 0; $i < $length; $i++){
				$ntmp = unpack("c",substr($this->data,$this->pointer,1));
				$this->pointer++;
				$res[$i] = $ntmp[1];
			}
		}else{
			for ($i = 0; $i < $length; $i++){
				$res[$i] = $this->byte_parse($dimention-1);
			}
		}
		
		return $res;
	}
	
	private function bool_parse($dimention){
		$length = ord($this->data[$this->pointer]);
		$this->pointer++;
		
		if ($dimention == 1){
			for ($i = 0; $i < $length; $i++){
				$res[$i] = (bool)ord($this->data[$this->pointer]);
				$this->pointer++;
			}
		}else{
			for ($i = 0; $i < $length; $i++){
				$res[$i] = $this->bool_parse($dimention-1);
			}
		}
		
		return $res;
	}
	
	private function short_parse($dimention){
		$length = ord($this->data[$this->pointer]);
		$this->pointer++;
		
		if ($dimention == 1){
			for ($i = 0; $i < $length; $i++){
				$ntmp = unpack("s",substr($this->data,$this->pointer,2));
				$this->pointer+=2;
				$res[$i] = $ntmp[1];
			}
		}else{
			for ($i = 0; $i < $length; $i++){
				$res[$i] = $this->short_parse($dimention-1);
			}
		}
		
		return $res;
	}
	
	private function int_parse($dimention){
		$length = ord($this->data[$this->pointer]);
		$this->pointer++;
		//debug($length);
		if ($dimention == 1){
			for ($i = 0; $i < $length; $i++){
				//debug($dimention);
				$ntmp = unpack("i",substr($this->data,$this->pointer,6));
				//debug($ntmp);
				$res[$i] = (int)$ntmp[1];
				$this->pointer += 4;
			}
		}else{
			for ($i = 0; $i < $length; $i++){
				$res[$i] = $this->int_parse($dimention-1);
			}
		}
		//debug($res);
		return $res;
	}
	
	private function long_parse($dimention){
		$length = ord($this->data[$this->pointer]);
		$this->pointer++;
		
		if ($dimention == 1){
			for ($i = 0; $i < $length; $i++){
					$ntmp = unpack("l",substr($this->data,$this->pointer,4));
					$this->pointer+=4;
					$res[$i] = $ntmp[1];
			}
		}else{
			for ($i = 0; $i < $length; $i++){
				$res[$i] = $this->long_parse($dimention-1);
			}
		}
		
		return $res;
	}
	
	private function float_parse($dimention){
		$length = ord($this->data[$this->pointer]);
		$this->pointer++;
		
		if ($dimention == 1){
			for ($i = 0; $i < $length; $i++){
				$ntmp = unpack("f",substr($this->data,$this->pointer,4));
				//var_dump($ntmp);
				$res[$i] = (float)$ntmp[1];
				$this->pointer += 4;
			}
		}else{
			for ($i = 0; $i < $length; $i++){
				$res[$i] = $this->float_parse($dimention-1);
			}
		}
		
		return $res;
	}
	
	private function double_parse($dimention){
		$length = ord($this->data[$this->pointer]);
		$this->pointer++;
		
		if ($dimention == 1){
			for ($i = 0; $i < $length; $i++){
				$ntmp = unpack("d",substr($this->data,$this->pointer,8));
					$this->pointer+=8;
					$res[$i] = $ntmp[1];
			}
		}else{
			for ($i = 0; $i < $length; $i++){
				$res[$i] = $this->double_parse($dimention-1);
				
			}
		}
		
		return $res;
	}
	
	private function string_parse($dimention){
		$length = ord($this->data[$this->pointer]);
		$this->pointer++;
		if ($dimention == 1){
			for ($i = 0; $i < $length; $i++){
				$str_len = ord($this->data[$this->pointer]);
				$this->pointer++;
				$res[$i] = substr($this->data,$this->pointer,$str_len);
				$this->pointer += $str_len;
			}
		}else{
			for ($i = 0; $i < $length; $i++){
				$res[$i] = $this->string_parse($dimention-1);
			}
		}
		
		return $res;
	}
}


class protocol_coder{
	public function code($data){
		$argc = count($data);
		
		$res = "";
		$res .= chr(strlen($data[0]));
		$res .= $data[0];
		$res .= chr($argc-1);
		//debug($argc);
		for ($i = 1; $i < $argc; $i++){
		
			if (is_int($data[$i])){
				$res .= chr(5);
				$res .= pack("i",$data[$i]); 
			}
		
			if (is_bool($data[$i])){
				$res .= chr(2);
				if ($data[$i]){
					$res .= chr(1);
				}else{
					$res .= chr(0);
				}
			}
			
			if (is_float($data[$i])){
				$res .= chr(9);
				$res .= pack("f",$data[$i]);
				//debug($res);
				
			}
			
			if (is_string($data[$i])){
				$res .= chr(11);
				$res .= chr(strlen($data[$i]));
				$res .= $data[$i];
				
			}
			
			if (is_array($data[$i])){
				$inf = $this->get_array_inf($data[$i]);
				//debug($inf);
				$res .= chr(12);
				$res .= chr($inf['dimention']);
				$res .= chr($inf['type']);
				$res .= $this->array_parse($data[$i],$inf['type']);
			}
		
			
		}
		return $res;
	}
	
	private function get_array_inf($data){
		$dim = 0;
		while (is_array($data)){
			$dim++;
			$data = $data[0];
		}
		
		if (is_int($data)){
			$type = 5;
		}
		
		if (is_bool($data)){
			$type = 2;
		}
		
		if (is_float($data)){
			$type = 9;
		}
		
		if (is_string($data)){
			$type = 11;
		}
		
		return array("type"=>$type,"dimention"=>$dim);
	}
	
	private function array_parse($data,$type){
		switch($type){
			case (2):
				return $this->array_parse_bool($data);
			break;
			
			case (5):
				return $this->array_parse_int($data);
			break;
			
			case (9):
				return $this->array_parse_float($data);
			break;
			
			case (11):
				return $this->array_parse_string($data);
			break;
		}
			
	}
	
	private function array_parse_int($data){
		$length = count($data);
		$res = "";
		
		$res.=chr($length);
		if (is_array($data[0])){
			for ($i = 0; $i < $length; $i++){
				$res .= $this->array_parse_int($data[$i]);
			}
		}else{
			for ($i = 0; $i < $length; $i++){
				$res .= pack("i",$data[$i]);
			}
		}
		//debug($res);
		return $res;
	}
	
	private function array_parse_float($data){
		$length = count($data);
		$res = "";
		$res.=chr($length);
		
		if (is_array($data[0])){
			for ($i = 0; $i < $length; $i++){
				$res .= $this->array_parse_float($data[$i]);
			}
		}else{
			for ($i = 0; $i < $length; $i++){
				$res .= pack("f",$data[$i]);
			}
		}
	
		return $res;
	}
	
	private function array_parse_bool($data){
		$length = count($data);
		$res = "";
		$res.=chr($length);
		
		if (is_array($data[0])){
			for ($i = 0; $i < $length; $i++){
				$res .= $this->array_parse_bool($data[$i]);
			}
		}else{
			for ($i = 0; $i < $length; $i++){
				if ($data[$i]){
					$res .= chr(1);
				}else{
					$res .= chr(0);
				}
			}
		}
	
		return $res;
	}
	
	private function array_parse_string($data){
		$length = count($data);
		$res = "";
		$res.=chr($length);
		
		if (is_array($data[0])){
			for ($i = 0; $i < $length; $i++){
				$res .= $this->array_parse_string($data[$i]);
			}
		}else{
			for ($i = 0; $i < $length; $i++){
				$res .= chr(strlen($data[$i]));
				$res .= $data[$i];
			}
		}
		//debug ($res);
		return $res;
	}
}

class server_RRPC{
	private $functions;
	private $adress;
	private $port;
	
	public function server_RRPC($adress,$port){
		$this->adress = $adress;
		$this->port = $port;
	}
		
	public function publicate_function($fullname,$shortname){
		$this->functions[$shortname] = $fullname;
	}
	
	public function debug(){
		var_dump($this->functions);
	}
	
	public function start(){
		header('Content-Type: text/plain;');
		error_reporting(E_ALL ^ E_WARNING);
		set_time_limit(0);
		ob_implicit_flush();
     
		echo "-= Server =-\n\n";

		try {
     
			echo 'Create socket ... ';
			if (($sock = socket_create(AF_INET, SOCK_STREAM, SOL_TCP)) < 0) {
				throw new Exception('socket_create() failed: '.socket_strerror(socket_last_error())."\n");
			}else{
				echo "OK\n";
			}

			echo 'Bind socket ... ';
			if (($ret = socket_bind($sock, $this->adress, $this->port)) < 0) {
				throw new Exception('socket_bind() failed: '.socket_strerror(socket_last_error())."\n");
			} else {
				echo "OK\n";
			}

			echo 'Listen socket ... ';
			if (($ret = socket_listen($sock, 5)) < 0) {
				throw new Exception('socket_listen() failed: '.socket_strerror(socket_last_error())."\n");
			} else {
				echo "OK\n";
			}

			do {
				echo 'Accept socket ... ';
				if (($msgsock = socket_accept($sock)) < 0) {
					throw new Exception('socket_accept() failed: '.socket_strerror(socket_last_error())."\n");
				} else {
					echo "OK\n";
				}
				$msg = socket_read($msgsock,1024);
				
				$pe = new protocol_encoder();
				$pc = new protocol_coder();
				$argv = $pe->encode($msg);
				echo "RECIVE:";
				debug($argv);
				echo "SEND:";
				debug($argv);
				$msg2 = $pc->code($argv);
				//$tmp = $pe->encode($msg2);
				//debug($tmp);
				socket_write($msgsock,$msg2,strlen($msg2));
				socket_close($msgsock);
				 
			} while (true);

		} catch (Exception $e) {
			echo "\nError: ".$e->getMessage();
		}
		 
		if (isset($sock)) {
		 
			echo 'Close socket ... ';
			socket_close($sock);
			echo "OK\n";
		 
		}
		
		
	}
}

$rpc = new server_RRPC("localhost",13002);
$rpc->publicate_function("blablabla","bla");
$rpc->publicate_function("blablablabla","bla2");
$rpc->start();

?>
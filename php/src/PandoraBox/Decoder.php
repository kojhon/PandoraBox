<?php
class Decoder{
	var $data;
	var $pointer;
		
	public function encodeResult($string){
		$this->data = $string;
		$this->pointer = 0;
		
		$len = strlen($string);
			/*echo "<br>";
			for ($i = 0; $i < $len; $i++){
				echo ord($string[$i])."/";
			}
			echo "<br>";*/
		
		$tmp = ord($this->data[$this->pointer]);
		$this->pointer++;
		
		if ($tmp == 1){
			$result[0] = TRUE;
			$length = ord(substr($this->data,$this->pointer,1));
			$this->pointer++;
			$result[1] = substr($this->data,$this->pointer,$length);
		}else{
			$result[0] = FALSE;			
			$result[1] = $this->getElement();
		}
		return $result;
	}
	
	public function encodeRequest($string){
		$this->pointer = 0;
		$this->data = $string;
		$tmp = ord($this->data[$this->pointer]);
		$this->pointer++;
		$a['function_name'] = substr($this->data,$this->pointer,$tmp);
		$this->pointer += $tmp;
		$a['count'] = ord($this->data[$this->pointer]);
		$this->pointer++;
		for ($i = 0; $i < $a['count']; $i++){
			$a[$i] = $this->getElement();
		}
		
		return $a;
	}
	
	private function getElement(){
		$tmp = ord($this->data[$this->pointer]);
		$this->pointer++;
		$element;
		switch ($tmp){
			case (1):
				$ntmp = unpack("c",substr($this->data,$this->pointer,1));
				$this->pointer++;
				$element = $ntmp[1];
			break;
		
			case (2)://bool block
				$element = (bool)ord($this->data[$this->pointer]);
				$this->pointer++;
			break;
			
			case(3):
				$ntmp = unpack("s",substr($this->data,$this->pointer,2));
				$this->pointer+=2;
				$element = $ntmp[1];
			break;
			
			case (5)://integer block
				$ntmp = unpack("i",substr($this->data,$this->pointer,4));
				//var_dump($ntmp);
				$element = (int)$ntmp[1];
				$this->pointer += 4;
			break;
			
			case(7):
				$ntmp = unpack("l",substr($this->data,$this->pointer,4));
				$this->pointer+=4;
				$element = $ntmp[1];
			break;
			
			case (9)://float block
				$ntmp = unpack("f",substr($this->data,$this->pointer,4));
				//var_dump($ntmp);
				$element = (float)$ntmp[1];
				$this->pointer += 4;
			break;
			
			case(10):
				$ntmp = unpack("d",substr($this->data,$this->pointer,8));
				$this->pointer+=8;
				$element = $ntmp[1];
			break;
			
			case (11)://string block
				$length =  ord($this->data[$this->pointer]);
				$this->pointer++;
				$element = substr($this->data,$this->pointer,$length);
				$this->pointer+=$length;
			break;
			
			case (12):
				$dimention = ord($this->data[$this->pointer]);
				$this->pointer++;
				$type = ord($this->data[$this->pointer]);
				$this->pointer++;
				$dimentionsSizes;
				for ($i = 0; $i < $dimention; $i++){
					$dimentionsSizes[$i] = ord($this->data[$this->pointer]);
					$this->pointer++;
				}
				$length = count($dimentionsSizes);
				for ($i = 0; $i < $length/2; $i++){
					$tmp = $dimentionsSizes[$i];
					$dimentionsSizes[$i] = $dimentionsSizes[$length-$i-1];
					$dimentionsSizes[$length-$i-1]= $tmp;
				}
				
				$element = $this->parseToArray($dimention,$type,$dimentionsSizes);
			break;
		}
		
		return $element;
	}	
	
	private function parseToArray($dimention,$type,$dimentionsSizes){
			$array;
			if ($dimention != 1){
				for ($i = 0; $i < $dimentionsSizes[$dimention-1];$i++){
					$array[$i] = $this->parseToArray($dimention-1,$type,$dimentionsSizes);	
				}
				return $array;
			}
		
			switch($type){
				case (1):
					for ($i = 0; $i < $dimentionsSizes[$dimention-1]; $i++){
						$ntmp = unpack("c",substr($this->data,$this->pointer,1));
						$this->pointer++;
						$array[$i] = $ntmp[1];
					}
				break;
			
				case (2)://bool block
					for ($i = 0; $i < $dimentionsSizes[$dimention-1]; $i++){
						$res[$i] = (bool)ord($this->data[$this->pointer]);
						$this->pointer++;
					}
				break;
				
				case(3):
					for ($i = 0; $i < $dimentionsSizes[$dimention-1]; $i++){
						$ntmp = unpack("s",substr($this->data,$this->pointer,2));
						$this->pointer+=2;
						$array[$i] = $ntmp[1];
					}
				break;
				
				case (5)://integer block
					for ($i = 0; $i < $dimentionsSizes[$dimention-1]; $i++){
						$ntmp = unpack("i",substr($this->data,$this->pointer,6));
						//debug($ntmp);
						$array[$i] = (int)$ntmp[1];
						$this->pointer += 4;
					}
				break;
				
				case(7):
					for ($i = 0; $i < $dimentionsSizes[$dimention-1]; $i++){
						$ntmp = unpack("l",substr($this->data,$this->pointer,4));
						$this->pointer+=4;
						$array[$i] = $ntmp[1];
					}
				break;
				
				case (9)://float block
					for ($i = 0; $i < $dimentionsSizes[$dimention-1]; $i++){
						$ntmp = unpack("f",substr($this->data,$this->pointer,4));
						//var_dump($ntmp);
						$array[$i] = (float)$ntmp[1];
						$this->pointer += 4;
					}
				break;
				
				case(10):
					for ($i = 0; $i < $dimentionsSizes[$dimention-1]; $i++){
						$ntmp = unpack("d",substr($this->data,$this->pointer,8));
						$this->pointer+=8;
						$array[$i] = $ntmp[1];
					}
				break;
				
				case (11)://string block
					for ($i = 0; $i < $dimentionsSizes[$dimention-1]; $i++){
						$str_len = ord($this->data[$this->pointer]);
						$this->pointer++;
						$array[$i] = substr($this->data,$this->pointer,$str_len);
						$this->pointer += $str_len;
					}
				break;
			}
			
			return $array;
	}
}
?>
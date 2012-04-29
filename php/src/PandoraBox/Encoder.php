<?php
class Encoder{
	
	
	public function encodeResult($data){
		$output = "";
		
		if ($data[0]){
			$output .= chr(1);
			$output .= chr(strlen($data[1]));
			$output .= $data[1];
		}else{
			$output .= chr(0);
			$output = $this->writeElement($data[1]);
		}
		
		return $output;
	}
	
	public function encodeRequest($data){
		$output = "";
		$argc = count($data);
		$output .= chr(strlen($data[0]));
		$output .= $data[0];
		$output .= chr($argc-1);
		
		for ($i = 1; $i < $argc; $i++){
			$output .= $this->writeElement($data[$i]);
		}
		return $output;	
	}
	
	private function writeElement($element){
		$type = $this->getType($element);
		$output = "";
		$output .= chr($type);
		switch($type){
			case(2):
				if ($element){
					$output .= chr(1);
				}else{
					$output .= chr(0);
				}
			break;
			
			case(5):
				$output .= pack("i",$element);
			break;
			
			case(9):
				$output .= pack("f",$element);
			break;
			
			case(11):
				$output .= chr(strlen($element));
				$output .= $element;
			break;
			
			case(12):
				$arrayInformation = $this->getArrayInformation($element);
				$output .= chr($arrayInformation['dimention']);
				$output .= chr($arrayInformation['type']);
				$output .= $this->writeDementionsSizes($element);
				$output .= $this->arrayEncode($element,$arrayInformation['type']);
			break;
		}
		
		return $output;
	}
	
	private function getType($element){
		if (is_int($element)){
			return 5;
		}
	
		if (is_bool($element)){
			return 2;
		}
		
		if (is_float($element)){
			return 9;
			
		}
		
		if (is_string($element)){
			return 11;
			
		}
		
		if (is_array($element)){
			return 12;
		}
	}
	
	private function getArrayInformation($data){
		$size = 0;
		while (is_array($data)){
			$size++;
			$data = $data[0];
		}
		
		$result['dimention'] = $size; 
		$result['type'] = $this->getType($data[0]);
		return $result;
	}
	
	private function writeDementionsSizes($data){
		$output = "";
		while (is_array($data)){
			$length = count($data);
			$output .= chr($length);
			$data = $data[0];
		}
		
		return $output;
	}
	
	private function arrayEncode($element, $type){
		$output = "";
		$length = count($element);
		if (is_array($element[0])){
			for ($i = 0; $i < $length; $i++){
				$output .= $this->arrayEncode($element[$i],$type);
			}
		}else{
			switch($type){
				case(2):
					for($i = 0; $i < $length; $i++){
						if ($element[$i]){
							$output .= chr(1);
						}else{
							$output .= chr(0);
						}
					}
				break;
				
				case(5):
					for($i = 0; $i < $length; $i++){
						$output .= pack("i",$element[$i]);	
					}
				break;
				
				case(9):
					for($i = 0; $i < $length; $i++){
						$output .= pack("f",$element[$i]);
					}
				break;
				
				case(11):
					for($i = 0; $i < $length; $i++){
						$output .= chr(strlen($element[$i]));
						$output .= $element[$i];	
					}
				break;
			}
		}
		
		return $output;
	}
}
?>
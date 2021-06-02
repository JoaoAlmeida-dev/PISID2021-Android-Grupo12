<?php

abstract class DatabaseConnection {
	
	protected $ipConnectionAddress;
	protected $username;
	protected $password;
	protected $dbName;
	protected $connection;
	
	protected function connectToDatabase() {
		$this->connection = mysqli_connect($this->ipConnectionAddress, $this->username, $this->password, $this->dbName);
		if (mysqli_connect_errno()) {
			$errorMessage = "Coneccao falhada: %s\n" . mysqli_connect_error();
			//fwrite($logsFile, $errorMessage . "\n");
			//printf($errorMessage);
			//exit();
			echo "failed <br>";
			throw new Exception($errorMessage);
		} else {
			$msg = "Connection to Database with IP: " . $this->ipConnectionAddress . " Username: " . $this->username . " and Database Schema: " . $this->dbName;
		}
	}
	
	public static function testConnectToDatabase($username, $password, $ipAddress, $dbName) {
		$connectionTested = @mysqli_connect($ipAddress, $username, $password, $dbName);
		if (mysqli_connect_errno()) {
			return false;
		} else {
			mysqli_close($connectionTested);
			return true;
		}
	}
	
	public function getipConnectionAddress() {
		return $this->ipConnectionAddress;
	}
	
	public function getUsername() {
		return $this->username;
	}
	
	public function getPassword() {
		return $this->password;
	}
	
	public function getDatabaseName() {
		return $this->dbName;
	}
	
	public function getConnection() {
		return $this->connection;
	}
	
	public function closeConnection() {
		if(is_resource($this->connection)) {
			mysqli_close($this->connection);
		}
	}
}

class LogsDatabase extends DatabaseConnection {

	private static $inst = null;
	
	public static function init($logsServer, $logsUser, $logsPassword, $logsdb) {
        if (self::$inst === null) {
            self::$inst = new LogsDatabase($logsServer, $logsUser, $logsPassword, $logsdb);
        }
        return self::$inst;
	}
	
	public static function Instance() {
		return self::$inst;
	}
	
	
	private function __construct($ipConnectionAddress, $username, $password, $dbName) {
		$this->ipConnectionAddress = $ipConnectionAddress;
		$this->username = $username;
		$this->password = $password;
		$this->dbName = $dbName;
		$this->connectToDatabase();
    }
	

}

?>
<?php
ini_set('soap.wsdl_cache_enabled', '0'); 
ini_set('soap.wsdl_cache_ttl', '0'); 

$client = new SoapClient("http://localhost:9000/SoapContext/SoapPort?WSDL");


var_dump($client -> __getFunctions());
var_dump($client -> __getTypes());

var_dump($client -> search(array("query" => "Microsoft")));
?>
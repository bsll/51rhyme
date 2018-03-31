<?php
error_reporting(E_ALL);
include "../function.php";
include "../config.php";
if ($argc !=2){
    die("Usage: php import.php path\n ");
}
$filename = $argv[1];
$fr = fopen($filename,"r");
$string = "";
$i = getTotal();
$index = array();
$index['index']['_index'] = "yayun";
$index['index']['_type'] = 'yayun';
$bulk_file = "";
while(!feof($fr)){
    $line = trim(fgets($fr));
    if($line != ""){
        $i += 1;
        $index['index']['_id'] = $i;
        $index_json = json_encode($index)."\n";
        $bulk_file = $bulk_file.$index_json.$line."\n";
    }
    if($i % 1000 == 0){
        print $i."\n";
        print substr(curl_post_es(YAYUN_BULK,$bulk_file,"POST"),0,200)."\n";
        $bulk_file = "";
    }
}
print substr(curl_post_es(YAYUN_BULK,$bulk_file,"POST"),0,200)."\n";


 ?>

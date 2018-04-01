<?php
error_reporting(E_ALL);
include "./function.php";
include "./config.php";
$flag = $_GET["flag"];
$word = $_GET["word"];
if($flag == "word"){
    $arr = getBody($word);
    foreach($arr as $key=>$value){
        $num = $key+1;
        print "第".(string)($num)."篇:".'</br >';
        print "姓名:".$value["_source"]['author'].'</br >';
        print "题目:".$value["_source"]['title'].'</br >';
        print "文章:".$value["_source"]['body'].'</br >';
        print "来源:".$value["_source"]['source'].'</br >';
        print "时期:".$value["_source"]['year'].'</br >';
    }
}
else{
    $arr = getYaYun($word);
    foreach($arr as $key=>$value){
        print $value."\n";
    }
}


?>

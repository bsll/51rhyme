<?php
function getTotal(){
    $res = json_decode(curl_post_es(YAYUN,"","POST"),true);
    return $res["hits"]["total"];
}
function getYaYun($word){
    $json = '{
        "text":"'.$word.'"
    }';
    $res = json_decode(curl_post_es(YAYUN_ANALYZER,$json,"POST"),true);
    foreach($res["tokens"] as $key => $value ){
        $syn_arr[] = $value["token"];
    }
    return $syn_arr;
}
function getBody($word,$start,$limit){
    $json = '{
        "from": '.$start.',
        "size":'.$limit.',
        "query": {
            "match": {
                "lastword": "'.$word.'"
            }
        }
    }';
    $res = json_decode(curl_post_es(YAYUN,$json,"POST"),true);
    if($res["hits"]["total"] == 0)
         return ;
    else{
         return $res["hits"]["hits"];
     }
}

function curl_post_es($es_url,$json_doc,$action){
    $baseUri = $es_url;
    $ci = curl_init();
    $response = "";
    while(!strcmp($response,"")){
        curl_setopt($ci, CURLOPT_URL, $baseUri);
        curl_setopt($ci, CURLOPT_TIMEOUT, 300);
        curl_setopt($ci, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ci, CURLOPT_FORBID_REUSE, 0);
        curl_setopt($ci, CURLOPT_POSTFIELDS, $json_doc);
        curl_setopt($ci, CURLOPT_CUSTOMREQUEST, $action);
        $response = curl_exec($ci);
    }
    return $response;
}

?>

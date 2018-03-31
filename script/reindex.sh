#!/bin/sh

# Polygon geo shape demo
curl -XDELETE 'localhost:9200/yayun'
curl -XPUT 'localhost:9200/yayun' -d @yayun.txt
php import.php json.txt > json.result
php import.php songCiJson.txt > song.result

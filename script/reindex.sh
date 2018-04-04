#!/bin/sh
source /etc/profile
echo "delte the index"
curl -XDELETE 'localhost:9200/yayun'
echo "recreate the index"
curl -XPUT 'localhost:9200/yayun' -d @yayun.txt
echo "import the tangshi..."
php import.php json.txt > json.result.txt
echo "import the songci..."
php import.php songCiJson.txt > song.result.txt
echo "import the yuanqu..."
php import.php yuanquJson.txt > yuanqu.result.txt

#!/usr/bin/python
# -*- coding: utf-8 -*-

import concurrent
import urllib3
import json
import sys
import requests
#流程
#从代理池取出代理连接进行访问
#多次检测连接，确定数据过滤有效
#减少从代理取数据的消耗。
# TODO:
#如果没有返回正确结果，则重新取代理进行访问


headers = {
'User-Agent': r'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) '
r'Chrome/45.0.2454.85 Safari/537.36 115Browser/6.0.3',
'Connection': 'keep-alive'
}
def get_proxy():
    return requests.get("http://127.0.0.1:5010/get/").content
def getResult(id,proxy_request,flag):
    if flag == "id":
        url = "http://music.163.com/api/song/media?id="+(str)(id)
    elif flag == "song":
        ids = '%5B' + (str)(id) + '%5D'
        url = "http://music.163.com/api/song/detail/?id="+(str)(id)+"&ids="+ids
    headers['Referer'] = 'http://music.163.com/song?id' + (str)(id)
    try:
        req = proxy_request.urlopen('GET',url,headers = headers,redirect=False)
        print("req")
        page = req.data.decode('utf-8')
        print(page)
        if len(page) == 0 or page[0] != "{":
            return;
        else:
            result = json.loads(page)
            #{"code":-460,"msg":"Cheating"
            if "msg" in result and result['msg'] == "Cheating":
                return;
    except urllib3.exceptions.ConnectTimeoutError as e:
        print(e)
        return;
    except urllib3.exceptions.HTTPError as e:
        print(e)
        return;
    return result
if __name__ == "__main__":
    if len(sys.argv) != 4:
        print("Usage: python spiders.py start end filename\n")
        exit()
    start = sys.argv[1].strip()
    end = sys.argv[2].strip()
    filename = sys.argv[3].strip()
    fw = open(filename, "a")
    #初始化一个proxy
    proxy_flag = False
    proxy = "http://"+get_proxy().decode()
    proxy_request = urllib3.ProxyManager(proxy,timeout=2.0,retries=3)
    #取出id后，利用当前proxy_request进行访问
    for id in range((int)(start),(int)(end)):
        result = ""
        while result == "":
            result = getResult(id,proxy_request,"id")
            #如果代理失效，则进行更换，继续上述步骤
            if result == "" :
                proxy = "http://"+get_proxy().decode()
                proxy_request = urllib3.ProxyManager(proxy,timeout=2.0,retries=3)
        if "lyric" in result:
            print(id)
            res = {}
            attrRes = ""
            while attrRes == "":
                attrRes = getResult(id,proxy_request,"song")
                if attrRes == "" :
                    proxy = "http://"+get_proxy().decode()
                    proxy_request = urllib3.ProxyManager(proxy,timeout=2.0,retries=3)
            if "songs" in attrRes and len(attrRes["songs"]) != 0:
                res["songName"] = ""
                if "name" in attrRes["songs"][0]:
                    res["songName"] = attrRes["songs"][0]["name"]
                    res["artistsName"] = ""
                    if "name" in attrRes["songs"][0]["artists"][0]:
                        res["artistsName"] = attrRes["songs"][0]["artists"][0]["name"]
                        res["lrc"] = result['lyric']
                        res["id"] = id
                        final_res = json.dumps(res)
                        fw.write(final_res+"\n")
    fw.close()

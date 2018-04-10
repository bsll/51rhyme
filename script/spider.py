from urllib import request, parse
import json


def getlrc(id):
    url = r'http://music.163.com/api/song/media'
    headers = {
    'User-Agent': r'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) '
    r'Chrome/45.0.2454.85 Safari/537.36 115Browser/6.0.3',
    'Connection': 'keep-alive'
    }
    data = {'id':id}
    data = parse.urlencode(data).encode('utf-8')
    req = request.Request(url, headers=headers, data=data)
    page = request.urlopen(req).read()
    page = page.decode('utf-8')
    return page
def getAttr(id):
    url = r'http://music.163.com/api/song/detail'
    headers = {
    'User-Agent': r'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) '
    r'Chrome/45.0.2454.85 Safari/537.36 115Browser/6.0.3',
    'Connection': 'keep-alive'
    }
    ids = '%5B' + (str)(id) + '%5D'
    data = {'id':id,'ids':ids}
    url = r'http://music.163.com/api/song/detail/?id='+(str)(id)+"&ids="+ids
    data = parse.urlencode(data).encode('utf-8')
    req = request.Request(url, headers=headers, data=data)
    page = request.urlopen(req).read()
    page = page.decode('utf-8')
    return page

count = 0
fw = open("musiclrc.txt", "w")
for id in range(1003165,100000000):
    res = {}
    artistsName = ""
    songName = ""
    result = json.loads(getlrc(id))
    if "lyric" in result:
        count += 1
        print(id)
        print(count)
        attrRes = json.loads(getAttr(id))
        if "songs" in attrRes and len(attrRes["songs"]) != 0:
            res["songName"] = ""
            if "name" in attrRes["songs"][0]:
                 res["songName"] = attrRes["songs"][0]["name"]
            res["artistsName"] = ""
            if "name" in attrRes["songs"][0]["artists"]:
                res["artistsName"] = attrRes["songs"][0]["artists"][0]["name"]
            res["lrc"] = result['lyric']
            res["id"] = id
            final_res = json.dumps(res)
            fw.write(final_res+"\n")

# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""
import jieba
import jieba.analyse
#import uniout  # 編碼格式，解决中文輸出亂碼問題
import requests

from bs4 import BeautifulSoup

test = input('文章網址：')
url = requests.get(test)
objSoup = BeautifulSoup(url.text,'lxml')
#爬標題下來
objList = objSoup.find('h1')


#將標題做結巴，找尋組合
Title_words = jieba.cut_for_search(str(objList),)
#標題的各種分詞可能
words = ''
for i in Title_words:
    if i != 'h1':
        #用 \t 將各種詞彙做區別來組成字串
        words += i+"\t"
        
#將標籤完全切掉，剩下字詞組成的字串
temp = words.split(">",3)
temp = temp[1].split("<",1)
#標題中各個可能之詞彙所組成之字串
words = temp[0]

#檢查一下詞彙組成的字串
print(words)



#TF-IDF jieba.analyse.extract_tags(字串, topK = 顯示的關鍵字詞數量 ,withWeight= 是否回傳權重值,allowPOs=(篩選詞性)
for x, w in jieba.analyse.extract_tags(words, topK = 10 ,withWeight=True ,allowPOS=()): 
    print('%s %s' % (x, w))






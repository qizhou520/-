# -*- coding: utf-8 -*-
"""
Created on Sun Apr  5 19:42:20 2020

@author: nuit5
"""
import requests
from bs4 import BeautifulSoup

ques = input('網址：')
#ETtoday - https://www.ettoday.net/news/20200705/1753117.htm?ercamp=sorted_hot_news

url = requests.get(ques)
objSoup = BeautifulSoup(url.text,'lxml')
objList = objSoup.find('div','story').find_all('p')
objList_clear = ''

for i in range(len(objList)):
    if(objList[i].find('strong') or objList[i].find('img') or '記者' in str(objList[i])):
        None
    else:
        objList_clear += str(objList[i]).replace('<p>' , '').replace('</p>','').replace('<br/>','')
    
print("文章："+objList_clear)  
print('\n')  

#-------------------------------------------------------------------------------------#

ques = input('網址：')
#聯合 - https://udn.com/news/story/7314/4680843?from=udn-ch1_breaknews-1-0-news

url = requests.get(ques)
objSoup = BeautifulSoup(url.text,'lxml')
objList = objSoup.find('div','article-content__paragraph').find_all('p')
objList_clear = ''

for i in range(len(objList)):
    if(objList[i].find('img') or '記者' in str(objList[i])):
        None
    else:
        objList_1 = str(objList[i]).replace('<p>' , '').replace('</p>','').replace('<','>').split('>')
        for j in range(len(objList_1)):
            if( 'a' in objList_1[j] or 'strong' in objList_1[j]):
                None
            else:
                objList_clear += objList_1[j]
    
print("文章："+objList_clear)  
print('\n')  
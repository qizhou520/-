import requests
from bs4 import BeautifulSoup
import csv 
import time
import random

#最近有被回應過的文章
url = requests.get("https://cofacts.g0v.tw/articles?filter=solved&orderBy=lastRepliedAt")
objSoup = BeautifulSoup(url.text,'lxml')
#print(objSoup.prettify())
objList = objSoup.find_all('ul','jss703')
#print(objList)
#查證文章的標題
cnt = 1
cnt_c = 0
cnt_w = 0
total = 1 
running = True
#固定編號
number = 852
while(running):
  for i in range(len(objList)):
    if(objList[i].find('div','jss425')) is None:
      None
    else:
      article_temp = objList[i].find('div','jss425').text
      article = article_temp.replace(',' , '，').replace('.' , '。').replace('/' , 'https:').split('https')
      print(objList[i].find('div','jss425'))
      #print(cnt,article[0])
      print('\n')

#查證文章的詳細(該頁面連結) -> objList[i].find('a').get('href')
      url2 = requests.get('https://cofacts.g0v.tw'+objList[i].find('a').get('href'))
      time.sleep(random.uniform(5, 10))
      objSoup2 = BeautifulSoup(url2.text,'lxml')
      objList2 = objSoup2.find(id='current-replies')
      #objList2_1 = objList2.find('strong', title = 'This message has some of its content proved to be false.')
      #print(objList2_1)
      if(objList2.find('strong', title = 'This message has some of its content proved to be false.')) is None:
        None
      else: #錯誤的文章
        with open('錯誤.csv', 'a+', newline='', encoding='utf-8-sig') as csvfile_1:
          rows = csv.writer(csvfile_1)
          if(len(article)>3 or article[0] == ''):
              None
          else:
              text = [cnt_w,'0',article[0]]
              rows.writerow(text)
              cnt_w += 1
      
      if(objList2.find('strong', title = 'The message has some of its content proved to be true.')) is None:
        None
      else: #正確的文章
        with open('正確.csv', 'a+', newline='', encoding='utf-8-sig') as csvfile_2:
          rows = csv.writer(csvfile_2)
          if(len(article)>3 or article[0] == ''):
              None
          else:
              text = [cnt_c,'1',article[0]]
              rows.writerow(text)
              cnt_c += 1
    
      print('\n\n')
    
      if(cnt==25):
          if(objSoup.find('p', 'jsx-684928770')) is None:
              running = False
          else: 
              print(total)
              total = total + 1
              objList_1 = objSoup.find_all('a', 'jsx-684928770')
              cnt = 1
              i = 0
              url_1 = requests.get('https://cofacts.g0v.tw/articles'+objList_1[1].get('href'))
              time.sleep(random.uniform(5, 10))
              objSoup = BeautifulSoup(url_1.text,'lxml')
              objList = objSoup.find_all('li','jsx-3720007368 item')
            
      cnt += 1
      
csvfile_1.close()
csvfile_2.close()
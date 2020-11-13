# -*- coding: utf-8 -*-
"""
Created on Sat Aug  1 14:45:57 2020

@author: 顏君翰
"""
import pymysql
try:
    db=pymysql.connect(host='localhost', user='root',port=3306,passwd="")
    cursor = db.cursor()
    #cursor.execute("CREATE DATABASE RUMO1")
    db.close()
    print("連線成功")
except Exception as e:
    print(e)
    print('連線失敗')

db=pymysql.connect(host='localhost', user='root',port=3306,passwd="" ,db="RUMO1")
cursor = db.cursor()
cursor.execute("DROP TABLE IF EXISTS CORRECT")
cursor.execute("DROP TABLE IF EXISTS FAKE")
cursor.execute("DROP TABLE IF EXISTS USERI")

sql=""" CREATE TABLE CORRECT(
        ID INT(11),
        SOURCE CHAR(20),
        CONTENT TEXT,
        KEY1 TEXT,
        KEY2 TEXT,
        KEY3 TEXT
        )"""
cursor.execute(sql)
sql=""" CREATE TABLE FAKE(
        ID INT(11),
        SOURCE CHAR(20),
        CONTENT TEXT,
        KEY1 TEXT,
        KEY2 TEXT,
        KEY3 TEXT
        )"""
cursor.execute(sql)
sql=""" CREATE TABLE USERI(
        ID CHAR(20),
        PASSWORD CHAR(20),
        FAKE TEXT,
        CORRECT TEXT
        )"""
cursor.execute(sql)
p =open("D:\\train\\db_correct_cofact.csv",'r',encoding="utf-8")
s=p.readline()
s=p.readline()
cnt=1
while s!="":
    a=s.split(',')
    sql='INSERT INTO rumo1.correct(id,source,content,key1,key2,key3) VALUES (%s,%s,%s,%s,%s,%s)'
    value=(cnt,'cofact',a[1],'','','')
    cursor.execute(sql,value)
    db.commit()
    cnt=cnt+1
    s=p.readline()
p =open("D:\\train\\db_correct_another.csv",'r',encoding="utf-8")
s=p.readline()
s=p.readline()
while s!="":
    a=s.split(',')
    sql='INSERT INTO rumo1.correct(id,source,content,key1,key2,key3) VALUES (%s,%s,%s,%s,%s,%s)'
    value=(cnt,'事實審查中心',a[1],'','','')
    cursor.execute(sql,value)
    db.commit()
    cnt=cnt+1
    s=p.readline()
p =open("D:\\train\\db_fake_cofact.csv",'r',encoding="utf-8")
s=p.readline()
s=p.readline()
cnt=1
while s!="":
    a=s.split(',')
    sql='INSERT INTO rumo1.fake(id,source,content,key1,key2,key3) VALUES (%s,%s,%s,%s,%s,%s)'
    value=(cnt,'cofact',a[1],'','','')
    cursor.execute(sql,value)
    db.commit()
    cnt=cnt+1
    s=p.readline()
p =open("D:\\train\\db_fake_another.csv",'r',encoding="utf-8")
s=p.readline()
s=p.readline()
while s!="":
    a=s.split(',')
    sql='INSERT INTO rumo1.fake(id,source,content,key1,key2,key3) VALUES (%s,%s,%s,%s,%s,%s)'
    value=(cnt,'事實審查中心',a[1],'','','')
    cursor.execute(sql,value)
    db.commit()
    cnt=cnt+1
    s=p.readline()
sql='INSERT INTO rumo1.useri(id,password,fake,correct) VALUES(%s,%s,%s,%s)'
value=('yen','1234','1.2.3','1.2.3')
cursor.execute(sql,value)
db.commit()
db.close()
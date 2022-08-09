import os
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import time
import requests

def get_all_links():
    list_links = []
    target_list =[]
    link_rank = {}
    list_links = open('/Users/nic/Courses/Information Retreival/lucene/Info_Retrieval_CP/targetFiles.txt').read().split("\n")
    for lst in list_links:
        target_list.append(lst.split('/')[2])
    return target_list


def find_value(text_input):
    text_input = text_input.split('\n')
    for l in text_input:
        s = "<h2><b>Google PageRank:"
        index1 = l.find(s)
        index2 = l.find('</span></b></h2><br/>')
        if index1 != -1 and index2!=-1:
            return l[index1+len(s)+30:index2]
    return 'NotFound'          


links = get_all_links()

i =1
for row in links:
    url = "https://checkpagerank.net/check-page-rank.php"
    myobj = {'name':row}
    x = requests.post(url, data = myobj)
    result = find_value(x.text) 
    print(str(i)+' :'+result)
    file = open('ranks.txt','a')
    f2 = open('data-pagerank/page'+str(i)+'.txt','w')
    f2.write(x.text)
    file.write(result+'\n')
    file.close()
    f2.close()
    time.sleep(30)
    i+=1

 





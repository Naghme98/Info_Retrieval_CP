
import shutil

def reading_file(file_name):
    dir = 'Scores given/Basic/'
    average = []
    for i in range(1,10):
        scores = open(dir + file_name + '/Q' + str(i) + '-scores.txt', 'r').read()
        scores = map(int,scores.split('\n'))
        ave = sum(scores)/10
        average.append(ave)

    return sum(average)/11


def editing_file():
    dir = 'Improvement/ranks.txt'
    file = open(dir).read().split('\n')
    f2 = open('page_ranks.txt','a')
    for f in file:
        f = f.split('/')
        rw = '0'
        if f[0] != '':
             rw = f[0]
        f2.write(rw+'\n')    


def create_new_files():
    source = 'Crawled-Pages/'
    for j in range (1,12):
        dir = 'Improvement/NewResults/Query'+str(j)+'_improved/results.txt'
        dest = 'Improvement/NewResults/Query'+str(j)+'_improved/'
        file = open(dir).read().split('\n')
        i = 1
        for f in file:
            open(dest+str(i)+'.txt' , 'x')
            shutil.copyfile(source+f[3:], dest+str(i)+'.txt') 
            i+=1
            if i==11:
                break    







print(reading_file('LM-s0.7'))
print(reading_file('TFIDF'))

# editing_file()
# create_new_files()

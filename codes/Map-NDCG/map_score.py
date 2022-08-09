
def map_score(scores):
    index = 1
    relevant = 0
    result = 0 
    for x in scores:
        if x > 0:
            relevant += 1
            result += (relevant/index)
            # print('res: ', result)
        index += 1

    return (result/relevant)

add = 'C:\\Users\\admin\\repos\\Info_Retrieval_CP\\LM-S0.7_res\\LM-S0.7-Q1\\LM-S0.7_Q1Socres.txt'
        
ls = []
with open(add, 'r') as reader:
        line = reader.readlines()
        for x in line:
            ls.append(int(x))
print(map_score(ls))
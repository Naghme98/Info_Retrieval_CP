# pass list of scores (integer list)
import math

def dcg_score(scores):
    result =0
    for i in range(len(scores)):
        if i ==0:
            result += scores[i]
        else:
            result += (scores[i]/math.log(i+1,2))
    return result

def ndcg_score(scores):
    dcg_sc = dcg_score(scores)
    scores.sort()
    scores.reverse()
    # print(scores)
    normalize = dcg_score(scores)
    if normalize ==0:
        return 0
    return (dcg_sc/normalize)

add = 'C:\\Users\\admin\\repos\\Info_Retrieval_CP\\LM-S0.7_res\\LM-S0.7-Q1\\LM-S0.7_Q1Socres.txt'
        
ls = []
with open(add, 'r') as reader:
        line = reader.readlines()
        for x in line:
            ls.append(int(x))
print(dcg_score(ls))
print(ndcg_score(ls))



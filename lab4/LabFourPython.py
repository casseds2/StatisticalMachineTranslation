##################################################
################### Question 1 ###################
##################################################

input_string = 'the cat sat on the mat with a cat'

# split the string by space and put it into a list
input_list = input_string.split()
print(input_list)

# count the number of tokens in the input sentence
number_of_tokens = len(input_list)
print(number_of_tokens)

# Counter() can return a dict with the occurrences of each token in the list
import collections
word_count_dict = collections.Counter(input_list)

# frequency = occurrences/number of tokens
for key, value in word_count_dict.iteritems():
    print('The word',key,'frequency is:', float(value)/number_of_tokens)

##################################################
################### Question 2 ###################
##################################################

import collections

# re-write Question 1 in a function way. Given a input word_frequency() returns 
# a list with frequency for each token.
def word_frequency(input_string):
    word_frequency_list=[]
    input_list = input_string.split()
    number_of_tokens = len(input_list)
    word_count_dict = collections.Counter(input_list)
    for key, value in word_count_dict.iteritems():
        word_frequency_list.append(float(value)/number_of_tokens)
    return word_frequency_list

input_string = 'the cat sat on the mat with a cat'
word_frequency_list = word_frequency(input_string)

# mutiple all the elements in a list
p_sentence = reduce(lambda x, y: x*y, word_frequency_list)
print p_sentence

##################################################
################### Question 3 ###################
##################################################

# read a file into a list
intput_file = open("corpus.txt", "r") 
input_sentences = intput_file.readlines()

# re-use the gram_scanner function in Question 1-2 of Lab 3.
# given the gram_number and input string, it returns a list
# containing all the n-grams.
def gram_scanner(n, input_string):
    result_list=[]
    input_list = input_string.split()
    for i in xrange(len(input_list)-n+1):
        result_list.append(tuple(input_list[i:i+n]))
    return result_list


# combine all bigram lists of each sentence in corpus.txt,
# because we need to regard all sentences as a whole.
N=2
ngram_combined_list=[]
for sent in input_sentences:
    ngram_list = gram_scanner(N, sent.strip())
    ngram_combined_list+=ngram_list
# print ngram_combined_list

# store all count(w1,w2) in a dict (=hash table in Java)
ngram_dict = {}
for item in ngram_combined_list:
    if ngram_dict.has_key(item): ngram_dict[item]+=1
    else: ngram_dict[item]=1
print ngram_dict

# store all count(w1,w) in a dict (=hash table in Java)
precursor_dict={}
for item in ngram_combined_list:
    precursor = item[0]
    if precursor_dict.has_key(precursor): precursor_dict[precursor]+=1
    else: precursor_dict[precursor]=1
print precursor_dict

# give a new sentence, to calculate its probability based on 
# two dicts: precursor_dict and ngram_dict.
to_calculate_sentence = '<s> a cat sat on the mat </s>'

# slice the input sentence into bigram list. 
to_cale_sent_gram_list = gram_scanner(N,to_calculate_sentence)

# search the two dicts (precursor_dict and ngram_dict) and find 
# corresponding values. Finally, multiple all values to obtain 
# the results
p_sentence = 1.0
for item in to_cale_sent_gram_list:
    print item
    print ngram_dict[item]
    print precursor_dict[item[0]]
    print float(ngram_dict[item])/precursor_dict[item[0]]
    print '------'
    p_sentence *= float(ngram_dict[item])/precursor_dict[item[0]]
print 'p_sentence:',p_sentence

##################################################
################### Question 4 ###################
##################################################

# test the above Question 3 code on another input sentence containing unknown word "car"
# you will get ERROR due to lack of smoothing method!
to_calculate_sentence = '<s> a cat sat on the car </s>'
to_cale_sent_gram_list = gram_scanner(N,to_calculate_sentence)
p_sentence = 1.0
for item in to_cale_sent_gram_list:
    if precursor_dict.has_key(item[0]): precursor_count = precursor_dict[item[0]]
    else: precursor_count=0
    if ngram_dict.has_key(item): ngram_count = ngram_dict[item]
    else: ngram_count = 0
    p_sentence *= float(ngram_count)/precursor_count
print 'p_sentence:',p_sentence

# we add add-one smoothing to Question 3. 
# most codes are the same.
intput_file = open("corpus.txt", "r") 
input_sentences = intput_file.readlines()

def gram_scanner(n, input_string):
    result_list=[]
    input_list = input_string.split()
    for i in xrange(len(input_list)-n+1):
        result_list.append(tuple(input_list[i:i+n]))
    return result_list

N=2
ngram_combined_list=[]
for sent in input_sentences:
    ngram_list = gram_scanner(N, sent.strip())
    ngram_combined_list+=ngram_list
print len(ngram_combined_list)

ngram_dict = {}
for item in ngram_combined_list:
    if ngram_dict.has_key(item): ngram_dict[item]+=1
    else: ngram_dict[item]=1
print len(ngram_dict)

precursor_dict={}
for item in ngram_combined_list:
    precursor = item[0]
    if precursor_dict.has_key(precursor): precursor_dict[precursor]+=1
    else: precursor_dict[precursor]=1
print len(precursor_dict)
print precursor_dict

# calculate vocabulary size
vocabulary = []
for line in input_sentences:
    for word in sent.strip().split():
        vocabulary.append(word)
vocabulary = list(set(vocabulary))

# add add-one smoothing method, and
# test the two examples in Question 3.
vocabulary_size = len(vocabulary)
print vocabulary_size

# to_calculate_sentence = '<s> a cat sat on the car </s>'
to_calculate_sentence = '<s> a cat sat on the mat </s>'
to_cale_sent_gram_list = gram_scanner(N,to_calculate_sentence)
p_sentence = 1.0
for item in to_cale_sent_gram_list:
    print item[0]
    if precursor_dict.has_key(item[0]): 
        # plus vocabulary_size 
        precursor_count = precursor_dict[item[0]] + vocabulary_size
    else: precursor_count=vocabulary_size
    if ngram_dict.has_key(item): 
        #plus one
        ngram_count = ngram_dict[item] + 1
    else: ngram_count = 1
    p_sentence *= float(ngram_count)/precursor_count
print 'p_sentence:',p_sentence

##################################################
################### Question 5 ###################
##################################################

 add one more argument (gram_number) to related functions
# then you can calculate any_gram results.
intput_file = open("corpus.txt", "r") 
input_sentences = intput_file.readlines()
N=4 # change it to 1,2,3,4

def gram_scanner(n, input_string):
    result_list=[]
    input_list = input_string.split()
    for i in xrange(len(input_list)-n+1):
        result_list.append(tuple(input_list[i:i+n]))
    return result_list

def get_ngram_list(n,input_sents):
    ngram_combined_list=[]
    for sent in input_sents:
        ngram_list = gram_scanner(n, sent.strip())
        ngram_combined_list+=ngram_list
    return ngram_combined_list

ngram_list = get_ngram_list(N, input_sentences)
print len(ngram_list)

def get_ngram_dict(ngram_li):
    ngram_dict = {}
    for item in ngram_li:
        if ngram_dict.has_key(item): ngram_dict[item]+=1
        else: ngram_dict[item]=1
    return ngram_dict

ngram_dict = get_ngram_dict(ngram_list)
print len(ngram_dict)

def get_precursor_dict(n,ngram_li):
    precursor_dict={}
    for item in ngram_li:
        precursor = item[0:n-1]
        if precursor_dict.has_key(precursor): precursor_dict[precursor]+=1
        else: precursor_dict[precursor]=1
    return precursor_dict

precursor_dict = get_precursor_dict(N,ngram_list)
print len(precursor_dict)
print precursor_dict

def get_vocabulary_size(input_sents):
    vocabulary = []
    for sent in input_sents:
        for word in sent.strip().split():
            vocabulary.append(word)
    vocabulary = list(set(vocabulary))
    return len(vocabulary)

vocabulary_size = get_vocabulary_size(input_sentences)
print vocabulary_size

def get_nram_probability(n, vocabulary_size, input_sent, precursor_dict, ngram_dict):
    p_sentence = 1.0
    input_sent_gram_list = gram_scanner(n,input_sent)

    for item in input_sent_gram_list:
        if precursor_dict.has_key(item[0:n-1]): 
            precursor_count = precursor_dict[item[0:n-1]] + vocabulary_size
        else: precursor_count=vocabulary_size
        
        if ngram_dict.has_key(item): 
            ngram_count = ngram_dict[item] + 1
        else: ngram_count = 1
        
        p_sentence *= float(ngram_count)/precursor_count
    return p_sentence

to_calculate_sentence = '<s> a cat sat on the mat </s>'

print 'p_sentence:', get_nram_probability(N, vocabulary_size, to_calculate_sentence, precursor_dict, ngram_dict)

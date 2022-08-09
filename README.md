# Project definition

This project is about documents related to the subject of "education" (including documents available on scientific and educational websites). The importance of this field is that people should be able to find the latest news and scientific writings in the field of education using one or more keywords. The field of education includes many sub-sections such as children's education, the effects of education, teaching methods and many more. 
As an example, with the keywords "Covid" and "Education", you should be able to find reliable news and articles about how to teach in the special conditions of dealing with Covid, the future of education with the possibility of the continuation of Covid and the effects of Covid on the quality and quantity of education.


## Data used

A collection of 10,000 documents includes the news text of informative sites, scientific texts related to the subject in reliable sites. (An attempt was made to create this dataset by assigning initial sources from the contents of the years 2019 to 2020)

## steps:
1.Crawling 1000 pages 
2. Using Lucene, creating an index on data collected (stop words removed)
3. Executing the queries on the index with the tf-idf method and Language Model with the Smoothing method (Jelinec-Mercer) and with 2 different arbitrary values for the alpha parameter.
4. Calculating the MAP and NDCG evaluation criteria for two methods

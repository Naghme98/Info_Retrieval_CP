import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.*;

public class SearcherImprovement {
    public static void main(String[] args) throws IllegalArgumentException,IOException, ParseException {    
        String indexDir = "/Users/nic/Courses/Information Retreival/lucene/TFIDF_index";
        ArrayList<String> queries = readQueries("/Users/nic/Courses/Information Retreival/lucene/Info_Retrieval_CP/queries.txt");
        String name = "targetFiles.txt";
        for (int i=0; i<queries.size(); i++){
            String q = queries.get(i);
            System.out.println(q);
            search(indexDir, q, name);
        }
        stripDuplicatesFromFile("targetFiles.txt");
        stripDuplicatesFromFile("targetWebPages.txt");
    }

    public static ArrayList<String> readQueries(String querisFileName) throws IOException{
        ArrayList<String> result = new ArrayList<String>();
        File qs = new File(querisFileName);
        BufferedReader br = new BufferedReader(new FileReader(qs));
        String line;
        while ((line = br.readLine())!=null){
            result.add(line);
        }
        br.close();
        return result;
    }
    public static void search(String indexDir, String q, String fileName) throws IOException, ParseException {

        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(indexDir));
        IndexReader indexReader = DirectoryReader.open(directory); 
        IndexSearcher indexSearcher = new IndexSearcher(indexReader); 

        indexSearcher.setSimilarity(new ClassicSimilarity());

        QueryParser parser = new QueryParser("contents", new StandardAnalyzer());
        Query query = parser.parse(q);

        long start = System.currentTimeMillis();
        TopDocs hits = indexSearcher.search(query, 300);
        long end = System.currentTimeMillis();
        System.err.println("Found " + hits.totalHits + " document(s) (in " + (end - start) +
        " milliseconds) that matched query '" + q + "':");
        FileWriter myWriter = new FileWriter(fileName, true);
        FileWriter myWriter2 = new FileWriter("targetWebPages.txt", true);
        for(ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = indexSearcher.doc(scoreDoc.doc);
            String docname = doc.get("filename");
            String temp = ("https://"+docname.substring(0,docname.length()-4).replace("**", "/"))+"\n";
           myWriter.write(temp);
            myWriter2.write(docname+"\n");
        }
        myWriter.close();
        myWriter2.close();
    }

    public static void stripDuplicatesFromFile(String filename) {
        try{
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        Set<String> lines = new HashSet<String>(10000); // maybe should be bigger
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (String unique : lines) {
            writer.write(unique);
            writer.newLine();
        }
        writer.close();
    }
    catch(Exception e){
        System.out.println(e.getMessage());
    }
    }
}

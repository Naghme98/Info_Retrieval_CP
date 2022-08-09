import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.lucene.store.FSDirectory;

public class Searcher {
    static int tmptmptmp;
    private void searchIndex(File indexDir, String queryStr , String filename) throws Exception {
        IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(FSDirectory.open(indexDir.toPath())));
        Rescorer rescorer = new Rescorer(searcher);
        searcher.setSimilarity(new SimilarityTFIDF());


        QueryParser parser = new QueryParser("contents", new StandardAnalyzer());
        Query query = parser.parse(queryStr);
        TopDocs topDocs = searcher.search(query, 20);
        ScoreDoc[] hits = topDocs.scoreDocs;
        rescorer.normalizeScore(hits);
        rescorer.addPageRank(hits);
        MyScoreDoc[] hits1 = new MyScoreDoc[hits.length];
        for(int i = 0 ; i < hits.length ; i++)
            hits1[i] = new MyScoreDoc(hits[i].doc, hits[i].score);
        Arrays.sort(hits1);
        int f = 0;
        FileWriter myWriter = new FileWriter(filename);
        for(ScoreDoc x: hits1) {
            if(f == 10){
            	f = 0;
				break;
            }

            int docId = x.doc;
            Document d = searcher.doc(docId);
            String name = d.get("filename");
           	String temp = Integer.toString(f)+ "- "+name+"\n";
			myWriter.write(temp);
            f++;

        }
		myWriter.close();
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

    public static void main(String[] args) throws Exception {
	    File indexDir;
			 String q;
	          ArrayList<String> queries = readQueries("queries.txt");

	          for (int i=1 ; i<queries.size()+1; i++){
                  indexDir = new File("Improvement/IR/new_index/");
	              q = queries.get(i-1);
				  String name = "Improvement/NewResults/Query"+i+"_improved/results.txt"; 
                  Searcher searcher = new Searcher();
                  searcher.searchIndex(indexDir, q, name);
	          }

    }
}

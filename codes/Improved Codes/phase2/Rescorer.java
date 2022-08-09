import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Rescorer {
    IndexSearcher searcher;
	String dirr = new String("targetWebPages.txt");
	ArrayList<String> lines;
	ArrayList<String> ranks;
    Rescorer(IndexSearcher searcher) {
        this.searcher = searcher;
    }
    public void normalizeScore(ScoreDoc[] docs) {
        float mn = 10000, mx = -10000;
        for(ScoreDoc x: docs) {
            mn = Math.min(mn, x.score);
            mx = Math.max(mx, x.score);
        }
        for(ScoreDoc x: docs) {
            x.score = (x.score - mn)/(mx-mn);
            x.score = x.score*100;
        }
    }
    public void addPageRank(ScoreDoc[] docs) {
		try {
		    this.lines = new ArrayList<>(Files.readAllLines(Paths.get(dirr)));
			this.ranks = new ArrayList<>(Files.readAllLines(Paths.get("Improvement/page_ranks.txt")));
		}
		catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR");
		}
		

		try {
        	for(ScoreDoc x:docs) {
	            int docId = x.doc;
	            Document d = searcher.doc(docId);
	            String name = d.get("filename");
                int rank = Integer.parseInt(ranks.get(lines.indexOf(name)));
                x.score = (float)(x.score * 0.8 + (float) rank * 0.2);
            }
		}
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("ERROR");
            }
        }
	}
		

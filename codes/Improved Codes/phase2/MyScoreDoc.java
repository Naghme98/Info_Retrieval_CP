//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import org.apache.lucene.search.ScoreDoc;

public class MyScoreDoc extends ScoreDoc implements Comparable<MyScoreDoc> {

    public MyScoreDoc(int doc, float score) {
        super(doc, score);
    }

    @Override
    public int compareTo(MyScoreDoc o) {
        return (int)(-score + o.score);
    }
}
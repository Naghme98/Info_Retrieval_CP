import org.apache.lucene.search.similarities.TFIDFSimilarity;

public class SimilarityTFIDF extends TFIDFSimilarity{

    @Override
    public float tf(float v) {
        return (float)(Math.log(1+v));
    }

    @Override
    public float idf(long l, long l1) {
        return (float)(Math.log((1+l1)/(1+l)));
    }

    @Override
    public float lengthNorm(int i) {
        return 1;
    }
}

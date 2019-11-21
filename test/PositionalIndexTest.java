import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PositionalIndexTest {


    @Test
    void initTermPosting() {
        PositionalIndex pi=new PositionalIndex("C:\\Users\\JasonHu\\Desktop\\data\\IR");
        System.out.println(pi.docFrequency("ball"));
        System.out.println(pi.postingsList("ball"));
    }

    @Test
    void initTermPosting2() {
        PositionalIndex pi=new PositionalIndex("./test resources/files");
        System.out.println(pi.docFrequency("asdf"));
        System.out.println(pi.postingsList("asdf"));
    }

    @Test
    void allUniqueTerms() {
        PositionalIndex pi=new PositionalIndex("./test resources/files");
        System.out.println(String.join(", ",pi.allUniqueTerms()));
    }

    @Test
    void termFrequency() {
        PositionalIndex pi=new PositionalIndex("./test resources/files");
        System.out.println(pi.termFrequency("aa","a.txt"));
    }

    @Test
    void TPScore2() {
        PositionalIndex pi=new PositionalIndex("./test resources/files");
        double tps=pi.TPScore("b a","ab.txt");
        assertTrue(tps-0.11764<1e-4);
    }

    @Test
    void VSScore1() {
        PositionalIndex pi=new PositionalIndex("./test resources/files");
        System.out.println(pi.VSScore("b a","ab.txt"));
    }

    @Test
    void Relevance() {
        String query = "a aa";
        PositionalIndex pi = new PositionalIndex("./test resources/files");
        String[] docs = pi.docs;
        int numDoc = pi.numDoc;
        for (int i = 0; i < numDoc; i++) {
            double score;
            System.out.println(docs[i]);
            score=pi.TPScore(query, docs[i]);
            System.out.println(docs[i]);
            System.out.println("TPScore: "+ score);
            score=pi.VSScore(query,docs[i]);
            System.out.println("VSScore: "+score);
            System.out.println();
        }
    }

    @Test
    void Relevance2() {
        String query = "baseball league";
        PositionalIndex pi = new PositionalIndex("C:\\Users\\JasonHu\\Desktop\\data\\IR");
        String[] docs = pi.docs;
        int numDoc = pi.numDoc;
        for (int i = 0; i < numDoc; i++) {
            double score;
            System.out.println(docs[i]);
            score=pi.TPScore(query, docs[i]);
            System.out.println(docs[i]);
            System.out.println("TPScore: "+ score);
            score=pi.VSScore(query,docs[i]);
            System.out.println("VSScore: "+score);
            System.out.println();
        }
    }
}
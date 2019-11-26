import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class QueryProcessorTest {

    @Test
    void topKDocs1() {
        QueryProcessor qp=new QueryProcessor(DataPath.dataPath+"/IR");
        ArrayList<String> ret=qp.topKDocs("world series", 5);
        System.out.println(ret.toString());
    }

    @Test
    void topKDocs2() {
        QueryProcessor qp=new QueryProcessor("./test resources/files");
        ArrayList<String> ret=qp.topKDocs("a aa", 5);
        System.out.println(ret.toString());
    }
}
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
    
    @Test
    void topKDocs3() {
        QueryProcessor qp=new QueryProcessor(DataPath.dataPath+"/IR");
        ArrayList<String> ret=qp.topKDocs("national", 5);
        System.out.println(ret.toString());
    }
    
    @Test
    void topKDocs4() {
        QueryProcessor qp=new QueryProcessor(DataPath.dataPath+"/IR");
        ArrayList<String> ret=qp.topKDocs("advantage of propitious", 5);
        System.out.println(ret.toString());
    }

    @Test
    void topKDocs5() {
        QueryProcessor qp=new QueryProcessor(DataPath.dataPath+"/IR");
        ArrayList<String> ret=qp.topKDocs("exact origin of the phrase", 5);
        System.out.println(ret.toString());
    }
    
    @Test
    void topKDocs6() {
        QueryProcessor qp=new QueryProcessor(DataPath.dataPath+"/IR");
        ArrayList<String> ret=qp.topKDocs("the diamond was in the north end of the block", 5);
        System.out.println(ret.toString());
    }
}
package com.xinhua;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class SearchIndex {

    private IndexSearcher indexSearcher;
    private IndexReader indexReader;

    @Before
    public void init() throws IOException {
       indexReader = DirectoryReader.open(FSDirectory.open(new File("D:\\Lucene\\index").toPath()));

        indexSearcher = new IndexSearcher(indexReader);
    }
///
        @Test
        public void testRangequery() throws Exception {


            Query query = LongPoint.newRangeQuery("size", 0l, 10000l);
            printResult(query);
        }

private void printResult(Query query)throws Exception{
    TopDocs topDocs = indexSearcher.search(query,10);
    System.out.println("总记录数"+topDocs.totalHits);
    ScoreDoc[] scoreDocs = topDocs.scoreDocs;
    for (ScoreDoc doc:
            scoreDocs) {
        int docId = doc.doc;
        Document document = indexSearcher.doc(docId);
        System.out.println(document.get("name"));
        System.out.println(document.get("path"));
        System.out.println(document.get("size"));
        //  System.out.println(document.get("content"));
        System.out.println("--------------------------------");
    }
    indexReader.close();
}


@Test
    public void testQueryParser()throws Exception{

        QueryParser queryParser = new QueryParser("name",new IKAnalyzer());

        Query query = queryParser.parse("Lucene是apache软件基金会");
printResult(query);


    }


}

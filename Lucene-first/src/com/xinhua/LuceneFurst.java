package com.xinhua;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.*;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.FileSwitchDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.Format;

public class LuceneFurst {

    @Test
    public void creatIndex() throws  Exception{

//        Directory directory = new RAMDirectory();

        Directory directory = FSDirectory.open( new File("D:\\Lucene\\index").toPath());

        IndexWriterConfig config = new IndexWriterConfig(new IKAnalyzer());
        IndexWriter indexWriter = new IndexWriter(directory,config);

        File dir = new File("E:\\AAAAA寒假教程\\Lucene\\01.Lucene\\87.lucene\\lucene\\02.参考资料\\searchsource");
        File[] files = dir.listFiles();
        for (File f: files
             ) {

            String fileName = f.getName();
            String filePath = f.getPath();
            String fileContent = FileUtils.readFileToString(f, "utf-8");
            long fileSize = FileUtils.sizeOf(f);

            Field fieldName = new TextField("name",fileName,Field.Store.YES);
            //Field fieldPath = new TextField("path",filePath,Field.Store.YES);
            Field fieldPath = new StoredField("path",filePath);
            Field fieldContent = new TextField("content",fileContent,Field.Store.YES);
//            Field fieldSize = new TextField("size",fileSize+"",Field.Store.YES);

            Field fieldSizeValue = new LongPoint("size",fileSize);
             Field fieldSizeStore = new StoredField("size",fileSize);

            Document document = new Document();
            document.add(fieldContent);
            document.add(fieldName);
            document.add(fieldPath);
//            document.add(fieldSize);
document.add(fieldSizeValue);
document.add(fieldSizeStore);

            indexWriter.addDocument(document);
        }

            indexWriter.close();

    }


    @Test
    public void searchIndex()throws Exception {

        Directory directory = FSDirectory.open( new File("D:\\Lucene\\index").toPath());
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        Query query = new TermQuery(new Term("name","spring"));
        TopDocs topDocs = indexSearcher.search(query, 10);
        System.out.println("查询总记录数"+        topDocs.totalHits
        );

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
    public void testTokenStream() throws IOException {

//        Analyzer analyzer = new StandardAnalyzer();

        Analyzer analyzer = new IKAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("","Lucene是apache软件基金会4 jakarta项目组的一个子项目，是一个开放源代码的全文检索引擎工具包，但它不是一个完整的全文检索引擎，而是一个全文检索引擎的架构。");
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

        tokenStream.reset();
while (tokenStream.incrementToken()){
    System.out.println(charTermAttribute.toString());
}

tokenStream.close();

    }





}

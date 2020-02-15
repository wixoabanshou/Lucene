package com.xinhua;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermContext;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class indexManager {

    private IndexWriter indexWriter;

    @Before
    public void init() throws Exception{
         indexWriter = new IndexWriter(FSDirectory.open(new File("D:\\Lucene\\index").toPath()),
                new IndexWriterConfig(new IKAnalyzer()));


    }

    @Test
    public void addDocument() throws Exception {

        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File("D:\\Lucene\\index").toPath()),
                                    new IndexWriterConfig(new IKAnalyzer()));

        Document document = new Document();
        document.add(new TextField("name","添加新的文件", Field.Store.YES));
        document.add(new TextField("content","添加新的文件内容", Field.Store.NO));
        document.add(new StoredField("path","D:\\Lucene\\hello"));


        indexWriter.addDocument(document);
        indexWriter.close();

    }

    @Test
    public void deleteAllDocument() throws Exception {
    indexWriter.deleteAll();
    indexWriter.close();

    }

    @Test
    public void deleteDocumentByQuery() throws Exception {

        indexWriter.deleteDocuments(new Term("name","apache"));
        indexWriter.close();

    }



    @Test
    public void updateDocument() throws Exception {

        Document document = new Document();

        document.add(new TextField("name","更新之后的文档",Field.Store.YES));
        document.add(new TextField("name1","更新之后的文档",Field.Store.YES));
        document.add(new TextField("name2","更新之后的文档",Field.Store.YES));

        indexWriter.updateDocument(new Term("name","spring"),document);

        indexWriter.close();

    }

    }

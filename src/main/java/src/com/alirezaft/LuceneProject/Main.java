package src.com.alirezaft.LuceneProject;


import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            File indexesDir = new File("indexes");
            if(indexesDir.exists() && indexesDir.isDirectory()){
                indexesDir.delete();
            }
            Indexer indexer = new Indexer();
            indexer.makeIndex();
            indexer.closeIndexWriter();
            Searcher searcher = new Searcher();
            TopDocs Results = searcher.search("Movie");
            for(ScoreDoc doc : Results.scoreDocs){
                Document res = searcher.getDoc(doc);
                System.out.println(res.get("Content"));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}

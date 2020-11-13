package src.com.alirezaft.LuceneProject;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.nio.file.Paths;

public class Indexer {
    private IndexWriter Writer;

    public Indexer() throws IOException {
        Directory dir = FSDirectory.open(Paths.get("indexes"));

        Writer = new IndexWriter(dir, new IndexWriterConfig(new StandardAnalyzer()));

    }

    private Document getDocument(File file) throws IOException {
        Document doc = new Document();

        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder Content = new StringBuilder();

        while(br.readLine() != null){
            Content.append(br.readLine());
        }

        Field content = new TextField("Content", Content.toString(), Field.Store.YES);
//        System.out.println(content.stringValue());
        Field name = new TextField("Name", file.getName(), Field.Store.YES);
        doc.add(content);
        doc.add(name);

        return doc;
    }

    private void indexFile(File f) throws IOException {
        Document d = getDocument(f);
        Writer.addDocument(d);
    }

    public int makeIndex() throws IOException {
        File[] HTMLs = new File("HTMLs\\HTMLs").listFiles();

        for(File HTML : HTMLs){
            if(!HTML.isHidden() && HTML.exists() && HTML.canRead()){
                indexFile(HTML);
            }
        }
        return Writer.getDocStats().numDocs;
    }

    public void closeIndexWriter() throws IOException {
        Writer.close();
    }
}

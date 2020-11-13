package src.com.alirezaft.LuceneProject;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class Searcher {
    private IndexSearcher Searcher;
    private Query SearchQuery;
    private QueryParser queryParser;

    public Searcher() throws IOException {
        Directory indexDir = FSDirectory.open(Paths.get("indexes"));

        Searcher = new IndexSearcher(DirectoryReader.open(indexDir));
        queryParser = new QueryParser("Content", new StandardAnalyzer());
    }

    public TopDocs search(String query) throws ParseException, IOException {
        SearchQuery = queryParser.parse(query);
        return Searcher.search(SearchQuery, 10);
    }

    public Document getDoc(ScoreDoc score) throws IOException {
        return Searcher.doc(score.doc);
    }
}

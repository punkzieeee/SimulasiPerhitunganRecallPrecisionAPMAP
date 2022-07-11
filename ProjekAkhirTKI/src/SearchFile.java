/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.FilterIndexReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.HitCollector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocCollector;

/** Simple command-line based search demo. */
public class SearchFile {
	String str = "";
	private Relevance rel = new Relevance();

  /** Use the norms from one field for all fields.  Norms are read into memory,
   * using a byte of memory per document per searched field.  This can cause
   * search of large collections with a large number of fields to run out of
   * memory.  If all of the fields contain only a single token, then the norms
   * are all identical, then single norm vector may be shared. */
  private static class OneNormsReader extends FilterIndexReader {
    private String field;

    public OneNormsReader(IndexReader in, String field) {
      super(in);
      this.field = field;
    }

    public byte[] norms(String field) throws IOException {
      return in.norms(this.field);
    }
  }

  SearchFile() {}

  /** Simple command-line based search demo. */
  public void main(String[] args, int angka, JTextArea hasil, JTextField[][] hasilArr, int count) throws Exception {
    String index = "index";
    String field = "contents";
    String queries = null;
    int repeat = 0;
    boolean raw = false;
    String normsField = null;
    boolean paging = true;
    int hitsPerPage = 5;
    
    for (int i = 0; i < args.length; i++) {
      if ("-index".equals(args[i])) {
        index = args[i+1];
        i++;
      } else if ("-field".equals(args[i])) {
        field = args[i+1];
        i++;
      } else if ("-queries".equals(args[i])) {
        queries = args[i+1];
        i++;
      } else if ("-repeat".equals(args[i])) {
        repeat = Integer.parseInt(args[i+1]);
        i++;
      } else if ("-raw".equals(args[i])) {
        raw = true;
      } else if ("-norms".equals(args[i])) {
        normsField = args[i+1];
        i++;
      } else if ("-paging".equals(args[i])) {
        if (args[i+1].equals("false")) {
          paging = false;
        } else {
          hitsPerPage = Integer.parseInt(args[i+1]);
          if (hitsPerPage == 0) {
            paging = false;
          }
        }
        i++;
      }
    }
    
    IndexReader reader = IndexReader.open(index);

    if (normsField != null)
      reader = new OneNormsReader(reader, normsField);

    Searcher searcher = new IndexSearcher(reader);
    Analyzer analyzer = new StandardAnalyzer();

    BufferedReader in = null;
    if (queries != null) {
      in = new BufferedReader(new FileReader(queries));
    } else {
      in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
    }
      QueryParser parser = new QueryParser(field, analyzer);
//      if (queries == null)                        // prompt the user
//        System.out.println("Enter query: ");

//      String line = in.readLine();
      String line = args[0];

      if (line == null || line.length() == -1)
        System.exit(0);

      line = line.trim();
      if (line.length() == 0)
    	System.exit(0);
      
      Query query = parser.parse(line);
//      System.out.println("Searching for: " + query.toString(field));

            
      if (repeat > 0) {                           // repeat & time as benchmark
        Date start = new Date();
        for (int i = 0; i < repeat; i++) {
          searcher.search(query, null, 100);
        }
        Date end = new Date();
        System.out.println("Time: "+(end.getTime()-start.getTime())+"ms");
      }

      if (paging) {
        doPagingSearch(in, searcher, query, angka, hitsPerPage, raw, queries == null, hasil, hasilArr, count);
      } else {
        doStreamingSearch(searcher, query);
      }
    reader.close();
  }
  
  /**
   * This method uses a custom HitCollector implementation which simply prints out
   * the docId and score of every matching document. 
   * 
   *  This simulates the streaming search use case, where all hits are supposed to
   *  be processed, regardless of their relevance.
   */
  public void doStreamingSearch(final Searcher searcher, Query query) throws IOException {
    HitCollector streamingHitCollector = new HitCollector() {
      
      // simply print docId and score of every matching document
      public void collect(int doc, float score) {
        System.out.println("doc="+doc+" score="+score);
      }
      
    };
    
    searcher.search(query, streamingHitCollector);
  }

  /**
   * This demonstrates a typical paging search scenario, where the search engine presents 
   * pages of size n to the user. The user can then go to the next page if interested in
   * the next hits.
   * 
   * When the query is executed for the first time, then only enough results are collected
   * to fill 5 result pages. If the user wants to page beyond this limit, then the query
   * is executed another time and all hits are collected.
   * 
   */
  public void doPagingSearch(BufferedReader in, Searcher searcher, Query query, int angka,
                             int hitsPerPage, boolean raw, boolean interactive, JTextArea hasil, JTextField[][] hasilArr, int count) throws IOException {
 
    TopDocCollector collector = new TopDocCollector(hitsPerPage);
    searcher.search(query, collector);
    ScoreDoc[] hits = collector.topDocs().scoreDocs;
    int numTotalHits = collector.getTotalHits();
    collector = new TopDocCollector(numTotalHits);
    
//    System.out.println(numTotalHits + " total matching documents");

    int start = 0;
    int end = hitsPerPage;
    String[] later = new String[end];
              
    for (int i = start; i < end; i++) {
      if (raw) {                              // output raw format
        System.out.println("doc="+hits[i].doc+" score="+hits[i].score);
        continue;
      }

    Document doc = searcher.doc(hits[i].doc);
    String path = doc.get("path");
    String[] raw_path = path.split("\\\\");
    String pth = raw_path[raw_path.length-1].substring(0, raw_path[raw_path.length-1].indexOf("."));
//    System.out.println(Arrays.toString(raw_path));
    
    if (path != null) {
//      System.out.println((i+1) + ". " + path);
    	later[i] = pth;
        str += (i+1) + ". " + later[i] + "\n";
        String title = doc.get("title");
        if (title != null) {
          System.out.println("   Title: " + doc.get("title"));
        }
    } else {
      System.out.println((i+1) + ". " + "No path for this document");
      }      
    }
    
    hasil.setText(str); str = "";
    rel.process(angka, later, hasilArr, count);
  }
}

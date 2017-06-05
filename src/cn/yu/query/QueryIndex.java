package cn.yu.query;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

public class QueryIndex {

	/**
	 * 需求:查询索引库索引
	 * 查询原理:根据索引单词(最小分词单元)进行匹配查询
	 * @throws Exception 
	 */
    @Test
    public void searchIndex() throws Exception{
        //指定索引库位置
        String path="F:/index";
        //读取索引库
        DirectoryReader reader=DirectoryReader.open(FSDirectory.open(new File(path)));
        //创建查询索引库的核心对象
        IndexSearcher indexSearcher=new IndexSearcher(reader);

        //指定查询关键词
        String keyWord="跑男";
        //创建查询解析器，对查询的词进行分词
        QueryParser queryParser=new QueryParser("title",new IKAnalyzer());
        //解析查询关键词，进行分词，返回分词查询包装类对象
        Query query=queryParser.parse(keyWord);

        //使用查询核心对象进行查询，返回文档概要信息
        //查询前十条，查询得分最高（匹配度最高）的十条数据
        //文档概要信息，文档得分，文档id，文档总记录数
        TopDocs topDocs = indexSearcher.search(query, 10);
        //获取文档的总记录数
        int totalHits = topDocs.totalHits;
        System.out.println("总命中数："+totalHits);
        //获取文档得分，文档id
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for(ScoreDoc score:scoreDocs){
            //获取文档id
            int docId = score.doc;
            System.out.println("文档id："+docId);
            //获取文档分数
            float score1 = score.score;
            System.out.println("文档得分："+score1);
            //根据文档id唯一确认一个文档
            Document doc = indexSearcher.doc(docId);
            //获取文档域id
            String id = doc.get("id");
            System.out.println("文档域id是："+id);
            //获取文档标题
            String title = doc.get("title");
            System.out.println("w文档标题是："+title);
            //获取文档内容
            String content = doc.get("content");
            System.out.println("文档内容是："+content);
        }
    }

}

package cn.yu.create;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;


public class CreateIndex {
	
	/**
	 * 需求:创建索引库索引(单词) 
	 * @throws Exception 
	 */

	@Test
	public  void  addIndex() throws Exception {
		//指定存储索引的存储路径
		String path="F:/index";
		//指定目录对象，关联索引单词存储位置
		FSDirectory directory=FSDirectory.open(new File(path));
		//创建一个分词器，把文档拆分成一个一个的单词（索引）
		//1、使用基本分词器创建索引库
//		Analyzer analyzer=new StandardAnalyzer();
		//2、使用ik分词器
		Analyzer analyzer=new IKAnalyzer();
		//创建核心配置文件
		IndexWriterConfig conf=new IndexWriterConfig(Version.LUCENE_4_10_3,analyzer);
		//创建写索引库的核心对象
		IndexWriter indexWriter = new IndexWriter(directory,conf);
		//创建文档对象，文档对象的数据来自网页，数据库，文件系统
		//网页变成文档对象，网页特点：标题、内容。url
		//数据库特点：字段对应文档字段域类型
		//文件服务器特点：标题、内容、描述、文件url
		Document document =new Document();
		//文档对象封装很多域字段，域字段封装内容
		//StringField类似于数据库的varchar类型，表示索引域的字段类型
		//特点：不分词--有索引--Store.NO/YES
		document.add(new StringField("id","1", Field.Store.NO));
		//文档标题
		//TextField特定：必须分词，必须有索引，Store.NO/YES
		document.add(new TextField("title","黄晓明带着媳妇参加跑男", Field.Store.YES));
		//文档内容
		document.add(new TextField("content","Lucene并不是现成的搜索引擎产品，但可以用来制作搜索引擎产品", Field.Store.NO));
		//把文档写入索引库，提交事物
		indexWriter.addDocument(document);
		//提交事物
		indexWriter.commit();
	}


}

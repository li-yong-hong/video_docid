package com.sohu.mrd.videoDocId.simhash;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
/**
 * 文档分词
 * @author Jin Guopan
 */
public final class WordsSegment {
	private static List<String> stopWords=new ArrayList<String>();
	static{
		InputStream  is=WordsSegment.class.getClassLoader().getResourceAsStream("dict/stop_words.utf8");
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(is, "utf-8"));
			String temp="";
			while((temp=br.readLine())!=null)
			{
				stopWords.add(temp);
			}
		} catch (Exception e) {
		  e.printStackTrace();
		}
	}
	private static final Logger logger = Logger.getLogger(WordsSegment.class);
	private WordsSegment() {
	}
	
	public static List<String>  splitWords(String str)
	{
		List<String>  splitWords=new ArrayList<String>();
		List<Term> terms=ToAnalysis.parse(str);
		for(int i=0;i<terms.size();i++)
		{
			Term term=terms.get(i);
			String word=term.getName();
			if(!"".equals(word.trim())&&!stopWords.contains(word))
			{
				splitWords.add(word);
			}
		}
		return splitWords;
	}
}
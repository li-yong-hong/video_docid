package com.sohu.mrd.videoDocId.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class KillPuctuation {
	public static String killPuctuation(String sentence)
	{
		Pattern patterncomma = Pattern.compile("(&[^;]+;)", Pattern.DOTALL);
		Matcher matchercomma = patterncomma.matcher(sentence);
		String strout = matchercomma.replaceAll(" ").replaceAll("\\pP", " ");
		return strout;
	}
	//对集合中的句子进行去除标点符号
	public  static  List<String> killPuctuationList(List<String> sentences)
	{
		 List<String> newSentences=new ArrayList<String>();
		 for(int i=0;i<sentences.size();i++)
		 {
			 newSentences.add(killPuctuation(sentences.get(i)));
		 }
		 return newSentences;
	}
	
	@Test
	public void testKillPu()
	{
		String sentence=KillPuctuation.killPuctuation("我是，表达；符号。");
		System.out.println("sentence "+sentence);
	}
	
}

package com.sohu.mrd.videoDocId.utils;
import java.util.ArrayList;
import java.util.List;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
/**
 * @author  Jin Guopan
 * @version 2016-12-13
 */
public class JaccardSimilaryKit {
	public static double getJaccardSimilarityBySet(String content1,String content2)
	{
		double similarity =0.0;
		List<String>  words1List = new ArrayList<String>();
		List<String>  words2List = new ArrayList<String>();
		List<Term>  list1=ToAnalysis.parse(content1);
		List<Term>  list2=ToAnalysis.parse(content2);
		for(int i=0;i<list1.size();i++)
		{
			words1List.add(list1.get(i).getName());
		}
		for(int j=0;j<list2.size();j++)
		{
			words2List.add(list2.get(j).getName());
		}
		int  interSection=SetKit.getListIntersection(words1List, words2List); //两个集合的交集
		int  union=SetKit.getListUnion(words1List, words2List);
		similarity = (double)interSection/union;
		return similarity;
	}
	public static double getJaccardSimilarity(String sentence1, String sentence2){
		List<String> words1=new ArrayList<String>();
		List<String> words2=new ArrayList<String>();
		List<Term>  list1=ToAnalysis.parse(sentence1);
		List<Term>  list2=ToAnalysis.parse(sentence2);
		for(int i=0;i<list1.size();i++)
		{
			if(!list1.get(i).getName().trim().equals(""))
			{
				words1.add(list1.get(i).getName());
			}
		}
		for(int j=0;j<list2.size();j++)
		{
			if(!list2.get(j).getName().trim().equals(""))
			{
				words2.add(list2.get(j).getName());
			}
		}
		int commonString=LCSequenceKit.getSequencelength(words1, words2);
		int unionLength=SetKit.getListUnion(sentence1, sentence2).size();//得到两个句子的词并集个数
		double jaccardDistance=(double)commonString/(unionLength+0.1);
		return jaccardDistance;
	}
	public static void main(String[] args){
		double similary=JaccardSimilaryKit.getJaccardSimilarity("她妈妈喊你回家吃饭", "你妈妈喊你回家吃饭");
		System.out.println("similary "+similary);
	}
}

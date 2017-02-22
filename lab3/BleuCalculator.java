import java.io.*;
import java.util.*;

class BleuCalculator{
	public static void main(String [] args){
		ArrayList<ArrayList<String>> candList = null;
		ArrayList<ArrayList<String>> refList = null;
		NGram ngram = new NGram();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String cand = "";
		String [] candA = null;
		String [] refA = null;
		String ref = "";
		float score = 0;
		float currentNGram = 0;
		float brevity = 0;
		int flag = 0;

		try{
			System.out.println("Enter 2 Sentences To Be Compared:");
			cand = /*in.readLine();*/ "The gunman was shot dead by police";
			//cand = "Israeli officials are responsible for airport security";
			System.out.println(cand);
			candA = cand.split(" ");
			ref = /*in.readLine();*/ "The gunman was shot dead by the police";
			//ref = "airport security Israeli officials are responsible";
			System.out.println(ref);
			refA = ref.split(" ");
			in.close();
		}
		catch(Exception e){
			System.out.println("Error Reading Input");
		}
		for(int i = 1; i < 5; i++){
			candList = ngram.nGram(candA, i);
			refList = ngram.nGram(refA, i);
			if(score != 0){
				currentNGram = calculatePercentDiff(candList, refList);
				score = score * calculatePercentDiff(candList, refList);
			}
			else{
				currentNGram = calculatePercentDiff(candList, refList);
				score = calculatePercentDiff(candList, refList);
			}
			if(candA.length > refA.length){
				brevity = (float)refA.length / (float)candA.length;
				flag = 1;
			}	
			else if (candA.length < refA.length) {
					brevity = (float)candA.length / (float)refA.length;
					flag = 2;
			}
			else{
				brevity = 1;
				flag = 3;
			}
			printArrayList(candList);
			printArrayList(refList);
			System.out.println("Score for nGram(" + i + ") :" + currentNGram);
		}
		System.out.println("Brevity = " + brevity + " FLAG: " + flag);
		System.out.println("Final Score (BLEU) : " + root(score, 4)*brevity);
	}

	static float calculatePercentDiff(ArrayList<ArrayList<String>> cand, ArrayList<ArrayList<String>> ref){
		float count = 0;
		for(int i = 0; i < cand.size(); i++){
			if(ref.contains(cand.get(i)))
				count++;
		}
		return count/ref.size();
	}

	static double root(double num, double root) {
	    return Math.pow(Math.E, Math.log(num)/root);
	}

	static void printArrayList(ArrayList<ArrayList<String>> list){
		System.out.print("{");
		for(ArrayList<String> a : list){
			System.out.print("{");
			for(String s : a){
				System.out.print(s + " ");
			}
			System.out.print("}");
		}
		System.out.print("}");
		System.out.println();
	}
}

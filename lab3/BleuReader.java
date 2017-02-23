import java.io.*;
import java.util.ArrayList;

class BleuReader{
	public static void main(String [] args){
		ArrayList<ArrayList<String>> candList = null;
		ArrayList<ArrayList<String>> refList = null;
		NGram ngram = new NGram();
		String cand = ""; //Stores Candidate String
		String ref = ""; //Stores Reference String
		String [] candA = null; //Split candidate into array
		String [] refA = null; //Split reference into array
		float score = 0; // Final BLEU Score
		float currentNGram = 0; //Score of the current nGram
		int nGram = 5; //Dictates how many nGrams will be called (nGram - 1 calls)
		float brevity = 0; //Brevity score
		int flag = 0; //Personal flag to see what brevity score is;

		ArrayList<String> allRefs = new ArrayList<>();

		try{
			BufferedReader in = new BufferedReader(new FileReader("TestStrings.txt"));
			cand = in.readLine();
			System.out.println("Cand: " + cand);
			while((ref = in.readLine()) != null){
				allRefs.add(ref);
				System.out.println("Ref: " + ref);
			}
			in.close();

			candA = cand.split(" ");

			for(String s : allRefs){
				refA = s.split(" ");
				score = calcPrecisionForNgram(candA, refA, nGram);
				brevity = (float) candA.length / (float) longestString.length;
				score = calcPrecisionForNgram(candA, refA, nGram);
				System.out.println("Brevity = " + brevity);
				System.out.println("Total Precisions: " + score);
				System.out.println("Final Score (BLEU) : " + root(score, 4)*brevity);
				score = 0;
				brevity = 0;
			}

		}
		catch(Exception e){
			System.out.println("I/O Error");
		}
	}

	static float calculatePercentDiff(ArrayList<ArrayList<String>> cand, ArrayList<ArrayList<String>> ref){
		float count = 0;
		for(int i = 0; i < cand.size(); i++){
			if(ref.contains(cand.get(i)))
				count++;
		}
		return count/cand.size();
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

	static float calcPrecisionForNgram(String [] candA, String [] refA, int nGram){
		float score = 0;
		float tempScore = 0;
		NGram ngram = new NGram();
		ArrayList<ArrayList<String>> candList = new ArrayList<>();
		ArrayList<ArrayList<String>> refList = new ArrayList<>();
		for(int i = 1; i < nGram; i++){
			candList = ngram.nGram(candA, i);
			refList = ngram.nGram(refA, i);
			if(score == 0){
				score = calculatePercentDiff(candList, refList);
				System.out.println("Current Precison Score : " + score);
			}
			else{
				tempScore = calculatePercentDiff(candList, refList);
				score = score * tempScore;
				System.out.println("Current Precison Score : " + tempScore);
			}
			printArrayList(candList);
			printArrayList(refList);
		}
		return score;
	}
}
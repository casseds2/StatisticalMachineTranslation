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
    int nGram = 5; //Dictates how many nGrams will be called (nGram - 1 calls)
    float bestnGramOne = 0; //holds best Value for Ngram = 1
    float bestnGramTwo = 0; //holds best value for Ngram = 2
    float bestnGramThree = 0; //holds best value for Ngram = 3
    float bestnGramFour = 0; //holds best value for Ngram = 4
    float score = 0; //holds score for ngram percision
    float bestStringLength = 0; //holds value for best string length for brevity
    float brevity = 0; //holds brevity

    ArrayList<String> allRefs = new ArrayList<>();

    try{
      BufferedReader br = new BufferedReader(new FileReader("TestStrings2.txt"));
      PrintWriter pw = new PrintWriter(new File("Outputs.txt"));
      //HANDLE CANDIDATE STRING
      cand = br.readLine();
      candA = cand.split(" ");
      System.out.println("Output : " + cand);

      //HANDLE REFERENCE STRINGS
      while((ref = br.readLine()) != null){
        System.out.println("Reference : " + ref);
        allRefs.add(ref);
      }
      br.close();

      for(int i = 1; i < nGram; i++){
        for(String s : allRefs){
          refA = s.split(" ");
          if(refA.length == candA.length){
            bestStringLength = refA.length;
          }
          else if(Math.abs(candA.length - refA.length) < Math.abs(candA.length - bestStringLength)){
            bestStringLength = refA.length;
          }
          score = calcPrecisionForNgram(candA, refA, i);
          switch(i){
            case 1 :
              //System.out.println("CASE 1");
              if(score > bestnGramOne){
                bestnGramOne = score;
              }
              break;
            case 2 :
              //System.out.println("CASE 2");
              if(score > bestnGramTwo){
                bestnGramTwo = score;
              }
              break;
            case 3 :
              //System.out.println("CASE 3");
              if(score > bestnGramThree){
                bestnGramThree = score;
              }
              break;
            case 4 :
              //System.out.println("CASE 4");
              if(score > bestnGramFour){
                bestnGramFour = score;
              }
              break;
          }
        }
      }
      System.out.println("BEST NGRAM 1 : " + bestnGramOne);
      System.out.println("BEST NGRAM 2 : " + bestnGramTwo);
      System.out.println("BEST NGRAM 3 : " + bestnGramThree);
      System.out.println("BEST NGRAM 4 : " + bestnGramFour);
      float bestPrecison = bestnGramOne * bestnGramTwo * bestnGramThree * bestnGramFour;
      System.out.println("Best Precision : " + bestPrecison);
      System.out.println("Best Length : " + bestStringLength);
      if(candA.length < bestStringLength)
        brevity = (float) candA.length / (float) bestStringLength;
      if(candA.length > bestStringLength)
        brevity = (float) bestStringLength / (float) candA.length;
      if(candA.length == bestStringLength)
        brevity = 1;
      System.out.println("Brevity : " + brevity);
      float bleuScore = (float) root(bestPrecison, 4) * brevity;
      System.out.println("Bleu Score : " + bleuScore);
      pw.write("Bleu Score : " + bleuScore);
      pw.close();
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
		candList = ngram.nGram(candA, nGram);
		refList = ngram.nGram(refA, nGram);
		if(score == 0){
			score = calculatePercentDiff(candList, refList);
			//System.out.println("Current Precison Score : " + score);
		}
		else{
			tempScore = calculatePercentDiff(candList, refList);
			if(tempScore != 0){
				score = score * tempScore;
				//System.out.println("Current Precison Score (TEMP) : " + tempScore);
			}
		}
		return score;
	}
}

import java.io.*; //BufferedReader, PrintWriter, FileReader, File
import java.util.ArrayList;

class BleuReader{
  public static void main(String [] args){
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
    ArrayList<String> allRefs = new ArrayList<>(); //holds all references

    try{
      // Read all appropriate information
      BufferedReader br = new BufferedReader(new FileReader("Trans.txt"));
      PrintWriter pw = new PrintWriter(new File("Outputs.txt"));
      cand = br.readLine();
      candA = cand.split(" ");
      System.out.println("Output : " + cand);
      br = new BufferedReader(new FileReader("Refs.txt"));
      while((ref = br.readLine()) != null){
        System.out.println("Reference : " + ref);
        allRefs.add(ref);
      }
      br.close();
      // Perform Bleu Calculations
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
              if(score > bestnGramOne){
                bestnGramOne = score;
              }
              break;
            case 2 :
              if(score > bestnGramTwo){
                bestnGramTwo = score;
              }
              break;
            case 3 :
              if(score > bestnGramThree){
                bestnGramThree = score;
              }
              break;
            case 4 :
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
      System.out.println("Overall Precision : " + bestPrecison);
      System.out.println("Best String Length : " + bestStringLength);
      if(candA.length < bestStringLength)
        brevity = (float) candA.length / (float) bestStringLength;
      if(candA.length > bestStringLength)
        brevity = (float) bestStringLength / (float) candA.length;
      if(candA.length == bestStringLength)
        brevity = 1;
      System.out.println("Brevity : " + brevity);
      float bleuScore = (float) rootFour(bestPrecison) * brevity;
      System.out.println("Bleu Score : " + bleuScore);
      pw.write("Bleu Score : " + bleuScore);
      pw.close();
    }
    catch(Exception e){
      System.out.println("I/O Error");
    }
  }
  // % Difference = Shared Words / No. of NGram 'sets' of OUTPUT
  static float calculatePercentDiff(ArrayList<ArrayList<String>> cand, ArrayList<ArrayList<String>> ref){
		float count = 0;
		for(int i = 0; i < cand.size(); i++){
			if(ref.contains(cand.get(i)))
				count++;
		}
		return count/cand.size();
	}
  // Returns 4th root of a numnber
	static double rootFour(double num) {
	    return Math.pow(num, 0.25);
	}
  // Breaks arrays into NGram 'sets' and returns the Precision Score
	static float calcPrecisionForNgram(String [] candA, String [] refA, int nGram){
		float score = 0;
		float tempScore = 0;
		NGram ngram = new NGram();
		ArrayList<ArrayList<String>> candList = new ArrayList<>();
		ArrayList<ArrayList<String>> refList = new ArrayList<>();
		candList = ngram.breakIntoNGrams(candA, nGram);
		refList = ngram.breakIntoNGrams(refA, nGram);
		if(score == 0)
			score = calculatePercentDiff(candList, refList);
		else{
			tempScore = calculatePercentDiff(candList, refList);
			if(tempScore != 0)
				score = score * tempScore;
		}
		return score;
	}
  /**Function Does NGram Break Up For Any number of N**/
  static ArrayList<ArrayList<String>> breakIntoNGrams(String [] tokens, int n){
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		ArrayList<String> setOfWords = new ArrayList<>();
		for(int i = 0; i < tokens.length-(n-1); i++){
			int position = i;
			int count = 0;
			while(count < n){
				setOfWords.add(tokens[position]);
				count++;
				position++;
			}
			list.add(setOfWords);
			setOfWords = new ArrayList<>();
			count = 0;
		}
		return list;
	}
}

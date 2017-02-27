import java.io.*;
import java.util.ArrayList;

class BleuScore{
  public static void main(String [] args){
    float bestnGramOne = 0;
    float bestnGramTwo = 0;
    float bestnGramThree = 0;
    float bestnGramFour = 0;
    float bestLengthMatch = 0;
    String [] transArray = null;
    String [] refArray = null;
    float brevity = 0;
    try{
      /**Handle Files**/
      BufferedReader br = new BufferedReader(new FileReader("Trans.txt"));
      PrintWriter pw = new PrintWriter(new File("Output.txt"));
      ArrayList<String> refList = new ArrayList<>();
      String output = br.readLine();
      System.out.println("Output : " + output);
      br = new BufferedReader(new FileReader("Refs.txt"));
      String ref = "";
      while((ref = br.readLine()) != null){
        refList.add(ref);
        System.out.println("Reference : " + ref);
      }
      br.close();
      /**Main Calculations**/
      for(int i = 1; i < 5; i++){
        ArrayList<ArrayList<String>> nTransList = calculateNgrams(output, i);
        for(String s : refList){
          transArray = output.split(" ");
          refArray = s.split(" ");
          if(transArray.length == refArray.length)
            bestLengthMatch = refArray.length;
          else if(Math.abs(transArray.length - refArray.length) < Math.abs(transArray.length - bestLengthMatch))
            bestLengthMatch = refArray.length;
          ArrayList<ArrayList<String>> nRefList = calculateNgrams(s, i);
          float precision = calcPrecision(nTransList, nRefList);
          switch(i){
            case 1 :
              if(precision > bestnGramOne)
                bestnGramOne = precision;
              break;
            case 2 :
              if(precision > bestnGramTwo)
                bestnGramTwo = precision;
              break;
            case 3 :
              if(precision > bestnGramThree)
                bestnGramThree = precision;
              break;
            case 4 :
              if(precision > bestnGramFour)
                bestnGramFour = precision;
              break;
          }
        }
      }
      /**If bestnGram(x) = 0, should I multiply the 0??**/
      float totalPrecision = bestnGramOne * bestnGramTwo * bestnGramThree * bestnGramFour;
      if(transArray.length > bestLengthMatch)
        brevity = bestLengthMatch / (float) transArray.length;
      if(transArray.length < bestLengthMatch)
        brevity = (float) transArray.length / bestLengthMatch;
      if(transArray.length == bestLengthMatch)
        brevity = 1;
      pw.println("Best n = 1 : " + bestnGramOne);
      pw.println("Best n = 2 : " + bestnGramTwo);
      pw.println("Best n = 3 : " + bestnGramThree);
      pw.println("Best n = 4 : " + bestnGramFour);
      pw.println("Product of Best Precisions : " + totalPrecision);
      pw.println("Best Length : " + bestLengthMatch);
      pw.println("Brevity : " + brevity);
      pw.println("Bleu : " + root(totalPrecision) * brevity);
      pw.close();
    }
    catch(IOException e){
      System.out.println("I/O Error");
    }
  }

  static double root(float n){
    return Math.pow(n, 0.25f);
  }

  static float calcPrecision(ArrayList<ArrayList<String>> trans, ArrayList<ArrayList<String>> ref){
    float precisionScore = calculatePercentDiff(trans, ref);
    return precisionScore;
  }

  static float calculatePercentDiff(ArrayList<ArrayList<String>> trans, ArrayList<ArrayList<String>> ref){
    ArrayList<ArrayList<String>> usedWords = new ArrayList<>();
    float similarities = 0;
    for(int i = 0; i < trans.size(); i++){
      if(ref.contains(trans.get(i)) && !usedWords.contains(trans.get(i))){
        similarities++;
        usedWords.add(trans.get(i));
      }
    }
    return similarities / (float) trans.size();
  }

  static ArrayList<ArrayList<String>> calculateNgrams(String s, int n){
    ArrayList<ArrayList<String>> allSets = new ArrayList<>();
    ArrayList<String> nGramSets = new ArrayList<>();
    String [] splitString = s.split(" ");
    for(int i = 0; i < splitString.length - (n-1); i++){
      for(int j = i; j < i + n; j++){
          nGramSets.add(splitString[j]);
      }
      allSets.add(nGramSets);
      nGramSets = new ArrayList<>();
    }
    return allSets;
  }
}

import java.io.*;
import java.util.ArrayList;

class BleuScore{
  public static void main(String [] args){
    float bestnGramOne = 0;
    float bestnGramTwo = 0;
    float bestnGramThree = 0;
    float bestnGramFour = 0;
    float bestLengthMatch = 0;
    String [] transLength = null;
    String [] refLength = null;
    float brevity = 0;
    try{
      /**HANDLE FILES**/
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
          transLength = output.split(" ");
          refLength = s.split(" ");
          if(transLength.length == refLength.length)
            bestLengthMatch = refLength.length;
          else if(Math.abs(transLength.length - refLength.length) < Math.abs(transLength.length - bestLengthMatch))
            bestLengthMatch = refLength.length;
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
      float totalPrecision = bestnGramOne * bestnGramTwo * bestnGramThree * bestnGramFour;
      pw.println("Best 1 : " + bestnGramOne);
      pw.println("Best 2 : " + bestnGramTwo);
      pw.println("Best 3 : " + bestnGramThree);
      pw.println("Best 4 : " + bestnGramFour);
      pw.println("Best Precision : " + totalPrecision);
      pw.println("Best Length : " + bestLengthMatch);
      if(transLength.length > bestLengthMatch)
        brevity = bestLengthMatch / (float) transLength.length;
      if(transLength.length < bestLengthMatch)
        brevity = (float) transLength.length / bestLengthMatch;
      if(transLength.length == bestLengthMatch)
        brevity = 1;
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
    float similarities = 0;
    for(int i = 0; i < trans.size(); i++){
      if(ref.contains(trans.get(i)))
        similarities++;
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

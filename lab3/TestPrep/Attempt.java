import java.io.*;
import java.util.ArrayList;

class Attempt{
  public static void main(String [] args){
    float bestnGramOne = 0;
    float bestnGramTwo = 0;
    float bestnGramThree = 0;
    float bestnGramFour = 0;
    float bestLengthMatch = 0;
    float brevity = 0;
    String output = "";
    String ref = "";
    String [] outputArray = null;
    String [] refArray = null;
    ArrayList<String> refList = new ArrayList<>();
    try{
      /**Handle Files**/
      BufferedReader br = new BufferedReader(new FileReader("trans.txt"));
      PrintWriter pw = new PrintWriter("Output.txt");
      output = br.readLine();
      System.out.println("Output : " + output);
      br = new BufferedReader(new FileReader("Refs.txt"));
      while((ref = br.readLine()) != null){
        System.out.println("Reference : " + ref);
        refList.add(ref);
      }
      br.close();
      /**Main Calculations**/
      for(int i = 1; i < 5; i++){
        ArrayList<ArrayList<String>> nTransList = calculateNgrams(output, i);
        for(String s : refList){
          outputArray = output.split(" ");
          refArray = s.split(" ");
          if(outputArray.length == refArray.length)
            bestLengthMatch = refArray.length;
          else if(Math.abs(outputArray.length - refArray.length) < Math.abs(outputArray.length - bestLengthMatch))
            bestLengthMatch = refArray.length;
          ArrayList<ArrayList<String>> nRefList = calculateNgrams(s, i);
          float precision = calculatePrecison(nTransList, nRefList);
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
      if(outputArray.length > bestLengthMatch)
        brevity = bestLengthMatch / (float) outputArray.length;
      if(outputArray.length < bestLengthMatch)
        brevity = (float) outputArray.length / bestLengthMatch;
      if(outputArray.length == bestLengthMatch)
        brevity = 1;
      pw.println("Best 1 : " + bestnGramOne);
      pw.println("Best 2 : " + bestnGramTwo);
      pw.println("Best 3 : " + bestnGramThree);
      pw.println("Best 4 : " + bestnGramFour);
      pw.println("Best Precision : " + totalPrecision);
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
    return Math.pow(n, 0.25);
  }

  static float calculatePrecison(ArrayList<ArrayList<String>> out, ArrayList<ArrayList<String>> ref){
    float countSimilars = 0;
    for(int i = 0; i < out.size(); i++){
      if(ref.contains(out.get(i)))
        countSimilars++;
    }
    return countSimilars / (float) out.size();
  }

  static ArrayList<ArrayList<String>> calculateNgrams(String s, int n){
    ArrayList<ArrayList<String>> allSets = new ArrayList<>();
    ArrayList<String> oneSet = new ArrayList<>();
    String [] splitString  = s.split(" ");
    for(int i = 0; i < splitString.length-(n-1); i++){
      for(int j = i; j < i + n; j++){
        oneSet.add(splitString[j]);
      }
      allSets.add(oneSet);
      oneSet = new ArrayList<>();
    }
    return allSets;
  }
}

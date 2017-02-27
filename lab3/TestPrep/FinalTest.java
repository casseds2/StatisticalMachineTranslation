import java.io.*;
import java.util.ArrayList;

class FinalTest{
  public static void main(String [] args){
    ArrayList<String> allRefs = new ArrayList<>();
    String [] outputArray = null;
    String [] refArray = null;
    float bestnGramOne = 0;
    float bestnGramTwo = 0;
    float bestnGramThree = 0;
    float bestnGramFour = 0;
    float bestLengthMatch = 0;
    float brevity = 0;
    try{
      BufferedReader in = new BufferedReader(new FileReader("Trans.txt"));
      String output = in.readLine();
      System.out.println("Output : " + output);
      in = new BufferedReader(new FileReader("Refs.txt"));
      String ref = "";
      while((ref = in.readLine()) != null){
        allRefs.add(ref);
        System.out.println("Ref : " + ref);
      }
      in.close();
      for(int i = 1; i < 5; i++){
        outputArray = output.split(" ");
        for(String s : allRefs){
          refArray = s.split(" ");
          if(refArray.length == outputArray.length)
            bestLengthMatch = refArray.length;
          if(Math.abs(outputArray.length - refArray.length) < Math.abs(outputArray.length - bestLengthMatch))
              bestLengthMatch = refArray.length;
          ArrayList<ArrayList<String>> nGramOutput = calculateNgrams(output, i);
          ArrayList<ArrayList<String>> nGramRef = calculateNgrams(s, i);
          float precision = calculatePrecision(nGramOutput, nGramRef);
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
      PrintWriter pw = new PrintWriter(new File("Output.txt"));
      float totalPrecision = bestnGramOne * bestnGramTwo * bestnGramThree * bestnGramFour;
      if(bestLengthMatch > outputArray.length)
        brevity = (float) outputArray.length / bestLengthMatch;
      if(bestLengthMatch < outputArray.length)
        brevity = bestLengthMatch /  (float) outputArray.length;
      if(bestLengthMatch == outputArray.length)
        brevity = 1;
      pw.println("Best Precison for n = 1 : " + bestnGramOne);
      pw.println("Best Precison for n = 2 : " + bestnGramTwo);
      pw.println("Best Precison for n = 3 : " + bestnGramThree);
      pw.println("Best Precison for n = 4 : " + bestnGramFour);
      pw.println("Total precision : " + totalPrecision);
      pw.println("Best Length : " + bestLengthMatch);
      pw.println("Brevity : " + brevity);
      pw.println("Bleu Score : " + Math.pow(totalPrecision, 0.25) * brevity);
      pw.close();
    }
    catch(IOException e){
      System.out.println("I/O Error");
    }
  }
  static float calculatePrecision(ArrayList<ArrayList<String>> out, ArrayList<ArrayList<String>> ref){
    ArrayList<ArrayList<String>> used = new ArrayList<>();
    float similarCount = 0;
    for(int i = 0; i < out.size(); i++){
      if(ref.contains(out.get(i)) && !used.contains(out.get(i))){
        similarCount++;
        used.add(out.get(i));
      }
    }
    return similarCount / (float) out.size();
  }

  static ArrayList<ArrayList<String>> calculateNgrams(String s, int n){
    ArrayList<ArrayList<String>> allSets = new ArrayList<>();
    ArrayList<String> oneSet = new ArrayList<>();
    String [] array = s.split(" ");
    for(int i = 0; i < array.length-(n-1); i++){
      for(int j = i; j < i + n; j++){
        oneSet.add(array[j]);
      }
      allSets.add(oneSet);
      oneSet = new ArrayList<>();
    }
    return allSets;
  }
}

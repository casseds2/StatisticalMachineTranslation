import java.util.ArrayList;

class RecursiveNgram{
   public static void main(String[] args) {
      ArrayList<ArrayList<String>> list = new ArrayList<>();
      int index = 0;
      String test = "the cat on the mat hid under the grey sack";
      String [] array = test.split(" ");
      ArrayList<ArrayList<String>> grams = new ArrayList<>();
      for(int i = 1; i < 5; i++){
         grams = gramSet(grams, array, i, index);
      }
      printList(grams);
   }

   static void printList(ArrayList<ArrayList<String>> list){
      for(ArrayList<String> a : list){
         System.out.print("{");
         for(String s : a){
            System.out.print(s + " ");
         }
         System.out.println("}");
      }
   }
   /**Recursive Function to Return all Ngrams of Size n**/
   static ArrayList<ArrayList<String>> gramSet(ArrayList<ArrayList<String>> list, String [] s, int n, int index){
      if(index < s.length-(n-1)){
         list.add(gram(s, n, index));
         gramSet(list, s, n, index+=1);
      }
      return list;
   }
   /**Return a gram of n size from index 'index' **/
   static ArrayList<String> gram(String [] s, int n, int index){
      ArrayList<String> set = new ArrayList<>();
      for(int i = index; i < index + n; i++){
         set.add(s[i]);
      }
      return set;
   }
}

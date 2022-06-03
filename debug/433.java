package debug;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

class Solution {
    public int minMutation(String start, String end, String[] bank) {
        Set<String> bank_set = new HashSet<>();
        for (String each: bank){
            bank_set.add(each);
        }
    
        Queue<String> queue = new LinkedList<>();
        
        char[] options = new char[]{'A', 'C', 'G','T'};
        
        if(!bank_set.contains(end)){
            return -1;
        }

        // initialise queue
        queue.offer(start);
        int res = 0;
        while(!queue.isEmpty()){
            int size = queue.size();
            while(size-->0){
                
                String cur_string = queue.poll();
                char[] cur_string_arr = cur_string.toCharArray();
                
                if (cur_string.equals(end)) return res;// if found
                    
                // attempt transition
                for (int i=0; i<8; i++){
                    char old_char = cur_string_arr[i];
                    for (int j=0; j<4; j++){
                        // if char not the same
                        if (cur_string_arr[i]!=options[j]){
                            cur_string_arr[i] = options[j];
                            String new_string  = new String(cur_string_arr);
                            
                            // if new_string is valid
                            if (bank_set.contains(new_string)){
                                queue.offer(new_string);
                                //bank_set.remove(new_string);
                            }
                        }
                    }
                    // change back
                    cur_string_arr[i]= old_char;
                }
                res++;

            }
        }
        return -1;
    }

    public static void main(String[] args) {
        

        String[] test = new String[]{"AAAACCCC","AAACCCCC","AACCCCCC"};
        new Solution().minMutation("AAAAACCC", "AACCCCCC", test);
        
    }
}
package ascii_art;

import java.util.HashSet;
import java.util.Set;

public class Algorithms {
    private final static String[] MORSE = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---",
            "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...- ", ".--", "-..-",
            "-.--", "--.."};


    public static int findDuplicate(int[] numList) {

        int slow = numList[0];
        int fast = numList[numList[0]];

        while (fast != slow) {
            slow = numList[slow];
            fast = numList[numList[fast]];
        }

        slow = 0;
        while (fast != slow) {
            slow = numList[slow];
            fast = numList[fast];
        }
        return slow;
    }

    public static int uniqueMorseRepresentations(String[] words) {
        Set<String> morse = new HashSet<>();
        for (String word : words) {
                String s = "";
                for (int i = 0; i < word.length(); i++) {
                    s = s.concat(MORSE[(int) word.charAt(i) - 97]);

                }
                morse.add(s);

        }
        return morse.size();

    }
}



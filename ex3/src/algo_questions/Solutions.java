package algo_questions;

public class Solutions {
    public Solutions() {
    }

    /**
     * Method computing the maximal amount of tasks out of n tasks that can be completed with m time slots.
     * A task can only be completed in a time slot if the length of the time slot is grater than the no.
     * of hours needed to complete the task.
     * Parameters:
     * tasks - array of integers of length n. tasks[i] is the time in hours required to complete task i.
     * timeSlots - array of integersof length m. timeSlots[i] is the length in hours of the slot i.
     * Returns:
     * maximal amount of tasks that can be completed
     */
//    public static int alotStudyTime​(int[] tasks, int[] timeSlots){

//    }

    /**
     * Method computing the nim amount of leaps a frog needs to jumb across n waterlily leaves, from leaf 1 to leaf n.
     * The leaves vary in size and how stable they are, so some leaves allow larger leaps than others. leapNum[i]
     * is an integer telling you how many leaves ahead you can jump from leaf i. If leapNum[3]=4, the frog can jump
     * from leaf 3, and land on any of the leaves 4, 5, 6 or 7.
     * Parameters:
     * leapNum - array of ints. leapNum[i] is how many leaves ahead you can jump from leaf i.
     * Returns:
     * minimal no. of leaps to last leaf.
     */
    public static int minLeap(int[] leapNum) {
        int len = leapNum.length;
        int[] jumps = new int[len];
        jumps[len - 1] = 0;
        for (int i = len - 2; i >= 0; i--) {
            if (leapNum[i] == 0) {
                jumps[i] = 0;
                continue;
            }
            int min = len + 1;
            for (int j = 1; j <= leapNum[i]; j++) {
                if (i + j == len - 1) {
                    jumps[i] = 1;
                    break;
                }
                if (i + j < len) {
                    if (jumps[i + j] < min && jumps[i + j] != 0) {
                        jumps[i] = jumps[i + j] + 1;
                        min = jumps[i + j];
                    }
                }
            }
        }
        return jumps[0];

    }

    /**
     * Method computing the solution to the following problem: A boy is filling the water trough for his
     * father's cows in their village. The trough holds n liters of water. With every trip to the village well,
     * he can return using either the 2 bucket yoke, or simply with a single bucket. A bucket holds 1 liter.
     * In how many different ways can he fill the water trough? n can be assumed to be greater or equal to 0,
     * less than or equal to 48.
     * Parameters:
     *
     * @param n n
     * @return valid output of algorithm.
     */
    public static int bucketWalk(int n) {
        int[] buckets = new int[n + 1];
        buckets[0] = 1;
        buckets[1] = 1;
        for (int i = 2; i <= n; i++) {
            buckets[i] = buckets[i - 1] + buckets[i - 2];
        }
        return buckets[n];
    }

    /**
     * Method computing the solution to the following problem: Given an integer n, return the number of structurally
     * unique BST's (binary search trees) which has exactly n nodes of unique values from 1 to n. You can assume
     * n is at least 1 and at most 19. (Definition: two trees S and T are structurally distinct if one can
     * not be obtained from the other by renaming of the nodes.) (credit: LeetCode)
     *
     * @param n n
     * @return valid output of algorithm.
     */
    public static int numTrees(int n) {
        // Checking for Invalid Input
        if (n < 0) {
            throw new IllegalArgumentException("Invalid Input");
        }
        // For n == 0, empty tree is a valid BST.
        // For n == 1, valid BST can have only one node.
        if (n <= 1) {
            return 1;
        }

        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            // We only need to do half as dp[i] is symmetrical.
            // For example, when i = 5:
            // dp[i] = dp[0]*dp[4] + dp[1]*dp[3] + dp[2]*dp[2] + dp[3]*dp[1] + dp[4]*dp[0]
            // Here except dp[2]*dp[2] all others are appearing twice.
            for (int j = 0; j < i / 2; j++) {
                dp[i] += dp[j] * dp[i - 1 - j];
            }
            dp[i] *= 2;

            // Only add i/2 * i/2 for odd numbers.
            if ((i & 1) == 1) {
                dp[i] += dp[i / 2] * dp[i / 2];
            }
        }

        return dp[n];
    }
//    public static long factorial(int n){
//        if (n<=2) {return n;}
//        return factorial(n-1)*n;
//    }

}
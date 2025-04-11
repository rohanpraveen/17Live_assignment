import org.example.util.JsonUtil;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TopThreeUniquenessTest {

    @Test
    public void testTopThreeAreGloballyUnique() throws Exception {
        String outputPath = "src/main/resources/output.json"; // ✅ Define your output path
        Map<String, List<String>> output = JsonUtil.readOutput(outputPath); // ✅ Read flattened output

        Set<String> usedTop3 = new HashSet<>();

        for (Map.Entry<String, List<String>> entry : output.entrySet()) {
            List<String> streamers = entry.getValue();
            for (int i = 0; i < Math.min(3, streamers.size()); i++) {
                String id = streamers.get(i);
                for (String used : usedTop3) {
                    double similarity = calculateSimilarity(used, id);
                    assertTrue(similarity < 0.9, "❌ Duplicate or similar ID found in top-3: " + id + " ~ " + used);
                }
                usedTop3.add(id);
            }
        }
    }

    private static double calculateSimilarity(String s1, String s2) {
        if (s1 == null || s2 == null) return 0;
        String str1 = s1.toLowerCase().trim();
        String str2 = s2.toLowerCase().trim();
        if (Math.abs(str1.length() - str2.length()) > 5) return 0;
        int maxLength = Math.max(str1.length(), str2.length());
        int editDistance = levenshteinDistance(str1, str2);
        return 1.0 - ((double) editDistance / maxLength);
    }

    private static int levenshteinDistance(String s1, String s2) {
        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) costs[j] = j;
                else if (j > 0) {
                    int newValue = costs[j - 1];
                    if (s1.charAt(i - 1) != s2.charAt(j - 1))
                        newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
                    costs[j - 1] = lastValue;
                    lastValue = newValue;
                }
            }
            if (i > 0) costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }
}

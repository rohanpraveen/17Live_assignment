package org.example;

import org.example.model.Section;
import org.example.model.Stream;
import org.example.util.JsonUtil;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            String inputPath = "src/main/resources/input.json";
            String outputPath = "src/main/resources/output.json";

            List<Section> sections = JsonUtil.readInput(inputPath);
            System.out.println("✅ Input loaded! Sections found: " + sections.size());

            Set<String> usedTop3IDs = new HashSet<>();


            for (int sectionIndex = 0; sectionIndex < sections.size(); sectionIndex++) {
                Section section = sections.get(sectionIndex);
                List<Stream> streams = section.getSectionData();

                for (int position = 0; position < Math.min(3, streams.size()); position++) {
                    String currentID = streams.get(position).getStreamerID();

                    boolean isDuplicate = false;
                    for (String usedID : usedTop3IDs) {
                        if (usedID.equals(currentID) || calculateSimilarity(usedID, currentID) > 0.9) {
                            isDuplicate = true;
                            break;
                        }
                    }

                    if (isDuplicate) {
                        boolean swapped = false;

                        for (int j = 3; j < streams.size(); j++) {
                            String candidateID = streams.get(j).getStreamerID();

                            boolean isUnique = true;
                            for (String usedID : usedTop3IDs) {
                                if (usedID.equals(candidateID) || calculateSimilarity(usedID, candidateID) > 0.9) {
                                    isUnique = false;
                                    break;
                                }
                            }

                            if (isUnique) {
                                Stream temp = streams.get(position);
                                streams.set(position, streams.get(j));
                                streams.set(j, temp);

                                System.out.println("🔁 Swapped duplicate in section '" + section.getSectionID()
                                        + "' — replaced '" + currentID + "' with '" + candidateID + "'");

                                currentID = candidateID;
                                swapped = true;
                                break;
                            }
                        }

                        if (!swapped) {
                            System.out.println("⚠️ No unique swap found for '" + currentID + "' in section '"
                                    + section.getSectionID() + "'");
                        }
                    }

                    usedTop3IDs.add(currentID);
                }

                System.out.println("🎯 Final Top 3 for section '" + section.getSectionID() + "':");
                for (int i = 0; i < 3 && i < streams.size(); i++) {
                    System.out.println("   • " + streams.get(i).getStreamerID());
                }
            }

            JsonUtil.writeFlattenedOutput(sections, outputPath);
            System.out.println("✅ Output written to " + outputPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double calculateSimilarity(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return 0;
        }

        String str1 = s1.toLowerCase().trim();
        String str2 = s2.toLowerCase().trim();

        if (Math.abs(str1.length() - str2.length()) > 5) {
            return 0;
        }

        int maxLength = Math.max(str1.length(), str2.length());
        if (maxLength == 0) {
            return 1.0;
        }

        int editDistance = levenshteinDistance(str1, str2);
        return 1.0 - ((double) editDistance / maxLength);
    }

    private static int levenshteinDistance(String s1, String s2) {
        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    costs[j] = j;
                } else if (j > 0) {
                    int newValue = costs[j - 1];
                    if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                        newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
                    }
                    costs[j - 1] = lastValue;
                    lastValue = newValue;
                }
            }
            if (i > 0) {
                costs[s2.length()] = lastValue;
            }
        }
        return costs[s2.length()];
    }
}
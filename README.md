# 17Live Developer Assignment

## How to Run the Project

1. **Extract** the project zip file.
2. **Open** the project in IntelliJ IDEA (or any Java IDE of your choice).
3. Make sure you have **Java 17+** and **Maven** installed on your machine.
4. Place your `input.json` file inside:  
5. Run the `Main.java` class.
6. The program will generate `output.json` in the same `resources` folder.



---

## Algorithm Explanation 

We process each section one by one. For each section, we look at the **top 3 streamers** in the list.

If any of these top 3 streamers have **already been featured in the top 3 of a previous section**, we try to **swap them out** with another streamer from the same section — someone who hasn’t been used yet.

We also check for **near-duplicate IDs** using string similarity (Levenshtein distance), to catch cases where streamers may have slightly different-looking IDs but actually represent the same person.

If we can't find a unique replacement, we log a warning and move on — this way, we always keep the experience smooth, without crashing or getting stuck.

##  Input &  Output

You can find the input and output files inside the `src/main/resources/` folder:

- `input.json`: contains the raw section data and streamer IDs as provided
- `output.json`: contains the processed output with unique top 3 streamers per section

Both files are in standard JSON format and are human-readable.

---

##  Test Cases Used

###  Test Case 1: No duplicates across sections
All top-3 streamers are already unique. No swaps required.

### Test Case 2: Same streamer in top 3 of multiple sections
Expected behavior: The second occurrence should be replaced with an unused ID.

###  Test Case 3: Streamers with similar IDs (e.g. typos)
Expected behavior: IDs that are 90% similar are treated as duplicates and also replaced.

###  Test Case 4: Fewer than 3 streamers in a section
Expected behavior: The algorithm processes what it can. No crash, no unnecessary logic.

---

##  Edge Case & Suggested Improvement

###  Scenario:
If **all streamers in a section** are already used in the top-3 of other sections, there’s no unique candidate left to swap.

###  What happens:
The code logs a warning and leaves the duplicate as is. It doesn't crash, but the uniqueness constraint isn't met.

###  Suggested Solution:
To improve user experience:
- **Option 1:** Lower the similarity threshold slightly (e.g., from 90% to 85%)
- **Option 2:** Add an "override mode" where a streamer can appear again but is visually marked (like "⭐ Popular")
- **Option 3:** Prioritize lesser-used streamers using frequency tracking

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 1 | 1. Code quality | BikeShareApp.java:28 | A generic `Exception` is caught when parsing user input instead of specific exceptions like `NumberFormatException` or `IllegalArgumentException`. | Should fix | Catch specific exceptions to handle invalid input types gracefully without hiding unexpected runtime errors. |
| 2 | 1. Code quality | RideRepository.java:15 | The `FileWriter` is instantiated inside the `try` block but the file path `"rides.csv"` is hardcoded as a string literal. | Note | Extract `"rides.csv"` into a `private static final String` constant or pass it into the constructor. |
| 3 | 2. Cohesion, coupling and connascence | BikeShareApp.java:42-45 | The command arguments are still accessed directly via raw array indices (`parts[1]`, `parts[2]`), which retains a minor connascence of position. | Note | Map the array elements to appropriately named local variables immediately after splitting, or pass them to a structured `Command` object. |

**Findings Summary:**
- Check 1 (Code quality): 2 findings
- Check 2 (Cohesion, coupling and connascence): 1 finding
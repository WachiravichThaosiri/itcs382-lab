# Lab Report, Code Quality and Design Quality

Repository URL: https://github.com/WachiravichThaosiri/itcs382-lab.git

## Tools

| Item | Version or name |
| --- | --- |
| JDK | 25.0.4.1 |
| Maven | 3.10.0 |
| IDE | VS code |
| SonarQube for IDE | Vs code |
| AI tool and exact model | Gemini |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change:

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 | Java(536871799) | Ln8, Col17 | Resource leak: 'in' is never closed |
| 2 | Java(268435844) |  Ln8, Col8 | The import java.util.Data is never used |
| 3 |  Java(570425421)|  Ln12, Col17 | The value of the field ParcelService.maxParcels is not used |
| 4 | Java(536871799)|  Ln76, Col24 | Resource leak: 'writer' is never closed |

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- | 
| Date | 21/10/2026 |
| AI tool and exact model | Gemini |
| Commit reviewed | N/A (Code provided in prompt) |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | Pasted the files |
| Output file in the reviews folder | pracelpoint-review.md |

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | Main.java:7 | Resource Scanner in is never closed. | Both | java:S2095 | No, closing a Scanner wrapped around System.in closes the standard input stream, which cannot be reopened and is generally avoided in simple console apps. |
| 2 | ParcelService.java:5 | Unused import java.util.Date. | Both | java:S1128 | Yes, the import statement is never used. |
| 3 | ParcelService.java:10 | Unused field maxParcels. | Both | java:S1068 | Yes, the field is declared but never utilized in the class. |
| 4 | ParcelService.java:40 | Objects compared with == instead of equals(). | Both | java:S4973 | Yes, comparing String objects with == checks reference identity, not value equality. |
| 5 | ParcelService.java:75 | Empty catch block for IOException. | Both | java:S108 | Yes, silently swallowing exceptions hides system failures and makes debugging difficult. |
| 6 | ParcelService.java:71 | Resource FileWriter writer is never closed. | Both | java:S2095 | Yes, failing to close the writer can lead to resource leaks and missing data because the buffer is not flushed. |
| 7 | Parcel.java:6 | Field status is public, not private. | Both | java:S1104 | Yes, exposing public fields directly violates the principle of encapsulation. |
| 8 | ParcelService.java:18 | Operation returns internal collection lockers directly. | Both | java:S2384 | Yes, returning a mutable internal collection allows calling code to modify the list externally. |
| 9 | Locker.java:5 | Connascence of convention: integer size maps to sizes. | AI | Connascence of convention | Yes, using magic integers creates a hidden dependency requiring developers to know the implicit mapping. |
| 10 | ParcelService.java:26 | Content coupling: modifying the public status field. | AI | Content coupling | Yes, directly mutating another class's internal state creates tight, rigid coupling. |
| 11 | Main.java:22 | Strong coupling (reaching through): calling service.getLockers().stream()... | AI | Strong coupling / Law of Demeter | Yes, chaining through intermediate objects to query their states exposes internal structure. |
| 12 | ParcelService.java:63 | Control coupling: forStaff boolean flag. | AI | Control coupling | Yes, passing flags to control internal execution flow indicates the caller is overly aware of the method's logic. |
| 13 | ParcelService.java:63 | Logical cohesion due to the forStaff flag. | AI | Logical cohesion | Yes, grouping distinct behaviors (staff printing vs. student printing) into one method weakens cohesion. | 

2.1 Name two problems that only the AI review found.
* Content coupling: `ParcelService` directly modifies the public `status` field of the `Parcel` object rather than using a setter or behavior method.
* Control coupling and logical cohesion: The `printParcels` method uses a boolean `forStaff` flag to dictate its internal control flow.

2.2 Name one problem that only SonarQube found, or write none.
* Using `System.out.println` instead of a dedicated logger (SonarQube rule `java:S106`: Standard outputs should not be used directly to log anything).

2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.
* **Wrong finding:** "Resource `Scanner in` is never closed" (Main.java:7). While closing resources is a standard rule, calling `.close()` on a `Scanner` that wraps `System.in` will permanently close the application's standard input stream. In simple, single-threaded console applications like this one, it is standard practice to leave `System.in` unclosed. The AI blindly applied a generic resource-leak rule without considering the specific context of standard input.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | Kept | Closing a Scanner wrapped around `System.in` closes the standard input stream, which cannot be reopened and causes errors if more input is needed later. |
| 2 | Fixed | Removed the unused `java.util.Date` import. |
| 3 | Fixed | Removed the unused `maxParcels` field. |
| 4 | Fixed | Changed `==` to `.equals(code)` for String comparison. |
| 5 | Fixed | Added `e.printStackTrace()` to the empty `IOException` catch block. |
| 6 | Fixed | Wrapped the `FileWriter` in a try-with-resources block to ensure it closes properly. |
| 7 | Fixed | Changed `status` to `private` and added `getStatus()` and `setStatus()` methods. |
| 8 | Fixed | Returned `Collections.unmodifiableList(lockers)` to prevent external modification. |

**Number of SonarQube issues that remain after your fixes:** 1

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| **Global State / Common Coupling** | Encapsulated all static variables into instance fields inside a `ParcelStorage` class. | `Refactor: Encapsulate global state` |
| **Control Coupling (Flag Argument)** | Split the single transaction method taking a boolean flag into separate `storeParcel()` and `pickupParcel()` methods. | `Refactor: Eliminate control flag argument` |

### Output of the program after fixes
For the commands `store P1 6501 2`, `pickup P1 5`, and `staff`:

```text
> store P1 6501 2
Parcel P1 stored successfully.
> pickup P1 5
--- Receipt ---
Parcel picked up: P1
Duration: 5
Total fee: 15 baht
---------------
> staff
Unknown command.
> quit
```
## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | October 21, 2026 | October 21, 2026 |
| AI tool and exact model | Gemini (Gemini 2.5 Pro) | Gemini (Gemini 2.5 Pro) |
| Prompt (B or C as given, or your changed prompt in full) | Write a Java 17 command-line program, built with Maven and with no external library, for the specification below. Give it a poor design on purpose. Put the menu, the fee rules, the bike records and the file saving into as few classes as possible, keep shared data in public static fields, use int codes for the member types, and pass a boolean flag that changes what a method does. Keep the code itself tidy, so close every file, handle every exception and leave no unused code. Keep the program short. Give me the pom.xml and every Java file. Specification: BikeShare lends bikes at three campus stations, Library, Canteen and Dorm, and each station starts with two bikes. 1. A member rents an available bike at a station by entering the member ID, the member type (student or staff) and the station. 2. The member returns the bike at any station and enters the ride time in minutes. 3. The fee is 2 baht per minute for a student and 3 baht per minute for a staff member. 4. The program prints a receipt with the bike, the minutes and the fee. 5. The program adds every finished ride to the file rides.csv. 6. The program reads commands from the keyboard until the user types quit. | Write a Java 17 command-line program, built with Maven and with no external library, for the specification below. Give it a high-quality object-oriented design following SOLID principles. Separate responsibilities into dedicated classes (e.g., domain entities, manager/service logic, file repositories, enums for member types, and CLI presentation). Avoid static state, control flags, and magic numbers. Keep the code clean, close all resources, and handle exceptions gracefully. Give me the pom.xml and every Java file. Specification: BikeShare lends bikes at three campus stations, Library, Canteen and Dorm, and each station starts with two bikes. 1. A member rents an available bike at a station by entering the member ID, the member type (student or staff) and the station. 2. The member returns the bike at any station and enters the ride time in minutes. 3. The fee is 2 baht per minute for a student and 3 baht per minute for a staff member. 4. The program prints a receipt with the bike, the minutes and the fee. 5. The program adds every finished ride to the file rides.csv. 6. The program reads commands from the keyboard until the user types quit. |
| Builds with mvn package (yes or no) | Yes | Yes |
| Number of classes, interfaces, enums and records | 1 class (`BikeShareApp`) | 4 classes/enums (`BikeShareApp`, `StationManager`, `RideRepository`, `MemberType`) |
| SonarQube issues, total | 8 | 1 |
| SonarQube issues, by rule | `java:S106` (2), `java:S1104` (5), `java:S3776` (1) | `java:S106` (1) |
| AI review findings in Check 1 | 2 | 2 |
| AI review findings in Check 2 | 6 | 1 |
| Review output file in the reviews folder | `reviews/bikeshare-poor-review.md` | `reviews/bikeshare-goodreview.md` |

### 4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.
**No.** SonarQube primarily focuses on static code analysis, code smells, and potential security vulnerabilities. It reported structural smells like `java:S1104` ("Class variable fields should not have public accessibility") and cognitive complexity (`java:S3776`), but it did **not** explicitly report high-level design design issues like class cohesion levels (e.g., logical cohesion) or class coupling types (e.g., control or common coupling).

### 4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.
**Yes, clearly.** The AI review identified 8 design/quality issues in `bikeshare-poor` versus only 3 minor notes in `bikeshare-good`. 
- **Finding present only in `bikeshare-poor`:**
  > *Finding:* The `processTransaction` method requires an `isRent` boolean flag that changes its control flow, creating control coupling.
  > *Location:* `BikeShareApp.java:60`
  > *Suggested fix:* Remove the boolean parameter and separate the logic into distinct functional methods.

### 4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?
I would strongly prefer extending **`bikeshare-good`**. 
- In `bikeshare-good`, member types are modeled using a `MemberType` enum containing rates and business rules. Adding a `VISITOR` type requires simply adding `VISITOR(4)` to the enum without touching the transaction processing logic.
- In `bikeshare-poor`, adding a visitor requires updating hardcoded integer ternary/if-else logic (`currentMemberType == 1 ? 2 : 3`), modifying global static fields, and risking bug propagation across unrelated procedural methods.

---

## Task 5. Reflection

### 5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.
- **What it checks:** SonarQube is excellent at checking syntax-level bugs, resource leaks, unused code, naming conventions, basic visibility (`public` fields via `java:S1104`), and cyclomatic/cognitive complexity (`java:S3776`).
- **What it does NOT check:** SonarQube does not evaluate architectural design quality, domain modeling, or semantic coupling/cohesion relationships. For example, it failed to flag that `processTransaction(boolean isRent, ...)` combines two completely separate domain actions via control coupling, nor did it detect connascence of convention from raw integer codes (`1` = student, `2` = staff).

### 5.2 When should you not trust the AI review? Give an example from this lab.
You should not trust AI reviews when evaluating contextual line numbers, subtle framework lifecycle requirements, or when the AI hallucinates missing requirements. 
- **Example from this lab:** In the AI review for `bikeshare-good`, the AI flagged catching `Exception` instead of specific exceptions as a "Should fix". However, in a small CLI loop intended to prevent the program from crashing on arbitrary user inputs, a high-level exception handler is often an intentional design choice rather than a bug. Furthermore, AI tools frequently miscalculate exact line numbers when reviewing large files or multiple snippets.

### 5.3 What will you do differently in Project Phase 2 after this lab?
1. **Eliminate Control Flags and Global State Early:** Avoid passing boolean flags to methods that change internal workflow; split them into single-purpose methods early in the design phase.
2. **Use Enums Instead of Magic Numbers/Strings:** Model status codes, roles, and types using Java enums rather than primitive integers or strings.
3. **Combine Linter Rules with Architectural Reviews:** Rely on SonarQube for static bug checks, but perform peer code reviews focusing specifically on single responsibility (SRP), encapsulation, and class cohesion.

---

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| Gemini (Gemini 2.5 Pro) | Initial code generation for poor/good designs and code quality reviews. | Created `BikeShareApp.java`, `pom.xml`, `reviews/bikeshare-poor-review.md`, and `reviews/bikeshare-goodreview.md`. | Verified Maven build via `mvn package`, manually ran code execution test cases, and cross-checked line references against source files. |

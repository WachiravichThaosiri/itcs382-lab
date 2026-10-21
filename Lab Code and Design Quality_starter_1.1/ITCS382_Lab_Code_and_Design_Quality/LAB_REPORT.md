# Lab Report, Code Quality and Design Quality

Repository URL: https://github.com/WachiravichThaosiri/itcs382-lab.git

## Tools

| Item | Version or name |
| JDK | 25.0.4.1 |
| Maven | 3.10.0 |
| IDE | VS code |
| SonarQube for IDE | Vs code |
| AI tool and exact model | Gemini |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change:

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| 1 | Java(536871799) | Ln8, Col17 | Resource leak: 'in' is never closed |
| 2 | Java(268435844) |  Ln8, Col8 | The import java.util.Data is never used |
| 3 |  Java(570425421)|  Ln12, Col17 | The value of the field ParcelService.maxParcels is not used |
| 4 | Java(536871799)|  Ln76, Col24 | Resource leak: 'writer' is never closed |

## Task 2. AI review of the starter code

| Item | Value |
| Date | 21/10/2026 |
| AI tool and exact model | Gemini |
| Commit reviewed | N/A (Code provided in prompt) |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | Pasted the files |
| Output file in the reviews folder | pracelpoint-review.md |

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
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
| | | |
| | | |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text

```

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | | |
| AI tool and exact model | | |
| Prompt (B or C as given, or your changed prompt in full) | | |
| Builds with mvn package (yes or no) | | |
| Number of classes, interfaces, enums and records | | |
| SonarQube issues, total | | |
| SonarQube issues, by rule | | |
| AI review findings in Check 1 | | |
| AI review findings in Check 2 | | |
| Review output file in the reviews folder | | |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.

5.2 When should you not trust the AI review? Give an example from this lab.

5.3 What will you do differently in Project Phase 2 after this lab?

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| | | | |

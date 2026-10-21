| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 1 | 1. Code quality | BikeShareApp.java:91-100 | The string literals "library", "canteen", and "dorm" are repeated multiple times. | Should fix | Extract these string literals into private static final constants or use an enum. |
| 2 | 1. Code quality | BikeShareApp.java:16-57 | The `main` method is long and mixes UI printing, input loop control, command parsing, and exception handling. | Should fix | Extract the command parsing and execution logic into a separate method. |
| 3 | 2. Cohesion, coupling and connascence | BikeShareApp.java:7-103 | The `BikeShareApp` class represents more than one thing (UI, business logic, file I/O), showing weak class cohesion. | Must fix | Separate the responsibilities into distinct classes like UI, StationManager, and RideRepository. |
| 4 | 2. Cohesion, coupling and connascence | BikeShareApp.java:60-90 | The `processTransaction` method exhibits logical cohesion because it groups fundamentally different operations (renting and returning) based on a flag. | Must fix | Split this method into two distinct methods: `rentBike` and `returnBike`. |
| 5 | 2. Cohesion, coupling and connascence | BikeShareApp.java:60 | The `processTransaction` method requires an `isRent` boolean flag that changes its control flow, creating control coupling. | Must fix | Remove the boolean parameter and separate the logic into distinct functional methods. |
| 6 | 2. Cohesion, coupling and connascence | BikeShareApp.java:9-14 | Fields `libraryBikes`, `canteenBikes`, `dormBikes`, `currentMemberId`, and `currentMemberType` are public and static, creating common coupling and breaking encapsulation. | Must fix | Make these fields private, remove the static modifier, and manage them within an instantiated class. |
| 7 | 2. Cohesion, coupling and connascence | BikeShareApp.java:14,74 | The code uses integer codes (1 and 2) to represent member types, creating connascence of convention. | Should fix | Replace the integer codes with a `MemberType` enum containing `STUDENT` and `STAFF`. |
| 8 | 2. Cohesion, coupling and connascence | BikeShareApp.java:39-45 | The command parsing relies heavily on array indices (`parts[1]`, `parts[2]`, `parts[3]`), creating connascence of position. | Note | Encapsulate the parsed command arguments into a structured `Command` record or object. |

**Findings Summary:**
- Check 1 (Code quality): 2 findings
- Check 2 (Cohesion, coupling and connascence): 6 findings
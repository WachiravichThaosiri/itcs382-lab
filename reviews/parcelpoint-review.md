# ParcelPoint Code Review

Files Reviewed: `Locker.java`, `Main.java`, `Parcel.java`, `ParcelService.java`

## Summary

| Category                            | Number of Findings |
| ----------------------------------- | ------------------ |
| Code Quality                        | 6                  |
| Cohesion, Coupling, and Connascence | 7                  |
| Total                               | 13                 |

## 1. Code Quality

| No. | Code Location           | Finding                                                             | Severity   | Suggested Fix                                                                 |
| --- | ----------------------- | ------------------------------------------------------------------- | ---------- | ----------------------------------------------------------------------------- |
| 1   | `Main.java:7`           | Resource `Scanner in` is never closed.                              | Must fix   | Call `in.close()` at the end of the method or use a try-with-resources block. |
| 2   | `ParcelService.java:5`  | Unused import `java.util.Date`.                                     | Should fix | Remove the unused import statement.                                           |
| 3   | `ParcelService.java:10` | Unused field `maxParcels`.                                          | Should fix | Remove the unused `maxParcels` field.                                         |
| 4   | `ParcelService.java:40` | String `code` values are compared using `==` instead of `equals()`. | Must fix   | Change `parcel.getCode() == code` to `parcel.getCode().equals(code)`.         |
| 5   | `ParcelService.java:75` | Empty `catch` block for `IOException`.                              | Should fix | Log or print the exception trace inside the catch block.                      |
| 6   | `ParcelService.java:71` | Resource `FileWriter writer` is never closed.                       | Must fix   | Use a try-with-resources block for the `FileWriter`.                          |

## 2. Cohesion, Coupling, and Connascence

| No. | Code Location                    | Finding                                                                                                    | Severity   | Suggested Fix                                                                           |
| --- | -------------------------------- | ---------------------------------------------------------------------------------------------------------- | ---------- | --------------------------------------------------------------------------------------- |
| 7   | `Parcel.java:6`                  | Field `status` is public rather than private.                                                              | Must fix   | Change visibility to `private` and add getter and setter methods.                       |
| 8   | `ParcelService.java:18`          | The operation returns the internal `lockers` collection directly.                                          | Should fix | Return an unmodifiable list or a copy of the collection.                                |
| 9   | `Locker.java:5`, `Parcel.java:5` | Connascence of convention: integer `size` values represent sizes (`1 = small`, `2 = medium`, `3 = large`). | Note       | Use an `enum` for parcel sizes instead of magic integers.                               |
| 10  | `ParcelService.java:26, 42`      | Content coupling: `ParcelService` directly modifies the public `status` field of `Parcel`.                 | Must fix   | Use a setter method on `Parcel` to update its status.                                   |
| 11  | `Main.java:22`                   | Strong coupling (reaching through): `Main` calls `service.getLockers()` to filter lockers.                 | Should fix | Add a `getFreeLockerCount()` method to `ParcelService`.                                 |
| 12  | `ParcelService.java:63`          | Control coupling: the `forStaff` boolean flag controls the behavior of `printParcels`.                     | Should fix | Split it into two methods, such as `printParcelsForStaff()` and `printStoredParcels()`. |
| 13  | `ParcelService.java:63`          | Logical cohesion: the `forStaff` flag combines different printing logic in one method.                     | Note       | Separate the distinct printing logic into cohesive methods.                             |

## 3. Severity Guide

- Must fix: Address the issue as a priority.
- Should fix: Recommended improvement.
- Note: Design observation or suggested refinement.
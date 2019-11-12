## Regarding the fixes
All fixes in this case study are documented using git tags. They are listed on [GitHub](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/tags).

They can be viewed with: `git tag -l "fix*"`

And details can be viewed with: `git show fix1`

### PCM <--> UML --> Java (8 fixes)
Bidirectional transformation between UML and PCM, but only unidirectional transformation from UML to Java.

 | tag | correlating pull request | addressed problem |
 | -- | -- | -- |
 | fix1 | [PR 42](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/42) | UML parent packages were deleted before child packages. |
 | ~~fix2~~ *(deleted)* | *not really a fix, see [PR 45](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/45)* | *See fix4.* |
 | fix3 | [PR 46](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/46) | While renaming UML packages the namespaces of child packages were not updated. |
 | fix4 | [PR 45](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/45) | Java parameters were not created when a unnamed UML parameter was renamed. |
 | fix5 | [PR 47](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/47) | Missing adaption of Java types (collection <--> non-collection type) when changing the multiplicities of UML return types. |
 | fix6 | [PR 43](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/43) | When inserting Java compilation units, correlating UML classes were not moved to the correlating UML package of the compilation unit. |
 | fix7 | [PR 43](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/43) | *Multiple commits for one issue, see fix6.* |
 | fix8 | [PR 43](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/43) | UML classes were deleted although Java compilation units were only removed from their container. |
 
The following table shows the concept test cases affected by the problems that were addressed by the fixes (with PCM <--> UML --> Java). Fail means the test did not produce the expected results, while error means the test crashed during its execution. A total of 39 test cases were executed. 
 
| Affected Concept Test Case                     | fix1 | fix3 | fix4 | fix5 | fix6/7 | fix8 | fix9 |
|------------------------------------------------|------|------|------|------|--------|------|------|
| Composite Datatype (Creation, UML)             |      |      |      |      |        |      |      |
| Composite Datatype (Creation with Parent, UML) |      |      |      |      |        |      |      |
| Interface (UML)                                |      |      |      |      |        |      |      |
| Parameter (collection type, UML)               |      |      | Fail | Fail |        |      |      |
| Parameter (primitive type, UML)                |      |      | Fail |      |        |      |      |
| Parameter (composite type, UML)                |      |      | Fail |      |        |      |      |
| Repository Component (UML)                     |      |      |      |      | Fail   |      |      |
| Repository Component (PCM)                     |      |      |      |      |        |      |      |
| Repository (Deletion, PCM)                     | Fail |      |      |      |        |      |      |
| Repository (Rename, PCM)                       |      | Fail |      |      |        |      |      |
| Repository (Creation, UML)                     |      | Fail |      |      |        |      |      |
| Required Role (Constructor Parameter, UML)     |      |      | Fail |      |        |      |      |
| Signature (Collection return type, UML)        |      |      |      | Fail |        |      |      |
| System (UML)                                   |      |      |      |      | Fail   |      |      |

### PCM <--> UML <--> Java
Bidirectional transformation between UML and PCM, as well as between UML and Java.

  | tag | correlating pull request | addressed problem |
  | -- | -- | -- |
  | fix9* | [PR 44](https://github.com/vitruv-tools/Vitruv-Domains-ComponentBasedSystems/pull/44) | Unnamed Java packages lead to problems with invalid paths. This could lead to duplicate package names. |

**this fix tag is not located in this repository, as it concerns the domains repository. The correlating pull request is also part of that repository.*

The following table shows the concept test cases affected by the problems that were addressed by the fixes (with PCM <--> UML <--> Java). Fail means the test did not produce the expected results, while error means the test crashed during its execution. A total of 39 test cases were executed.

| Affected Concept Test Case                     | fix1 | fix3 | fix4 | fix5 | fix6/7 | fix8  | fix9  |
|------------------------------------------------|------|------|------|------|--------|-------|-------|
| Composite Datatype (Creation, UML)             |      |      |      |      | Fail   | Error |       |
| Composite Datatype (Creation with Parent, UML) |      |      |      |      | Error  | Fail  |       |
| Interface (UML)                                |      |      |      |      | Fail   | Fail  |       |
| Parameter (collection type, UML)               |      |      | Fail | Fail |        |       |       |
| Parameter (primitive type, UML)                |      |      | Fail |      |        |       |       |
| Parameter (composite type, UML)                |      |      | Fail |      |        |       |       |
| Repository Component (UML)                     |      |      |      |      | Error  | Error | Error |
| Repository Component (PCM)                     |      |      |      |      | Error  | Error |       |
| Repository (Deletion, PCM)                     | Fail |      |      |      |        |       |       |
| Repository (Rename, PCM)                       |      | Fail |      |      |        |       |       |
| Repository (Creation, UML)                     |      | Fail |      |      |        |       |       |
| Required Role (Constructor Parameter, UML)     |      |      | Fail |      |        |       |       |
| Signature (Collection return type, UML)        |      |      |      | Fail |        |       |       |
| System (UML)                                   |      |      |      |      | Error  | Error |       |

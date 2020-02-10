## Regarding the fixes
All fixes in this case study are documented using git tags. They are listed on [GitHub](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/tags).

They can be viewed with: `git tag -l "fix*"`

And details can be viewed with: `git show fix1`

### PCM ↔︎ UML → Java (8 fixes)
Bidirectional transformation between UML and PCM, but only unidirectional transformation from UML to Java.

 | fix commit tag | correlating pull request | addressed problem |
 | -- | -- | -- |
 | [fix1](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix1) | [PR 42](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/42) | UML parent packages were deleted before child packages. |
 | ~~fix2~~ *(deleted)* | *not really a fix, see [PR 45](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/45)* | *See fix4.* |
 | [fix3](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix3) | [PR 46](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/46) | While renaming UML packages the namespaces of child packages were not updated. |
 | [fix4](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix4) | [PR 45](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/45) | Java parameters were not created when a unnamed UML parameter was renamed. |
 | [fix5](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix5) | [PR 47](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/47) | Missing adaption of Java types (collection <--> non-collection type) when changing the multiplicities of UML return types. |
 | [fix6](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix6) | [PR 43](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/43) | When inserting Java compilation units, correlating UML classes were not moved to the correlating UML package of the compilation unit. |
 | [fix7](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix7) | [PR 43](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/43) | *Multiple commits for one issue, see fix6.* |
 | [fix8](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix8) | [PR 43](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/43) | UML classes were deleted although Java compilation units were only removed from their container. |

### PCM ↔︎ UML ↔︎ Java
Bidirectional transformation between UML and PCM, as well as between UML and Java.

  | fix commit tag | correlating pull request | addressed problem |
  | -- | -- | -- |
  | fix9* | [PR 44](https://github.com/vitruv-tools/Vitruv-Domains-ComponentBasedSystems/pull/44) | Unnamed Java packages lead to problems with invalid paths. This could lead to duplicate package names. |

**this fix tag is not located in this repository, as it concerns the domains repository. The correlating pull request is also part of that repository.*

### PCM ↔︎ UML ↔︎ Java ← PCM
Bidirectional transformation between UML and PCM, as well as between UML and Java. Unidirectional transformation from PCM to Java.

  | fix commit tag | correlating pull request | addressed problem |
  | -- | -- | -- |
  | [fix10](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix10) | [PR 50](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/50) | The repository name was not adapted when creating the correlating Java packages (first letter lower case). This led to duplicate packages. |
  | [fix11](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix11) | [PR 51](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/51) | Misplaced Java packages were created when creating Java packages correlating to unnamed PCM repositories. |
  | [fix12](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix12) | [PR 53](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/53) | Duplicate UML root models were sometimes created due to missing model tag correspondence. |
  | [fix13](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix13) | [PR 54](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/54) | Duplicate UML packages were created due to the UML package already existing but missing the UML ↔︎ Java correspondence. |
  | [fix14](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix14) | [PR 58](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/58) | Duplicate UML interfaces were created due to the UML interface already existing but missing the UML ↔︎ Java correspondence. |
  | [fix15](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix15) | [PR 57](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/57) | Fixed duplicate interface and compilation unit creation to avoid null segment crashes while saving resources. |
  | [fix16](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix16) | [PR 63](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/63) | Java packages were not properly deleted due to duplicate creation caused by the Java package already existing but missing the correspondence. |
  | [fix17](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix17) | [PR 64](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/64) | UML elements were incorrectly renamed. They were either dropping their first character or completely chopped up due to careless use of methods that expect regular expressions. |
  | [fix18](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix18) | [PR 65](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/65) | Java/UML types were created twice due to already existing but missing the correspondences. This caused crashes during test setups. |
   | [fix19](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix19) | [PR 66](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/66) | PCM systems were created twice due to already existing but missing the correspondences. Additionally, system name changes were incorrectly propagated. |
   | [fix20](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix20) | [PR 66](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/66) | Multiple UML models were created due to the lack of any control mechanism that checked the existence of UML models. |

### PCM ↔︎ UML ↔︎ Java ↔︎ PCM
Bidirectional transformation between UML and PCM, between UML and Java, and between PCM and Java.

  | fix commit tag | correlating pull request | addressed problem |
  | -- | -- | -- |
  | [fix21](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix21) | [PR 68](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/68) | Duplicate creation of PCM repositories due to already being created but missing the correspondences. |
  | [fix22](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix22) | [PR 69](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/69) | Invalid repository name propagation due to missing naming pattern enforcement. |
  | [fix23](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix23) | [PR 70](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/70) | Change propagation crashed due to inability to deal with null-names when reacting to the creation of Java packages. |
  | [fix24](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix24) | [PR 71](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/71) | Duplicate operation interface creation in the PCM model due to the existing operation interface missing the required correspondences. |
  | [fix25](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix25) | [PR 72](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/72) | Duplicate creation of PCM systems due to already being created but missing the correspondences. |
  | [fix26](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix26) | [PR 73](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/73) | Broken Java to PCM name change propagation that led amongst other problems to an endless suffix-adding rename cycle for classes and compilation units. |
  | [fix27](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix27) | [PR 74](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/74) | Duplicate repository component creation due to the element already existing but missing the required correspondences. |
  | [fix28](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix28) | [PR 75](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/75) | Incorrect first letter capitalization during name propagation for repository components and classifiers. |
  | [fix29](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix29) | [PR 76](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/76) | Duplicate CompositeDataType creation due to the element already existing but missing the required correspondences. |
  | [fix30](https://github.com/tsaglam/Vitruv-Applications-ComponentBasedSystems-CaseStudy/releases/tag/fix30) | [PR 77](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/pull/77) | Duplicate creation of assembly contexts and correlating fields, constructors, import declarations, and access methods. |

### Fix to Test Case Failure/Error Correlation

The following tables shows the concept test cases affected by the problems that were addressed by the fixes. Fail means the test did not produce the expected results, while error means the test crashed during its execution. A total of 39 test cases were executed for the first two phases. A total of 16 test cases were executed for the second two phases, as not every concept could be fully fixed due to the time constraints of this case study. Note that the complexity of the change propagation increases exponentially with each additional transformation. As a result the amount of issues with each concept grew exponentially as well.

The following terms are used:
* Error - the test case was executed but did not meet its success conditions
* Fail  - the test case crashed during its execution
* Loop  - the test case never terminated due to a endless loop in the change propagation
* Wrong - while the test case was executed and did meet its success conditions, the persisted files are not as expected

**PCM ↔︎ UML → Java:**

 Note that 14 out of 39 test cases are displayed here, the others did not have any issues.
| Fix Number                                     | fix1 | fix3 | fix4 | fix5 | fix6/7 | fix8 | fix9 |
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

**PCM ↔︎ UML ↔︎ Java:**

Note that 14 out of 39 test cases are displayed here, the others did not have any issues.
| Fix Number                                     | fix1 | fix3 | fix4 | fix5 | fix6/7 | fix8  | fix9  |
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

**PCM ↔︎ UML ↔︎ Java ← PCM:**

 Note that all 16 fixed test cases are displayed here.
|                                                | fix10 | fix11 | fix12 | fix13 | fix14 | fix15 | fix16 | fix17 | fix18 | fix19 | fix20 |
|------------------------------------------------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|
| Assembly Context (Creation, PCM)               | Error |       | Error | Error |       |       |       |       |       |       |       |
| Assembly Context (Creation, UML)               | Error |       | Error | Error |       |       |       |       |       |       |       |
| Composite Datatype (Creation, PCM)             | Error |       | Error | Error |       |       |       |       | Error |       |       |
| Composite Datatype (Creation, UML)             | Error |       | Error | Error |       |       |       |       | Error |       |       |
| Composite Datatype (with Parent, PCM)          | Error |       | Error | Error |       |       |       |       | Error |       |       |
| Composite Datatype (with Parent, UML)          | Error |       | Error | Error |       |       |       |       | Error |       |       |
| Interface (PCM)                                | Error |       | Error | Error | Error |       |       |       |       |       |       |
| Interface (UML)                                | Error |       | Error | Error | Error | Error |       |       |       |       |       |
| Repository Component (UML)                     | Error |       | Error | Error |       |       |       | Fail  | Error |       |       |
| Repository Component (PCM)                     | Error |       | Error | Error |       |       |       |       |       |       |       |
| Repository (Deletion, PCM)                     | Fail  |       | Error | Error |       |       |       |       |       |       |       |
| Repository (Rename, PCM)                       | Fail  |       | Error |       |       |       | Wrong |       |       |       |       |
| Repository (Creation, PCM)                     | Fail  |       | Error |       |       |       |       |       |       |       |       |
| Repository (Creation, UML)                     |       | ???   |       |       |       |       |       |       |       |       | Error |
| System (PCM)                                   |       |       | Fail  | Error |       |       |       |       |       |       |       |
| System (UML)                                   |       |       |       |       |       |       |       |       | Error | Error | Error |

_Note: The test case marked with ??? did not error when removing fix11, as other fixes still prevented the error. This test case only errored when removing the repository find-or-create-pattern as well, which was introduced by a later fix._

**PCM ↔︎ UML ↔︎ Java ↔︎ PCM:**

 Note that all 16 fixed test cases are displayed here.
|                                                | fix21 | fix22 | fix23 | fix24 | fix25 | fix26 | fix27 | fix28 | fix29 | fix30 |
|------------------------------------------------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|
| Assembly Context (Creation, PCM)               | Error |       |       |       |       | Loop  | Error |       |       | Error |       
| Assembly Context (Creation, UML)               | Error |       |       |       |       | Loop  | Error |       |       | Error |       
| Composite Datatype (Creation, PCM)             | Error |       |       |       |       | Error |       |       |       |       |      
| Composite Datatype (Creation, UML)             | Error |       |       |       |       | Error |       |       |       |       |       
| Composite Datatype (with Parent, PCM)          | Error |       |       |       |       | Error |       |       |       |       |       
| Composite Datatype (with Parent, UML)          | Error | Error | Error |       |       | Error |       |       | Error |       |       
| Interface (PCM)                                | Error |       |       |       |       |       |       |       |       |       |       
| Interface (UML)                                | Error |       |       | Error |       | Loop  |       |       |       |       |       
| Repository Component (UML)                     | Error | Error | Error |       |       | Loop  | Error |       |       |       |       
| Repository Component (PCM)                     | Error | Fail  |       |       |       | Loop  | Error | Error |       |       |       
| Repository (Deletion, PCM)                     | Error | Fail  |       |       |       |       |       |       |       |       |       
| Repository (Rename, PCM)                       | Error | Fail  |       |       |       |       |       |       |       |       |       
| Repository (Creation, PCM)                     | Error | Fail  | Error |       |       |       |       |       |       |       |       
| Repository (Creation, UML)                     |       |       |       |       |       |       |       |       |       |       |       
| System (PCM)                                   |       |       |       |       |       | Error |       |       |       |       |       
| System (UML)                                   |       |       | Error |       | Error | Error |       |       |       |       |       

---
layout: page
title: Celeste's Project Portfolio Page
---

### Project: SGSafe

SGSafe is a command-line application designed to help investigative teams manage case files efficiently. It supports structured data entry, filtering, and verbose summaries for legal and operational clarity. The system is written in Java and organized into modular packages for maintainability and scalability.

Given below are my contributions to the project.

* **Project Management**:
    * Created and managed all GitHub milestones (`v1.0`, `v2.0`, `v2.1`), including deadlines and scope definitions.
    * Coordinated milestone goals with team members to ensure timely delivery and feature alignment.

* **New Feature**: Implemented `ListCommand`
    * What it does: Lists cases in either summary or verbose format, with optional filtering by status and category.
    * Justification: This feature is central to the application's usability, allowing users to view case data clearly and flexibly.
    * Highlights:
        * Implemented `ListCommand` in `v1.0`.
        * Added `--mode` flag in `v2.0` to toggle between `verbose` and `summary` views.
        * Added `--status` flag in `v2.0` to filter cases by `open`, `closed`, or `all`.
        * Enhanced summary mode with a header line showing `ID`, `TITLE`, etc.
        * Added a dynamic info sentence indicating how many cases are listed and their status.
        * Refined verbose mode formatting for readability and alignment.
        * Integrated category display and ensured soft-deleted cases are excluded from listings.
        * Maintained and updated `ListCommandTest` to reflect evolving logic and formatting changes. 
        * Converted internal numeric case IDs to 6-character hexadecimal strings for consistent display formatting.

* **Foundational Contributions**:
    * Laid the foundation for the `Case` and `CaseManager` classes.
    * Built the initial `Display` and `Parser` classes, including the embedded `Validator`.
    * Created the base `Command` class and defined the full action workflow: parsing → dispatch → execution.
    * Refactored all references from Duke to SGSafe, establishing project identity.
    * Organized the codebase into logical packages: `utils/command`, `utils/ui`, `domain/casefiles`.

* **Testing Contributions**:
    * Set up the first actual and expected UI test text files for system-level testing.
    * Created JUnit tests for `Parser` to validate command parsing and error handling (team-wide coverage).
    * Added targeted parser tests for `ListCommand`, covering flag combinations and edge cases.
    * Authored and maintained `ListCommandTest`, testing summary vs verbose output, status filtering, category inclusion, and truncation behavior.

* **Code contributed**: [RepoSense link](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=xelisce&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-09-19T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=xelisce&tabRepo=AY2526S1-CS2113-W13-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Documentation**:
    * **User Guide**:
        * Added documentation for the `list` command, including `--mode` and `--status` flags.
        * Provided examples for both verbose and summary modes.
        * Clarified expected output structure and optional field behavior.
    * **Developer Guide**:
      * **Architecture & Sequence Diagrams**:
          * Designed and documented the **overall Architecture Diagram** for SGSafe, showing UI, Logic, Model, Storage, Commons, and Main.
          * Created **sequence diagram boilerplates** for both parsing and execution phases.
          * Established a **template for execution‑phase sequence diagrams**, enabling consistent documentation of future commands (`AddCommand`, `DeleteCommand`, etc.).
      * **Model Component Section**:
          * Authored the Model component write‑up, detailing responsibilities of `CaseManager` and `Case`.
          * Explained integrity constraints (unique IDs, valid statuses) and query/filtering support.
    * Added comprehensive Javadoc comments for all core classes and methods I authored:
        * `Display`, `Parser`, `Validator`, `Command`, `ListCommand`, `Case`, and `CaseManager`.
    * Documented formatting logic, flag behavior, and conditional field inclusion.
    * Ensured all public methods have clear descriptions, parameter annotations, and example outputs where relevant.

* **Community**:
    * Reviewed PRs (with non-trivial review comments). Examples: [#18](https://github.com/AY2526S1-CS2113-W13-3/tp/pull/18), [#27](https://github.com/AY2526S1-CS2113-W13-3/tp/pull/27), [#37](https://github.com/AY2526S1-CS2113-W13-3/tp/pull/37), [#132](https://github.com/AY2526S1-CS2113-W13-3/tp/pull/132), [#138](https://github.com/AY2526S1-CS2113-W13-3/tp/pull/138)
    * Replied to feedback on my own PRs and incorporated suggestions from teammates. [#8](https://github.com/AY2526S1-CS2113-W13-3/tp/pull/8), [#9](https://github.com/AY2526S1-CS2113-W13-3/tp/pull/9), [#11](https://github.com/AY2526S1-CS2113-W13-3/tp/pull/11)
    * Identified bugs: [Example Bug Report](https://github.com/AY2526S1-CS2113-W13-3/tp/issues/72)
    * Provided feedback on teammates' command structure, formatting logic, and test clarity.

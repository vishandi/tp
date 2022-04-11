---
layout: page
title: Tay Yan Han's Project Portfolio Page
---

### Project: UniGenda

UniGenda is a **desktop app for managing contacts and schedules**, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you are a university student having a hard time organising your timetable, or commonly find difficulty scheduling a suitable time to meet up with your project group mates or friends, UniGenda is the app just for you!

Given below are my contributions to the project.

* **New Feature**: Added the ability to add events to a contact's schedule.
  * Justification: This feature is one of the core commands in our application, since it affords a `Person` to have a schedule so that a user can check to see his/her contacts' availability.
  * Highlights: I first had to implement the `Schedule` and `Event` classes to start off the project, before adding the AddEvent command. The most challenging part was definitely getting the Storage to successfully save/load the new nested json structure. I spent some time learning about the Jackson library, and also considered alternative Json structures and libraries(such as GSON) to save/load the Schedule, before finding out that Jackson requires an empty constructor to initialise objects using a nested Json structure. Later on in the project however, I realised that a better solution would have been to create `JsonAdaptedEvent` and `JsonAdaptedSchedule` classes for Jackson to load and store.

* **New Feature**: Add recurring event functionality.
  * What it does: Allows the user to add daily, weekly or biweekly events to their contacts' schedule instead of just 1 time events.
  * Highlights: Ensuring that the events recurred correctly was the challenging part. I also had to read up on how to make use of `LocalDate`, `LocalTime`, `ChronoUnit` etc. to get the correct recurring dates. Thankfully, my teammates Vishandi and Han Qin were ready to help me test for bugs in my code. Also, since dates and times were prone to bugs, I spent some time ensuring that intentional misuse of our application (by supplying nonsensical dates) would not break the app, but rather show logical feedback to the user.
    * Credits: Han Qin (GitHub username: hanqinilnix) and Vishandi (GitHub username: vishandi)

* **New Feature**: Added the ability to import schedules.
  * What it does: Allows users to import a schedule from a JSON file, instead of having to add events 1 by 1.
  * Highlights: I had to re-look at how the `Storage` worked in order to ensure that we could read/write to json files in the format that we wanted. I realised here that adding `JsonAdaptedEvent` and `JsonAdaptedSchedule` would be necessary, as Jackson's default save structure differed from how it expects the json file to be read. Besides having to deal with the Storage, I had to consider the possible errors that a user could encounter when reading the json file.

* **New Feature**: Added the ability to clear a person's schedule.
  * Justification: This feature allows for a quick and easy way for users to clear their contacts' schedule, rather than delete events 1 by 1.

* **New Feature**: Added the ability to set a contact as the user.
  * What it does: Shifts the specified contact to the top of the list for the user to reference more easily if he/she wants to view his/her own schedule.

* **Feature Contributed**: Displaying of common timings feature
  * Highlights: Assisted with the basic logic of the command.
    * Credits: This feature was created by Mark (GitHub username: markbiju).

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s2.github.io/tp-dashboard/?search=tyanhan&breakdown=true)

* **Project management**:
  * Set up the team's organisation and repository.
  * Wrote the full `v1.3 Demo` documentation.
  * Evaluated the feasibility of incorporating NUSMods API into our app given our time constraints, and decided against it.

* **Enhancements to existing features**:
  * Fixed the GUI size and removed the resizable icon on mouse hover bug. [#121](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/121)
  * Wrote tests for `AddEventCommand`, `ClearScheduleCommand`, `SetUserCommand` and their related parsers. [#84](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/84/files) [#119](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/119) [#113](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/113)
  * Wrote tests for `JsonAdaptedEvent` and `JsonAdaptedSchedule` [#77](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/77/files)

* **Documentation**:
  * User Guide:
    * Added documentation for the `AddEvent`, `ImportSchedule`, `ClearSchedule` and `SetUser` features.
    * Refined the product description and organised the command summary.
  * Developer Guide:
    * Added documentation for the implementation of `Schedule` feature.
    * Wrote parts of use case scenarios, user stories and product scope.

* **Community**:
  * PRs reviewed (with non-trivial review comments):
    * [#25](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/25), [#102](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/102), [#108](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/108), [#109](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/109), [#124](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/124)

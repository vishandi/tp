---
layout: page
title: Vishandi Rudy Keneta's Project Portfolio Page
---

### Project: UniGenda

UniGenda is a **desktop app for managing contacts and schedules**, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you are a university student having a hard time organising your timetable, or commonly find difficulty scheduling a suitable time to meet up with your project group mates or friends, UniGenda is the app just for you!

Given below are my contributions to the project.

* **Improved Feature**: Edited the existing attributes and added new attributes of a `Person` in AB3 to match UniGenda's needs.
  * Justification: As UniGenda is an app meant for university students to use, and nowadays people don't know each other's address and/or email, we decided to make email and address optional in UniGenda. Also, these days, lots of university students use `Telegram` and `GitHub` to support their school activities. Hence, we add these two attributes also to a `Person`'s attribute in UniGenda.
  * Highlights: I tried some implementations that could model the optional attributes, i.e. `Email`, `Address`, `Telegram`, and `GitHub`. This is an important (and tedious) step because it needs to be easily developed for future optional attributes as well, as it is only logical for future developments to add more optional attributes. I tried making the `Person` to have an `Optional<attribute>` , and I also tried changing the getter functions to return an `Optional<attribute>`, but both didn't work. The hardest part of the implementation is in deciding what an `empty` attribute should look like. At last, we decided to have a static `empty` attribute for each of the optional attribute because it is easier to implement and easier to maintain as well. In exploring the ways to model these, I discussed this modelling part with my group mates, hanqinilnix and tyanhan.

* **Improved Feature**: Edited the `add` and `edit` commands to make the edited attributes work well.
  * Justification: With the newly added (and edited) optional attributes, it is only logical to make the user doesn't have to input these optional attributes when adding a `Person` to UniGenda. Also, we need to allow user to delete the optional attributes from a `Person`'s information.
  * Highlights: The most challenging part is in making sure the `VALIDATION_REGEX` for each of the new (and old) attributes works well.

* **New Feature**: Added the ability to delete events from a `Person`'s schedule.
  * What it does: Allows the user to delete a particular `Event` from a `Person`'s schedule.
  * Justification: This feature is necessary for users to delete an `Event` if he/she made a mistake when adding a particular `Event` to a `Person`.

* **New Feature**: Added the ability to view a `Person`'s schedule in detail.
  * What it does: Allows the user to view a `Person`'s schedule and upcoming schedule for the upcoming week.
  * Justification: Listing all the events for a `Person` in his/her card will make the schedule really hard to read and sometimes it could make the list very long. To mitigate this issue, we decided to make another panel (placed on the right) for the user to see the `Person`'s detailed schedule.
  * Highlights: The challenging parts of implementing this feature is in the changes I need to make in the GUI and filtering out the events that are going to occur at a particular date. As the `Event`s in UniGenda can be a Daily, Weekly, and BiWeekly, the latter part is tricky, because even a small mistake can make the display doesn't work. Thankfully, hanqinilnix and tyanhan continuously helped me to fix the issues.

* **Improved Feature**: Improving GUI.
  * Justification: To accommodate the changes we made, especially the new `Schedule`, I made the necessary changes to the existing GUI.
  * Highlights: Adding logos beside a `Person`'s attribute to make it look nicer and easier to differentiate. Changing the structure of the GUI by adding the right panel. This part is especially challenging for me, because I didn't have any experience with JavaFX before, and moving things around in SceneBuilder could be tricky sometimes, as it is really prone to mistake. One wrong click could make my whole window break.

* **Tests Added**:
  * Wrote tests for `Telegram` [#59](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/59), `GitHub` [#65](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/65), and edit tests for `Email` and `Address` [#25](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/25). Some changes in the tests made along the progression of the project.
  * Wrote tests for `Event` [#190](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/190).
  * Added tests for `EditCommandParser` and `AddCommandParser` [#79](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/79) to include the changes we made.
  * Added tests for `ParserUtil` [#118](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/118), [#123](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/123).

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s2.github.io/tp-dashboard/?search=vishandi&breakdown=true)

* **Project management**:
  * Wrote the full `v1.2 Demo` documentation.
  * Maintains the `v1.3` milestone near the deadline of `v1.3` and closes the milestone.

* **Documentation**:
  * User Guide:
    * Added and edited documentation for the `add`, `edit`, `deleteEvent`, and `viewSchedule` features.
    * Updated screenshots used in the User Guide.
  * Developer Guide:
    * Added documentation for the `viewSchedule` feature.
    * Added manual testings for some new and enhanced features. [#221](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/221)

* **Community**:
  * PRs reviewed (with non-trivial review comments): [#58](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/58), [#69](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/69), [#194](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/194)
  * Contributed to forum discussion: [#155](https://github.com/nus-cs2103-AY2122S2/forum/issues/155)

---
layout: page
title: Ong Han Qin's Project Portfolio Page
---

### Project: UniGenda

UniGenda is a **desktop app for managing contacts and schedules**, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you are a university student having a hard time organising your timetable, or find difficulty scheduling a suitable time to meet up with your project group mates or friends, UniGenda is the app just for you!

Given below are my contributions to the project.

* **New Feature**: Added the ability to edit events
  * What it does: Allows user to be able to edit any attribute of an event without deleting and adding.
  * Justification: An `Event` has many attributes to it, e.g. `EventDescription`, `Date`, `Time`, e.t.c. If a user made a mistake while adding event, even though the target user can type fast, it is still inconvenient for the user to type duplicate events if the user only made a minor mistake for one of the attributes. Therefore, allowing the user to edit an event without having to type a lot will make the user experience much better than without this feature.
  * Highlights: This feature was relatively easy to implement since most of the code can be referenced from `EditCommand` in the existing code base, however, it was also rather time-consuming. Since it was one of the first features to be implemented and the project idea constantly changed, thus, I had to make changes to the implementation as new features were added.

* **New Feature**: Added the feature to find who is free with given date and time
  * What it does: Allows user to find who, in user's address book, is free given the user's date and time input.
  * Justification: This is a main feature of our application. This feature provides convenience and better planning for the user.
  * Highlights: Dealing with dates and times were a great hassle. For instance, the initial implementation of date and time collisions were separated to checking dates and checking times. However, as the development of the project continued, I realised that the collision cannot be separated and must be considered together at all times. This is because an event can span across multiple days, and it would be hard to implement a solution if I were to consider dates and times separately. Also, this feature's command name was `freeSchedule` instead of `whoIsFree` which is the current name. It was changed later during the project as we thought the latter made more sense for users.
<div style="page-break-after: always;"></div>
* **New Feature**: Added the ability to export schedule
  * What it does: Allows user to share schedules.
  * Justification: This feature is used together with the ability to import schedule, which Yan Han handled. It improves the efficiency of creating new schedules. Users would only have to create their own schedules, and they can export their schedules for sharing. And together with the ability to import schedules, users can use the shared exported file to update the schedules of their contacts. This reduces the number of events that users have to create, improving users' experiences.

* **Feature Contributed**: Next recurring event functionality
  * What it does: If the event does not occur on the day that we are checking (aka today), we get it's next recurring date else we get the date that is closest to and before today.
  * Highlights: Due to the changing nature of our project, old features have to be updated to accommodate the new features. While testing a new feature, it was discovered by Vishandi (GitHub username: vishandi) that the recurring event was not working as intended, which caused the application to hang. I was tasked to modify the existing code to fix the problem it had with the new feature since I had experienced with this issue when implementing the code for the second feature listed here. Despite the experience, it still was a challenging as the calculation for the dates were really complicated, especially when dealing with dates that are from the past. With the help of Vishandi as tester, I was able to complete the fix within 6 hours.
  * Credits: This feature was created by Yan Han (GitHub username: tyanhan).

* **Feature Contributed**: Displaying of common timings feature
  * Highlights: I assisted with the implementation of displaying the common timings of the `findCommonTiming`.
  * Credits: This feature was created by Mark (GitHub username: markbiju).
    
* **Tests added:**
  * Created utility classes like `EventBuilder`, `TypicalSchedule`, `EditEventDescriptorBuilder` to make testing easier. [\#70](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/70/)
  * Wrote tests for `WhoIsFreeCommand`, `WhoIsFreeCommandParser`, `EditEventCommand` and `EditEventCommandParser`. [\#70](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/70/) and [\#75](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/75/)

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s2.github.io/tp-dashboard/?search=hanqinilnix&breakdown=true)

* **Project management**:
  * Facilitated team meetings and ensured that meeting agendas are met for each meeting.

* **Documentation**:
  * UserGuide:
    * Added documentation for the features `editEvent`, `whoIsFree` and `exportSchedule`.
  * Developer Guide:
    * Added implementation documentation for `whoIsFree`[\#109](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/109), `importSchedule` and `exportSchedule` features [\#211](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/211).
    * Added manual testing for `whoIsFree` and `exportSchedule` features [\#229](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/229/)
      
* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#35](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/35/), [\#59](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/59/), [\#77](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/77/), [\#104](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/104/), [\#108](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/108/), [\#125](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/125/), [\#184](https://github.com/AY2122S2-CS2103T-W09-1/tp/pull/184/)
    * Reported bugs and suggestions for other teams in the class (examples: [1](https://github.com/hanqinilnix/ped/issues/1), [4](https://github.com/hanqinilnix/ped/issues/4), [6](https://github.com/hanqinilnix/ped/issues/6), [7](https://github.com/hanqinilnix/ped/issues/7))

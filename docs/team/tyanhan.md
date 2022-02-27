---
layout: page
title: Tay Yan Hans's Project Portfolio Page
---

### Project: UniGenda

UniGenda is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI)
while still having the benefits of a Graphical User Interface (GUI). If you are an NUS student,
and you can type fast, UniGenda can get your contact management tasks done faster than traditional GUI apps.

Given below are my contributions to the project.

* **New Feature**: Added the ability to add an Event to a Person's Schedule.
    * Justification: This feature is one of the core commands in our application, since it affords a Person to have a Schedule so that a user can check to see his/her contacts' availability.
    * Highlights: I first had to implement the Schedule and Event classes to start off the project, before adding the AddEvent command. I had to make decisions regarding how the Schedule object should be added and stored, since it would affect future commands as well. The most challenging part was definitely getting the Storage to successfully save/load the changes to a Person's Schedule, because the Jackson library used by AB3 seemed to have problems loading a nested Json object. I spent a few days googling how I could get the Jackson library to work, and also considered alternative Json structures and libraries(such as GSON) to save/load the Schedule, before finding out that Jackson requires an empty constructor to initialise objects using a nested Json structure.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s2.github.io/tp-dashboard/?search=tyanhan&breakdown=true)

* **Project management**:
    * To be added

* **Enhancements to existing features**:
    * To be added

* **Documentation**:
    * User Guide:
        * Added documentation for the features `AddEvent`
    * Developer Guide:
        * To be added

* **Community**:
    * Set up the team's organisation and repository
    * PRs reviewed (with non-trivial review comments): [\#12](), [\#32](), [\#19](), [\#42]()

* **Tools**:
    * To be added

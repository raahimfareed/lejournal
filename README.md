# Le Journal
### Table of Contents
- [Introduction](#introduction)
- [How to run](#how-to-run)
- [Setup](#setup)
- [What have I learned through this?](#what-have-i-learned-through-this)
- [Gallery](#gallery)

### Introduction

This application helps the user take notes, track their expense and track their events.

This project was originally made for a university project for the course Software Construction and Development SE3512 which is being taught by [Qaisar Manzoor](https://github.com/QaisarManzoor).

### How to run

Download the release jar file which from this repo's release section.

You need Java (JRE) installed.

On windows and mac, double-click the `.jar` file to run it.

On linux
```shell
$ java -jar file.jar
```

Make sure to change `file.jar` to the name of the file that you download.

### Setup

This project was made with IntelliJ IDEA, so this is for IntelliJ IDEA exclusively. I'm sure it can be worked around for other IDEs as well, you can figure it out yourself.

First clone this repository
```shell
$ https://github.com/raahimfareed/se3512-project.git
```

Open IntelliJ and open the cloned folder in IDEA. All libraries will automatically be installed as they are present in the lib folder in this git repo.

Disclaimer: I do not own any rights to the external libraries I've used.

Copy `.example.env` file
```shell
$ cp .example.env .env
```

Make sure to change the details in `.env` file according to your own system. You will need to create a database. For now, this app has a hard coded database URI which means that it will only work on MySQL and MariaDB. If you use a different type of database such as PostgreSQL, you can change that in `src/Components/HibernateUtil.java` file.

Finally just run the application (Debug configuration is already set, but if that doesn't work, you can just run `src/Main.java` file as it is the main entry point)

### What have I learned through this?
- Git (Team Collaboration, Branching, Merging, Handling Merge Conflicts)
- Java
- Different Java 3rd Party Libraries (Hibernate, FlatLaf, CommonMark, RSyntaxTextArea, Dotenv)
- Database Integration
- Separating code into different modules
- SOLID principles

### Gallery

![Index](/images/index.png)

![Dashboard](/images/dashboard.png)

![Notes](/images/notes.png)

![Add Note](/images/add_note.png)

![View Note](/images/view_note.png)

![Expense Tracker](/images/expense_tracker.png)

![Add Expense](/images/add_expense.png)

![Calendar](/images/calendar.png)

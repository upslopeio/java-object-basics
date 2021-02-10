# Java Objects Basics

## Step 1

Run the following command, and enter your name:

```
./gradlew login
```

## Exercise #1

1. create a class named `Screenplay` in `src/main/java`
1. add a private String field named `title` with a getter and setter
1. add a private field named `lines` that's an ArrayList of Strings
1. add a mutator method named `addLine` that takes a `String`, and adds it to the `lines` field
1. add an accessor method named `generate` that returns a `String` containing the title and all the lines, separated by a newline

As you write code, run the `ScreenplayTest` regularly. You can do this from within IntelliJ or by running tests from the command line:

```
./gradlew test --tests ScreenplayTest
open ./build/reports/tests/test/index.html
```

Example usage:

```java
Screenplay screenplay = new Screenplay();
screenplay.setTitle("Star Wars");
screenplay.addLine("may the force be with you");

System.out.println(screenplay.generate());
// should print
// Star Wars
// may the force be with you

screenplay.addLine("luke I am your father");
System.out.println(screenplay.generate());
// should print
// Star Wars
// may the force be with you
// luke I am your father
```

# Java Objects Basics

✨ See the Cheat Sheet at the bottom ✨

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

## Cheat Sheet

### Basic Pattern

1. Define a private field
1. Define a mutator method (sometimes it's a setter)
1. Define an accessor method (sometimes it's a getter)

### [Declare an internal field](https://docs.oracle.com/javase/tutorial/java/javaOO/variables.html)

```java
class SomeClass {
    
    // declare a String field
    private String something;
    
    // declare a field that holds a list of Strings
    private ArrayList<String> someWords = new ArrayList<>();
    
    // declare a field that holds a list of Integers
    private ArrayList<Integer> someNumbers = new ArrayList<>();
    
    // declare a field that holds a map of Strings to Integers
    private HashMap<String, Integer> someMap = new HashMap<>();
    
}
```


### [Define a method](https://docs.oracle.com/javase/tutorial/java/javaOO/methods.html)

```java
class SomeClass {
    
    private int id;
    
    // define a mutator (setter)
    //  - return type is void   
    //  - there is no return statement   
    public void setId(int value) {
        this.value = value;
    }
    
    // define an accessor (getter)
    //  - return type is not void   
    //  - there is a return statement   
    public void setId(int value) {
        this.value = value;
    }
    
}
```


### [Loop over a list](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html)

This is called the "enhanced for loop."

```java
ArrayList<String> words = new ArrayList<>();
words.add("happy");
words.add("joyful");

// this loop will execute twice
for(String word : words) {
    System.out.println(word);
}
```


### [Loop over a HashMap](https://mkyong.com/java/how-to-loop-a-map-in-java/)

```java
HashMap<String, String> myMap = new HashMap<>();
myMap.put("name", "Tim");
myMap.put("zipCode", "10101");

// this loop will execute twice
for(Map.Entry<String, String> entry : myMap.entrySet()) {
    System.out.println(entry.getKey());
    System.out.println(entry.getValue());
}
```


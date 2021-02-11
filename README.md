# Java Objects Basics

✨ See the Cheat Sheet at the bottom ✨

If you haven't downloaded IntelliJ, (try out the community edition)[https://www.jetbrains.com/idea/download/].

## Step 1

Run the following command, and enter your name:

```
./gradlew login
```

## Exercise #1

1. create a class named `Screenplay` in `src/main/java`
1. add a private `String` field named `title` with a getter and setter
1. add a private field named `lines` that's an `ArrayList` of Strings
1. add a mutator method named `addLine` that takes a `String`, and adds it to the `lines` field
1. add an accessor method named `generate` that returns a `String` containing the title and all the lines, separated by a newline

As you write code, run the `ScreenplayTest` regularly.

You can run code in the `ScreenplayApplication` class. For example:

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

#### Running from the command line

Run the Screenplay application:

```
./gradlew assemble
java -cp build/classes/java/main ScreenplayApplication
```

Run tests:

```
./gradlew test --tests ScreenplayTest
```

Open test results from the command line (Mac / Linux):

```
open ./build/reports/tests/test/index.html
```

## Exercise #2 - Form Fields

1. create a class named `FormFields`
1. add a private field `HashMap` named `fields` that maps `String` keys to `String` values
1. add a mutator method named `addField` that takes two `String`s (a key and a value), and adds an entry to the `HashMap`
1. add an accessor method named `urlEncoded` that returns a `String` containing all the fields and values like

The output of `urlEncoded` should look like this:
   
```
field1=value2&field2=value2
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

// The code inside the loop will run twice 
// since there are two items in the ArrayList
for(String word : words) {
    System.out.println(word);
}
```


### [Loop over a HashMap](https://mkyong.com/java/how-to-loop-a-map-in-java/)

```java
HashMap<String, String> myMap = new HashMap<>();
myMap.put("name", "Tim");
myMap.put("zipCode", "10101");

// The code inside the loop will run twice 
// since there are two key/value pairs in the HashMap
for(Map.Entry<String, String> entry : myMap.entrySet()) {
    System.out.println(entry.getKey());
    System.out.println(entry.getValue());
}
```

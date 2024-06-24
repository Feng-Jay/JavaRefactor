# JavaRefactor [![Build, Test and Depoly](https://github.com/Feng-Jay/JavaRefactor/actions/workflows/maven.yml/badge.svg)](https://github.com/Feng-Jay/JavaRefactor/actions/workflows/maven.yml)

JavaRefactor is a tool that can refactor java code while keeping original semantic.

## Supported Refactor Methods

- [x] Rename Method
- [x] Rename Variable
- [x] Exchange Loop
- [x] Insert LogStatement
- [x] Insert UnusedStatement
- [x] Negate Condition
- [x] Reorder Condition
- [x] SwitchToIf


## Usage

This project is build by maven and has been test on Java11, you can import it directly import it to your IDE (IDEA, Eclipse...).

Or you can use the [Jar file](https://github.com/Feng-Jay/JavaRefactor/blob/master/out/artifacts/JavaRefactor_jar/JavaRefactor.jar) in here.

JavaRefactor accept two file paths, the first is the location of the file need to be transformed, the second is the location of transformed outcome.

For now, the input file should be either a class which contains a function or just one function, there is an [example](https://github.com/Feng-Jay/JavaRefactor/blob/master/d4j-info/testCases/test.java)

You can set the function of JavaRefactor by using this [setting file](https://github.com/Feng-Jay/JavaRefactor/blob/master/src/main/resources/setting.properties).


## Implement Details

```text
.
├── d4j-info:               JavaRefactor test on java codes from Defects4J.
├── out:                    The jar file.
├── src:                            
│   ├── main
│   │   ├── java
│   │   │   ├── transform:  Serval transform visitors, the main logic of JavaRefactor
│   │   │   └── utils:      Utilities functions.
│   │   └── resources:      Setting files.
│   └── test
│       ├── java
│       │   └── transform:  Junit testing methods on each visitor.
│       └── resources:      Testcases extracted from Defects4j.
```

## Known Bug

The `SwithToIf` module is incomplete: 

When encountered with enum in switch cases, Java will inference the enum classes automatically, like this example from [Java tutorial](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html):

```java
public class EnumTest {
    Day day;

    public EnumTest(Day day) {
        this.day = day;
    }

    public void tellItLikeItIs() {
        switch (day) {
            case MONDAY:
                System.out.println("Mondays are bad.");
                break;

            case FRIDAY:
                System.out.println("Fridays are better.");
                break;

            case SATURDAY:
            case SUNDAY:
                System.out.println("Weekends are best.");
                break;

            default:
                System.out.println("Midweek days are so-so.");
                break;
        }
    }
}
```

The transformed version is like this:

```java
...
if (day == MONDAY)
    ...
else if (day == FRIDAY)
    ....
....
```

We can directly find the transformed version can not pass the compiler. This project use a tricky way to solve this problem, it skip cases whose expression is all-uppercase.



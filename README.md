# JavaRefactor [![Build, Test and Depoly](https://github.com/Feng-Jay/JavaRefactor/actions/workflows/maven.yml/badge.svg)](https://github.com/Feng-Jay/JavaRefactor/actions/workflows/maven.yml)

JavaRefactor is used in our paper [Evaluating the Generalizability of LLMs in Automated Program Repair @ICSE2025-NIER](https://conf.researchr.org/track/icse-2025/icse-2025-nier)! If you think this repo is helpful, pls cite us😜!

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

This project is build by maven and has been tested on Java11. You can import it directly import it to your IDE (IDEA, Eclipse...), or directly use the [Jar file](https://github.com/Feng-Jay/JavaRefactor/blob/master/out/artifacts/JavaRefactor_jar/JavaRefactor.jar) like:

```bash
java -jar JavaRefactor.jar path/to/the/target/javafile.java path/to/the/transformed/result.java
```

As shown, JavaRefactor accept two parameters: the first one is the path of the file need to be transformed, the second one is the path of transformed result.

> [!NOTE]
> The input file should be either a class which contains a function or just one function, here is an [example](https://github.com/Feng-Jay/JavaRefactor/blob/master/d4j-info/testCases/test.java).
> You can set the behavior of JavaRefactor by modifying this [file](https://github.com/Feng-Jay/JavaRefactor/blob/master/src/main/resources/setting.properties).

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

> [!CAUTION]
> The `SwitchToIf` module is complete, it will cause two types of bugs: 
> 1. When encountered with Enumerate object in switchCase, JavaRefactor may trigger a `can not find symbol ...` error.
> 2. Due to JavaRefactor change switchStatement to a bunch of IfStatement, compiler may not aware the equality of these, and report `uninitialized object` or `missing returnStatement` errors.
> The detailed explanations are below:

1. When encountered with enum in switch cases, Java will inference the enum classes automatically, like this example from [Java tutorial](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html):

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

2. Uninitialized object or Missing ReturnStatement error

```java
...
OneClass example;
switch (expr):
case x:
    example = ...;
    return null;
case y:
    example = ...;
    return example;
default:
    example = ...;
    return xxx;
...
```
the transformed version of this will be:

```java
...
OneClass example;
if (condition1){
    example = ...;
    return null;
}
if (condition2){
    example = ...;
    return example;
}
if (condition3){
    example = ...;
    return xxx;
}
...
```

The compiler will report error after analysis even the semantic of this two versions of codes are identical. But these two kinds of codes are rare: only 4 bugs out of 438 defects4j bugs suffer from this.

## Contribution

Fell free to raise issues and submit pull requests! I will give you a quick response! :)

## Acknowledgements

Thanks to the [Defects4j](https://github.com/rjust/defects4j/) project for providing real world java codes.

Assumptions:

1. General:
   - All points should be a part of the canvas.
   - From the user point of view coordinates system starts in (1, 1).
   - Whitespaces in commands are ignored(except for the first one).
   - Commands are case-insensitive(except for color parameter in Fill command)
   - Additional parameters passed by user are ignored
2. Creating canvas:
   - I assumed that both values should be above 0.
   - It's possible to create a new canvas which overrides previous one.
   - There is no size limit of the canvas(might cause OutOfMemoryError)
3. Draw line:
   - In case line is tilted I display error message.
   - Line can be defined by any two points for which one of coordinates is the same.
4. Draw rectangle:
   - I assumed that there is not allowed to draw rectangles with points that are in a line(one of the dimension being 1).
   - I display error message if points aren't in a correct order.
5. Fill:
   - It is possible to use any character to fill(also empty space).
   - All contiguous points that are of the same color(character) as the given point are filled.

How to run:

1. Project requires JDK and Maven in the path

2. To run drawing application one needs to execute in the root folder of the project:
  ```
   mvn compile exec:java
  ```
3. To run tests one needs to run:
```
 mvn test
```
4. To run kotest tests in IntelliJ there is Kotest plugin required (https://plugins.jetbrains.com/plugin/14080-kotest).
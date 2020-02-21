## Statement of contributions

Simon - insertComments, getDeclarationAsString\
Fabian Waxin: cleanLines, prettyPrintingTextNode\
Gustav Ung: applyAddedDiffElement, calculatedSyntaxModelForNode
solveSymbolAsValue\
Ramiz Dündar: applyKeptDiffElement, applyRemovedDiffElement\
Love: jjMoveStringLiteralDfa7_0, isApplicable\

# PART 1: Complexity Measurement

Lizard is a tool which analyzes the cyclomatic complexity of programs in many languages,
counting NLOC, CCN, tokens and parameters of functions in the program. It does not handle

exceptions aside from counting ​ throw ​s. Catches should be considered an additional branch.

**Function - cleanLines**
Path: javaparser/javaparser-core/src/main/java/com/github/javaparser/JavadocParser.java
Lizard: CCN 14
Count by hand: 14 CCN

The function got 31 NLOC (excluding comments) which is not long for its complexity. The
behaviour of the function is to remove all spaces before the sentence, asterisk and newline (\n)
from the input parameter which is of type string. There is no documentation that describes the
function but some of the if-statements are commented on.

**Function - prettyPrintingTextNode**
Path:
javaparser/javaparser-core/src/main/java/com/github/javaparser/printer/lexicalpreservation/Lexic
alPreservingPrinter.java
Lizard: CCN 14
Count by hand: 15 CCN

The function got 51 NLOC (excluding comments) and the task of it is to make the Node-object
printable. There is a 9 switch-case statement that makes the function relatively high in
complexity. There is no documentation about the private function but it’s easy to understand
because every branch is short and well programmed.

**Function - applyAddedDiffElement**
Path:
javaparser-core/src/main/java/com/github/javaparser/printer/lexicalpreservation/Difference.java
Lizard: CCN 27
Count by hand 27 CCN
NLOC: 84 NLOC


**Function - applyKeptDiffElement**
Path:
javaparser-core/src/main/java/com/github/javaparser/printer/lexicalpreservation/Difference.java
Lizard: CCN 27
NLOC: 57 NLOC

The purpose of this function is to apply the changes made on Nodes if they are kept means they
aren't modified.

**Function - applyRemovedDiffElement**
Path:
javaparser-core/src/main/java/com/github/javaparser/printer/lexicalpreservation/Difference.java
Lizard: CCN 29
Count by hand 29 CCN
NLOC: 50 NLOC

The purpose of this function is to apply the changes made on Nodes if they are kept means they
are removed. Most of the complexity comes from compound statements. Since these
statements are not modifying anything(they are mostly booleans or getters) actual complexity is
lower. However, though a lot of branches and conditions make it hard to understand code.
For complexity measurement by hand please look at the comments. It's on top of the function.

Cyclomatic Complexity: 1 1 3 2 2 5 (9 6) At total 29, same as lizard output. These numbers are
contributions to cyclomatic complexity wrt their branches but in reversed order. For example 4th
number 2 means that 4th last branch else if (removed.isPrimitiveType()) increases cyclomatic
complexity by 2. Note that the last 2 numbers are for the first if. They are seperated branches
inside that if because it's too big to count. Also note that total cyclomatic complexity is different
from covered branches because there are compound statements.

**Function - calculatedSyntaxModelForNode**
Path:
javaparser-core/src/main/java/com/github/javaparser/printer/lexicalpreservation/LexicalDifferenc
eCalculator.java
Lizard: CCN 37
NLOC: 130

The purpose of this function is to calculate the concrete syntax model for a given node, given
some node transformations. This function has a high CNN because it has to handle all different
subclasses of a CsmElement and handle each of those cases. Some subcases such as
CsmList could be refactored into its own function.

**Function - solveSymbolAsValue**
Path:
javaparser-symbol-solver-core/src/main/java/com/github/javaparser/symbolsolver/javaparsermo
del/contexts/LambdaExprContext.java
LIzard: CCN 27
NLOC: 112

This function tries to take a symbol, which is often a variable and tries to resolve it as a value. In
particular, this function specializes in resolving lambda functions. The main branches analyze 3
different cases. The first case is MethodCallExpr which is when we pass a lambda to a higher
order function. The next case is VariableDeclarator, that is when we declare a variable and
assign a lambda to it. The last case is ReturnStmt, when we return a lambda from a function.

**Function - isApplicable**
Path:
javaparser-symbol-solver-core/src/main/java/com/github/javaparser/symbolsolver/resolution/Met
hodResolutionLogic.java
Lizard: CCN 20
NLOC: 98

The purpose of this function is to return a boolean value answering whether a list of arguments
argumentsTypes can be applied to a particular method. The method is long and complicated
and it is hard to understand exactly what it does (especially since there are no comments). At
many times in the function it loops through the list argumentsTypes. It has a high CC mostly
because there are many nested if’s.

**Function - jjMoveStringLiteralDfa7_**
Path:
/Users/lovealmgren/Documents/dd2480/javaparser/javaparser-core/target/generated-sources/ja
vacc/com/github/javaparser/GeneratedJavaParserTokenManager.java
Lizard: CCN 16
NLOC: 59

This is a helper function inside the class GeneratedJavaParserTokenManager to parse a
character at a precise position of a file. It attempts to read the 7th character from an input
stream input_stream and handles the character in a switch case,

**Function - CommentsInserter::insertComments**
This function tries to assign comments to the nodes of the Abstract Syntax Tree generated while
parsing the string, adding them to the tree.
Count: CCN 13
Lizard: 13


**Function - MethodDeclaration::getDeclarationAsString**
This function gets a method declaration in a standardized string format. The complexity is high
because it has a lot of if statements parsing the properties of the declaration. This is not
necessary and could be split up into another function. The original documentation does not
involve branches.
Count: CCN 11
Lizard: 11

# Part 2: Coverage measurement & improvement

**Function - cleanLines**
The Coverage Measurement did not take ternary operations into account which led to rewriting
to if-else-statement. Every output is written to f1coverage.txt file. The function is private and is
not directly tested. The function parse in JavadocPars class is the one who calls cleanLines.
The result of the measurement was 100% branch covered and in total the function was called
over 3500 times.

Location of f1coverage.txt: javaparser/javaparser-core-testing

In the first Map directly calls another function. The return value of that function is then
processed throughout the remainder of the Map. One could refactor by splitting up the
remainder of the map function to a seperate one. That function would take two parameters, the
map value and the return value of the first called function.

**Function - prettyPrintingTextNode**
The function did not have any ternary operations or exceptions, except throwing one in the
switch-default branch. The measurement tool is hardcoded into the function. Meaning at every
branch it saves a string into an arrayList. At the end of the function it prints out every string in
the list to a f2coverage.txt file. If the function would change one needs to remove or add string
output.

Location of f2coverage.txt: javaparser/javaparser-core-testing

The function is private and not directly tested. The function getOrCreateNodeText in
LexicalPreservingPrinter class is the one who calls prettyPrintingTextNode.

The result of the measurement was 0.715% and was called over 3500 times.


After tests improvement branch coverage was increased to 92%, only missing one
default-switch branch.

**Function - calculatedSyntaxModelForNode**
The function did not have any exceptions except for throws which are equivalent to return
statements. Other than that, it only contained simple if statements and loops. Some if
statements were compound statements, so && were split into nested if statements. Else
statements were added to lonely if statements. The tool was implemented by adding BeforeAll
and AfterAll annotations and counting active branches over all unit tests. The branch coverage
was not equal to jacoco’s coverage report.

Refactoring:

Jacoco report: 79% coverage
Our report: 56% coverage

**Function - solveSymbolAsValue**
As the previous function, this function did not contain any complex branches in a large sense. It
was however hard to annotate the branch ids due to large and very deep if statements. The
branch coverage was not equal to jacoco’s coverage report.

Before new test:
Jacoco report: 32% coverage
Our report: 34% coverage

**Function - isApplicable**
At many times in the function it loops through the list argumentsTypes. There could instead be a
separate function that performs the required actions on only 1 list item, which this function could
utilize. Also, there are many branches that check if two things are equal, or empty. This could be
done in a separate function as well. Branch coverage percentage of the test of this function was
improved by 14%, up from 45% to 59%.

**Function - jjMoveStringLiteralDfa7_**
By looking through the class GeneratedJavaParserTokenManager I realize there are many such
functions jjMoveStringLiteralDfa1_0, jjMoveStringLiteralDfa2_0, jjMoveStringLiteralDfa3_0, etc..
(One for each character position, up to position 11) So I think that they have already tried to split
the code into smaller units to reduce the complexity of the parser, so I think it is hard to improve
it more. Especially since there are so many specific switch cases for each possible character.
The whole function is basically a giant switch case. There are many branches that check if a
specific int is NULL though, this could be done in a separate function. It’s not possible to call the
function directly but it can be tested indirectly through


parseResource(makeFilename("someFile")). Branch coverage percentage was improved from
25% to 31.25%.

**Function - CommentsInserter::insertComments**
This function had good branch coverage already, with all branches except for one simple
conditional tested. The tests provided for this function verifies that certain comments are
correctly parsed by comparing them to expected results. This function iterates through the tree
and does different things depending on the properties of the node it is reading. Documentation
clarified some of the branches that are taken, but isn’t completely clear

**Function - applyRemovedDiffElement**
Path:
javaparser-core/src/main/java/com/github/javaparser/printer/lexicalpreservation/Difference.java
This function had high branch coverage with 19 out of 23 of its branches covered. However this
is a little misleading because one of the branches not covered was a main branch inside that
function. Test cases for that specific branch created. Now it is 20 out of 23.

Refactoring: Most of the complexity comes from compound statements. Since these statements
are not modifying anything(they are mostly booleans or getters) actual complexity is lower.
However, though a lot of branches and conditions make it hard to understand code. There
are 7 branches function can go and other than first if they are relatively small. So isolating
first if to a function with same parameters would be a nice choice. And since half of the
complexity comes from that branch it's also a good balance.

**Function - applyKeptDiffElement**
Path:
javaparser-core/src/main/java/com/github/javaparser/printer/lexicalpreservation/Difference.java

This function was similar to applyRemovedDiffElement with high branch coverage. It was 20 out
of 23. Untested branches were mostly exceptions and created one test case for one of the
untested branches. Now coverage is 21 out of 23.

**Function - MethodDeclaration::getDeclarationAsString**
This function had no tests so the branch coverage was 0%. Some requirements that should be
tested:
The returned string should include or not include modifiers, throws and parameter names
depending on the arguments when calling the function.
The returned string should be correctly formatted.
I added 2 tests to this function, testing that it gave the proper string result.
Ad hoc branch coverage results before tests:
[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1]
The first 11 numbers represent getDeclarationAsString.
After tests:


[1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1]

Branch coverage improved to 64%
The CCN of this function could be reduced by 6 by replacing nested if statements with a single
function call.

**1. What is the quality of your own coverage measurement? Does it take into account
ternary operators (condition? yes : no) and exceptions, if available in your language?**

We implemented manual branch coverage by creating a global array of zeros, where each
position represent a branch. Then, if the branch is taken during the tests, 1 is written to the
corresponding index. This implementation does not take ternary operators or exceptions into
account, since they were not used in the functions.

**2. What are the limitations of your tool? How would the instrumentation change if you
modify the program?**

If we modify the program, we would have to change the indexes modified. This would be a
problem if we were to add more branches to an existing function, since we would either have to
move all indexes forward to make room in the array, or have branch tests that are not
consecutive to the other tests in the array.



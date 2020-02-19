# Notes

## problem 
Cannot import static from com....lexicalpreservation.*;

## Tests

## Tasks

## 3.3 Onboarding
1. 	a) I had to install maven, and that was the only additional tool which was needed.
		b) Maven is very well documented, but it is a bit overwelming. It is also unclear how it and its plugins interact on large multimodule projects. 
		c) The other components were automactially installed by maven.
		d) The build concluded without errors. 
		e) The tests run well on my computer, about 1 and a half mins for running all (thousands of) tests.

2. To our avail, we will use this project.

## 3.4 Part 1: Complexity measurement
1) With lizard we can find that:
- LexicalDifferenceCalculator::calculatedSyntaxModelForNode has a cyclomatic complexity number (CCN) of 37 and NLOC 187
- LambdaExprContext::solveSymbolAsValue has a CCN of 27 and NLOC of 112.
- (Difference::applyAddedDiffElement has a CCN of 27 and NLOC of 71.)

2) Both functions are quite large, Both functions hav about 1 complexity per 5 lines. I think this a quite a lot when you think about it, 1 if statement per 5 lines is quite a large number.

3) The purpose (We think) of the first function is to calculate the new concrete syntax model for a given syntax node. (The latter functions purpose is to apply a change to the concrete syntax tree.) The pupose of the latter function is to resolve a value from a lambda expression.  Since there are many different types of tokens and types of nodes, it is reasonable to have somewhat high complexity. However, handling each tokens edge case might be refactored into smaller functions. 

4) Throwing exceptions are not supported, catching is supported. The CC should be incremented when catching.

5) There is no documentation about these classes at all. 

## 3.5 Part 2: Coverage measurement & improvement

### 3.5.1 DIY

1) The quality is low, it requires manual annotation and analysis of all the branches. This makes it hard to use since it is very easy to make mistakes. It does take ternary expressions into account if the user wishes. It does not handle exceptions. 

2) This tool is extremely limited, fistly it is directly coded into a test class. Secondly, it required a lot of manual annotations. If the code changes, new annotation is required to be written.

3)  ??

## 3.5.2 Task 2: Coverage improvement


#Docs for solver



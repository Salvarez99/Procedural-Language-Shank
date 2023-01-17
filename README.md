# Procedural-Language-Shank

Shank is a program that is meant to be beginner friendly by being easily read and using the using the fundamental and core tools a programmer needs inorder to step their foot into Computer Science.

Core Concepts used to make Shank  
-Inheritance  
-Depth First Search  
-Casting  
-Linked List  
-Tokenization  
-Parsing  
-Interpretation  
-AST  
  
Shank uses three major building blocks in order to function.
Starting with the Lexer. The lexer is used to create valid tokens derived from a provided text file and scans each line character by charater. 
Once a string is validated the appropiate token will be added to a list of tokens.

The Parser depends on the token list created by the lexer. The parser uses the token list to verify the proper code formatting for the Shank language. It will create the necessary Nodes and add them to an AST, abstract symbol tree.

The interpreter, interprets the AST produced by the Parser interprets function definition, function bodies, function calls aswell as all statements.

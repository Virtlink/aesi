parser grammar PapljAntlrParser;
@header {
package com.virtlink.paplj.syntax;
}

options { tokenVocab=PapljAntlrLexer; }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                                Program and Classes                                                 //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// The first rule is the start rule, and must be a non-terminal.
program :
    'program' qualifiedName ';'?

    imports*
    type*

    ('run' expr ';'?)?
;

qualifiedName: ID ('.' ID)* ;
qualifiedNameWithWildcard: qualifiedName '.*'?;

imports: 'import' qualifiedNameWithWildcard ';'?;

type:
    'class' ID ('extends' qualifiedName) '{'
        classMember*
    '}'
;

classMember: field | method;

field: qualifiedName ID ';'?;

method: qualifiedName ID '(' (param (',' param)*)? ')'
    block2
;

param: qualifiedName ID;

binding: qualifiedName ID '=' expr;

block2:
    '{'
        (expr (';' expr)*)? ';'?
    '}'
;



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                                     Expressions                                                    //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

expr
    : '(' expr ')'                              # Parens
    | block2                                    # Block
    | ID                                        # Var
    | ID '(' (expr (',' expr)*)? ')'            # Call
    | 'new' qualifiedName '(' ')'               # New
    | 'null' qualifiedName?                     # Null
    | 'this'                                    # This
    | v=('true' | 'false')                      # Bool
    | INT                                       # Num
    | expr '.' ID                               # Member
    | expr '.' ID '(' (expr (',' expr)*)? ')'   # MemberCall
    | '-' expr                                  # Negate
    | '!' expr                                  # Not
    | <assoc=right> expr 'as' qualifiedName     # Cast
    | expr op=('*' | '/') expr                  # Multiplicative
    | expr op=('+' | '-') expr                  # Additive
    | expr op=('=='|'!='|'<') expr              # Compare
    | expr '&&' expr                            # And
    | expr '||' expr                            # Or
    | <assoc=right> expr '=' expr               # Assignment
    | 'let' binding* 'in' expr                  # Let
    | 'if' '(' expr ')' expr 'else' expr        # If
;

//expr
//    : 'if' '(' expr ')' expr 'else' expr        # If
//    | 'let' binding* 'in' expr                  # Let
//    | expr '='<assoc=right> expr                # Assignment
//    | expr '||' expr                            # Or
//    | expr '&&' expr                            # And
//    | expr op=('=='|'!='|'<') expr              # Compare
//    | expr op=('+' | '-') expr                  # Additive
//    | expr op=('*' | '/') expr                  # Multiplicative
//    | expr 'as'<assoc=right> qualifiedName      # Cast
//    | '!' expr                                  # Not
//    | '-' expr                                  # Negate
//    | expr '.' ID '(' (expr (',' expr)*)? ')'   # MemberCall
//    | expr '.' ID                               # Member
//    | INT                                       # Int
//    | ('true' | 'false')                        # Bool
//    | 'this'                                    # This
//    | 'null' qualifiedName?                     # Null
//    | 'new' qualifiedName '(' ')'               # New
//    | ID '(' (expr (',' expr)*)? ')'            # Call
//    | ID                                        # Var
//    | block2                                    # Block
//    | '(' expr ')'                              # Parens
//;

//
//expr
//    : ifLetExpr
//;
//
//ifLetExpr
//    : ifs
//    | let
//    | assignmentExpr
//;
//
//assignmentExpr
//    : logicalOrExpr ('=' expr)?
//;
//
//logicalOrExpr
//    : logicalAndExpr ('||' logicalAndExpr)*
//;
//
//logicalAndExpr
//    : comparativeExpr ('&&' comparativeExpr)*
//;
//
//comparativeExpr
//    : additiveExpr op=('=='|'!='|'<') additiveExpr
//    | additiveExpr
//;
//
//additiveExpr
//    : multiplicativeExpr (('+' | '-') multiplicativeExpr)*
//;
//
//multiplicativeExpr
//    : unaryExpr (('*' | '/') unaryExpr)*
//;
//
//unaryExpr
//    : memberExpr ('as' qualifiedName)?
//    | '!' unaryExpr
//    | '-' unaryExpr
//;
//
//memberExpr
//    : primaryExpr ('.' ID ('(' (expr (',' expr)*)? ')')?)*
//;
//
//primaryExpr
//    : INT
//    | ('true' | 'false')
//    | 'this'
//    | 'null' qualifiedName?
//    | 'new' qualifiedName '(' ')'
//    | ID ('(' (expr (',' expr)*)? ')')?
//    | block2
//    | '(' expr ')'
//;
//
//ifs:
//    'if' '(' expr ')'
//        expr
//    'else'
//        expr
//;
//
//let:
//    'let' binding*
//    'in' expr
//;
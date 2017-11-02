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

    ('run' expr)?
;

qualifiedName: ID ('.' ID)* ;
qualifiedNameWithWildcard: qualifiedName '.*'?;

imports: 'import' qualifiedNameWithWildcard ';'?;

type:
    'class' ID ('extends' qualifiedName) '{'
        member*
    '}'
;

member: field | method;

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

expr:
    ifLetExpr
;

ifLetExpr
    : ifs
    | let
    | assignmentExpr
;

assignmentExpr
    : logicalOrExpr ('=' expr)?
;

logicalOrExpr
    : logicalAndExpr ('||' logicalAndExpr)*
;

logicalAndExpr
    : comparativeExpr ('&&' comparativeExpr)*
;

comparativeExpr
    : additiveExpr (('==' | '!=' | '<') additiveExpr)?
;

additiveExpr
    : multiplicativeExpr (('+' | '-') multiplicativeExpr)*
;

multiplicativeExpr
    : unaryExpr (('*' | '/') unaryExpr)*
;

unaryExpr
    : memberExpr ('as' qualifiedName)?
    | '!' unaryExpr
    | '-' unaryExpr
;

memberExpr
    : primaryExpr ('.' ID ('(' (expr (',' expr)*)? ')')?)*
;

primaryExpr
    : INT
    | ('true' | 'false')
    | 'this'
    | 'null' qualifiedName?
    | 'new' qualifiedName '(' ')'
    | ID ('(' (expr (',' expr)*)? ')')?
    | block2
    | '(' expr ')'
;

ifs:
    'if' '(' expr ')'
        expr
    'else'
        expr
;

let:
    'let' binding*
    'in' expr
;
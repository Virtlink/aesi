lexer grammar PapljLexer;

@header {
package com.virtlink.paplj.syntax;
}
channels {
    WHITESPACE,
    COMMENTS
}

PROGRAM     : 'program';
RUN         : 'run';
IMPORT      : 'import';
CLASS       : 'class';
EXTENDS     : 'extends';
IF          : 'if';
ELSE        : 'else';
LET         : 'let';
IN          : 'in';
AS          : 'as';
TRUE        : 'true';
FALSE       : 'false';
THIS        : 'this';
NULL        : 'null';
NEW         : 'new';


SEMICOLON   : ';';
DOTSTAR     : '.*';
COMMA       : ',';
DOT         : '.';
LBRACE      : '{';
RBRACE      : '}';
LPAREN      : '(';
RPAREN      : ')';
EQ          : '==';
NEQ         : '!=';
LTE         : '<=';
GTE         : '>=';
LT          : '<';
GT          : '>';
OR          : '||';
AND         : '&&';
ASSIGN      : '=';
PLUS        : '+';
MIN         : '-';
MUL         : '*';
DIV         : '/';
NOT         : '!';

ID          : [A-Za-z]+;
INT         : [0-9]+;
COMMENT     : '/*' .*? '*/' -> channel(COMMENTS);
LINE_COMMENT: '//' .*? '\r'? '\n' -> channel(COMMENTS);
WS          : [ \t\r\n]+ -> channel(WHITESPACE);
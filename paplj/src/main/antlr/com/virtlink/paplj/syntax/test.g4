grammar test;

@header {
package com.virtlink.paplj.syntax;
}

r   : 'hello' ID;
ID  : [a-z]+ ;
WS  : [ \t\r\n]+ -> skip ;
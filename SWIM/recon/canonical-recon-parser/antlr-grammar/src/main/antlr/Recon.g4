grammar Recon;

@header {

package playground.swim.recon.antlr;

}

record: '{' block '}';

block: slot ((',' | ';') slot)*;

slot: value (':' value?)?;

value: literal | attr;

literal: record
       | NUMBER
       | STRING
       | BOOLEAN;

attr: '@' IDENTIFIER ('(' block ')')?;

WS: [ \t\r\n]+ -> skip ;

BOOLEAN: 'true' | 'false';

NUMBER: '-'? (([1-9] [0-9]*) | [0-9]);

STRING: '"' [a-zA-Z0-9_]* '"';

IDENTIFIER: [a-zA-Z] [a-zA-Z0-9_]*;

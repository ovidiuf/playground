grammar LibExpr;

import CommonLexerRules;

prog: stat+;

stat: expr NEWLINE              # printExpr
    | ID '=' expr NEWLINE       # assign
    | NEWLINE                   # blank
    ;

expr: expr ('*'|'/') expr       # MulDiv
    | expr ('+'|'-') expr       # AddSub
    | INT                       # int
    | ID                        # id
    | '(' expr ')'              # parens
    ;

MUL: '*';
DIV: '/';
ADD: '+';
SUB: '-';


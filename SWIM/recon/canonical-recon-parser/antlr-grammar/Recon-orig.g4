grammar Recon;

record: '{' block '}';

block: WS* slots WS*;

slots: slot SP* ((',' | ';' | NL) WS* slots)?;

slot:  blockValue (SP* ':' SP* blockValue?)?;

blockValue: literal SP* (attr SP* blockValue?)? | attr SP* blockValue? | COMMENT;

attr: '@' (IDENT | STRING) ('(' block ')')?;

literal: record | DATA | IDENT | STRING | NUMBER | BOOLEAN | selector;

//literal: record | markup | DATA | IDENT | STRING | NUMBER | BOOLEAN | selector;
//
//inlineValue: attr (record | markup)? | record | markup;
//
//markup: '[' (MARKUP_CHAR* | CHAR_ESCAPE | inlineValue)* ']';

selector: '$' (literal | '*' | '**' | '*:' | '#' INTEGER | filter) ('.' (literal | '*' | '**' | '*:') | '#' INTEGER | filter | '(' block ')')*;

filter: '[' blockExpr ']';

blockExpr: condExpr;

condExpr: lorExpr SP* ('?' SP* condExpr SP* ':' SP* condExpr)?;

lorExpr: landExpr SP* ('||' SP* landExpr)*;

landExpr: borExpr SP* ('&&' SP* borExpr)*;

borExpr: bxorExpr SP* ('|' SP* bxorExpr)*;

bxorExpr: bandExpr SP* ('^' SP* bandExpr)*;

bandExpr: compExpr SP* ('&' SP* compExpr)*;

compExpr: attrExpr SP* (('<' | '<=' | '==' | '>=' | '>') SP* attrExpr)?;

attrExpr: addSubExpr SP* (attr SP* attrExpr?)? | attr SP* attrExpr?;

addSubExpr: mulDivExpr SP* (('+' | '-') SP* mulDivExpr)*;

mulDivExpr: prefixExpr SP* (('*' | '/' | '%') SP* prefixExpr)*;

prefixExpr: primaryExpr SP* || ('!' | '~' | '-' | '+') SP* prefixExpr;

primaryExpr: literal || '(' blockExpr ')';

SP :             [\u0009\u0020]; // tab, space

NL :             [\u000A\u000D]; // new line, carriage return

WS :             SP | NL; // white space

CHAR :           [\u0001-\uD7FF] | [\uE000-\uFFFD]; // TODO how to represent supplementary characters in Antlr?

NAME_START_CHAR: [A-Z] | '_' | [a-z] |
                 [\u00C0-\u00D6] | [\u00D8-\u00F6] | [\u00F8-\u02FF] |
                 [\u0370-\u037D] | [\u037F-\u1FFF] | [\u200C-\u200D] |
                 [\u2070-\u218F] | [\u2C00-\u2FEF] | [\u3001-\uD7FF] |
                 [\uF900-\uFDCF] | [\uFDF0-\uFFFD];

NAME_CHAR:       NAME_START_CHAR | '-' | [0-9] |
                  '\u00B7' | [\u0300-\u036F] | [\u203F-\u2040];

//MARKUP_CHAR:     CHAR ~('\\' | '@' | '{' | '}' | '[' | ']');

STRING_CHAR:     CHAR ~('"' | '\\' | '@' | '{' | '}' | '[' | ']' | '\b' | '\f' | '\n' | '\r' | '\t');

CHAR_ESCAPE:     '\\' ('"' | '\\' | '/' | '@' | '{' | '}' | '[' | ']' | 'b' | 'f' | 'n' | 'r' | 't');

BASE64_CHAR:     [A-Za-z0-9+/];

IDENT:           NAME_START_CHAR NAME_CHAR*;

STRING:          '"' (STRING_CHAR* | CHAR_ESCAPE)* '"';

// DATA:            '%' (BASE64_CHAR{4})* (BASE64_CHAR BASE64_CHAR ((BASE64_CHAR '=') | ('=' '=')))?;
// TODO: how to express {4} ?
DATA:            '%' BASE64_CHAR* (BASE64_CHAR BASE64_CHAR ((BASE64_CHAR '=') | ('=' '=')))?;

NUMBER:          '-'? (([1-9] [0-9]*) | [0-9]) ('.' [0-9]+)? (('E' | 'e') ('+' | '-')? [0-9]+)?;

INTEGER:          '-'? (([1-9] [0-9]*) | [0-9]);

BOOLEAN:         'true' | 'false';

COMMENT:          '#' [^\n]*;


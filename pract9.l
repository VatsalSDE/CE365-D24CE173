%{
#include "pract9.tab.h"
%}

%%

i       return 'i';
t       return 't';
e       return 'e';
b       return 'b';
a       return 'a';

\n      return 0;
[ \t]   ;

.       return yytext[0];

%%

int yywrap() {
    return 1;
}
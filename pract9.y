%{
#include <stdio.h>
#include <stdlib.h>

int yylex(void);
void yyerror(const char *s);
%}

%%

S  : 'i' E 't' S S1
   | 'a'
   ;

S1 : 'e' S
   | /* empty */
   ;

E  : 'b'
   ;

%%

int main() {
    printf("Enter String: ");
    if (yyparse() == 0)
        printf("Valid String\n");
    else
        printf("Invalid String\n");
    return 0;
}

void yyerror(const char *s) {
    // do nothing (handled in main)
}
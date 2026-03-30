%option noyywrap
%{
    #include<stdio.h>
    int c = 0;
    int w = 0;
    int l = 0;
%}

%%
\n              { l++; c++; }
[ \t]+          { c += yyleng; }
[^ \t\n]+       { w++; c += yyleng; }
.               { c++; }
%%

int main()
{
    FILE *fp;
    fp = fopen("input.txt","r");

    yyin = fp;
    yylex();

    printf("Character : %d\n", c);
    printf("Words : %d\n", w);
    printf("Lines : %d\n", l);

    fclose(fp);
    return 0;
}

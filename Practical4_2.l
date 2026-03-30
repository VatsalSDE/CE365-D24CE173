%option noyywrap
%{
	#include<stdio.h>
%}

%%
[Cc][Hh][Aa][Rr][Uu][Ss][Aa][Tt]	{ printf("university"); }
.		{ printf("%s", yytext); }
%%
int main()
{
	yylex();
	return 0;
}
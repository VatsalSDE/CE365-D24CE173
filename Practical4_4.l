%{
	#include<stdio.h>
	#include<string.h>

	int lower=0,upper=0,digit=0,symbol=0;
	int length =0;
%}

%%
[a-z]	{ lower++;length++; }
[A-Z]	{ upper++;length++; }
[0-9]	{ digit++;length++; }
[*;#$@] { symbol++;length++; }
\n	{
		if(length >= 9 && length >= 15 && lower >= 1 && upper >= 1 && digit >= 1 && symbol >= 1)
			printf("Valid Password\n");
		else
			printf("Invalid Password\n");

		lower = upper = digit = symbol = length =0;
	}
.	{ length++; }
%%
int main()
{
	printf("Enter Password:\n");
	yylex();
	return 0;
}
int yywrap(){
	return 1;
}
%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int line_no = 1;
int symbol_count = 0;
int error_count = 0;

typedef struct {
    char name[100];
} SymbolEntry;

typedef struct {
    int line;
    char lexeme[100];
} ErrorEntry;

SymbolEntry symbol_table[1000];
ErrorEntry error_table[1000];

void add_to_symbol_table(char *name) {
    int i;
    for(i = 0; i < symbol_count; i++) {
        if(strcmp(symbol_table[i].name, name) == 0) return;
    }
    strcpy(symbol_table[symbol_count].name, name);
    symbol_count++;
}

void report_error(char *lexeme) {
    error_table[error_count].line = line_no;
    strcpy(error_table[error_count].lexeme, lexeme);
    error_count++;
}

void print_errors() {
    int i;
    printf("\nLEXICAL ERRORS\n");
    if(error_count == 0) {
        printf("No lexical errors\n");
    } else {
        for(i = 0; i < error_count; i++) {
            printf("Line %d : %s invalid lexeme\n", error_table[i].line, error_table[i].lexeme);
        }
    }
}

void print_symbol_table() {
    int i;
    printf("\nSYMBOL TABLE ENTRIES\n");
    for(i = 0; i < symbol_count; i++) {
        printf("%d) %s\n", i+1, symbol_table[i].name);
    }
}
%}

DIGIT      [0-9]
LETTER     [a-zA-Z_]
ID         {LETTER}({LETTER}|{DIGIT})*
NUM        {DIGIT}+
INVALID    {DIGIT}+{LETTER}+

%%

"//".*                { }
"/*"([^*]|\*+[^*/])*\*+"/"   { }

"int"|"char"|"float"|"double"|"void"|"return"|"if"|"else"|"while"|"for" {
    printf("Keyword: %s\n", yytext);
}

{INVALID}             { report_error(yytext); }

{ID}                  {
                        printf("Identifier: %s\n", yytext);
                        add_to_symbol_table(yytext);
                      }

{NUM}                 { printf("Constant: %s\n", yytext); }

"'"."'"               { printf("Constant: %s\n", yytext); }

\"[^\"]*\"            { printf("String: %s\n", yytext); }

[+\-*/=<>]            { printf("Operator: %s\n", yytext); }

[(){};,]              { printf("Punctuation: %s\n", yytext); }

[ \t]+                { }
\n                    { line_no++; }

.                     { report_error(yytext); }

%%

int main(int argc, char *argv[]) {
    FILE *file;

    if(argc < 2) {
        printf("Usage: %s <input_file>\n", argv[0]);
        return 1;
    }

    file = fopen(argv[1], "r");
    if(!file) {
        printf("Cannot open file\n");
        return 1;
    }

    yyin = file;

    printf("TOKENS\n");
    yylex();

    print_errors();
    print_symbol_table();

    fclose(file);
    return 0;
}

int yywrap() {
    return 1;
}
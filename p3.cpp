#include <iostream>
#include <fstream>
#include <cctype>
#include <string>
#include <unordered_set>
#include <vector>
using namespace std;

unordered_set<string> keywords = {
    "int", "char", "float", "double", "if", "else", "while", "for",
    "return", "void", "break", "continue", "long", "short", "signed",
    "unsigned", "struct", "union", "typedef", "static"
};

vector<string> symbolTable;
vector<pair<int, string>> lexicalErrors;

bool isKeyword(const string &s) {
    return keywords.count(s);
}

bool isOperator(char c) {
    string ops = "+-*/=%<>!";
    return ops.find(c) != string::npos;
}

bool isPunctuation(char c) {
    string punc = "();{},[]";
    return punc.find(c) != string::npos;
}

bool existsInSymbolTable(const string &id) {
    for (auto &s : symbolTable)
        if (s == id) return true;
    return false;
}

int main() {
    ifstream file("input.c");
    if (!file) {
        cout << "Error opening file.\n";
        return 1;
    }

    string line;
    int lineNo = 0;
    int tokenCount = 0;
    bool inBlockComment = false;

    cout << "Tokens\n";

    while (getline(file, line)) {
        lineNo++;
        int i = 0;

        while (i < line.length()) {

            if (inBlockComment) {
                if (line[i] == '*' && i + 1 < line.length() && line[i + 1] == '/') {
                    inBlockComment = false;
                    i += 2;
                } else i++;
                continue;
            }

            if (line[i] == '/' && i + 1 < line.length() && line[i + 1] == '/') {
                break;
            }

            if (line[i] == '/' && i + 1 < line.length() && line[i + 1] == '*') {
                inBlockComment = true;
                i += 2;
                continue;
            }

            if (isspace(line[i])) {
                i++;
                continue;
            }

            if (isalpha(line[i]) || line[i] == '_') {
                string word;
                while (i < line.length() && (isalnum(line[i]) || line[i] == '_'))
                    word += line[i++];

                if (isKeyword(word))
                    cout << "Keyword: " << word << endl;
                else {
                    cout << "Identifier: " << word << endl;
                    if (!existsInSymbolTable(word))
                        symbolTable.push_back(word);
                }
                tokenCount++;
            }

            else if (isdigit(line[i])) {
                string num;
                while (i < line.length() && isalnum(line[i]))
                    num += line[i++];

                bool valid = true;
                for (char c : num) {
                    if (!isdigit(c)) {
                        valid = false;
                        break;
                    }
                }

                if (valid) {
                    cout << "Constant: " << num << endl;
                    tokenCount++;
                } else {
                    lexicalErrors.push_back({lineNo, num});
                }
            }

            else if (line[i] == '\'') {
                string chConst;
                chConst += line[i++];

                if (i < line.length())
                    chConst += line[i++];

                if (i < line.length() && line[i] == '\'') {
                    chConst += line[i++];
                    cout << "Constant: " << chConst << endl;
                    tokenCount++;
                } else {
                    lexicalErrors.push_back({lineNo, chConst});
                }
            }

            else if (isOperator(line[i])) {
                cout << "Operator: " << line[i] << endl;
                tokenCount++;
                i++;
            }

            else if (isPunctuation(line[i])) {
                cout << "Punctuation: " << line[i] << endl;
                tokenCount++;
                i++;
            }

            else {
                string err(1, line[i]);
                lexicalErrors.push_back({lineNo, err});
                i++;
            }
        }
    }

    file.close();

    cout << "\nLexical Errors\n";
    for (auto &e : lexicalErrors)
        cout << "Line " << e.first << " : " << e.second << " invalid lexeme\n";

    cout << "\nSymbol table Entries\n";
    for (int i = 0; i < symbolTable.size(); i++)
        cout << i + 1 << ") " << symbolTable[i] << endl;

    cout << "\nTotal Tokens: " << tokenCount << endl;

    return 0;
}   
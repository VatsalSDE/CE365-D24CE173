#include <iostream>
#include <map>
#include <vector>
#include <set>
#include <string>

using namespace std;

map<char, vector<string>> grammar;
map<char, set<char>> firstSet;

bool isTerminal(char c) {
    return !(c >= 'A' && c <= 'Z');
}

set<char> findFirst(string str) {
    set<char> result;

    if (str.length() == 0)
        return result;

    char firstSymbol = str[0];

    if (isTerminal(firstSymbol)) {
        result.insert(firstSymbol);
        return result;
    }

    for (string production : grammar[firstSymbol]) {

        if (production == "e") {
            result.insert('e');
        }
        else {
            set<char> temp = findFirst(production);
            for (char c : temp)
                result.insert(c);
        }
    }

    return result;
}

void computeFirstSets() {

    for (auto rule : grammar) {

        char nonTerminal = rule.first;
        set<char> result;

        for (string production : rule.second) {

            set<char> temp = findFirst(production);

            for (char c : temp)
                result.insert(c);
        }

        firstSet[nonTerminal] = result;
    }
}

int main() {

    grammar['S'] = {"ABC", "D"};
    grammar['A'] = {"a", "e"};
    grammar['B'] = {"b", "e"};
    grammar['C'] = {"(S)", "c"};
    grammar['D'] = {"AC"};

    computeFirstSets();

    return 0;
}
#include <stdio.h>
#include <string.h>

int main() {
    char str[100];
    int i = 0;

    fgets(str, sizeof(str), stdin);

    str[strcspn(str, "\n")] = '\0';

    while (str[i] == 'a') {
        i++;
    }

    if (str[i] == 'b' && str[i + 1] == 'b' && str[i + 2] == '\0') {
        printf("Valid String");
    } else {
        printf("Invalid String");
    }

    return 0;
}
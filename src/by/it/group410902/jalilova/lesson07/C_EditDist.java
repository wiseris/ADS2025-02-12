package by.it.group410902.jalilova.lesson07;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: расстояние Левенштейна
    https://ru.wikipedia.org/wiki/Расстояние_Левенштейна
    http://planetcalc.ru/1721/

Дано:
    Две данных непустые строки длины не более 100, содержащие строчные буквы латинского алфавита.

Необходимо:
    Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ
    Итерационно вычислить алгоритм преобразования двух данных непустых строк
    Вывести через запятую редакционное предписание в формате:
     операция("+" вставка, "-" удаление, "~" замена, "#" копирование)
     символ замены или вставки

    Sample Input 1:
    ab
    ab
    Sample Output 1:
    #,#,

    Sample Input 2:
    short
    ports
    Sample Output 2:
    -s,~p,#,#,#,+s,

    Sample Input 3:
    distance
    editing
    Sample Output 2:
    +e,#,#,-s,#,~i,#,-c,~g,


    P.S. В литературе обычно действия редакционных предписаний обозначаются так:
    - D (англ. delete) — удалить,
    + I (англ. insert) — вставить,
    ~ R (replace) — заменить,
    # M (match) — совпадение.
*/


public class C_EditDist {

    String getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        int len1 = one.length();
        int len2 = two.length();

        int[][] dp = new int[len1 + 1][len2 + 1];
        String[][] operation = new String[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
            if (i > 0) operation[i][0] = operation[i - 1][0] + "-"+ one.charAt(i - 1) + ",";
            else operation[i][0] = "";
        }

        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
            if (j > 0) operation[0][j] = operation[0][j - 1] + "+" + two.charAt(j - 1) + ",";
            else operation[0][j] = "";
        }

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                int cost = one.charAt(i - 1) == two.charAt(j - 1) ? 0 : 1;

                int del = dp[i - 1][j] + 1;
                int ins = dp[i][j - 1] + 1;
                int rep = dp[i - 1][j - 1] + cost;

                dp[i][j] = Math.min(Math.min(del, ins), rep);

                if (dp[i][j] == rep) {
                    if (cost == 0) {
                        operation[i][j] = operation[i - 1][j - 1] + "#,";
                    } else {
                        operation[i][j] = operation[i - 1][j - 1] + "~" + two.charAt(j - 1) + ",";
                    }
                } else if (dp[i][j] == del) {
                    operation[i][j] = operation[i - 1][j] + "-" + one.charAt(i - 1) + ",";
                } else {
                    operation[i][j] = operation[i][j - 1] + "+" + two.charAt(j - 1) + ",";
                }
            }
        }

        String result = operation[len1][len2];
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = C_EditDist.class.getResourceAsStream("dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}
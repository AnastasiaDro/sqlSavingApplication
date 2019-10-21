package com.mymur.sqlsavingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase myDB;
    ArrayList <ArrayList> dataArr;
    ArrayList dataRowArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataArr = new ArrayList();
        dataRowArr = new ArrayList();


        setContentView(R.layout.activity_main);
        myDB = openOrCreateDatabase("my.db", MODE_PRIVATE, null);

        //После создания базы данных вы можете использовать метод execSQL()
        // для запуска SQL-инструкций. В следующем коде показано, как использовать
        // оператор SQL CREATE TABLE для создания таблицы названной user, которая имеет три столбца:


        //здесь название таблицы user, а в скобочках столбцы - имя, возраст, код инт - является ли свободным




        myDB.delete("user2", null, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS user (name VARCHAR(200), age INT, is_single INT)"
        );

        //Хотя можно вставить новые строки в таблицу с помощью метода execSQL(),
        // лучше использовать метод insert(). Метод insert() ожидает объект ContentValues,
        // содержащий значения для каждого столбца таблицы. Объект ContentValues очень похож на объект Map и содержит пары ключ-значение.
        //ключи, которые вы передаете методу put(), должны соответствовать именам столбцов в таблице.
        ContentValues row1 = new ContentValues();
        row1.put("name", "Alice");
        row1.put("age", 25);
        row1.put("is_single", 1);

        ContentValues row2 = new ContentValues();
        row2.put("name", "Bob");
        row2.put("age", 22);
        row2.put("is_single", 0);

        //Когда объекты ContentValues готовы, вы можете передать их методу insert() вместе с именем таблицы.
        myDB.insert("user", null, row1);
        myDB.insert("user", null, row2);

        //Чтобы отправить запрос в базу данных, вы можете использовать метод rawQuery(),
        // который возвращает объект Cursor, содержащий результаты запроса.
        Cursor myCursor = myDB.rawQuery("select name, age, is_single from user", null);

        //Объект Cursor может содержать ноль или несколько строк.
        //Самый простой способ перебрать все его строки, так это вызвать метод moveToNext() внутри цикла while.
        //Чтобы получить значение отдельного столбца, вы должны использовать такие методы,
        // как getString() и getInt(), которые ожидают индекс столбца. Например,
        // вот как вы получите все значения, вставленные в таблице user:
        int i = 0;
        while (myCursor.moveToNext()) {
            i++;
            String name = myCursor.getString(0);
            int age = myCursor.getInt(1);
            boolean isSingle = (myCursor.getInt(2)) ==1 ? true:false;
            dataRowArr.add(name);
           // System.out.println("имя "+name);
            dataRowArr.add(age);
          //  System.out.println("возраст "+age);
            dataRowArr.add(isSingle);
         //   System.out.println("одинок ли "+isSingle);
//            ArrayList newArr = new ArrayList();
//            newArr.addAll(dataRowArr)
            dataArr.add(new ArrayList(dataRowArr));

            dataRowArr.clear();

        }
        System.out.println("Размер массива dataArr: " + dataArr.size());
        System.out.println("Что у нас в массиве dataArr в первом элементе: " + dataArr.get(0).toString());
        System.out.println("Что у нас в массиве dataArr во втором элементе:"  + dataArr.get(1).toString());



        //После того, как вы получите все результаты вашего запроса, убедитесь,
        //что вы вызвали метод close() для объекта Cursor, чтобы освободить все ресурсы, которые он хранит.
        myCursor.close();
        //Аналогичным образом, когда вы закончили все операции с базой данных, не забудьте вызвать метод close() для объекта SQLiteDatabase.
        myDB.close();

    }
}

package org.example;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {


//        // не запишет, нет записи с "homework_id"=8
//        LessonDao.writeDataToLesson("lessonQQQ", 8);
//
//        // запишет
//        LessonDao.writeDataToLesson("lessonQwwwwQQ", 5);

//        // есть поле с данным id - удалит, нет поля - вывод сообщения об этом
//        LessonDao.deleteDataFromLesson(18);


        // получить все уроки
        List<Lesson> lessons = LessonDao.createLessonList("Lesson");
        LessonDao.printAllDataFromList(lessons);

        // получить урок по id
        Lesson lesson = LessonDao.getDataFromLessonById(4);
        System.out.println(lesson.toString());

    }
}
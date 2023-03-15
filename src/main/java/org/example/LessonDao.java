package org.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonDao {

    // метод додавання уроку
    public static void writeDataToLesson(String lessonName, Integer homeworkId) {
        if (checkIdInTable("Lesson", "homework_id", homeworkId)) {
            String sql = "INSERT INTO public.\"Lesson\"(name, homework_id) VALUES (?, ?);";
            PreparedStatement preparedStatement;

            try {
                preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, lessonName);
                preparedStatement.setInt(2, homeworkId);
                preparedStatement.execute();
                System.out.println("Lesson " + lessonName + "was added");
                DataBaseConnection.close(preparedStatement.getConnection());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("There is not homework with id = " + homeworkId);
        }
    }

    // метод видалення уроку
    public static void deleteDataFromLesson(Integer lessonId) {
        if (checkIdInTable("Lesson", "id", lessonId)) {
            String sql = "DELETE FROM public.\"Lesson\" WHERE \"id\"=(?) ;";
            PreparedStatement preparedStatement;

            try {
                preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, lessonId);
                preparedStatement.execute();
                if (!checkIdInTable("Lesson", "id", lessonId)) {
                    System.out.println("lesson with id = " + lessonId + " was deleted");
                }

                DataBaseConnection.close(preparedStatement.getConnection());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("no lesson with id = " + lessonId);
        }
    }

    // метод отримання всіх уроків - возвращает список объектов
    public static List<Lesson> createLessonList(String tableName) throws SQLException {
        ResultSet resultSet = getDataFromDB(tableName);

        List<Lesson> lessons = new ArrayList<>();
        while (resultSet.next()) {
            Lesson lesson = new Lesson();
            lesson.setId(resultSet.getInt("id"));
            lesson.setName(resultSet.getString("name"));
            lesson.setHomework_id(resultSet.getInt("homework_id"));
            lessons.add(lesson);
        }
        return lessons;
    }

    // метод отримання уроку за ID - возвращает объект
    public static Lesson getDataFromLessonById(Integer lessonId) {
        Lesson lesson = null;
        if (checkIdInTable("Lesson", "id", lessonId)) {
            String sql = "SELECT * FROM public.\"Lesson\" WHERE \"id\"=(?) ;";
            PreparedStatement preparedStatement;
            ResultSet resultSet;

            try {
                preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, lessonId);
                preparedStatement.execute();

                resultSet = preparedStatement.getResultSet();
                lesson = new Lesson();
                while (resultSet.next()) {
                    lesson.setId(resultSet.getInt("id"));
                    lesson.setName(resultSet.getString("name"));
                    lesson.setHomework_id(resultSet.getInt("homework_id"));
                }

                DataBaseConnection.close(preparedStatement.getConnection());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("no lesson with id = " + lessonId);
            return new Lesson();
        }
        return lesson;
    }




    private static ResultSet getDataFromDB(String tableName) throws SQLException {
        String sql = "SELECT * FROM \"" + tableName + "\";";
        ResultSet resultSet = DataBaseConnection.getConnection().createStatement().executeQuery(sql);
        DataBaseConnection.close(resultSet.getStatement().getConnection());
        return resultSet;
    }

    private static boolean checkIdInTable(String tableName, String fieldName, Integer id) {
        String sql = "SELECT * FROM \"" + tableName + "\";";
        ResultSet resultSet;
        try {
            resultSet = DataBaseConnection.getConnection().createStatement().executeQuery(sql);
            DataBaseConnection.close(resultSet.getStatement().getConnection());
            while (resultSet.next()) {
                if (resultSet.getObject(fieldName) == id) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static <T> void printAllDataFromList(List<T> list) {
        for (T t : list) {
            System.out.println(t.toString());
        }
        System.out.println();
    }

}

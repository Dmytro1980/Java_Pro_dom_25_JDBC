package org.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonDao {

    // метод додавання уроку
    public static void writeDataToLesson(String lessonName, Integer homeworkId) throws SQLException {
        if (checkIdInTable("Lesson", "homework_id", homeworkId)) {
            String sql = "INSERT INTO public.\"Lesson\"(name, homework_id) VALUES (?, ?);";
            PreparedStatement preparedStatement = null;

            try {
                preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, lessonName);
                preparedStatement.setInt(2, homeworkId);
                preparedStatement.execute();
                System.out.println("Lesson " + lessonName + "was added");
                DataBaseConnection.close(preparedStatement.getConnection());

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DataBaseConnection.close(preparedStatement.getConnection());
            }
        } else {
            System.out.println("There is not homework with id = " + homeworkId);
        }
    }

    // метод видалення уроку
    public static void deleteDataFromLesson(Integer lessonId) throws SQLException {
        if (checkIdInTable("Lesson", "id", lessonId)) {
            String sql = "DELETE FROM public.\"Lesson\" WHERE \"id\"=(?) ;";
            PreparedStatement preparedStatement = null;
            try {

                preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, lessonId);
                preparedStatement.execute();
                if (!checkIdInTable("Lesson", "id", lessonId)) {
                    System.out.println("lesson with id = " + lessonId + " was deleted");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DataBaseConnection.close(preparedStatement.getConnection());
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
    public static Lesson getDataFromLessonById(Integer lessonId) throws SQLException {
        Lesson lesson = null;
        if (checkIdInTable("Lesson", "id", lessonId)) {
            String sql = "SELECT * FROM public.\"Lesson\" WHERE \"id\"=(?) ;";
            PreparedStatement preparedStatement = null;
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DataBaseConnection.close(preparedStatement.getConnection());
            }
        } else {
            System.out.println("no lesson with id = " + lessonId);
            return new Lesson();
        }
        return lesson;
    }

    private static ResultSet getDataFromDB(String tableName) throws SQLException {
        String sql = "SELECT * FROM \"" + tableName + "\";";

        ResultSet resultSet = null;
        try {
            resultSet = DataBaseConnection.getConnection().createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DataBaseConnection.close(resultSet.getStatement().getConnection());
        }
        return resultSet;
    }

    private static boolean checkIdInTable(String tableName, String fieldName, Integer id) throws SQLException {
        String sql = "SELECT * FROM \"" + tableName + "\";";
        ResultSet resultSet = null;
        try {
            resultSet = DataBaseConnection.getConnection().createStatement().executeQuery(sql);

            while (resultSet.next()) {
                if (resultSet.getObject(fieldName) == id) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataBaseConnection.close(resultSet.getStatement().getConnection());
        }
    }

    public static <T> void printAllDataFromList(List<T> list) {
        for (T t : list) {
            System.out.println(t.toString());
        }
        System.out.println();
    }

}

package IA;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Storage
{
    private static String DB_NAME = "nirvana.db";

    public static void save(ArrayList<Session> sessionList)
    {
        try {
            FileOutputStream fileOutputStream = null;
            ObjectOutputStream objectOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(DB_NAME);
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(sessionList);
            }
            finally {
                if (objectOutputStream != null) objectOutputStream.close();
                if (fileOutputStream != null) fileOutputStream.close();
            }
        }
        catch (IOException cause) {
            throw new UnexpectedErrorException(cause);
        }
    }

    public static ArrayList<Session> load() {
        try {
            FileInputStream fileInputStream = null;
            ObjectInputStream objectInputStream = null;
            try {
                fileInputStream = new FileInputStream(DB_NAME);
                objectInputStream = new ObjectInputStream(fileInputStream);
                //noinspection unchecked
                return (ArrayList<Session>) objectInputStream.readObject();
            } finally {
                if (objectInputStream != null) objectInputStream.close();
                if (fileInputStream != null) fileInputStream.close();
            }
        }
        catch (FileNotFoundException cause) {
            return new ArrayList<>();
        }
        catch (IOException | ClassNotFoundException cause) {
            throw new UnexpectedErrorException(cause);
        }
    }
}

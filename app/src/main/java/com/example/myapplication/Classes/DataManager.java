package com.example.myapplication.Classes;


import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

public class DataManager
{

    /**
     * True if specified file exists.
     * @param context
     * @param filename
     * @return
     */
    public static boolean fileExists(Context context, String filename)
    {
        File file = context.getFileStreamPath(filename);

        return file != null && file.exists();
    }

    public static boolean deleteFile(Context context, String filename)
    {
        File dir = context.getApplicationContext().getFilesDir();
        File file = new File(dir, filename);
        return file.delete();
    }

    public static void renameFile(Context context, String fromFilename, String toFilename)
    {
        File dir = context.getApplicationContext().getFilesDir();
        File fromFile = new File(dir, fromFilename);
        File toFile = new File(dir, toFilename);
        fromFile.renameTo(toFile);
    }

    /**
     * Saves object to file
     *
     * @param context say "this"
     * @param data
     * @param file
     */
    public static void save(Context context, Object data, String file)
    {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try
        {
            fos = context.getApplicationContext().openFileOutput(file, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(data);

        } catch (IOException e)
        {
            e.printStackTrace();
            //////ToasakeText(context, e.toString(), //Toast.LENGTH_LONG).show();
        } finally
        {
            if (fos != null)
            {
                try
                {
                    fos.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                    ////Toast.makeText(context, e.toString(), //Toast.LENGTH_LONG).show();
                }
            }
            if (oos != null)
            {
                try
                {
                    oos.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                    //Toast.makeText(context, e.toString(), //Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    /**
     * Loads object from file
     *
     * @param context say "this"
     * @param file
     * @return A cast may be required
     */
    public static Object load(Context context, String file)
    {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object data = null;

        try
        {
            fis = context.getApplicationContext().openFileInput(file);
            ois = new ObjectInputStream(fis);
            data = ois.readObject();
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            ////Toast.makeText(context, e.toString(), //Toast.LENGTH_LONG).show();
        } finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                    ////Toast.makeText(context, e.toString(), //Toast.LENGTH_LONG).show();
                }
            }

            if (ois != null)
            {
                try
                {
                    ois.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                    //Toast.makeText(context, e.toString(), //Toast.LENGTH_LONG).show();
                }
            }
        }

        return data;
    }

    /*
    // please don't
    @Deprecated
    public static void saveRandomAccess(Context context, String data, String path, long position)
    {
        File file = new File(path);
        RandomAccessFile raf = null;
        try
        {
            raf = new RandomAccessFile(file, "rw");
            raf.seek(position);
            raf.writeUTF(data);
        } catch (IOException e)
        {
            e.printStackTrace();
            //Toast.makeText(context, e.toString(), //Toast.LENGTH_LONG).show();

        } finally
        {
            if (raf != null)
            {
                try
                {
                    raf.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                    //Toast.makeText(context, e.toString(), //Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Deprecated
    public static String loadRandomAccess(Context context, String path, long position)
    {
        File file = new File(path);
        RandomAccessFile raf = null;
        String data = "";

        try
        {
            raf = new RandomAccessFile(file, "rw");
            raf.seek(position);
            data = raf.readUTF();
        } catch (IOException e)
        {
            e.printStackTrace();
            //Toast.makeText(context, e.toString(), //Toast.LENGTH_LONG).show();

        } finally
        {
            if (raf != null)
            {
                try
                {
                    raf.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                    //Toast.makeText(context, e.toString(), //Toast.LENGTH_LONG).show();                }
            }
        }
        return data;
    }*/
}

package com.bula.Wallet.app.Data;

import android.content.Context;

/**
 * Created by Krzysiek on 2014-11-27.
 */
public final class DataBaseConnection {

    private static volatile DataBaseHelper instance = null;

    public static DataBaseHelper getConnection (Context context)
    {
        if(instance == null)
        {
            synchronized (DataBaseConnection.class)
            {
                if(instance == null)
                {
                    instance = new DataBaseHelper(context);
                }
            }
        }

        return instance;
    }

    private DataBaseConnection()
    {

    }

    public static void CloseConnection()
    {
        if(instance != null)
        {
            instance.closeDataBase();
            instance = null;
        }
    }
}


package com.bula.Wallet.app.Core.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.Cursor;
import android.util.SparseArray;

import com.bula.Wallet.app.Core.Data.AllData;
import com.bula.Wallet.app.Core.Data.IntervalDateTime;
import com.bula.Wallet.app.Core.Data.WalletData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WalletDataBase";
    private static final String TABLE_DATA = "DataWallet";
    private static final String TABLE_TYPE = "TypeWallet";
    private static final String TABLE_VALUE_COST = "Cost";
    private static final String TABLE_VALUE_TYPE = "Type";
    private static final String TABLE_VALUE_COMMENT = "Comment";
    private static final String TABLE_VALUE_DATETIME = "DateTime";
    private static final String TABLE_VALUE_NAME = "Name";
    public SQLiteDatabase db;


    public DataBaseHelper(Context context)
    {
        super(context,"/sdcard/Wallet/" + DATABASE_NAME + ".db",null,DATABASE_VERSION);
        File file = new File("/sdcard/Wallet");
        file.mkdirs();
        SQLiteDatabase.openOrCreateDatabase("/sdcard/Wallet/" + DATABASE_NAME + ".db", null); // zapis bazy nakarcie pamięci
        db = this.getWritableDatabase();
        //db = SQLiteDatabase.openDatabase("/sdcard/Wallet/" +DATABASE_NAME + ".db", null,SQLiteDatabase.CREATE_IF_NECESSARY);
        //onCreate(SQLiteDatabase.openOrCreateDatabase("/sdcard/Wallet/" + DATABASE_NAME + ".db",null));
        //this.onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase dataBase) {

        Log.d("create database","create start");
        String createTableData= "CREATE TABLE IF NOT EXISTS "+TABLE_DATA+" ( Id INTEGER PRIMARY KEY AUTOINCREMENT, Cost DOUBLE, Type INTEGER, DateTime DATETIME,Comment TEXT)";
        String createTableType= "CREATE TABLE IF NOT EXISTS "+TABLE_TYPE+" ( Id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT)";

        dataBase.execSQL(createTableData);
        dataBase.execSQL(createTableType);

        FillTable(dataBase,"jedzenie");
        FillTable(dataBase,"mieszkanie");
        FillTable(dataBase,"zdrowie");
        FillTable(dataBase,"transport");
        FillTable(dataBase,"ubrania");
        FillTable(dataBase,"relaks");
        FillTable(dataBase,"inne");

        Log.d("create database","create end");
    }

    @Override
    public void onUpgrade(SQLiteDatabase dataBase, int oldVersion, int newVersion) {
        dataBase.execSQL("DROP TABLE IF EXISTS "+TABLE_DATA+"");

        this.onCreate(dataBase);

    }

    public void addData(WalletData walletData)
    {
        SQLiteDatabase dataBase= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(TABLE_VALUE_COST,walletData.getCost());
        values.put(TABLE_VALUE_TYPE,getTypeId(dataBase,walletData.getType()));
        values.put(TABLE_VALUE_DATETIME,walletData.getDate());
        values.put(TABLE_VALUE_COMMENT,walletData.getComment());
        dataBase.insert(TABLE_DATA, null, values);
    }

    public void addtype(String type)
    {
        SQLiteDatabase dataBase= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(TABLE_VALUE_NAME,type);
        dataBase.insert(TABLE_TYPE, null, values);
    }

    public List<String> getAllTypes()
    {
        List<String> listTypes= new ArrayList<String>();
        String item;
        String query = "SELECT Name FROM "+TABLE_TYPE;
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            do {
                listTypes.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }else {
            listTypes.add("null");
        }

        return  listTypes;
    }

    public List<String> getAllItem()
    {
        List<String> listTypes= new ArrayList<String>();
        String item;
        String query = "SELECT DataWallet.Cost, DataWallet.DateTime, TypeWallet.Name FROM "+TABLE_DATA + " INNER JOIN "+TABLE_TYPE+" ON DataWallet.Type = TypeWallet.id ORDER BY DataWallet.Id DESC";
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            do {
                listTypes.add(cursor.getString(0) + "  \t|\t  " + cursor.getString(1)+ "  \t|\t  " + cursor.getString(2));
            }while (cursor.moveToNext());
        }else {
            listTypes.add("null");
        }

        return  listTypes;
    }

    public List<String> getAllItemFromType(int typeId)
    {
        List<String> listTypes= new ArrayList<String>();
        String item;
        String query = "SELECT DataWallet.Cost, DataWallet.DateTime, TypeWallet.Name FROM "+TABLE_DATA + " INNER JOIN "+TABLE_TYPE+" ON DataWallet.Type = TypeWallet.id WHERE DataWallet.Type =" +typeId +
                " ORDER BY DataWallet.Id DESC ";
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            do {
                listTypes.add(cursor.getString(0) + "  \t|\t  " + cursor.getString(1)+ "  \t|\t  " + cursor.getString(2));
            }while (cursor.moveToNext());
        }else {
            listTypes.add("null");
        }

        return  listTypes;
    }

    public SparseArray<AllData> getAllItemFromType(int typeId, IntervalDateTime dateTimeHelper)
    {
        //List<String> listTypes= new ArrayList<String>();
        SparseArray<AllData> listAllData = new SparseArray<AllData>();
        AllData allData = null;

        String name = new String();
        Integer key=0;

        String query ;
        if(typeId==0)
            query = "SELECT DataWallet.id, DataWallet.Cost, DataWallet.DateTime, TypeWallet.Name, DataWallet.Comment FROM "+TABLE_DATA + " INNER JOIN "+TABLE_TYPE+" ON DataWallet.Type = TypeWallet.id WHERE" +
                    " DataWallet.DateTime Between '"+dateTimeHelper.getBeginDateTime()+"' AND '"+dateTimeHelper.getEndDateTime()+"' ORDER BY TypeWallet.Name  ";
        else
            query = "SELECT DataWallet.id, DataWallet.Cost, DataWallet.DateTime, TypeWallet.Name, DataWallet.Comment FROM "+TABLE_DATA + " INNER JOIN "+TABLE_TYPE+" ON DataWallet.Type = TypeWallet.id WHERE DataWallet.Type =" +typeId +
                " AND DataWallet.DateTime Between '"+dateTimeHelper.getBeginDateTime()+"' AND '"+dateTimeHelper.getEndDateTime()+"' ORDER BY TypeWallet.Name ";
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            do {
                //listTypes.add(cursor.getString(0) + "  \t|\t  " + cursor.getString(1) + "  \t|\t  " + cursor.getString(2));
                name = cursor.getString(3);
                if(allData == null)
                {
                    allData =  new AllData(cursor.getString(3));
                    allData.typeData.add(new WalletData(Integer.parseInt(cursor.getString(0)),Double.parseDouble(cursor.getString(1)),cursor.getString(3),cursor.getString(4),cursor.getString(2)));
                    //allData.typeData.add(cursor.getString(0) + "  \t|\t  " + cursor.getString(1) + "  \t|\t  " + cursor.getString(3));
                }
                if(cursor.moveToNext())
                {
                    if(!name.equals(cursor.getString(3)))
                    {
                        //allData.typeData.add(cursor.getString(0) + "  \t|\t  " + cursor.getString(1) + "  \t|\t  " + cursor.getString(2));
                        listAllData.append(key,allData);
                        allData = null;
                        key++;
                    }
                    else
                    {
                        //allData.typeData.add(cursor.getString(0) + "  \t|\t  " + cursor.getString(1) + "  \t|\t  " + cursor.getString(3));
                        allData.typeData.add(new WalletData(Integer.parseInt(cursor.getString(0)), Double.parseDouble(cursor.getString(1)),cursor.getString(3),cursor.getString(4),cursor.getString(2)));
                    }

                    cursor.moveToPrevious();
                }


            }while (cursor.moveToNext());
        }else {
            //listTypes.add("null");
        }
        if(allData != null)
            listAllData.append(key,allData);

        return  listAllData;
    }

    public String getSumCost(IntervalDateTime dateTimeHelper)
    {
        String query ="SELECT SUM(Cost) as 'TotalCost' FROM DataWallet WHERE DateTime Between '"+dateTimeHelper.getBeginDateTime()+"' and '"+dateTimeHelper.getEndDateTime()+"'";

        return getSum(query);
    }

    public String getSumCostTypeInterval(int type,IntervalDateTime dateTimeHelper)
    {
        String query ="SELECT SUM(Cost) as 'TotalCost' FROM DataWallet WHERE Type ="+type+" AND DateTime Between '"+dateTimeHelper.getBeginDateTime()+"' and '"+dateTimeHelper.getEndDateTime()+"'";

        return getSum(query);
    }

    public void UpdateData(WalletData walletData)
    {
        String[] args = new String[1];
        args[0]=walletData.getId().toString();
        SQLiteDatabase dataBase= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(TABLE_VALUE_COST,walletData.getCost());
        values.put(TABLE_VALUE_TYPE,getTypeId(dataBase,walletData.getType()));
        values.put(TABLE_VALUE_DATETIME,walletData.getDate());
        values.put(TABLE_VALUE_COMMENT,walletData.getComment());
        dataBase.update(TABLE_DATA,values,"Id=?",args);
    }

    public void DeleteData(Integer id)
    {
        SQLiteDatabase dataBase= this.getWritableDatabase();
        dataBase.delete(TABLE_DATA,"Id= "+id,null);
    }

    public void closeDataBase()
    {
        db.close();
    }

    private String getSum(String query)
    {
        String sum = "";

        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst())
        {
            do{
                sum = cursor.getString(0);
            }while(cursor.moveToNext());
        }
        if (sum == null){
            sum="0";
        }

        return sum;
    }

    private Integer getLastInsertId(SQLiteDatabase dataBase)
    {
        Integer id;
        String query="SELECT last_insert_rowid()";
        Cursor cursor= dataBase.rawQuery(query,null);

        if (cursor.moveToFirst())
        {
            do{
                id = Integer.parseInt(cursor.getString(0));
            }while(cursor.moveToNext());
        }else return -1;
        return id;
    }


    private Integer getTypeId(SQLiteDatabase dataBase , String name)
    {
        Integer id;
        String query = "SELECT id FROM "+TABLE_TYPE+ " WHERE Name = '"+name+"'";
        Cursor cursor= dataBase.rawQuery(query,null);

        if (cursor.moveToFirst())
        {
            do{
                id = Integer.parseInt(cursor.getString(0));
            }while(cursor.moveToNext());
        }else return -1;
        return id;
    }

    public Integer getTypeId(String name)
    {
        return getTypeId(db,name);
    }

    private void FillTable(SQLiteDatabase dataBase, String type)
    {
        ContentValues values= new ContentValues();
        values.put(TABLE_VALUE_NAME,type);
        dataBase.insert(TABLE_TYPE, null, values);
    }

}

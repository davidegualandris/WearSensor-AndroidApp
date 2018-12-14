package com.example.lapuile.wearsensor;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class ExcelSheet {

    public static final String TAG = "BasicSensorsApi";

    private String sensorName;
    private float[] sensorValue;

    private ArrayList<String> dataSensorList = new ArrayList<String>();

    public ExcelSheet(String name, float[] value, ArrayList<String> dataSensorPass) {
        sensorName = name;
        sensorValue = value;
        dataSensorList = dataSensorPass;

    }

    public ExcelSheet(ArrayList<String> dataSensorList){
        this.dataSensorList = dataSensorList;
    }

    public void exportListToExcel(){

        File sensorDir = new File(Environment.getExternalStorageDirectory(),"WearSensor");

        if(!sensorDir.exists())
            sensorDir.mkdirs();


        final String dirName = "List of sensors";



        //file path
        File directory = new File(sensorDir, dirName);

        if(!directory.exists())
            directory.mkdirs();

        String fileName = "List.xls";

        File file = new File(directory, fileName);

        if (!directory.mkdirs()) {
            Log.e(TAG, "Directory not created");

            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;

            try {
                workbook = Workbook.createWorkbook(file, wbSettings);
                //Excel sheet name. 0 represents first sheet
                WritableSheet sheet = workbook.createSheet("SensorList", 0);

                try {
                    int row = 0;
                    for (int i = 0; i < dataSensorList.size(); i++) {

                        sheet.addCell(new Label(0, row, dataSensorList.get(i))); // column and row

                        row++;

                    }

                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                workbook.write();
                try {
                    workbook.close();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void exportToExcel() {

        File sensorDir = new File(Environment.getExternalStorageDirectory(),"WearSensor");

        if(!sensorDir.exists())
            sensorDir.mkdirs();


        final String dirName = sensorName;



        //file path
        File directory = new File(sensorDir, dirName);

        if(!directory.exists())
            directory.mkdirs();

        String fileName = "Data.xls";

        File file = new File(directory, fileName);

        if (!directory.mkdirs()) {
            Log.e(TAG, "Directory not created");

            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;

            try {
                workbook = Workbook.createWorkbook(file, wbSettings);
                //Excel sheet name. 0 represents first sheet
                WritableSheet sheet = workbook.createSheet(sensorName, 0);

                try {
                    int row = 0;
                    for (int i = 0; i < sensorValue.length && i < dataSensorList.size(); i++) {



                        sheet.addCell(new Label(0, row, dataSensorList.get(i))); // column and row

                        sheet.addCell(new Number(1, row, sensorValue[i]));
                        row++;

                    }

                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                workbook.write();
                try {
                    workbook.close();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}


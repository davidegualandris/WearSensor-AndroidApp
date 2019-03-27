package com.example.lapuile.wearsensor;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.nfc.Tag;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

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
    private String description;
    private String blueData;

    private float maxRange;
    private float power;
    private float resolution;
    private String vendor;
    private int version;

    private ArrayList<String> dataSensorList = new ArrayList<String>();

    private String mdeviceName;

    public ExcelSheet(String name, float[] value, ArrayList<String> dataSensorPass, String descr,
                      float range, float battery, float res, int vers, String vend) {
        sensorName = name;
        sensorValue = value;
        dataSensorList = dataSensorPass;
        description = descr;
        maxRange = range;
        power = battery;
        resolution = res;
        version = vers;
        vendor = vend;


    }

    public ExcelSheet(String chara, String data, String name, String descr) {
        sensorName = chara;
        blueData = data;
        mdeviceName = name;
        description = descr;

    }

    public ExcelSheet(ArrayList<String> dataSensorList) {
        this.dataSensorList = dataSensorList;
    }

    public void exportListToExcel() {

        File sensorDir = new File(Environment.getExternalStorageDirectory(), "WearSensor");

        if (!sensorDir.exists())
            sensorDir.mkdirs();


        final String dirName = "List of Phone sensors";


        //file path
        File directory = new File(sensorDir, dirName);

        if (!directory.exists())
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


    public void exportListWearToExcel() {

        File sensorDir = new File(Environment.getExternalStorageDirectory(), "WearSensor");

        if (!sensorDir.exists())
            sensorDir.mkdirs();


        final String dirName = "List of Wear sensors";


        //file path
        File directory = new File(sensorDir, dirName);

        if (!directory.exists())
            directory.mkdirs();

        String fileName = "ListWear.xls";

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

    public void exportBluetoothToExcel() {
        File sensorDir = new File(Environment.getExternalStorageDirectory(), "WearSensor");

        if (!sensorDir.exists())
            sensorDir.mkdirs();

        File phoneDir = new File(sensorDir, mdeviceName);

        if (!phoneDir.exists())
            phoneDir.mkdirs();


        final String dirName = sensorName;


        //file path
        File directory = new File(phoneDir, dirName);

        if (!directory.exists())
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

                    sheet.addCell(new Label(0, 0, sensorName));


                    sheet.addCell(new Label(0, 1, "Value")); // column and row


                    sheet.addCell(new Label(1, 1, blueData));

                    sheet.addCell(new Label(0, 2, "Description"));
                    sheet.addCell(new Label(1, 2, description));


                    // sheet.addCell(new Label(0, row, description));

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

        File sensorDir = new File(Environment.getExternalStorageDirectory(), "WearSensor");

        if (!sensorDir.exists())
            sensorDir.mkdirs();

        File phoneDir = new File(sensorDir, "PhoneSensor");

        if (!phoneDir.exists())
            phoneDir.mkdirs();


        final String dirName = sensorName;


        //file path
        File directory = new File(phoneDir, dirName);

        if (!directory.exists())
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
                    Log.i(TAG, "LIST" + dataSensorList);
                    sheet.addCell(new Label(0, 0, sensorName));

                    int row = 1;
                    for (int i = 1; i <= dataSensorList.size() - 1; i++) {

                        String[] splitted = dataSensorList.get(i).split(":");


                        sheet.addCell(new Label(0, row, splitted[0])); // column and row


                        sheet.addCell(new Label(1, row, splitted[1]));
                        row++;

                    }
                    sheet.addCell(new Label(0, row+1, description));

                    sheet.addCell(new Label(0, row+2, "Vendor"));
                    sheet.addCell(new Label(1, row+2, vendor));

                    sheet.addCell(new Label(0, row+3, "Version"));
                    sheet.addCell(new Number(1, row+3, version));

                    sheet.addCell(new Label(0, row+4, "Max Range"));
                    sheet.addCell(new Number(1, row+4, maxRange));

                    sheet.addCell(new Label(0, row+5, "Resolution"));
                    sheet.addCell(new Number(1, row+5, resolution));

                    sheet.addCell(new Label(0, row+6, "Power Consumed"));
                    sheet.addCell(new Number(1, row+6, power));


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

    public void exportWearToExcel() {

        File sensorDir = new File(Environment.getExternalStorageDirectory(), "WearSensor");

        if (!sensorDir.exists())
            sensorDir.mkdirs();

        File watchDir = new File(sensorDir, "WatchSensor");

        if (!watchDir.exists())
            watchDir.mkdirs();

        final String dirName = sensorName;


        //file path
        File directory = new File(watchDir, dirName);

        if (!directory.exists())
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


                    sheet.addCell(new Label(0, 0, sensorName));
                    int row = 1;
                    for (int i = 1; i <= dataSensorList.size() - 1; i++) {

                        String[] splitted = dataSensorList.get(i).split(":");


                        sheet.addCell(new Label(0, row, splitted[0])); // column and row


                        sheet.addCell(new Label(1, row, splitted[1]));
                        row++;

                    }
                    sheet.addCell(new Label(0, row+1, description));
                    sheet.addCell(new Label(0, row+2, "Vendor"));
                    sheet.addCell(new Label(1, row+2, vendor));

                    sheet.addCell(new Label(0, row+3, "Version"));
                    sheet.addCell(new Number(1, row+3, version));

                    sheet.addCell(new Label(0, row+4, "Max Range"));
                    sheet.addCell(new Number(1, row+4, maxRange));

                    sheet.addCell(new Label(0, row+5, "Resolution"));
                    sheet.addCell(new Number(1, row+5, resolution));

                    sheet.addCell(new Label(0, row+6, "Power Consumed"));
                    sheet.addCell(new Number(1, row+6, power));

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


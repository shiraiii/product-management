package com.firstapp.scanproduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtmasp, edttensp, edtsoluong, edtdongia;
    Button btnadd, btndelete, btnupdate, btnquery;
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;
    SQLiteDatabase mydatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtmasp = findViewById(R.id.edtmasp);
        edttensp = findViewById(R.id.edttensp);
        edtsoluong = findViewById(R.id.edtsoluong);
        edtdongia = findViewById(R.id.edtdongia);
        btnadd = findViewById(R.id.btnadd);
        btndelete = findViewById(R.id.btndelete);
        btnupdate = findViewById(R.id.btnupdate);
        btnquery = findViewById(R.id.btnquery);
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,mylist);
        lv.setAdapter(myadapter);
        mydatabase = openOrCreateDatabase("qlsanpham.db", MODE_PRIVATE,null);
        try {
            String sql = "CREATE TABLE tblsp(masanpham TEXT primary key,tensanpham TEXT, soluong INTEGER, dongia INTEGER)";
            mydatabase.execSQL(sql);
        }
        catch (Exception e){
            Log.e("Error","Table đã tồn tại");
        }
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String masanpham = edtmasp.getText().toString();
                String tensanpham = edttensp.getText().toString();
                int soluong = Integer.parseInt(edtsoluong.getText().toString());
                int dongia = Integer.parseInt(edtdongia.getText().toString());
                ContentValues myvalue = new ContentValues();
                myvalue.put("masanpham",masanpham);
                myvalue.put("tensanpham",tensanpham);
                myvalue.put("soluong",soluong);
                myvalue.put("dongia",dongia);
                String msg = "";
                if(mydatabase.insert("tblsp",null,myvalue)==-1){
                    msg = "Fail to Insert Record!";
                }
                else{
                    msg = "Insert Record Successfully";
                }
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String masanpham = edtmasp.getText().toString();
                int n = mydatabase.delete("tblsp","masanpham=?",new String[]{masanpham});
                String msg ="";
                if(n==0){
                    msg = "No record to delete";
                }
                else{
                    msg = n+" record is deleted";
                }
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();


            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String masanpham = edtmasp.getText().toString();
                String tensanpham = edttensp.getText().toString();
                int soluong = Integer.parseInt(edtsoluong.getText().toString());
                int dongia = Integer.parseInt(edtdongia.getText().toString());
                ContentValues myvalue = new ContentValues();
                myvalue.put("masanpham",masanpham);
                myvalue.put("tensanpham",tensanpham);
                myvalue.put("soluong",soluong);
                myvalue.put("dongia",dongia);
                int n = mydatabase.update("tblsp",myvalue,"masanpham = ?",new String[]{masanpham});
                String msg = "";
                if(n==0){
                    msg = "No record to update";
                }
                else{
                    msg = n+" record is updated";
                }
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
        btnquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mylist.clear();
                Cursor c = mydatabase.query("tblsp",null,null,null,null,null,null);
                c.moveToNext();
                String data = "";
                while(!c.isAfterLast()){
                    data = c.getString(0)+" - "+c.getString(1)+" - "+c.getString(2)+" - "+c.getString(3)+"đ";
                    c.moveToNext();
                    mylist.add(data);
                }
                c.close();
                myadapter.notifyDataSetChanged();
            }
        });
    }
}
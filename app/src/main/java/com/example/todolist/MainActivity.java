package com.example.todolist;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.provider.FontsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listview;
    ArrayList<SpannableString> sheet = new ArrayList<>();
    List<ArrayList<SpannableString>> lists = new ArrayList<ArrayList<SpannableString>>();

    public int row = 0;
    int num = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText item =  findViewById(R.id.editText);
        final ListView list2 = findViewById(R.id.list);
        list2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        final ArrayAdapter<SpannableString> adapt = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, sheet);
        list2.setAdapter(adapt);

        // Listener to see when enter is pressed
        item.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_UP)&&(keyCode == KeyEvent.KEYCODE_ENTER)) {
                    SpannableString x;
                    if (item.getText().toString().equals("Sudhanva is Gay")) {
                        x = new SpannableString(  "More like ganesh");
                    }
                    else {
                        x = new SpannableString(item.getText() + "     ");
                    }


                    ForegroundColorSpan textColor = new ForegroundColorSpan(Color.WHITE);
                    StyleSpan bold = new StyleSpan(Typeface.BOLD);
                    AbsoluteSizeSpan size = new AbsoluteSizeSpan(65);

                    x.setSpan(textColor,0,x.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    x.setSpan(bold,0,x.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    x.setSpan(size, 0,x.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    sheet.add(x);
                    lists.set(row, sheet);
                    adapt.notifyDataSetChanged();
                    item.getText().clear();
                    return true;

                }

                return false;
            }
        });


        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int pos = i;
                num++;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (num == 1) {
                            SpannableString cros = new SpannableString(sheet.get(pos));
                            cros.setSpan(new StrikethroughSpan(), 0, sheet.get(pos).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sheet.add(cros);
                            sheet.remove(pos);
                            lists.set(row, sheet);

                            adapt.notifyDataSetChanged();
                        }
                        else if (num == 2) {
                            String x = sheet.get(pos).toString();
                            SpannableString c = new SpannableString(x);
                            ForegroundColorSpan textColor = new ForegroundColorSpan(Color.WHITE);
                            StyleSpan bold = new StyleSpan(Typeface.BOLD);
                            AbsoluteSizeSpan size = new AbsoluteSizeSpan(65);

                            c.setSpan(textColor,0,c.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            c.setSpan(bold,0,c.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            c.setSpan(size, 0,c.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sheet.remove(pos);
                            sheet.add(0,c);
                            lists.set(row, sheet);

                            adapt.notifyDataSetChanged();

                        }
                        num = 0;

                    }
                }, 400);



            }
        });
        Button clear = findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheet.clear();
                lists.set(row, sheet);
                adapt.notifyDataSetChanged();

            }
        });

        final ArrayList<SpannableString> sheet2 = new ArrayList<>();
//        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                SpannableString d = new SpannableString(((TextView) view).getText());
//                if(sheet2.contains(d)){
//                    //sheet2.remove(d);
//                }
//                else{
//                    //sheet2.add(d);
//                }
//            }
//        });
        // long click to delete

        list2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int wh_item = position;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sheet.remove(wh_item);
                                adapt.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                        lists.set(row, sheet);
                return true;

            }

        });







    }


}

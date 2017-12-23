package com.syberkeep.take_a_note;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ListView listView;
    private ArrayList list_of_items;
    private List selection;
    private int count_selected = 0;
    private Context thisContext = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Notes");
        toolbar.setBackground(getDrawable(R.drawable.toolbar_design));
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabClick();
            }
        });

        final String[] FILES = {"File 1", "File 2", "File 3"};
        final String[] DATES = {"date created 1", "date created 2", "date created 3"};

        listView = (ListView) findViewById(R.id.list_view);
        list_of_items = new ArrayList();
        for(String item : FILES){
            list_of_items.add(item);
        }
        selection = new ArrayList();
        class ListAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                return list_of_items.size();
            }

            @Override
            public Object getItem(int position) {
                return list_of_items.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = getLayoutInflater().inflate(R.layout.list_item, null);
                TextView fileName = (TextView) convertView.findViewById(R.id.file_name_text);
                TextView date = (TextView) convertView.findViewById(R.id.date_text);

                fileName.setText(FILES[position]);
                date.setText(DATES[position]);

                return convertView;
            }

            public void remove(int position)
            {
                list_of_items.remove(position);
                this.notifyDataSetChanged();
            }
            public void remove(Object object)
            {
                list_of_items.remove(object);
                this.notifyDataSetChanged();
            }
        }

        final ListAdapter listAdapter = new ListAdapter();
        listView.setAdapter(listAdapter);

        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if(checked){
                    selection.add(list_of_items.get(position));
                    count_selected++;
                    mode.setTitle(count_selected + " Selected");
                }else{
                    selection.remove(list_of_items.get(position));
                    count_selected--;
                    mode.setTitle(count_selected + " Selected");
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.select_item_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if(item.getItemId() == R.id.delete_item_select_menu){
                    for(Object Item : selection){
                        //String item_name = Item.toString();
                        list_of_items.remove(Item);
                        listAdapter.remove(Item);
                        Toast.makeText(thisContext, Item.toString() + " is removed", Toast.LENGTH_SHORT).show();
                    }
                    listAdapter.notifyDataSetChanged();
                    mode.finish();
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                count_selected = 0;
                selection.clear();
            }
        });
    }

    private void fabClick() {
        startActivity(new Intent(thisContext, NoteEditor.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

}

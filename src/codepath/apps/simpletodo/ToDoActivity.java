package codepath.apps.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoActivity extends ActionBarActivity {
	
	ArrayList<String> items; 
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	private final int REQUEST_CODE = 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		
		lvItems = (ListView)findViewById(R.id.lvItems);
		items = new ArrayList<String>();
		readItems();
		//items.add("First Item");
		//items.add("Second Item");
		itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
		setupListViewListener();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	private void readItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try{
		items = new ArrayList<String>(FileUtils.readLines(todoFile));
		}catch(IOException e){
		items = new ArrayList<String>();
		e.printStackTrace();
		}

		}
	
	private void editItems(){
		
		
		
	}

		private void saveItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try{
		FileUtils.writeLines(todoFile, items);
		}catch(IOException e){
		e.printStackTrace();
		}
		}
		
		public void addToDoItem(View v){
			EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
			itemsAdapter.add(etNewItem.getText().toString());
			etNewItem.setText("");
			saveItems();
			}
			
		
			
			
			private void setupListViewListener(){
			lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long rowId){
			items.remove(position);
			itemsAdapter.notifyDataSetChanged();
			saveItems();
			return true;
			}
			});
			
			lvItems.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					String value = items.get(position).toString();
					Intent intent = new Intent(ToDoActivity.this, EditActivity.class);
					intent.putExtra("index", position);
					intent.putExtra("value", value);
					startActivityForResult(intent, REQUEST_CODE);
					
					
				}
				
				
			});
			
			}
			
			
			@Override
			protected void onActivityResult(int requestCode, int resultCode, Intent data) {

			if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

			String value = data.getExtras().getString("editText");
			int index = data.getIntExtra("index",-1);
			items.remove(index);
			itemsAdapter.insert(value, index);
			itemsAdapter.notifyDataSetChanged();
			saveItems();

			}
			}
	
	

}

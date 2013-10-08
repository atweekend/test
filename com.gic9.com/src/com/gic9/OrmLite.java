package com.gic9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gic9.com.R;
import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;
import com.mobeta.android.dslv.DragSortListView.RemoveListener;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OrmLite extends ListActivity {

	private EditText et_memo;
	private Button insertButton;
	private ArrayAdapter<String> adapter;
	private MemoDaoImpl  memoDaoImpl;
	private List<String> listItem;
	
	private DragSortListView.DropListener onDropListener = new DragSortListView.DropListener() {
		
		@Override
		public void drop(int from, int to) {
			// TODO Auto-generated method stub
			if(from!=to){
				DragSortListView dragSortListView =  getListView();
				String item = adapter.getItem(from);
				adapter.remove(item);
				adapter.insert(item, to);
				dragSortListView.moveCheckState(from, to);
					
			}
			
			
		}
	};
	
	private DragSortListView.RemoveListener removeListener =new DragSortListView.RemoveListener() {
		
		@Override
		public void remove(int which) {
			// TODO Auto-generated method stub
			Toast.makeText(OrmLite.this, "remove", Toast.LENGTH_SHORT).show();
			DragSortListView dragSortListView = getListView();
			String item =adapter.getItem(which);
			adapter.remove(item);
			dragSortListView.removeCheckState(which);
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orm_lite);
	
		memoDaoImpl = new MemoDaoImpl(this);
		memoDaoImpl.createMemoTable();
		et_memo =(EditText) findViewById(R.id.editText1);
		insertButton =(Button) findViewById(R.id.add);
		
		
		insertButton.setOnClickListener(clickListener);
		
		
		refreshListItem();
		adapter = new ArrayAdapter<String>(this, R.layout.list_item_checkable,R.id.text,listItem);
		setListAdapter(adapter);
		DragSortListView dragSortListView =getListView();
		dragSortListView.setDropListener(onDropListener);
		dragSortListView.setRemoveListener(removeListener);
		
		DragSortController controller = new DragSortController(dragSortListView);
		controller.setDragHandleId(R.id.drag_handle);
		controller.setSortEnabled(true);
		controller.setDragInitMode(1);
		dragSortListView.setFloatViewManager(controller);
		dragSortListView.setOnTouchListener(controller);
		dragSortListView.setDragEnabled(true);
		
		
	}
	
	@Override
	public DragSortListView getListView() {
		// TODO Auto-generated method stub
		return (DragSortListView) super.getListView();
	}

	View.OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.add:
				String contents = et_memo.getText().toString();
				if(contents.length() == 0){
					
					Toast.makeText(OrmLite.this, "내용을 입력하시오", Toast.LENGTH_SHORT).show();
				}else {
					
					memoDaoImpl.insertMemo(new Memo(contents));
					et_memo.setText("");
					
					InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(et_memo.getWindowToken(), 0);
					refreshListItem();
					adapter.notifyDataSetChanged();
				}
				
				break;

			}
		}
	};

	private void refreshListItem() {
		// TODO Auto-generated method stub
		List<Memo>  memoItem = memoDaoImpl.getMemoList();
		listItem =new ArrayList<String>();
		
		for( Memo m: memoItem){
			listItem.add(m.getContents());
		}
		
		Collections.reverse(listItem);
		adapter =new ArrayAdapter<String>(this, R.layout.list_item_checkable,R.id.text,listItem);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.orm_lite, menu);
		return true;
	}

}

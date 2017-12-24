package me.iamhere.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
{

	ListView mListView;
	FloatingActionButton mAddTask;
	Database mTasksDB;
	TaskAdapter mAdapter;
	Context mContext;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext 	= getApplicationContext();
        mListView 	= findViewById(R.id.lvTask);
        mAddTask 	= findViewById(R.id.addToDo);
        mTasksDB 	= Database.getInstance(mContext);

        //set adapter to ListView
        mAdapter 	= new TaskAdapter(this, mTasksDB.getCursor(), true);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(findViewById(R.id.empty_list_view));

        //Click Listener for FloatingActionButton to add new task
        mAddTask.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if(Utils.toast!=null)
					Utils.toast.cancel();
				showAddTaskDialog();
			}
		});
    }
	//shows Alert Dialog to take input/task name from user
	public void showAddTaskDialog()
	{
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
		LayoutInflater inflater 		  = this.getLayoutInflater();
		final View dialogView 			  = inflater.inflate(R.layout.custom_dialog, null);
		final EditText edt 				  = dialogView.findViewById(R.id.task_name);

		dialogBuilder.setView(dialogView);
		dialogBuilder.setTitle("Add Task");
		dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton)
			{
				String title = edt.getText().toString();
				if(title.length()>0)
				{
					//add task in Database
					mTasksDB.addTask(title);
					//notify ListView adapter for data change
					mAdapter.changeCursor(mTasksDB.getCursor());

					Utils.showToast(title+" added to ToDo list", MainActivity.this);
				}
				else
				{
					edt.setError("Error");
				}
			}
		});
		dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton)
			{
				//pass
			}
		});
		AlertDialog b = dialogBuilder.create();
		b.show();
	}
}

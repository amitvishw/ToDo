package me.iamhere.todo;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


//Adapter class for ListView
public class TaskAdapter extends CursorAdapter
{
    private Database mTasksDB;

    TaskAdapter(Context context, Cursor c, boolean autoRequery)
    {
        super(context, c, autoRequery);
        mTasksDB      = Database.getInstance(context);
    }

        @Override
        public void bindView(final View view, final Context context, Cursor cursor)
        {
            int id              = cursor.getInt(cursor.getColumnIndex(Database.getColumnId()));
            String task         = cursor.getString(cursor.getColumnIndex(Database.getColumnTask()));
            TextView tvTitle    = view.findViewById(R.id.task_item);
            ImageView bDelete   = view.findViewById(R.id.delete_button);

            //Click Listener to remove sinlge row form list
            bDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    ViewGroup parent = (ViewGroup) v.getParent();
                    removeListItem(view, parent, context);
                }
            });

            tvTitle.setText(task);
            view.setTag(id);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup vg)
        {
            return LayoutInflater.from(context).inflate(R.layout.list_item, null);
        }
    //delete task from database and list
    private void removeListItem(final View row, final ViewGroup parent, Context context)
    {
        TextView textView         = row.findViewById(R.id.task_item);
        String title              = (String) textView.getText();
        final Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
        mTasksDB.deleteTask((Integer)parent.getTag());
        row.startAnimation(animation);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                animation.cancel();
                changeCursor(mTasksDB.getCursor());
            }
        },300);
        Utils.showToast(title+" deleted from ToDo list", context);
    }
}

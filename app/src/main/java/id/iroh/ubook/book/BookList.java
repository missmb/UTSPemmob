package id.iroh.ubook.book;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import id.iroh.ubook.AddActivity;
import id.iroh.ubook.MainActivity;
import id.iroh.ubook.R;

public class BookList extends AppCompatActivity {
    View view;
    private RecyclerView recyclerView;
    ArrayList<Book> list;
    BookListAdapter adapter = null;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

//        if (searchManager != null) {
//            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//            searchView.setQueryHint(getResources().getString(R.string.search_hint));
//            searchView.setSubmitButtonEnabled(true);
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    Toast.makeText(BookList.this, query, Toast.LENGTH_SHORT).show();
//                    // return true;
//                    searchContact(query);
//                    return false;
//                }
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    Toast.makeText(BookList.this, newText, Toast.LENGTH_SHORT).show();
//                    // return true;
//                    searchContact(newText);
//                    return false;
//                }
//            });
//        }
        return true;
        // return super.onCreateOptionsMenu(menu);
    }
    private void searchContact(String keyword) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list_activity);

        recyclerView = (RecyclerView) findViewById(R.id.gridView);
        list = new ArrayList<>();
    data();
    whenWeClick();

    }

    private void data(){
        Cursor cursor = AddActivity.sqLiteHelper.getData("SELECT * FROM BOOK");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String author = cursor.getString(2);
            String description = cursor.getString(3);
            byte[] image = cursor.getBlob(4);

            list.add(new Book(title, author , description, image, id));
        }

        adapter = new BookListAdapter(getApplicationContext(), list);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void whenWeClick(){
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
             //   Toast.makeText(BookList.this, "Single Click on position :"+(position+1), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, final int position) {
                Toast.makeText(BookList.this, "Long press on position :"+(position+1), Toast.LENGTH_LONG).show();



                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(BookList.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            Cursor c =  AddActivity.sqLiteHelper.getData("SELECT id FROM BOOK");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                            showDialogUpdate(BookList.this, arrID.get(position));

                        } else if (item == 1) {
                            // delete
                            Cursor c =  AddActivity.sqLiteHelper.getData("SELECT id FROM BOOK");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();
            }}));
    }
    ImageView imageViewBook;

        private void showDialogUpdate(Activity activity, final int position){

            final Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.update_book_activity);
            dialog.setTitle("Update");

            imageViewBook = (ImageView) dialog.findViewById(R.id.imageViewBook);
            final EditText edtTitle = (EditText) dialog.findViewById(R.id.edtTitle);
            final EditText edtAuthor = (EditText) dialog.findViewById(R.id.edtAuthor);
            final EditText edtDesccription = (EditText) dialog.findViewById(R.id.edtDescription);
            Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);

            // set width for dialog
            int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
            // set height for dialog
            int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 1.0);
            dialog.getWindow().setLayout(width, height);
            dialog.show();

            imageViewBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // request photo library
                    ActivityCompat.requestPermissions(
                            BookList.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            888
                    );
                }
            });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        AddActivity.sqLiteHelper.updateData(
                                edtTitle.getText().toString().trim(),
                                edtAuthor.getText().toString().trim(),
                                edtDesccription.getText().toString().trim(),
                                AddActivity.imageViewToByte(imageViewBook),
                                position
                        );
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Update successfully!!!",Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception error) {
                        Log.e("Update error", error.getMessage());
                    }
                    updateBookList();
                }
            });
        }

        private void showDialogDelete(final int idBook){
            final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(BookList.this);

            dialogDelete.setTitle("Warning!!");
            dialogDelete.setMessage("Are you sure you want to this delete?");
            dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        AddActivity.sqLiteHelper.deleteData(idBook);
                        Toast.makeText(getApplicationContext(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        Log.e("error", e.getMessage());
                    }
                    updateBookList();
                }
            });

            dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialogDelete.show();
        }


        private void updateBookList(){
            // get all data from sqlite
            Cursor cursor = AddActivity.sqLiteHelper.getData("SELECT * FROM BOOK");
            list.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String author = cursor.getString(2);
                String description = cursor.getString(3);
                byte[] image = cursor.getBlob(4);

                list.add(new Book(title, author, description, image, id));
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

            if(requestCode == 888){
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, 888);
                }
                else {
                    Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if(requestCode == 888 && resultCode == RESULT_OK && data != null){
                Uri uri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imageViewBook.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            super.onActivityResult(requestCode, resultCode, data);
        }

    public void onActionHome (MenuItem mi){
        Intent intent = new Intent(BookList.this, MainActivity.class);
        startActivity(intent);
    }

    public void onActionAdd (MenuItem mi){
        Intent intent = new Intent(BookList.this, AddActivity.class);
        startActivity(intent);
    }


    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


}

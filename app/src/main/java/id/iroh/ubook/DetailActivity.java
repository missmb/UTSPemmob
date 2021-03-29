package id.iroh.ubook;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import id.iroh.ubook.book.Book;
import id.iroh.ubook.book.BookList;
import id.iroh.ubook.book.BookListAdapter;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_BOOK = "extra_book";
    public static final String EXTRA_IMAGE = "extra_image";
    private TextView title, author, description;
    private Integer id;
    private ImageView imgBook;
    private Book book;
    ArrayList<Book> list;
    BookListAdapter adapter = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_book);
        initialisation();
        getSupportActionBar().setTitle("Detail Book");
        book = getIntent().getParcelableExtra(EXTRA_BOOK);
        //image = getIntent().getParcelableExtra(EXTRA_IMAGE);
        //byte[] bytes = getIntent().getByteArrayExtra("BMP");
        //Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        setData();

//        if(book.getImage()==null){
////            Toast.makeText(this, "image parcelable is null ", Toast.LENGTH_SHORT).show();
////        }

        TextView btnLIdah= findViewById(R.id.location);
        btnLIdah.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo: -7.323891, 112.735475");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }

    private void setData() {

        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        description.setText(book.getDescription());
detail(1);
//        imgBook.setImageBitmap();
       // DetailActivity.setImageViewWithByteArray(imgBook,);
        byte[] bytes = getIntent().getByteArrayExtra("BMP");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        imgBook.setImageBitmap(bmp);
        }


        //final byte[] data = image.getImage();
        //final Bitmap bmp = BitmapFactory.decodeByteArray(data,0,data.length);
//        byte[] bytes = getIntent().getByteArrayExtra("BMP");
//        Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        //imgBook.setImageBitmap(bmp);


        //final Book book = list.get(position);
//        Glide.with(holder.list_item.getContext())
//                .load(book.getImage())
//                .apply(new RequestOptions().override(200, 300))
//                .into(imgBook);
//        Glide.with(DetailActivity.this)
//                .load(image.getImage())
//                .into(imgBook);

    private void detail(final int idBook){
        try {
            AddActivity.sqLiteHelper.detailData(idBook);
            //Toast.makeText(getApplicationContext(), "Detail successfully!!!",Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Log.e("error", e.getMessage());
        }

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

//        adapter = new BookListAdapter(getApplicationContext(), list);
//        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public  static void setImageViewWithByteArray(ImageView view, byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        //ImageView imageView = new ImageView(DetailActivity.this);
        view.setImageBitmap(bitmap);
    }

    private void initialisation() {
        title = findViewById(R.id.titleDet);
        author = findViewById(R.id.authorDet);
        description = findViewById(R.id.descriptionDet);
        imgBook = findViewById(R.id.imageViewDet);
    }

}

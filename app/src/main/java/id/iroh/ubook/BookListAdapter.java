package id.iroh.ubook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.MyViewHolder>{
    private Context mContext;
    private List<Book> mBook;

    public BookListAdapter(Context mContext, List<Book> mBook) {
        this.mContext = mContext;
        this.mBook = mBook;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.book_item, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {
        holder.title.setText(mBook.get(position).getTitle());
        holder.author.setText(mBook.get(position).getAuthor());
        holder.description.setText(mBook.get(position).getDescription());
        final Book book = mBook.get(position);
        Glide.with(holder.list_item.getContext())
                .load(book.getImage())
                .apply(new RequestOptions().override(200, 300))
                .into(holder.img_book);
        holder.list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent detail = new Intent(context, DetailActivity.class);
                detail.putExtra(DetailActivity.EXTRA_BOOK, mBook.get(position));
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBook.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout list_item;
        private TextView title, author, description;
        private ImageView img_book;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            list_item = itemView.findViewById(R.id.list_item);
            title = itemView.findViewById(R.id.bookTitle);
            author = itemView.findViewById(R.id.edtAuthor);
            description = itemView.findViewById(R.id.edtDescription);
            img_book = itemView.findViewById(R.id.imgBook);
        }
    }
}

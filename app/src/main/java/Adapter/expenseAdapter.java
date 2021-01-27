package Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.Models.Note;
import com.example.expensemanager.R;
import com.example.expensemanager.UI.UpdateExpenseActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class expenseAdapter extends FirestoreRecyclerAdapter<Note,expenseAdapter.expenseHolder> {
    public expenseAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull expenseHolder holder, int position, @NonNull Note model) {

        holder.textViewTitle.setText(model.getTitle());
        holder.textViewDescription.setText(model.getDescription());
        holder.textViewAmount.setText(model.getAmount());
        holder.textViewDate.setText(model.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            String docId = getSnapshots().getSnapshot(position).getId();
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), UpdateExpenseActivity.class);
                i.putExtra("title",model.getTitle());
                i.putExtra("description",model.getDescription());
                i.putExtra("date",model.getDate());
                i.putExtra("amount",model.getAmount());
                i.putExtra("noteId",docId);
                v.getContext().startActivity(i);
            }
        });
    }

    @NonNull
    @Override
    public expenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item,
                parent, false);
        return new expenseHolder(v);
    }
    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class expenseHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle,textViewDescription,textViewAmount,textViewDate;
        public expenseHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription =itemView.findViewById(R.id.text_view_description);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);
            textViewDate = itemView.findViewById(R.id.text_view_date);
        }
    }
}

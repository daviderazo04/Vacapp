package Clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacapp.R;

import java.util.List;

public class VacaAdapter extends RecyclerView.Adapter<VacaAdapter.VacaViewHolder> {
    private Context context;
    private List<Vaca> vacaList;

    public VacaAdapter(Context context, List<Vaca> vacaList) {
        this.context = context;
        this.vacaList = vacaList;
    }

    @NonNull
    @Override
    public VacaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_vaca, parent, false);
        return new VacaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VacaViewHolder holder, int position) {
        Vaca vaca = vacaList.get(position);

        holder.textId.setText(vaca.getCodigo());
        holder.textRaza.setText(vaca.getRaza());
        holder.textPeso.setText(String.valueOf(vaca.getPeso()));
        holder.textEdad.setText(String.valueOf(vaca.getEdad()));
        holder.textEstablo.setText(String.valueOf(vaca.getEstablo()));
    }

    @Override
    public int getItemCount() {
        return vacaList.size();
    }

    public static class VacaViewHolder extends RecyclerView.ViewHolder {
        TextView textId, textRaza, textPeso, textEdad, textEstablo;

        public VacaViewHolder(@NonNull View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.textId);
            textRaza = itemView.findViewById(R.id.textRaza);
            textPeso = itemView.findViewById(R.id.textPeso);
            textEdad = itemView.findViewById(R.id.textEdad);
            textEstablo = itemView.findViewById(R.id.textEstablo);
        }
    }
}

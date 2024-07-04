package Clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacapp.AdminSQLiteOpenHelper;
import com.example.vacapp.R;

import java.util.List;

public class EstabloAdapter extends RecyclerView.Adapter<EstabloAdapter.EstabloViewHolder> {
    private Context context;
    private List<Establo> establoList;
    private AdminSQLiteOpenHelper admin;
    public EstabloAdapter(Context context, List<Establo> establoList) {
        this.context = context;
        this.establoList = establoList;
    }

    @NonNull
    @Override
    public EstabloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_establo, parent, false);
        return new EstabloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EstabloViewHolder holder, int position) {
        Establo establo = establoList.get(position);

        holder.textId.setText(establo.getCodigo());
        holder.textAncho.setText(String.valueOf(establo.getAncho()));
        holder.textLargo.setText(String.valueOf(establo.getAncho()));
        holder.textVacas.setText(String.valueOf(admin.obtenerNumeroVacasEstablo(establo.getCodigo())));
    }

    @Override
    public int getItemCount() {
        return establoList.size();
    }

    public static class EstabloViewHolder extends RecyclerView.ViewHolder {
        TextView textId, textAncho, textLargo, textVacas;

        public EstabloViewHolder(@NonNull View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.textId);
            textAncho = itemView.findViewById(R.id.textAncho);
            textLargo = itemView.findViewById(R.id.textLargo);
            textVacas = itemView.findViewById(R.id.textVacas);
        }
    }
}

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

public class ProduccionAdapter extends RecyclerView.Adapter<ProduccionAdapter.ProduccionViewHolder> {

    private Context context;
    private List<Produccion> produccionList;

    public ProduccionAdapter(Context context, List<Produccion> produccionList) {
        this.context = context;
        this.produccionList = produccionList;
    }

    @NonNull
    @Override
    public ProduccionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_produccion, parent, false);
        return new ProduccionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProduccionViewHolder holder, int position) {
        Produccion produccion = produccionList.get(position);
        holder.bind(produccion);
    }

    @Override
    public int getItemCount() {
        return produccionList.size();
    }

    public class ProduccionViewHolder extends RecyclerView.ViewHolder {

        private TextView codigoProduccionTextView;
        private TextView codigoVacaTextView;
        private TextView fechaTextView;
        private TextView litrosTextView;

        public ProduccionViewHolder(@NonNull View itemView) {
            super(itemView);
            codigoProduccionTextView = itemView.findViewById(R.id.codigoProduccionTextView);
            codigoVacaTextView = itemView.findViewById(R.id.codigoVacaTextView);
            fechaTextView = itemView.findViewById(R.id.fechaTextView);
            litrosTextView = itemView.findViewById(R.id.litrosTextView);
        }

        public void bind(Produccion produccion) {
            codigoProduccionTextView.setText("Código de producción: " + produccion.getCodigoProduccion());
            codigoVacaTextView.setText("Código de vaca: " + produccion.getCodigoVaca());
            fechaTextView.setText("Fecha: " + produccion.getFecha());
            litrosTextView.setText("Litros: " + String.valueOf(produccion.getLitros()));
        }
    }
}

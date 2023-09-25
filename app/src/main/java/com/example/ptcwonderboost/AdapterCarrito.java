package com.example.ptcwonderboost;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class AdapterCarrito extends RecyclerView.Adapter<AdapterCarrito.MyViewHolder>{

    private Context context;
    private List<Carrito> dataList;

    public AdapterCarrito(Context context, List<Carrito> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.carrito_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Carrito data = dataList.get(position);
        // Convertir el arreglo de bytes (foto) en un Drawable
        byte[] fotoBytes = data.getFoto();
        if (fotoBytes != null && fotoBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
            Drawable fotoDrawable = new BitmapDrawable(context.getResources(), bitmap);
            holder.imgFotoProducto.setImageDrawable(fotoDrawable);
        } else {
            // Establece una imagen predeterminada en caso de que no haya foto
            holder.imgFotoProducto.setImageResource(R.drawable.productos1);
        }
        holder.textViewNombre.setText(data.getNombre());
        holder.textViewPrecio.setText(data.getPrecio().toString());
        holder.textViewCategoria.setText(data.getCategoria());
        holder.textViewEstado.setText(data.getEstado());
        // Configura los otros TextViews aquí para estado, categoría, ID y precio
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFotoProducto;
        TextView textViewNombre;
        TextView textViewPrecio;
        TextView textViewCategoria;
        TextView textViewEstado;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFotoProducto = itemView.findViewById(R.id.imgFotoProducto);
            textViewNombre = itemView.findViewById(R.id.txtNmbProducto);
            textViewPrecio = itemView.findViewById(R.id.PrecioCarr);
            textViewCategoria = itemView.findViewById(R.id.categoriaCarrito);
            textViewEstado = itemView.findViewById(R.id.EstadoProductoCarrito);
        }
    }
}

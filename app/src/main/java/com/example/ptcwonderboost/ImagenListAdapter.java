package com.example.ptcwonderboost;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;

public class ImagenListAdapter extends BaseAdapter {

    private Context context;
    private List<Producto> productos;

    public ImagenListAdapter(Context context, List<Producto> productos) {
        this.context = context;
        this.productos = productos;
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int position) {
        return productos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        }

        ImageView imgFotoProducto = convertView.findViewById(R.id.imgFotoProducto);
        TextView txtNombreProducto = convertView.findViewById(R.id.txtNombreProducto);
        TextView txtPrecio = convertView.findViewById(R.id.PrecioCat);
        TextView txtEstado = convertView.findViewById(R.id.EstadoProductoCat);
        TextView txtTipo = convertView.findViewById(R.id.TipoPrecioCat);
        TextView txtCantidad = convertView.findViewById(R.id.CantidadCat);
        TextView txtVendedor = convertView.findViewById(R.id.Vendedor);
        ImageButton btnNegociacionesCate = convertView.findViewById(R.id.btnNegociacionesCat);
        ImageButton btnCarroCate = convertView.findViewById(R.id.btnCarroCat);
        Producto producto = productos.get(position);
        byte[] fotoBytes = producto.getFotoProducto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
        imgFotoProducto.setImageBitmap(bitmap);
        txtNombreProducto.setText(producto.getProducto());
        double precio = producto.getPrecio();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        String formattedPrecio = currencyFormatter.format(precio);
        txtPrecio.setText(formattedPrecio);
        txtEstado.setText(producto.getEstadoProducto());
        txtTipo.setText(producto.getTipoPrecio());
        txtCantidad.setText(String.valueOf(producto.getCantidad()));
        txtVendedor.setText(producto.getVendedor());



        btnNegociacionesCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Se ha ingresado el producto a negociaciones", Toast.LENGTH_SHORT);
            }
        });

        btnCarroCate.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               Toast.makeText(v.getContext(), "Se ha ingresado el producto a carrito de compras", Toast.LENGTH_SHORT);
            }
       });
        return convertView;
    }
}

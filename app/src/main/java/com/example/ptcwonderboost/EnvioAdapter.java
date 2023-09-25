package com.example.ptcwonderboost;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

public class EnvioAdapter extends BaseAdapter {

    private Context context;
    private List<Envio> envio;

    public EnvioAdapter(Context context, List<Envio> envio) {
        this.context = context;
        this.envio = envio;
    }

    @Override
    public int getCount() {
        return envio.size();
    }

    @Override
    public Object getItem(int position) {
        return envio.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_negociaciones, parent, false);
        }
        TextView txtIDNegociacion = convertView.findViewById(R.id.idNegociacion);
        ImageView imgFoto = convertView.findViewById(R.id.imgFotoNegociaciones);
        TextView txtNombre = convertView.findViewById(R.id.txtNombreProductoServicio);
        TextView txtPrecioOri = convertView.findViewById(R.id.PrecioOriginal);
        TextView txtPrecioOfr = convertView.findViewById(R.id.PrecioOfrecido);
        TextView txtPrecioVen = convertView.findViewById(R.id.PrecioVenta);
        TextView txtVendedor = convertView.findViewById(R.id.VendedorNego);
        Envio producto = envio.get(position);
        txtIDNegociacion.setText(String.valueOf(producto.getIdNegociacion()));
        byte[] fotoBytes = producto.getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
        imgFoto.setImageBitmap(bitmap);
        txtNombre.setText(producto.getNombre());
        double precioOrig = producto.getPrecioOriginal();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        String formattedPrecio = currencyFormatter.format(precioOrig);
        txtPrecioOri.setText(formattedPrecio);
        double precioOfre = producto.getPrecioOfrecido();
        NumberFormat currencyFormatter2 = NumberFormat.getCurrencyInstance();
        String formattedPrecio2 = currencyFormatter2.format(precioOfre);
        txtPrecioOfr.setText(formattedPrecio2);
        double precioVen = producto.getPrecioVenta();
        NumberFormat currencyFormatter3 = NumberFormat.getCurrencyInstance();
        String formattedPrecio3 = currencyFormatter3.format(precioVen);
        txtPrecioVen.setText(formattedPrecio3);
        txtVendedor.setText(producto.getVendedor());
        return convertView;
    }
}
package com.example.ptcwonderboost;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class CommentAdapter extends ArrayAdapter<Comment> {
    private Context mContext;
    private int mResource;

    public CommentAdapter(Context context, int resource, List<Comment> comments) {
        super(context, resource, comments);
        mContext = context;
        mResource = resource;
    }

    private Bitmap getRoundedBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int size = Math.min(width, height);

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // Calcula el radio del círculo, que es la mitad del tamaño más pequeño (ancho o alto)
        float radius = size / 2f;

        // Calcula el centro del círculo (coordenadas x e y)
        float centerX = width / 2f;
        float centerY = height / 2f;

        // Dibuja el círculo redondeado sin recortar la imagen original
        canvas.drawCircle(centerX, centerY, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // Dibuja la imagen original sobre el círculo
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return output;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        ImageView authorImageView = convertView.findViewById(R.id.authorImageView);
        TextView authorTextView = convertView.findViewById(R.id.authorTextView);
        RatingBar ratingBar = convertView.findViewById(R.id.ratingBarCom);
        TextView opinionTextView = convertView.findViewById(R.id.opinionTextView);

        Comment comment = getItem(position);

        if (comment != null) {
            // Convierte el arreglo de bytes en una imagen y configúrala en el ImageView
            if (comment.getAuthorImage() != null && comment.getAuthorImage().length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(comment.getAuthorImage(), 0, comment.getAuthorImage().length);
                Bitmap roundedBitmap = getRoundedBitmap(bitmap);
                authorImageView.setImageBitmap(roundedBitmap);
            } else {
                // Si no hay datos de imagen, puedes configurar una imagen de marcador de posición
                authorImageView.setImageResource(R.drawable.person_24);
            }
            authorTextView.setText(comment.getAuthorName());
            ratingBar.setRating(comment.getRating());
            opinionTextView.setText(comment.getOpinion());
        }

        return convertView;
    }
}

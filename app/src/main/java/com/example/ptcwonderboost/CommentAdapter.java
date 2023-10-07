package com.example.ptcwonderboost;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
                authorImageView.setImageBitmap(bitmap);
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

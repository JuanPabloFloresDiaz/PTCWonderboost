package com.example.ptcwonderboost;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class Servicio_de_Notificaciones extends Service {

    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Aquí debes implementar la lógica para mostrar notificaciones cuando se acepte o rechace un permiso.
        // Puedes utilizar la clase NotificationCompat.Builder para crear notificaciones.

        // Ejemplo de creación de una notificación:
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "canal_id")
                .setSmallIcon(R.drawable.logowonderboost)
                .setContentTitle("Permiso Aceptado")
                .setContentText("Tu permiso ha sido aceptado.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, builder.build());

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

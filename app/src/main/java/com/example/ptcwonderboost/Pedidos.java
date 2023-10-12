package com.example.ptcwonderboost;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;

public class Pedidos extends AppCompatActivity {
    ListView lista;
    private int idSeleccionado;
    private int idP;
    private BigDecimal PRECIO;
    private String correo;
    final void AceptarPedido(){
        try{
            Pedido ocu = new Pedido();
            ocu.setPrecioventa(PRECIO);
            ocu.setIdNegociacion(idSeleccionado);
            int valor = ocu.AceptarPedido();
            if(valor == 1){
                CargarLista();
                Toast.makeText(this,"Se acepto el pedido", Toast.LENGTH_SHORT ).show();
                try {
                    Correo crec = new Correo();
                    crec.setId(idSeleccionado);
                    ResultSet rss = crec.CapturaIDdeNegociacion();
                    while (rss.next()){
                        idP = rss.getInt("idPersonas");
                    }
                    crec.setId(idP);
                    ResultSet rs = crec.NotificarCorreo();
                    while(rs.next()){
                        // Obtener el correo del resultado
                        correo = rs.getString("Correo");
                    }
                    String mensaje =" Tu pedido fue aceptado por el monto de: $" + PRECIO;
                    EmailUtils.sendEmailOld(correo, "Pedido aceptado", mensaje);
                } catch (Exception ex) {
                    Toast.makeText(this,"Error: "+ ex, Toast.LENGTH_SHORT).show();
                }
            }
        }catch(Exception ex){
            System.out.println("Error: " + ex);
        }
    }
    final void RechazarPedido(){
        try{
            Pedido ocu = new Pedido();
            ocu.setIdNegociacion(idSeleccionado);
            int valor = ocu.RechazarPedido();
            if(valor == 1){
                CargarLista();
                Toast.makeText(this,"Se rechazo el pedido", Toast.LENGTH_SHORT ).show();
                try {
                    Correo crec = new Correo();
                    crec.setId(idSeleccionado);
                    ResultSet rss = crec.CapturaIDdeNegociacion();
                    while (rss.next()){
                        idP = rss.getInt("idPersonas");
                    }
                    crec.setId(idP);
                    ResultSet rs = crec.NotificarCorreo();
                    while(rs.next()){
                        // Obtener el correo del resultado
                        correo = rs.getString("Correo");
                    }
                    String mensaje =" Tu pedido fue rechazado";
                    EmailUtils.sendEmailOld(correo, "Pedido rechazado", mensaje);
                } catch (Exception ex) {
                    Toast.makeText(this,"Error: "+ ex, Toast.LENGTH_SHORT).show();
                }
            }
        }catch(Exception ex){
            System.out.println("Error: " + ex);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        CargarLista();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    // Obtén el id de negociación seleccionado
                    Negociaciones NegociacionSeleccionado = (Negociaciones) parent.getItemAtPosition(position);
                    // Guarda el valor del campo "id" en la variable idSeleccionado
                    idSeleccionado = NegociacionSeleccionado.getIdNegociacion();
                    PRECIO = BigDecimal.valueOf(NegociacionSeleccionado.getPrecioOfrecido());
                    Toast.makeText(Pedidos.this, "id: " + idSeleccionado, Toast.LENGTH_LONG).show();
                }catch (Exception ex){
                    Toast.makeText(Pedidos.this, "ERROR: " + ex, Toast.LENGTH_LONG).show();
                }
                // Aquí puedes mostrar un AlertDialog con opciones
                mostrarDialogoDeOpciones();
            }
        });
    }

    private void mostrarDialogoDeOpciones() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una opción")
                .setItems(new CharSequence[]{"Aceptar el pedido", "Rechazar pedido"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Aquí puedes manejar la selección del usuario
                        switch (which) {
                            case 0:
                                AceptarPedido();
                                break;
                            case 1:
                                RechazarPedido();
                                break;
                        }
                    }
                })
                .create()
                .show();
    }

    public final void CargarLista(){
        try {
            lista = findViewById(R.id.listviewPed);
            Negociaciones negociacion = new Negociaciones();
            List<Negociaciones> negocio = negociacion.CargarPedidos(VariablesGlobales.getIdPersona());

            if (negocio != null) {
                CartAdapter adapter = new CartAdapter(this, negocio);
                lista.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No se ha podido cargar la tabla", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error desconocido: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
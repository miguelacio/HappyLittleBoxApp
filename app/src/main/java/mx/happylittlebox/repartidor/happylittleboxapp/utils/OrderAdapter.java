package mx.happylittlebox.repartidor.happylittleboxapp.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mx.happylittlebox.repartidor.happylittleboxapp.R;
import mx.happylittlebox.repartidor.happylittleboxapp.models.Order;

/**
 * Created by miguelacio on 17/10/17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private OrderCallBacks orderCallBacks;
    private ArrayList<Order> orderArrayList;

    public static interface OrderCallBacks {
        public void onOrderSelected(Order order);
    }

    public OrderAdapter(Context context, ArrayList<Order> orderArrayList) {
        this.context = context;
        this.orderCallBacks = (OrderCallBacks) context;
        this.orderArrayList = orderArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_venta, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Order order = orderArrayList.get(position);
        holder.textViewEstado.setText(order.getStatus());
        holder.textViewFechaHora.setText(order.getFecha_venta());
        holder.textViewNombrePedido1.setText(order.getProductArrayList().get(0).getName());
        holder.textViewNumeroPedido1.setText(order.getProductArrayList().get(0).getCantidad() + " ");
        int sizeProducts = order.getProductArrayList().size();
        if ( sizeProducts > 1) {
            holder.textViewNombrePedido2.setText(order.getProductArrayList().get(1).getName());
            holder.textViewNumeroPedido2.setText(order.getProductArrayList().get(1).getCantidad() + " ");
            if (sizeProducts != 2) {
                if (sizeProducts > 2) {
                    holder.textViewNombrePedido3.setText("...");
                } else {
                    holder.textViewNombrePedido2.setText(order.getProductArrayList().get(3).getName());
                    holder.textViewNumeroPedido2.setText(order.getProductArrayList().get(3).getCantidad() + " ");
                }
            }
        }
        holder.clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderCallBacks.onOrderSelected(order);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNumeroPedido1,
                textViewNumeroPedido2,
                textViewNumeroPedido3,
                textViewNombrePedido1,
                textViewNombrePedido2,
                textViewNombrePedido3,
                textViewFechaHora,
                textViewEstado;
        View clickView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewNombrePedido1 = itemView.findViewById(R.id.text_view_name_pedido_1);
            this.textViewNombrePedido2 = itemView.findViewById(R.id.text_view_name_pedido_2);
            this.textViewNombrePedido3 = itemView.findViewById(R.id.text_view_name_pedido_3);
            this.textViewNumeroPedido1 = itemView.findViewById(R.id.text_view_number_pedido_1);
            this.textViewNumeroPedido2 = itemView.findViewById(R.id.text_view_number_pedido_2);
            this.textViewNumeroPedido3 = itemView.findViewById(R.id.text_view_number_pedido_3);
            this.textViewFechaHora = itemView.findViewById(R.id.text_view_fecha_hora);
            this.textViewEstado = itemView.findViewById(R.id.text_view_estado);
            this.clickView = itemView.findViewById(R.id.click_view_ruta);
        }
    }
}

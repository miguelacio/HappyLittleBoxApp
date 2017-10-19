package mx.happylittlebox.repartidor.happylittleboxapp.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mx.happylittlebox.repartidor.happylittleboxapp.R;
import mx.happylittlebox.repartidor.happylittleboxapp.models.Product;

/**
 * Created by miguelacio on 17/10/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Product> productArraylist;



    public ProductAdapter(Context context, ArrayList<Product> productArraylist) {
        this.context = context;
        this.productArraylist = productArraylist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = productArraylist.get(position);
        holder.textViewProduct.setText(product.getName());
        holder.textViewNumber.setText(product.getCantidad()+ " ");

    }

    @Override
    public int getItemCount() {
        return productArraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewProduct, textViewNumber;


        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewProduct = itemView.findViewById(R.id.text_view_product);
            this.textViewNumber = itemView.findViewById(R.id.text_view_number);

        }
    }
}

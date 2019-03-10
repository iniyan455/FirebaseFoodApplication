package com.user.firebasedatabase.Adapter.Producer;


import android.widget.Filter;
import android.widget.Filterable;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.user.firebasedatabase.Common.Common;
import com.user.firebasedatabase.PaymentActivity;
import com.user.firebasedatabase.Pojo.CropList;
import com.user.firebasedatabase.Pojo.ProductAddPojo;
import com.user.firebasedatabase.Pojo.ProductList;
import com.user.firebasedatabase.Pojo.WeatherForecast.WeatherForecastResult;
import com.user.firebasedatabase.R;
import com.user.firebasedatabase.UserModule.ProducerAdd;
import com.user.firebasedatabase.admin.AddCrop;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ProductViewAdapter extends RecyclerView.Adapter<ProducerViewDetails> implements Filterable {
    private Context context;
    private List<ProductList> cropLists = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference myRef;
    private List<ProductAddPojo> mFilteredList = new ArrayList<>();

    String type = "";

    public ProductViewAdapter(Context context, List<ProductList> cropLists, String type, List<ProductAddPojo> addCropPojoList) {
        this.context = context;
        this.cropLists = cropLists;
        database = FirebaseDatabase.getInstance();
        this.type = type;


        this.mFilteredList = addCropPojoList;
        myRef = database.getReference("ProductDetails");
    }

    @NonNull
    @Override
    public ProducerViewDetails onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.item_product_details, parent, false);

        return new ProducerViewDetails(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProducerViewDetails viewCropAdapter, int i) {


        int position = i;
        viewCropAdapter.cropname.setText("Crop Name: " + cropLists.get(position).getCropPojos().get(position).getCropname());
        viewCropAdapter.marketprice.setText("Market Price: " + cropLists.get(position).getCropPojos().get(position).getMarketprice());
        viewCropAdapter.scheduleprice.setText("Schedule Price: " + cropLists.get(position).getCropPojos().get(position).getScheduleprice());
        viewCropAdapter.quantity.setText("Quantity: " + cropLists.get(position).getCropPojos().get(position).getQuantity());
        viewCropAdapter.address.setText("Address: " + cropLists.get(position).getCropPojos().get(position).getAddress());


        viewCropAdapter.total.setText("Total: " + cropLists.get(position).getCropPojos().get(position).getTotalamount());
        if (cropLists.get(position).getCropPojos().get(position).getDeliveryoption().equalsIgnoreCase("false")) {
            viewCropAdapter.deliveryoption.setText("Delivery Option: Not Available");

            viewCropAdapter.selfbuy.setVisibility(View.VISIBLE);

            viewCropAdapter.cardview.setVisibility(View.VISIBLE);
        } else {
            viewCropAdapter.deliveryoption.setText("Delivery Option: Available");

            viewCropAdapter.cardview.setVisibility(View.VISIBLE);
            viewCropAdapter.selfbuy.setVisibility(View.VISIBLE);

        }
        Glide.with(context).load(cropLists.get(position).getCropPojos().get(position).getImageurl()).into(viewCropAdapter.imageView);


        if (type.equalsIgnoreCase("consumer")) {
//            viewCropAdapter.selfbuy.setVisibility(View.VISIBLE);
//            viewCropAdapter.cardview.setVisibility(View.VISIBLE);

            viewCropAdapter.selfbuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    View view = LayoutInflater.from(context).inflate(R.layout.layout_user, null);
                    MaterialEditText username = (MaterialEditText) view.findViewById(R.id.username);
                    MaterialEditText mobileno = (MaterialEditText) view.findViewById(R.id.mobileno);
                    MaterialEditText quantity = (MaterialEditText) view.findViewById(R.id.quantity);

                    Button submit = view.findViewById(R.id.submit);
                    Button cancel = view.findViewById(R.id.cancel);


                    MaterialEditText address = (MaterialEditText) view.findViewById(R.id.address);

                    MaterialEditText amount = (MaterialEditText) view.findViewById(R.id.amount);
                    username.setText(Common.Username);
                    mobileno.setText(Common.mobileno);
                    address.setText(Common.address);
                    quantity.setText(cropLists.get(position).getCropPojos().get(position).getQuantity());

                    Double calculate = Double.parseDouble(String.valueOf(cropLists.get(position).getCropPojos().get(position).getScheduleprice())) * Double.parseDouble(String.valueOf(quantity.getText().toString()));
                    amount.setText("" + calculate);
                    quantity.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (s.length() > 0) {
                                Double calculate = Double.parseDouble(String.valueOf(cropLists.get(position).getCropPojos().get(position).getScheduleprice())) * Double.parseDouble(String.valueOf(quantity.getText().toString()));
                                amount.setText("" + calculate);

                            } else amount.setText("");
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });


                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setView(view);
                    //  dialog.show();
                    AlertDialog dialog1 = dialog.show();

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (amount.getText().toString().isEmpty()) {
                                Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();

                                return;
                            }
                            dialog1.dismiss();


                            Toast.makeText(context, "You COD Is Approved We Will Contact Soon", Toast.LENGTH_SHORT).show();

                        }
                    });


                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();

                        }
                    });






                }
            });


            viewCropAdapter.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = LayoutInflater.from(context).inflate(R.layout.layout_user, null);
                    MaterialEditText username = (MaterialEditText) view.findViewById(R.id.username);
                    MaterialEditText mobileno = (MaterialEditText) view.findViewById(R.id.mobileno);
                    MaterialEditText quantity = (MaterialEditText) view.findViewById(R.id.quantity);

                    Button submit = view.findViewById(R.id.submit);
                    Button cancel = view.findViewById(R.id.cancel);


                    MaterialEditText address = (MaterialEditText) view.findViewById(R.id.address);

                    MaterialEditText amount = (MaterialEditText) view.findViewById(R.id.amount);
                    username.setText(Common.Username);
                    mobileno.setText(Common.mobileno);
                    address.setText(Common.address);
                    quantity.setText(cropLists.get(position).getCropPojos().get(position).getQuantity());

                    Double calculate = Double.parseDouble(String.valueOf(cropLists.get(position).getCropPojos().get(position).getScheduleprice())) * Double.parseDouble(String.valueOf(quantity.getText().toString()));
                    amount.setText("" + calculate);
                    quantity.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (s.length() > 0) {
                                Double calculate = Double.parseDouble(String.valueOf(cropLists.get(position).getCropPojos().get(position).getScheduleprice())) * Double.parseDouble(String.valueOf(quantity.getText().toString()));
                                amount.setText("" + calculate);

                            } else amount.setText("");
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });


                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setView(view);
                    //  dialog.show();
                    AlertDialog dialog1 = dialog.show();

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (amount.getText().toString().isEmpty()) {
                                Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();

                                return;
                            }
                            dialog1.dismiss();

                            if (cropLists.get(position).getCropPojos().get(position).getDeliveryoption().equalsIgnoreCase("false")) {
                                Toast.makeText(context, "Delivery Not Available Buy Self", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Delivery Contact You Soon ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, PaymentActivity.class);
                                intent.putExtra("amount", amount.getText().toString());
                                context.startActivity(intent);
                            }

                        }
                    });


                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();

                        }
                    });


                }
            });
        } else {
            viewCropAdapter.cardview.setVisibility(View.GONE);
            viewCropAdapter.selfbuy.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return cropLists.size();
    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
//Toast.makeText(context,"search",Toast.LENGTH_SHORT).show();
             if (charString.isEmpty()) {
                    for (ProductList cropList : cropLists) {
                        mFilteredList = cropList.getCropPojos();

                    }
                } else {

                    ArrayList<ProductAddPojo> filteredList = new ArrayList<>();

                    for (ProductList cropList : cropLists) {
                        for (ProductAddPojo productAddPojo : cropList.getCropPojos()) {
                            if (productAddPojo.getCropname().toLowerCase().contains(charString) || productAddPojo.getAddress().toLowerCase().contains(charString) || productAddPojo.getQuantity().toLowerCase().contains(charString)) {

                                filteredList.add(productAddPojo);
                            }
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<ProductAddPojo>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }

    public void addItem(ProductList list, int position) {


        Intent intent = new Intent(context, ProducerAdd.class);
        intent.putExtra("cropname", cropLists.get(position).getCropPojos().get(position).getCropname());
        intent.putExtra("marketprice", cropLists.get(position).getCropPojos().get(position).getMarketprice());
        intent.putExtra("scheduleprice", cropLists.get(position).getCropPojos().get(position).getScheduleprice());
        intent.putExtra("key", cropLists.get(position).getCropKey().get(position));
        intent.putExtra("quantity", cropLists.get(position).getCropPojos().get(position).getQuantity());
        intent.putExtra("total", cropLists.get(position).getCropPojos().get(position).getTotalamount());
        intent.putExtra("image", cropLists.get(position).getCropPojos().get(position).getImageurl());
        intent.putExtra("toggle", cropLists.get(position).getCropPojos().get(position).getDeliveryoption());


        context.startActivity(intent);


//        notifyItemInserted(cropLists.size());
    }

    public void removeItem(int position) {
        Log.e(TAG, "----------" + cropLists.get(position).getCropKey().get(position));
        myRef.child(cropLists.get(position).getCropKey().get(position)).removeValue();
        cropLists.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cropLists.size());

    }
}

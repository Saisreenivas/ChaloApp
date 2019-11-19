package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaloa.android.app.MainActivity;
import com.chaloa.android.app.MapsActivity;
import com.chaloa.android.app.R;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

import Model.MockData;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MockDataAdapter
        extends RecyclerView.Adapter<MockDataAdapter.ViewHolder>
{
    public Context context;
    public ArrayList<MockData> mockDataArrayList;


    public MockDataAdapter(Context applicationContext, ArrayList<MockData> fullData) {
        context = applicationContext;
        mockDataArrayList= fullData;

    }

    @NonNull
    @Override
    public MockDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.routes_list_item, parent, false);

        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MockDataAdapter.ViewHolder holder, int position) {
        final MockData md = mockDataArrayList.get(position);

        holder.routeName.setText(md.getRouteName());
        holder.routeStartStopNames.setText(
                md.getStopDataList().get(0).getStopName() + " - " +
                md.getStopDataList().get(md.getStopDataList().size()-1).getStopName());

        holder.routeOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(
                        new Intent(context, MapsActivity.class)
                        .putExtra("Mock", (new Gson()).toJson(md)));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mockDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView routeName, routeStartStopNames;
        public LinearLayout routeOptions;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            routeName = (TextView) itemView.findViewById(R.id.route_name);
            routeStartStopNames = (TextView) itemView.findViewById(R.id.route_start_stop);
            routeOptions = (LinearLayout) itemView.findViewById(R.id.route_options);

        }
    }
}

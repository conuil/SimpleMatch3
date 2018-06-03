package emmet.com.draggrid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();
    private MainActivity.MyDragListener dragListener;
    private int maxHeight;
    private int maxWidth;

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList data, MainActivity.MyDragListener dragListener) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.dragListener = dragListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ImageItem item = (ImageItem)data.get(position);
        ((ImageView)row.findViewById(R.id.image)).setOnDragListener(this.dragListener);
        ((ImageView)row.findViewById(R.id.image)).setTag(null);
        holder.image.setImageBitmap(item.getImage());
        holder.image.setMaxHeight(maxHeight);
        holder.image.setMaxWidth(maxWidth);

        return row;
    }

    public void setMaxHeight(int maxHgt){
        this.maxHeight= maxHgt;
    }

    public void setMaxWidth(int maxWdt){
        this.maxWidth = maxWdt;
    }

    static class ViewHolder {
        ImageView image;
    }
}
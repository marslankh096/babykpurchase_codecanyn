package com.own.babykickcounter.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.own.babykickcounter.R;
import com.own.babykickcounter.model.TrackModel;
import com.own.babykickcounter.util.Constants;
import com.own.babykickcounter.util.DateTimeFormatUtil;
import com.own.babykickcounter.util.LogUtil;

import org.joda.time.DateTimeConstants;

import java.util.ArrayList;
import java.util.Date;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {
    private final String tagg = getClass().getSimpleName();

     Listener mListener;
    private ArrayList<TrackModel> mTrackList = new ArrayList<>();

    public interface Listener {
        void onDelete(TrackModel trackModel);

        void onItemclick(TrackModel trackModel);
    }

    public TrackAdapter(ArrayList<TrackModel> arrayList, Listener listener) {
        String str = this.tagg;
        LogUtil.i(str, "tracks_size: " + arrayList.size());
        this.mTrackList = arrayList;
        this.mListener = listener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_track, viewGroup, false));


    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(this.mTrackList.get(i));

    }

    public int getItemCount() {
        return this.mTrackList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView btnDelete;
        TextView tvDate;
        TextView tvDuration;
        TextView tvKicks;
        TextView tvStart;

        public ViewHolder(View view) {
            super(view);

            btnDelete = (ImageView) view.findViewById(R.id.btn_delete);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
            tvDuration = (TextView) view.findViewById(R.id.tv_duration);
            tvKicks = (TextView) view.findViewById(R.id.tv_kicks);
            tvStart = (TextView) view.findViewById(R.id.tv_start);

        }

        public void bind(final TrackModel trackModel) {
            this.btnDelete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    TrackAdapter.this.mListener.onDelete(trackModel);
                }
            });
            Date parse = DateTimeFormatUtil.getInstance().parse(trackModel.getStart(), Constants.TIMEFORMAT);
            Date parse2 = DateTimeFormatUtil.getInstance().parse(trackModel.getEnd(), Constants.TIMEFORMAT);
            this.tvDate.setText(DateTimeFormatUtil.getInstance().format(parse, "dd MMM yy"));
            this.tvStart.setText(DateTimeFormatUtil.getInstance().format(parse, "HH:mm"));
            int time = (int) ((parse2.getTime() - parse.getTime()) / 1000);
            if (time < 60) {
                TextView textView = this.tvDuration;
                textView.setText(time + " s");
            } else if (time < 3600) {
                TextView textView2 = this.tvDuration;
                textView2.setText((time / 60) + "m " + (time % 60) + "s");
            } else {
                int i = time / DateTimeConstants.SECONDS_PER_HOUR;
                TextView textView3 = this.tvDuration;
                textView3.setText(i + "h " + ((time - (i * DateTimeConstants.SECONDS_PER_HOUR)) / 60) + "m");
            }
            TextView textView4 = this.tvKicks;
            textView4.setText("" + trackModel.getKicks());
        }
    }
}

package com.example.shh;

import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.materialize.holder.StringHolder;

import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationModel extends AbstractItem<NotificationModel, NotificationModel.ViewHolder> {
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public NotificationModel(String message){
        this.message = message;
    }

    public NotificationModel(){}

    @NonNull
    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.framelayoutparent;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.card;
    }


    protected static class ViewHolder extends FastAdapter.ViewHolder<NotificationModel> {
        @BindView(R.id.text_card)
        TextView message;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void bindView(NotificationModel item, List<Object> payloads) {
            StringHolder.applyTo(new StringHolder(item.message), message);
        }

        @Override
        public void unbindView(NotificationModel item) {
            message.setText(null);
        }
    }
}

package aleks.sunshineapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Aleksreal7 on 12/22/16.
 */

public class ForecastAdapter extends CursorAdapter {

    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;

    private boolean mUseTodayLayout = true;

    public static class ViewHolder {
        public final ImageView iconView;
        public final TextView dateView;
        public final TextView descriptionView;
        public final TextView highTempView;
        public final TextView lowTempView;

        public ViewHolder(View view) {
            iconView = (ImageView) view.findViewById(R.id.list_item_icon);
            dateView = (TextView) view.findViewById(R.id.list_item_date_textview);
            descriptionView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
            highTempView = (TextView) view.findViewById(R.id.list_item_high_textview);
            lowTempView = (TextView) view.findViewById(R.id.list_item_low_textview);
        }
    }

    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1;
        switch (viewType) {
            case VIEW_TYPE_TODAY: {
                layoutId = R.layout.list_item_forecast_today;
                break;
            }
            case VIEW_TYPE_FUTURE_DAY: {
                layoutId = R.layout.list_item_forecast;
                break;
            }
        }

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        int viewType = getItemViewType(cursor.getPosition());
        int weatherId = cursor.getInt(ForecastFragment.COL_WEATHER_CONDITION_ID);
        int fallbackIconId;
        switch (viewType) {
            case VIEW_TYPE_TODAY: {
//                fallbackIconId = Utility.getArtResourceForWeatherCondition(
//                        weatherId);
                viewHolder.iconView.setImageResource(Utility.getArtResourceForWeatherCondition(
                        weatherId));
                break;
            }
//            default: {
//                fallbackIconId = Utility.getIconResourceForWeatherCondition(
//                        weatherId);
            case VIEW_TYPE_FUTURE_DAY: {
                viewHolder.iconView.setImageResource(Utility.getIconResourceForWeatherCondition(
                        weatherId));
                break;
            }
        }

//        Glide.with(mContext)
//                .load(Utility.getArtUrlForWeatherCondition(mContext, weatherId))
//                .error(fallbackIconId)
//                .crossFade()
//                .into(viewHolder.iconView);

        long dateInMillis = cursor.getLong(ForecastFragment.COL_WEATHER_DATE);
        viewHolder.dateView.setText(Utility.getFriendlyDayString(context, dateInMillis));

        String description = Utility.getStringForWeatherCondition(context, weatherId);
        viewHolder.descriptionView.setText(description);
        viewHolder.descriptionView.setContentDescription(context.getString(R.string.a11y_forecast, description));


        String high = Utility.formatTemperature(
                context, cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP));
        viewHolder.highTempView.setText(high);
        viewHolder.highTempView.setContentDescription(context.getString(R.string.a11y_high_temp, high));

        String low = Utility.formatTemperature(
                context, cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP));
        viewHolder.lowTempView.setText(low);
        viewHolder.lowTempView.setContentDescription(context.getString(R.string.a11y_low_temp, low));
    }

    public void setUseTodayLayout(boolean useTodayLayout) {
        mUseTodayLayout = useTodayLayout;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 && mUseTodayLayout) ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }
}


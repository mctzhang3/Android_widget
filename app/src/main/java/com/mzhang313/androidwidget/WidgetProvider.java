package com.mzhang313.androidwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by farooq on 8/10/2017.
 */

public class WidgetProvider extends AppWidgetProvider {
    private Button button;

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Toast.makeText(context, "Widget has been added to your home screen!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int appWidgetId : appWidgetIds) {

            // Create an Intent to launch MainActivity
            Intent intent = new Intent(context, MainActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);

            remoteViews.setOnClickPendingIntent(R.id.widget_button, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget_layout
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);

        Log.e("App Widget", "onAppWidgetOptionsChanged()");
        int colSize = getCellsForSize(newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH));
        Log.e("App Widget", String.valueOf(colSize));

        updateUI(context, colSize, appWidgetManager, appWidgetId);

    }

    /*
    * update the UI according to the number of cols
    * */
    private void updateUI(Context context, int colSize, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        switch (colSize) {
            case 1:
                remoteViews.setViewVisibility(R.id.icon2, View.GONE);
                remoteViews.setViewVisibility(R.id.icon3, View.GONE);
                remoteViews.setViewVisibility(R.id.icon4, View.GONE);
                remoteViews.setViewVisibility(R.id.icon5, View.GONE);

                remoteViews.setViewVisibility(R.id.icon1, View.VISIBLE);
                break;
            case 2:
                remoteViews.setViewVisibility(R.id.icon5, View.GONE);
                remoteViews.setViewVisibility(R.id.icon4, View.GONE);
                remoteViews.setViewVisibility(R.id.icon3, View.GONE);

                remoteViews.setViewVisibility(R.id.icon2, View.VISIBLE);
                remoteViews.setViewVisibility(R.id.icon1, View.VISIBLE);
                break;
            case 3:
                remoteViews.setViewVisibility(R.id.icon5, View.GONE);
                remoteViews.setViewVisibility(R.id.icon4, View.GONE);

                remoteViews.setViewVisibility(R.id.icon3, View.VISIBLE);
                remoteViews.setViewVisibility(R.id.icon2, View.VISIBLE);
                remoteViews.setViewVisibility(R.id.icon1, View.VISIBLE);
                break;
            default:
                remoteViews.setViewVisibility(R.id.icon5, View.VISIBLE);
                remoteViews.setViewVisibility(R.id.icon4, View.VISIBLE);
                remoteViews.setViewVisibility(R.id.icon3, View.VISIBLE);
                remoteViews.setViewVisibility(R.id.icon2, View.VISIBLE);
                remoteViews.setViewVisibility(R.id.icon1, View.VISIBLE);
                break;
        }

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    /**
     * Returns number of cells needed for given size of the widget_layout.
     *
     * @param size Widget size in dp.
     * @return Size in number of cells.
     */
    private static int getCellsForSize(int size) {
        int n = 2;
        while (70 * n - 30 < size)
            ++n;

        return n - 1;
    }
}

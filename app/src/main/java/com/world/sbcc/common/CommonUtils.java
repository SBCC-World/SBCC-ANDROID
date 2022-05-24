package com.world.sbcc.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.world.sbcc.dialog.InstallMetaDialog;
import com.world.sbcc.dialog.LightControlDialog;
import com.world.sbcc.dialog.NoMemberDialog;
import com.world.sbcc.dialog.NotAuthDeviceDialog;
import com.world.sbcc.property.SBCCPropertites;

public class CommonUtils {
    public static void safeStartService(Context context, Intent intent) {
        try {
            context.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void safeStartActivity(Context context, Intent intent) {
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void StartMetaMaskApp(Context context) {

        try {
            String url ="metamask://wc?uri=wc:00e46b69-d0cc-4b3e-b6a2-cee442f97188@1?bridge=https%3A%2F%2Fbridge.walletconnect.org&key=91303dedf64285cbbaf9120f6e9d160a5c8aa3deb67017a3874cd272323f48ae";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();

            ShowInstallMetaDialog(context);
        }
    }

    public static void ShowNoMemberDialog(Context context) {
        FragmentActivity fragmentActivity = (FragmentActivity) context;
        FragmentManager fm = fragmentActivity.getSupportFragmentManager();
        NoMemberDialog noMemberDialog = NoMemberDialog.newInstance(null, null);
        noMemberDialog.show(fm, "noMemberDialog");
    }

    public static void ShowNotAuthDeviceDialog(Context context) {
        FragmentActivity fragmentActivity = (FragmentActivity) context;
        FragmentManager fm = fragmentActivity.getSupportFragmentManager();
        NotAuthDeviceDialog notAuthDeviceDialog = NotAuthDeviceDialog.newInstance(null, null);
        notAuthDeviceDialog.show(fm, "notAuthDeviceDialog");
    }

    public static void ShowInstallMetaDialog(Context context) {
        FragmentActivity fragmentActivity = (FragmentActivity) context;
        FragmentManager fm = fragmentActivity.getSupportFragmentManager();
        InstallMetaDialog installMetaDialog = InstallMetaDialog.newInstance(null, null);
        installMetaDialog.show(fm, "InstallMetaDialog");
    }

    public static void ShowControlDialog(Context context, int controlId) {
        if (controlId == SBCCPropertites.CONTROL_LIGHT_ID) {
            FragmentActivity fragmentActivity = (FragmentActivity) context;
            FragmentManager fm = fragmentActivity.getSupportFragmentManager();
            LightControlDialog lightControlDialog = LightControlDialog.newInstance(null, null);
            lightControlDialog.show(fm, "LightControlDialog");
        }

    }

    public static Bitmap getRoundedCornerBitmap(View destView, Bitmap bitmap, float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius) {
        Bitmap output = Bitmap.createBitmap(destView.getWidth(), destView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final float [] outlines = {topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius};
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(0, 0, destView.getWidth(), destView.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        float [] corners = {
                topLeftRadius, topLeftRadius,        // Top left radius in px
                topRightRadius, topRightRadius,        // Top right radius in px
                bottomRightRadius, bottomRightRadius,          // Bottom right radius in px
                bottomLeftRadius, bottomLeftRadius           // Bottom left radius in px
        };

        final Path path = new Path();
        path.addRoundRect(rectF, corners, Path.Direction.CW);
        canvas.drawPath(path, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rectF, paint);

        return output;
    }

    public static int ConvertDPtoPX(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}

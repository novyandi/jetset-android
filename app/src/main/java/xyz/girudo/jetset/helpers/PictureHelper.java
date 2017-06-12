package xyz.girudo.jetset.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import xyz.girudo.jetset.R;
import xyz.girudo.jetset.controllers.Config;

public class PictureHelper {
    public static final int TAKE_PICTURE = 13579;
    public static int PICK_FROM_GALLERY = 2468;
    private static PictureHelper pictureHelper;
    private DateHelper dateHelper;
    private Uri selectedImage;
    private AlertDialog pickerDialog;
    private Context ctx;
    private Fragment fragment;
    private LayoutInflater inflater;

    public PictureHelper(Context context) {
        dateHelper = new DateHelper();
        this.ctx = context;
        inflater = LayoutInflater.from(context);
    }

    public PictureHelper(Context context, Fragment fragment) {
        this(context);
        this.fragment = fragment;
    }

    public static PictureHelper getInstance(Context context) {
        if (pictureHelper == null) {
            pictureHelper = new PictureHelper(context);
        }
        pictureHelper.ctx = context;
        return pictureHelper;
    }

    public static PictureHelper getInstance(Context context, Fragment fragment) {
        pictureHelper = getInstance(context);
        pictureHelper.fragment = fragment;
        return pictureHelper;
    }

    public String getBase64Bitmap(Bitmap bitmap) {
        return Base64.encodeToString(getByteBitmap(bitmap), Base64.DEFAULT);
    }

    public byte[] getByteBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        return baos.toByteArray();
    }

    public void showPickPhotoDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        title = "Add Photo " + title;
        builder.setTitle(title);
        View view = LayoutInflater.from(ctx).inflate(R.layout.view_dialog_pick_image, null);
        Button pickFromCamera = (Button) view.findViewById(R.id.from_camera);
        Button pickFromGallery = (Button) view.findViewById(R.id.from_gallery);

        pickFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFormCamera((Activity) ctx);
            }
        });

        pickFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGalery((Activity) ctx);
            }
        });

        builder.setView(view);
        builder.setNegativeButton(pictureHelper.ctx.getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        pickerDialog = builder.create();
        pickerDialog.show();
    }

    public void pickFormCamera(Activity activity) {
        int requestCode = TAKE_PICTURE;
        String fileName = "IMG-" + dateHelper.getDateTime() + "-" + Config.APP_NAME;
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ContentValues values = new ContentValues();
        values.put(Media.TITLE, fileName);
        selectedImage = ctx.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, selectedImage);
        activity.startActivityForResult(takePicture, requestCode);
        if (pickerDialog != null) pickerDialog.dismiss();
    }

    public void pickFromGalery(Activity activity) {
        int requestCode = PICK_FROM_GALLERY;
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(pickPhoto, requestCode);
        if (pickerDialog != null) pickerDialog.dismiss();
    }

    public String getPath(Context ctx, Uri uri) {
        String[] projection = {MediaColumns.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = ((Activity) ctx).managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public Bitmap rotate(Bitmap sourceBitmap, int degrees) {
        if (degrees != 0 && sourceBitmap != null) {
            Matrix matrix = new Matrix();
            matrix.setRotate(degrees, (float) sourceBitmap.getWidth() / 2,
                    (float) sourceBitmap.getHeight() / 2);
            try {
                Bitmap rotateBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0,
                        sourceBitmap.getWidth(), sourceBitmap.getHeight(),
                        matrix, true);
                sourceBitmap = rotateBitmap;
            } catch (OutOfMemoryError ex) {
                throw ex;
            }
        }
        return sourceBitmap;
    }

    public int getImageRotation(String imagePath) throws IOException {
        ExifInterface exif = new ExifInterface(imagePath);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);
        int rotationAngle = 0;

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotationAngle = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotationAngle = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotationAngle = 270;
                break;
            default:
                break;
        }
        return rotationAngle;
    }

    public Uri getSelectedImage() {
        return selectedImage;
    }

    public void loadImage(Object url, ImageView imageView, int stubImage) {
        Glide.with(ctx)
                .load(url)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.1f)
                .into(imageView);
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public int sizeInDp(int size) {
        float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (size * scale + 0.5f);
    }
}
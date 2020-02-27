package com.nexttech.easybusinesscard.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nexttech.easybusinesscard.DB.BusinessCardDb;
import com.nexttech.easybusinesscard.Model.UserInfoModel;
import com.nexttech.easybusinesscard.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;


public class ViewActivity extends AppCompatActivity {


    Button btnSave,btnShare;

    Drawable imageResourceFront, imageResourceBack;

    ImageView cardFrontBackground, cardBackBackground;
    TextView cardName, cardDesignation, cardMobile, cardAddress;

    BusinessCardDb businessCardDb;
    UserInfoModel userData;

    private String qrCodeValue;
    private String savePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Business Card/";
    private Bitmap qrBitmap;
    private QRGEncoder qrgEncoder;
    private AppCompatActivity activity;

    View dialogueView;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    String temp;

    TextView tvDialogTitle, tvCard, tvQrCode, tvCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        businessCardDb = new BusinessCardDb(ViewActivity.this);
        userData = businessCardDb.getUserData();

        btnSave=findViewById(R.id.btnSave);
        btnShare=findViewById(R.id.btnShare);
        builder = new AlertDialog.Builder(ViewActivity.this);

        activity = this;

        temp=getIntent().getStringExtra("template");

        assert temp != null;
        switch (temp) {
            case "temp1":
                imageResourceFront = getResources().getDrawable(R.drawable.temp1_front);
                imageResourceBack = getResources().getDrawable(R.drawable.temp1_back);
                break;
            case "temp2":
                imageResourceFront = getResources().getDrawable(R.drawable.temp2_front);
                imageResourceBack = getResources().getDrawable(R.drawable.temp2_back);
                break;
            case "temp3":
                imageResourceFront = getResources().getDrawable(R.drawable.temp3_front);
                imageResourceBack = getResources().getDrawable(R.drawable.temp3_back);
                break;
        }


        View vi = LayoutInflater.from(this).inflate(R.layout.business_card_front,null);

        cardFrontBackground = vi.findViewById(R.id.card_front_background);
        cardBackBackground = vi.findViewById(R.id.card_back_background);
        cardName = vi.findViewById(R.id.card_name);
        cardDesignation = vi.findViewById(R.id.card_designation);
        cardMobile = vi.findViewById(R.id.card_mobile);
        cardAddress = vi.findViewById(R.id.card_address);

        cardFrontBackground.setImageDrawable(imageResourceFront);
        cardBackBackground.setImageDrawable(imageResourceBack);

        cardName.setText(userData.getName());
        cardDesignation.setText(userData.getDesignation());
        cardMobile.setText(userData.getMobile());
        cardAddress.setText(userData.getAddress());

        JSONObject myData = new JSONObject();
        try {
            myData.put("all_data",userData);
            myData.put("template",temp);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        qrCodeValue = myData.toString();

        final LinearLayout linearLayout = findViewById(R.id.linearlayout);
        if(vi.getParent() != null) {
            ((ViewGroup)vi.getParent()).removeView(vi); // <- fix
        }
        linearLayout.addView(vi);

        dialogueView = getLayoutInflater().inflate(R.layout.share_dialoguebox, null);

        tvDialogTitle = dialogueView.findViewById(R.id.tv_dialog_title);
        tvCard= dialogueView.findViewById(R.id.tv_card);
        tvQrCode = dialogueView.findViewById(R.id.tv_qrcode);
        tvCancel = dialogueView.findViewById(R.id.tv_cancel);

        builder.setView(null);
        builder.setView(dialogueView);

        alertDialog=builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogDismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvDialogTitle.setText("Save");

                tvCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap b = loadBitmapFromView(linearLayout);
                                saveImage(b, "MyCard-"+System.currentTimeMillis(), savePath+"/Cards/");
                            }
                        }, 1000);
                    }
                });

                tvQrCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                saveImage(qrBitmap, "QrCode-"+System.currentTimeMillis(), savePath+"/Qr Code/");
                            }
                        }, 1000);
                    }
                });

                alertDialogDismiss();
                alertDialog.show();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDialogTitle.setText("Share");

                tvCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap b = loadBitmapFromView(linearLayout);
                                shareIntent(b);
                            }
                        }, 1000);
                    }
                });

                tvQrCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                shareIntent(qrBitmap);
                            }
                        }, 1000);
                    }
                });

                alertDialogDismiss();
                alertDialog.show();
            }
        });


        generateQrCode();

    }

    private void generateQrCode() {

        if (qrCodeValue.length() > 0) {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(
                    qrCodeValue, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);

            try {
                qrBitmap = qrgEncoder.getBitmap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(activity, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    public Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }

    private void saveImage(final Bitmap b, final String fileName, final String filePath){

        Dexter.withActivity(activity).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                try {
                    File file = new File(filePath);
                    if(!file.exists()){
                        file.mkdirs();
                    }
                    boolean save = new QRGSaver().save(filePath, fileName, b, QRGContents.ImageType.IMAGE_PNG);
                    String result = save ? "Image Saved" : "Image Not Saved";
                    Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    private void shareIntent(Bitmap b){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, getImageUri(this,b));
        startActivity(Intent.createChooser(share,"Share via"));
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        Uri uri = Uri.parse(path);
        grantUriPermission("com.nexttech.easybusinesscard", uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//revoke permisions
        revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return uri;
    }

    private void alertDialogDismiss(){
        if (alertDialog.isShowing()){
            alertDialog.dismiss();
        }
    }
}
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
import android.util.Log;
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
import com.nexttech.easybusinesscard.Model.CollectionCardModel;
import com.nexttech.easybusinesscard.Model.UserInfoModel;
import com.nexttech.easybusinesscard.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.EnumMap;
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
    CollectionCardModel cardData;

    private String qrCodeValue;
    private String savePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Business Card/";
    private Bitmap qrBitmap;
    private QRGEncoder qrgEncoder;
    private AppCompatActivity activity;

    View dialogueView;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    LinearLayout linearLayout;

    String temp;

    TextView tvDialogTitle, tvCard, tvQrCode, tvCancel;
    JSONObject qrData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        businessCardDb = new BusinessCardDb(ViewActivity.this);

        btnSave=findViewById(R.id.btnSave);
        btnShare=findViewById(R.id.btnShare);
        builder = new AlertDialog.Builder(ViewActivity.this);

        activity = this;

        View vi = LayoutInflater.from(this).inflate(R.layout.business_card_front,null);

        cardFrontBackground = vi.findViewById(R.id.card_front_background);
        cardBackBackground = vi.findViewById(R.id.card_back_background);
        cardName = vi.findViewById(R.id.card_name);
        cardDesignation = vi.findViewById(R.id.card_designation);
        cardMobile = vi.findViewById(R.id.card_mobile);
        cardAddress = vi.findViewById(R.id.card_address);

        dialogueView = getLayoutInflater().inflate(R.layout.share_dialoguebox, null);

        linearLayout = findViewById(R.id.linearlayout);

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

        if(getIntent().hasExtra("data")){
            Log.e("data","found");
            if(getIntent().getStringExtra("data")!=null){
                Log.e("data","available");
                try {
                    qrData = new JSONObject(getIntent().getStringExtra("data"));
                    if (qrData.has("name") &&
                            qrData.has("designation") &&
                            qrData.has("project") &&
                            qrData.has("companyName") &&
                            qrData.has("email") &&
                            qrData.has("phone") &&
                            qrData.has("fax") &&
                            qrData.has("mobile") &&
                            qrData.has("website") &&
                            qrData.has("address")&&
                            qrData.has("template")) {
                        setUpAsOtherUser();
                    }else {
                        Toast.makeText(activity, "This is not a valid Data!", Toast.LENGTH_SHORT).show();
                    }


                }catch (JSONException e){
                    Toast.makeText(activity, "Unknown Data Type!", Toast.LENGTH_SHORT).show();
                }
            }

        }else if(getIntent().hasExtra("template")) {

            temp=getIntent().getStringExtra("template");

            userData = businessCardDb.getUserData();

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


            cardFrontBackground.setImageDrawable(imageResourceFront);
            cardBackBackground.setImageDrawable(imageResourceBack);

            cardName.setText(userData.getName());
            cardDesignation.setText(userData.getDesignation());
            cardMobile.setText(userData.getMobile());
            cardAddress.setText(userData.getAddress());

            JSONObject myData = new JSONObject();
            try {


                String name = userData.getName();
                String designation = userData.getDesignation();
                String project = userData.getProject();
                String companyName = userData.getCompanyName();
                String email = userData.getEmail();
                String phone = userData.getPhone();
                String fax = userData.getFax();
                String mobile = userData.getMobile();
                String website = userData.getWebsite();
                String address = userData.getAddress();



                myData.put("name",name);
                myData.put("designation",designation);
                myData.put("project",project);
                myData.put("companyName",companyName);
                myData.put("email", email);
                myData.put("phone",phone);
                myData.put("fax",fax);
                myData.put("mobile",mobile);
                myData.put("website",website);
                myData.put("address",address);
                myData.put("template",temp);

                Log.e("userdata",myData.toString());

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            qrCodeValue = myData.toString();

            if(vi.getParent() != null) {
                ((ViewGroup)vi.getParent()).removeView(vi); // <- fix
            }
            linearLayout.addView(vi);


            generateQrCode();
        }else if(getIntent().hasExtra("user_id")) {

            String id = getIntent().getStringExtra("user_id");

            cardData = businessCardDb.getSingleCardData(id);

            Log.e("card",cardData.getCardTemplate());

            switch (cardData.getCardTemplate()) {
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


            cardFrontBackground.setImageDrawable(imageResourceFront);
            cardBackBackground.setImageDrawable(imageResourceBack);

            cardName.setText(cardData.getName());
            cardDesignation.setText(cardData.getDesignation());
            cardMobile.setText(cardData.getMobile());
            cardAddress.setText(cardData.getAddress());

            JSONObject myData = new JSONObject();
            try {


                String name = cardData.getName();
                String designation = cardData.getDesignation();
                String project = cardData.getProject();
                String companyName = cardData.getCompanyName();
                String email = cardData.getEmail();
                String phone = cardData.getPhone();
                String fax = cardData.getFax();
                String mobile = cardData.getMobile();
                String website = cardData.getWebsite();
                String address = cardData.getAddress();



                myData.put("name",name);
                myData.put("designation",designation);
                myData.put("project",project);
                myData.put("companyName",companyName);
                myData.put("email", email);
                myData.put("phone",phone);
                myData.put("fax",fax);
                myData.put("mobile",mobile);
                myData.put("website",website);
                myData.put("address",address);
                myData.put("template",temp);

                Log.e("userdata",myData.toString());

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            qrCodeValue = myData.toString();

            if(vi.getParent() != null) {
                ((ViewGroup)vi.getParent()).removeView(vi); // <- fix
            }
            linearLayout.addView(vi);


            generateQrCode();
        }



    }

    void setUpAsOtherUser(){


        Log.e("debugging","here");

        try {

            String name = qrData.getString("name");
            String designation = qrData.getString("designation");
            String project = qrData.getString("project");
            String companyName = qrData.getString("companyName");
            String email = qrData.getString("email");
            String phone = qrData.getString("phone");
            String fax = qrData.getString("fax");
            String mobile = qrData.getString("mobile");
            String website = qrData.getString("website");
            String address = qrData.getString("address");
            String template = qrData.getString("template");

            CollectionCardModel model = new CollectionCardModel(name, designation, project, companyName, email, phone, fax, mobile, website, address, template);

            businessCardDb.insertOthersCardData(model);

            Log.e("debugging",qrData.toString());
            switch (template) {
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


            cardName.setText(name);
            cardDesignation.setText(designation);
            cardMobile.setText(mobile);
            cardAddress.setText(address);


            if(vi.getParent() != null) {
                ((ViewGroup)vi.getParent()).removeView(vi); // <- fix
            }
            linearLayout.addView(vi);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void generateQrCode() {

        if (qrCodeValue.length() > 0) {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = Math.min(width, height);
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
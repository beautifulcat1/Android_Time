package Time.main_java.Theone;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Time.main_java.R;
import Time.main_java.Synchronization.Download_From_Server;
import Time.main_java.TheFour.Today_Fragment;
import Time.main_java.TheFour.calender_fragement;
import Time.main_java.TheFour.intime_fragement;
import Time.main_java.TheFour.study_house_fragement;

/**
 * 支撑四大界面的activity
 */
public class today extends AppCompatActivity {
    public static final int CHOOSE_PHOTO = 2;
    private Manage_user manage_user = new Manage_user();
    private User_bean user_bean = new User_bean();
    private BottomNavigationView mBottomNavigationView;
    private int lastIndex;
    private TextView textView;
    private DrawerLayout drawerLayout;
    private Time.main_java.libary.CircleImageView Avatar;
    private Time.main_java.libary.CircleImageView BigAvatar;
    public TextView Email, Nackname, Shuoming, Call;
    ConstraintLayout e, n, s, c;
    List<Fragment> mFragments;
    Context todayContext = this;
    private static final String TAG = "today";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_view);
        initBottomNative();
        initData();
        init_view();
        open_drawer();
        Set_Miaoshu();
    }


    //初始化
    public void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new Today_Fragment());
        mFragments.add(new calender_fragement());
        mFragments.add(new study_house_fragement());
        mFragments.add(new intime_fragement());
        textView = findViewById(R.id.toolbar_title);
        setFragmentPosition(0);
        user_bean = manage_user.Query_From_Db();
        if(!user_bean.getemail().equals("")&&user_bean.getImagNum().equals("1")){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String s[]=new String[2];
                    s[0]="2";
                    s[1]=user_bean.getemail();
                    manage_user.Update_to_Db("update user_list set ImagNum=? where Email=?",s);
                    Download_From_Server download_from_server =new Download_From_Server(user_bean.getemail());
                }
            }).start();
        }
    }

    //    初始化视图
    public void init_view() {
        e = findViewById(R.id.constraintLayout3);
        n = findViewById(R.id.constraintLayout4);
        s = findViewById(R.id.constraintLayout5);
        c = findViewById(R.id.constraintLayout6);
        Email = findViewById(R.id.nav_Email);
        Nackname = findViewById(R.id.nav_nack_name);
        Shuoming = findViewById(R.id.nav_Shuoming);
        Call = findViewById(R.id.nav_Call);
        Avatar = findViewById(R.id.avatar);
        BigAvatar = findViewById(R.id.circleImageView2);
        if (!user_bean.getemail().equals("")) {
            Email.setText(user_bean.getemail());
        }
        if (!user_bean.getNackname().equals("")) {
            Nackname.setText(user_bean.getNackname());
        }
        if (!user_bean.getShuoming().equals("")) {
            Shuoming.setText(user_bean.getShuoming());
        }
        if (!user_bean.getPhone().equals("")) {
            Call.setText(user_bean.getPhone());
        }
        if (!user_bean.getImagAddr().equals("")) {
            Bitmap b2 = base64ToBitmap(user_bean.getImagAddr());
            Avatar.setImageBitmap(b2);
            BigAvatar.setImageBitmap(b2);
        }
    }


    //打开抽屉
    public void open_drawer() {
        drawerLayout = findViewById(R.id.drawer1);
        Avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }


    //设置导航按钮
    public void initBottomNative() {
        mBottomNavigationView = findViewById(R.id.nav_view);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_today:
                        setFragmentPosition(0);
                        textView.setText(R.string.menu1);
                        break;
                    case R.id.navigation_calender:
                        setFragmentPosition(1);
                        textView.setText(R.string.menu2);
                        break;
//                case R.id.navigation_study_house:
//                    setFragmentPosition(2);
//                    textView.setText(R.string.menu3);
//                    break;
                    case R.id.navigation_InTime:
                        setFragmentPosition(3);
                        textView.setText(R.string.menu4);
                        break;
                    default:
                        break;
                }
                // 这里注意返回true,否则点击失效
                return true;
            }
        });
    }


    //显示fragment
    private void setFragmentPosition(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = mFragments.get(position);
        Fragment lastFragment = mFragments.get(lastIndex);
        lastIndex = position;
        ft.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            ft.add(R.id.today_fragment, currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();
    }

    public void Set_Miaoshu() {
        Set_Email();
        Set_NackName();
        Set_Shuoming();
        Set_phone();
        Set_TouXiang();
    }

    public void Set_Email() {
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialog showing = new show_dialog(todayContext);
                showing.show();
                showing.input();
                showing.Set_input_Email();

            }
        });
    }

    public void Set_NackName() {
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialog showing = new show_dialog(todayContext);
                showing.show();
                showing.input();
                showing.Set_input_Nackname();
            }
        });
    }

    public void Set_Shuoming() {
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialog showing = new show_dialog(todayContext);
                showing.show();
                showing.input();
                showing.Set_input_Shuoming();
            }
        });
    }

    public void Set_phone() {
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialog showing = new show_dialog(todayContext);
                showing.show();
                showing.input();
                showing.Set_input_Call();
            }
        });
    }

    public void Set_TouXiang() {
        BigAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(today.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(today.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlarm();
                }
            }
        });
    }

    public void openAlarm() {
        Log.d(TAG, "openAlarm: ");
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlarm();
                } else {
                    Toast.makeText(this, "你取消了授权", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        Log.d(TAG, "onActivityResult: " + "执行了onActivity");
                        handleImageOnkitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }

                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnkitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    public void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        String values[] = new String[2];
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            BigAvatar.setImageBitmap(bitmap);
            Avatar.setImageBitmap(bitmap);
            String data = bitmapToBase64(bitmap);
            values[0] = data;
            values[1] = user_bean.getemail();
            manage_user.Update_to_Db("update user_list set ImagAddr=? where Email=?", values);
        } else {
            Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
        }
    }

    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    class show_dialog {
        private String What_input = new String();
        private View dia_view;
        private Dialog cus_dialog;
        private Button btn;
        private EditText input;
        private Context mcontext;

        public show_dialog(Context context) {
            mcontext = context;
            Init_dialog();
        }

        public void Init_dialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
            // 布局填充器
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            this.dia_view = inflater.inflate(R.layout.set_miaosu_dialog, null);
            this.input = dia_view.findViewById(R.id.input_word2);
            this.btn = dia_view.findViewById(R.id.btn);
            // 设置自定义的对话框界面
            builder.setView(dia_view);
            cus_dialog = builder.create();
        }

        public void show() {
            cus_dialog.show();
        }

        public void input() {
            input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    What_input = s.toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        public void Set_input_Email() {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String values[] = new String[2];
                    values[0] = What_input;
                    values[1] = user_bean.getemail();
                    manage_user.Update_to_Db("update user_list set Email=? where Email=?", values);
                    Email.setText(What_input);
                    cus_dialog.cancel();
                }
            });
        }

        public void Set_input_Nackname() {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String values[] = new String[2];
                    values[0] = What_input;
                    values[1] = user_bean.getemail();
                    manage_user.Update_to_Db("update user_list set Nackname=? where Email=?", values);
                    Nackname.setText(What_input);
                    cus_dialog.cancel();
                }
            });
        }

        public void Set_input_Shuoming() {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String values[] = new String[2];
                    values[0] = What_input;
                    values[1] = user_bean.getemail();
                    manage_user.Update_to_Db("update user_list set Shuoming=? where Email=?", values);
                    Shuoming.setText(What_input);
                    cus_dialog.cancel();
                }
            });
        }

        public void Set_input_Call() {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String values[] = new String[2];
                    values[0] = What_input;
                    values[1] = user_bean.getemail();
                    manage_user.Update_to_Db("update user_list set Phone=? where Email=?", values);
                    Call.setText(What_input);
                    cus_dialog.cancel();
                }
            });
        }
    }


}

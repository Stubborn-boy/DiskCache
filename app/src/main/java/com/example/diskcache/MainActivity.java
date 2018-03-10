package com.example.diskcache;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diskcache.cache.DiskCache;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvStr;
    TextView tvObj;
    ImageView img1;
    ImageView img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStr = (TextView) findViewById(R.id.tvStr);
        tvObj = (TextView) findViewById(R.id.tvObj);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);

        tvStr.setOnClickListener(this);
        tvObj.setOnClickListener(this);
        img1.setOnClickListener(this);

        DiskCache.getInstance(this).put("StringKey", "保存的String");
        DiskCache.getInstance(this).put("ObjectKey", new Person("张三", "18"));
        img1.post(new Runnable() {
            @Override
            public void run() {
                DiskCache.getInstance(MainActivity.this).putDrawable("DrawableKey", img1.getDrawable());
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvStr:
                String str = DiskCache.getInstance(this).get("StringKey");
                tvStr.setText(str);
                break;
            case R.id.tvObj:
                Person p = DiskCache.getInstance(this).get("ObjectKey");
                tvObj.setText(p.name+":"+p.age);
                break;
            case R.id.img1:
                Drawable drawable = DiskCache.getInstance(this).getDrawable("DrawableKey");
                img2.setImageDrawable(drawable);
                break;
        }
    }
}

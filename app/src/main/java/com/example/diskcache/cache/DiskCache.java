package com.example.diskcache.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by jack on 2018/3/10.
 */

public class DiskCache {

    private static DiskCache instance;
    private final int maxSize = 50;//单位M
    private DiskLruCache diskLruCache;
    private Context context;

    private DiskCache(Context context){
        this.context = context;
        if(diskLruCache==null){
            try {
                File directory = Utils.getCacheDirectory(context);
                int appVersion = Utils.getAppVersion(context);
                diskLruCache = DiskLruCache.open(directory, appVersion, 1, maxSize * 1024 * 1024);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static DiskCache getInstance(Context context){
        if(instance==null){
            synchronized (DiskCache.class){
                if(instance==null){
                    instance = new DiskCache(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    /**
     * 保存Object对象，Object要实现Serializable
     * @param key
     * @param value
     */
    public void put(String key, Object value){
        try {
            key = Utils.toMd5Key(key);
            DiskLruCache.Editor editor = diskLruCache.edit(key);
            if (editor != null) {
                OutputStream os = editor.newOutputStream(0);
                if (Utils.writeObject(os, value)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
                diskLruCache.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存Bitmap
     * @param key
     * @param bitmap
     */
    public void putBitmap(String key, Bitmap bitmap) {
        put(key, Utils.bitmap2Bytes(bitmap));
    }

    /**
     * 保存Drawable
     * @param key
     * @param value
     */
    public void putDrawable(String key, Drawable value) {
        putBitmap(key, Utils.drawable2Bitmap(value));
    }

    /**
     * 根据key获取保存对象
     * @param key
     * @param <T>
     * @return
     */
    public <T> T get(String key){
        try {
            key = Utils.toMd5Key(key);
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);

            if (snapshot != null) {
                InputStream inputStream = snapshot.getInputStream(0);
                return (T)Utils.readObject(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Bitmap
     * @param key
     * @return
     */
    public Bitmap getBitmap(String key) {
        byte[] bytes = (byte[]) get(key);
        if (bytes == null) return null;
        return Utils.bytes2Bitmap(bytes);
    }

    /**
     * 获取Drawable
     * @param key
     * @return
     */
    public Drawable getDrawable(String key) {
        byte[] bytes = (byte[]) get(key);
        if (bytes == null) {
            return null;
        }
        return Utils.bitmap2Drawable(context, Utils.bytes2Bitmap(bytes));
    }

    public long size() {
        return diskLruCache.size();
    }

    public void setMaxSize(int maxSize) {
        diskLruCache.setMaxSize(maxSize * 1024 * 1024);
    }

    public File getDirectory() {
        return diskLruCache.getDirectory();
    }

    public long getMaxSize() {
        return diskLruCache.getMaxSize();
    }

    public boolean remove(String key) {
        try {
            key = Utils.toMd5Key(key);
            return diskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void delete(){
        try {
            diskLruCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void flush(){
        try {
            diskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            diskLruCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isClosed() {
        return diskLruCache.isClosed();
    }

}

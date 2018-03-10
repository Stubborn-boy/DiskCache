# DiskCache
一个封装DiskLruCache的缓存工具类

## 用法示例

#### 1.保存String
```
DiskCache
    .getInstance(context)
    .put("StringKey", "保存的String");
```
#### 获取String
```
String str = DiskCache
    .getInstance(context)
    .get("StringKey");
```
#### 2.保存Object（任意实现Serializable的Object）
```
public class Person implements Serializable{
    public Person(String name, String age){
        this.name = name;
        this.age = age;
    }
    public String name;
    public String age;

}

Person person = new Person("张三", "18");

DiskCache
    .getInstance(context)
    .put("ObjectKey", person);
```
#### 获取Object
```
Person p = DiskCache
               .getInstance(context)
               .get("ObjectKey");
```
#### 3.保存Bitmap
```
DiskCache
    .getInstance(context)
    .putBitmap("BitmapKey", bitmap);
```
#### 获取Bitmap
```
Bitmap bitmap = DiskCache
                    .getInstance(context)
                    .getBitmap("BitmapKey");
```
#### 4.保存Drawable
```
DiskCache
    .getInstance(context)
    .putBitmap("DrawableKey", drawable);
```
#### 获取Drawable
```
Drawable drawable = DiskCache
                        .getInstance(context)
                        .getDrawable("DrawableKey");
```

默认的缓存路径： /sdcard/Android/data/（应用包名）/cache/diskcache

默认的缓存大小： 50M

该工具类使用了DiskLruCache，所以要在build.gradle中添加：

dependencies  {

    implementation 'com.jakewharton:disklrucache:2.0.2'

}



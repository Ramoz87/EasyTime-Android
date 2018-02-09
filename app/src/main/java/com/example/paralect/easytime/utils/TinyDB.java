package com.example.paralect.easytime.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class TinyDB {
    Context mContext;
    SharedPreferences preferences;
    String DEFAULT_APP_IMAGEDATA_DIRECTORY;
    File mFolder = null;
    public static String lastImagePath = "";

    public TinyDB(Context appContext) {
        this.mContext = appContext;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
    }

    public TinyDB(Context appContext, String fileKey) {
        this.mContext = appContext;
        this.preferences = this.mContext.getSharedPreferences(fileKey, 0);
    }

    public Bitmap getImage(String path) {
        Bitmap theGottenBitmap = null;

        try {
            theGottenBitmap = BitmapFactory.decodeFile(path);
        } catch (Exception var4) {
            ;
        }

        return theGottenBitmap;
    }

    public String getSavedImagePath() {
        return lastImagePath;
    }

    public String putImagePNG(String theFolder, String theImageName, Bitmap theBitmap) {
        this.DEFAULT_APP_IMAGEDATA_DIRECTORY = theFolder;
        String mFullPath = this.setupFolderPath(theImageName);
        this.saveBitmapPNG(mFullPath, theBitmap);
        lastImagePath = mFullPath;
        return mFullPath;
    }

    private String setupFolderPath(String imageName) {
        File sdcard_path = Environment.getExternalStorageDirectory();
        this.mFolder = new File(sdcard_path, this.DEFAULT_APP_IMAGEDATA_DIRECTORY);
        if(!this.mFolder.exists() && !this.mFolder.mkdirs()) {
            Log.e("While creatingsave path", "Default Save Path Creation Error");
        }

        String savePath = this.mFolder.getPath() + '/' + imageName;
        return savePath;
    }

    private boolean saveBitmapPNG(String strFileName, Bitmap bitmap) {
        if(strFileName != null && bitmap != null) {
            boolean bSuccess1 = false;
            File saveFile = new File(strFileName);
            if(saveFile.exists() && !saveFile.delete()) {
                return false;
            } else {
                try {
                    bSuccess1 = saveFile.createNewFile();
                } catch (IOException var21) {
                    var21.printStackTrace();
                }

                FileOutputStream out = null;

                boolean bSuccess2;
                try {
                    out = new FileOutputStream(saveFile);
                    bSuccess2 = bitmap.compress(CompressFormat.PNG, 100, out);
                } catch (Exception var20) {
                    var20.printStackTrace();
                    bSuccess2 = false;
                }

                boolean bSuccess3;
                try {
                    if(out != null) {
                        out.flush();
                        out.close();
                        bSuccess3 = true;
                    } else {
                        bSuccess3 = false;
                    }
                } catch (IOException var19) {
                    var19.printStackTrace();
                    bSuccess3 = false;
                } finally {
                    if(out != null) {
                        try {
                            out.close();
                        } catch (IOException var18) {
                            var18.printStackTrace();
                        }
                    }

                }

                return bSuccess1 && bSuccess2 && bSuccess3;
            }
        } else {
            return false;
        }
    }

    public int getInt(String key, int defaultKey) {
        return this.preferences.getInt(key, defaultKey);
    }

    public long getLong(String key, int defaultKey) {
        return this.preferences.getLong(key, (long)defaultKey);
    }

    public String getString(String key, String defaultKey) {
        return this.preferences.getString(key, defaultKey);
    }

    public double getDouble(String key, double defaultKey) {
        String number = this.getString(key, defaultKey + "");

        try {
            double value = Double.parseDouble(number);
            return value;
        } catch (NumberFormatException var7) {
            return 0.0D;
        }
    }

    public void putInt(String key, int value) {
        Editor editor = this.preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void putLong(String key, long value) {
        Editor editor = this.preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void putDouble(String key, double value) {
        this.putString(key, String.valueOf(value));
    }

    public void putString(String key, String value) {
        Editor editor = this.preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putList(String key, ArrayList<String> marray) {
        Editor editor = this.preferences.edit();
        String[] mystringlist = (String[])marray.toArray(new String[marray.size()]);
        editor.putString(key, TextUtils.join("‚‗‚", mystringlist));
        editor.apply();
    }

    public ArrayList<String> getList(String key) {
        String[] mylist = TextUtils.split(this.preferences.getString(key, ""), "‚‗‚");
        ArrayList<String> gottenlist = new ArrayList(Arrays.asList(mylist));
        return gottenlist;
    }

    public void putListInt(String key, ArrayList<Integer> marray, Context context) {
        Editor editor = this.preferences.edit();
        Integer[] mystringlist = (Integer[])marray.toArray(new Integer[marray.size()]);
        editor.putString(key, TextUtils.join("‚‗‚", mystringlist));
        editor.apply();
    }

    public ArrayList<Integer> getListInt(String key, Context context) {
        String[] mylist = TextUtils.split(this.preferences.getString(key, ""), "‚‗‚");
        ArrayList<String> gottenlist = new ArrayList(Arrays.asList(mylist));
        ArrayList<Integer> gottenlist2 = new ArrayList();

        for(int i = 0; i < gottenlist.size(); ++i) {
            gottenlist2.add(Integer.valueOf(Integer.parseInt((String)gottenlist.get(i))));
        }

        return gottenlist2;
    }

    public void putListBoolean(String key, ArrayList<Boolean> marray) {
        ArrayList<String> origList = new ArrayList();
        Iterator var4 = marray.iterator();

        while(var4.hasNext()) {
            Boolean b = (Boolean)var4.next();
            if(b.booleanValue()) {
                origList.add("true");
            } else {
                origList.add("false");
            }
        }

        this.putList(key, origList);
    }

    public ArrayList<Boolean> getListBoolean(String key) {
        ArrayList<String> origList = this.getList(key);
        ArrayList<Boolean> mBools = new ArrayList();
        Iterator var4 = origList.iterator();

        while(var4.hasNext()) {
            String b = (String)var4.next();
            if(b.equals("true")) {
                mBools.add(Boolean.valueOf(true));
            } else {
                mBools.add(Boolean.valueOf(false));
            }
        }

        return mBools;
    }

    public void putBoolean(String key, boolean value) {
        Editor editor = this.preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defaultKey) {
        return this.preferences.getBoolean(key, defaultKey);
    }

    public void putFloat(String key, float value) {
        Editor editor = this.preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public float getFloat(String key) {
        return this.getFloat(key, 0.0F);
    }

    public float getFloat(String key, float defValue) {
        return this.preferences.getFloat(key, defValue);
    }

    public void remove(String key) {
        Editor editor = this.preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public Boolean deleteImage(String path) {
        File tobedeletedImage = new File(path);
        Boolean isDeleted = Boolean.valueOf(tobedeletedImage.delete());
        return isDeleted;
    }

    public void clear() {
        Editor editor = this.preferences.edit();
        editor.clear();
        editor.apply();
    }

    public <N> List<N> getAll(String fileKey) {
        Map<String, ?> keys = this.preferences.getAll();
        List<N> list = new ArrayList();
        Iterator var4 = keys.entrySet().iterator();

        while(var4.hasNext()) {
            Entry entry = (Entry)var4.next();

            try {
                list.add((N) entry.getValue());
            } catch (Throwable var7) {
                var7.printStackTrace();
            }
        }

        return list;
    }

    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        this.preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        this.preferences.unregisterOnSharedPreferenceChangeListener(listener);
    }
}

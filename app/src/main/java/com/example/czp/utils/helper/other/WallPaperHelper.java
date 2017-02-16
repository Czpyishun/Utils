package com.example.czp.utils.helper.other;

public class WallPaperHelper {

    /*public static Logger logger = Logger.getLogger("WallPaperHelper");

    @Inject
    FormatHelper mFormatHelper;
    @Inject
    OkHttpUtil mOkHttpUtil;
    @Inject
    CacheHelper mCacheHelper;


    //设置壁纸
    public void setWallPaper(final Activity mActivity, String url) {
        showToast(mActivity, mActivity.getResources().getString(R.string.ap_wallpaper_setting));
        WallpaperManager mWallManager = WallpaperManager.getInstance(mActivity);
        InputStream inputStream = null;
        try {
            String key = mCacheHelper.UriToDiskLruCacheString(url);
            DiskLruCache.Snapshot snapShot = mCacheHelper.getCache().get(key);
            if (snapShot != null) {
                inputStream = snapShot.getInputStream(0);
            }

            if (inputStream == null) {
                if (url.startsWith("http") || url.startsWith("https")) {
                    inputStream = getWallPaperFromNet(url);
                } else {
                    if (url.startsWith("file://")) {
                        url = url.substring(6, url.length());
                    }
                    inputStream = new FileInputStream(url);
                }
            }

            if (inputStream != null) {
                try {
                    mWallManager.setStream(inputStream);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            showToast(mActivity, mActivity.getResources().getString(R.string.ap_wallpaper_set_success));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            showToast(mActivity, mActivity.getResources().getString(R.string.ap_wallpaper_set_fail));
                        }
                    });
                }
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showToast(mActivity, mActivity.getResources().getString(R.string.ap_wallpaper_set_fail));
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private InputStream getWallPaperFromNet(String url) {
        InputStream is = null;
        try {
            URL myFileURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            is = conn.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }


    //设置锁屏
    private void setLockWallPaper(Context mContext, File file) {
        Intent intent = createSetAsIntent(Uri.fromFile(file), null);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        mContext.startActivity(Intent.createChooser(intent, mContext.getResources().getString(R.string.ap_wallpaper_set_lockscreen)));
    }


    public Intent createSetAsIntent(Uri uri, String mimeType) {
        if (uri.getScheme().equals("file")) {
            String path = uri.getPath();
            int lastDotIndex = path.lastIndexOf('.');
            if (lastDotIndex != -1) {
                mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(uri.getPath().substring(lastDotIndex + 1).toLowerCase());
            }
        }
        Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
        intent.setDataAndType(uri, mimeType);
        intent.putExtra("mimeType", mimeType);
        return intent;
    }

    Handler handler = new Handler(Looper.getMainLooper());

    public void setLockWallPaper(final String url, final Context mContext) {
        showToast(mContext, mContext.getResources().getString(R.string.ap_wallpaper_setting_lockscreen));
        if (url.startsWith("file://")) {
            File target = new File(url.substring(6, url.length()));
            if (target.exists()) {
                setLockWallPaper(mContext, target);
            }
        } else {
            setLockWallFromNet(url, mContext);
        }
    }

    private void setLockWallFromNet(final String url, final Context mContext) {
        showToast(mContext, mContext.getResources().getString(R.string.ap_wallpaper_download));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String fileName = mFormatHelper.getFileNameFromUrl(url);
                    File cache = new File(getCacheDir(), fileName);
                    mOkHttpUtil.httpDownload(url, cache);
                    setLockWallPaper(mContext, cache);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public File getCacheDir() {
        File file = new File(Environment.getExternalStorageDirectory(), "cache");
        if (!file.mkdirs()) {
            logger.debug("Directory not created");
        }
        return file;
    }


    public void scanPhoto(Context mContext, File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        mContext.sendBroadcast(mediaScanIntent);
    }

    public void showToast(final Context mActivity, final String msg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(msg)) {
                    Toast.makeText(mActivity, msg,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/


}

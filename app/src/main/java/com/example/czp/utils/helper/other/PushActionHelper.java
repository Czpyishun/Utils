package com.example.czp.utils.helper.other;

public class PushActionHelper {


    /*public static final String openDetail = "ifsorder://opendetail";


    public void miPushAction(Context context, MiPushMessage message) {
        try {
            //打开应用
            if (AppManager.getAppManager().isAppExit()) {
                launch(context);
            }

            // 小米后台发送
            String content = message.getContent();
            if (!TextUtils.isEmpty(content) && content.startsWith(openDetail)) {
                startFanDetail(context, content);
                return;
            }

            // 服务端发送
            String url = getURLDecoderString(message.getExtra().get("url"));
            if (!TextUtils.isEmpty(url) && url.startsWith(openDetail)) {
                startFanDetail(context, url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void umengPushAction(Context context, UMessage msg) {
        try {
            //打开应用
            if (AppManager.getAppManager().isAppExit()) {
                launch(context);
            }
            // 后台发送
            Map<String, String> map = msg.extra;
            if (map != null) {
                int id = Integer.valueOf(map.get("id").trim());
                if (id != 0) {
                    FanDetailActivity_.intent(context).flags(Intent.FLAG_ACTIVITY_NEW_TASK).mDramaId(id).start();
                }
            }
            // 服务端发送
            if (!TextUtils.isEmpty(msg.custom)) {
                PushMsg push = Jsoner.getInstance().fromJson(msg.custom, PushMsg.class);
                if (push.url.startsWith(openDetail)) {
                    startFanDetail(context, push.url);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class PushMsg extends Jsonable {
        public String url;
    }


    public void launch(Context context) {
        Intent launchIntent = new Intent(context, MainActivity_.class);
        launchIntent.setAction(Intent.ACTION_MAIN);
        launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(launchIntent);
    }


    public void startFanDetail(Context context, String url) {
        Uri uri = Uri.parse(url);
        String detailId = uri.getQueryParameter("id");
        FanDetailActivity_.intent(context).flags(Intent.FLAG_ACTIVITY_NEW_TASK).mDramaId(Integer.valueOf(detailId)).start();
    }

    private final static String ENCODE = "GBK";

    public String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, ENCODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }*/



}

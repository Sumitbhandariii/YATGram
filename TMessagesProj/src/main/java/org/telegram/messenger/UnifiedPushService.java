package org.telegram.messenger;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;

import org.telegram.tgnet.ConnectionsManager;

import org.unifiedpush.android.connector.FailedReason;
import org.unifiedpush.android.connector.PushService;
import org.unifiedpush.android.connector.UnifiedPush;
import org.unifiedpush.android.connector.data.PushEndpoint;
import org.unifiedpush.android.connector.data.PushMessage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.CountDownLatch;

public class UnifiedPushService extends PushService
{
    @Override
    public void onNewEndpoint(PushEndpoint endpoint, String instance)
    {
        Utilities.globalQueue.postRunnable(() -> {
            SharedConfig.pushStringGetTimeEnd = SystemClock.elapsedRealtime();
            String token = "{\"endpoint\": \"" + endpoint.getUrl() + "\", \"keys\": {\"p256dh\": \"" + endpoint.getPubKeySet().getPubKey() + "\", \"auth\": \"" + endpoint.getPubKeySet().getAuth() + "\"}}";
            PushListenerController.sendRegistrationToServer(PushListenerController.PUSH_TYPE_WEBPUSH, token);
        });
    }

    @Override
    public void onMessage(PushMessage message, String instance)
    {
        final long receiveTime = SystemClock.elapsedRealtime();
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        AndroidUtilities.runOnUIThread(() -> {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("UP PRE INIT APP");
            }
            ApplicationLoader.postInitApplication();
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("UP POST INIT APP");
            }
            Utilities.stageQueue.postRunnable(() -> {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.d("UP START PROCESSING");
                }
                for (int a : SharedConfig.activeAccounts) {
                    if (UserConfig.getInstance(a).isClientActivated()) {
                        ConnectionsManager.onInternalPushReceived(a);
                        ConnectionsManager.getInstance(a).resumeNetworkMaybe();
                    }
                }
                countDownLatch.countDown();
            });
        });
        Utilities.globalQueue.postRunnable(()-> {
            try {
                countDownLatch.await();
            } catch (Throwable ignore) { }
            if (BuildVars.DEBUG_VERSION) {
                FileLog.d("finished UP service, time = " + (SystemClock.elapsedRealtime() - receiveTime));
            }
        });
    }

    @Override
    public void onRegistrationFailed(FailedReason reason, String instance)
    {
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d("Failed to get endpoint");
        }
        SharedConfig.pushStringStatus = "__UNIFIEDPUSH_FAILED__";
        Utilities.globalQueue.postRunnable(() -> {
            SharedConfig.pushStringGetTimeEnd = SystemClock.elapsedRealtime();
            PushListenerController.sendRegistrationToServer(PushListenerController.PUSH_TYPE_WEBPUSH, null);
        });
    }

    @Override
    public void onUnregistered(String instance)
    {
        unregister();
    }

    public static void unregister()
    {
        SharedPreferences.Editor editor = MessagesController.getGlobalMainSettings().edit();
        editor.putString("UPProvider", "");
        editor.commit();

        SharedConfig.pushStringStatus = "__UNIFIEDPUSH_FAILED__";
        Utilities.globalQueue.postRunnable(() -> {
            SharedConfig.pushStringGetTimeEnd = SystemClock.elapsedRealtime();
            PushListenerController.sendRegistrationToServer(PushListenerController.PUSH_TYPE_WEBPUSH, null);
        });
    }
}

package braga.com.br.pomodorius.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import braga.com.br.pomodorius.R;
import braga.com.br.pomodorius.activity.MainActivity;

/**
 * Created by aluno-r17 on 11/05/16.
 */
public class Notification {

    public static void updateNotification(Context ctx, String tempo) {

        Intent intent = new Intent(ctx.getApplicationContext(), MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(ctx.getApplicationContext(), 1111, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx.getApplicationContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Pomodorius")
                        .setContentIntent(pi)
                        .setContentText("Tempo restante: " + tempo + "s");

        /* finished?
        if (true) {
            Intent startIntent = new Intent(ctx.getApplicationContext(), StartTaskReceiver.class);
            pi = PendingIntent.getBroadcast(ctx.getApplicationContext(), 123, startIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.addAction(R.mipmap.ic_launcher, "Ir para aplicação", pi);
        }*/

        NotificationManagerCompat notificationService = NotificationManagerCompat.from(ctx);
        android.app.Notification notification = mBuilder.build();

        notificationService.notify(100, notification);

    }
}

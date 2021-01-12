package Time.main_java.Alarm;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

 public class Alarm_Shock{
    public class Shock{

    }
    public class  Alarm{
        MediaPlayer mediaPlayer = new MediaPlayer();
        AssetManager assetManager;
        public void  initMediaPlayer(Context context){
            try {
                assetManager=context.getAssets();
                AssetFileDescriptor fd =assetManager.openFd("Delacey - Dream It Possible.mp3");

                mediaPlayer.setDataSource(fd.getFileDescriptor(),fd.getStartOffset(),fd.getLength());
                mediaPlayer.prepare();
            }catch (Exception e){ e.printStackTrace();}
        }
        public void Start_Music(){
            mediaPlayer.start();
        }
        public void Stop_Music(Context context){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.reset();
               initMediaPlayer(context);
            }
        }
        public void Pause_Music(){
            mediaPlayer.pause();
        }

    }
}


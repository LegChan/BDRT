package com.nrk4220.android.bdrt;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoStreaming extends YouTubeBaseActivity {

    YouTubePlayer youTubeVidPlayer;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener initializelistener;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    private boolean currentlyPlaying = false;
    private String streamSrc;
    private boolean isDisplaying = false;
    private final static int NOTIFICATION_ID = (int) System.currentTimeMillis();
    private NotificationCompat.Builder notifiBuilder;
    private NotificationManager notifiManager;
    private RemoteViews remoteViews;
    //private GestureDetector gestureDetector;

    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new
            AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int i) {
                    if (i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK || i ==
                            AudioManager.AUDIOFOCUS_LOSS) {
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    } else if (i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                        mediaPlayer.pause();
                    } else if (i == AudioManager.AUDIOFOCUS_GAIN) {
                        mediaPlayer.start();
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_streaming);

        streamSrc = SongDetail.getStreamUrl();

        if(streamSrc.isEmpty() || streamSrc == null){
            Toast toast = Toast.makeText(VideoStreaming.this, "Please select a song", Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(VideoStreaming.this, SongList.class);
            startActivity(intent);
        }

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.video);
        initializelistener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubeVidPlayer = youTubePlayer;
                youTubeVidPlayer.cueVideo(streamSrc);
                youTubeVidPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                if (youTubeInitializationResult == YouTubeInitializationResult.CLIENT_LIBRARY_UPDATE_REQUIRED) {
                    Toast toast = Toast.makeText(VideoStreaming.this, "Failed to initialize video view : CLIENT_LIBRARY_UPDATE_REQUIRED", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (youTubeInitializationResult == YouTubeInitializationResult.DEVELOPER_KEY_INVALID) {
                    Toast toast = Toast.makeText(VideoStreaming.this, "Failed to initialize video view : DEVELOPER_KEY_INVALID", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (youTubeInitializationResult == YouTubeInitializationResult.ERROR_CONNECTING_TO_SERVICE) {
                    Toast toast = Toast.makeText(VideoStreaming.this, "Failed to initialize video view : ERROR_CONNECTING_TO_SERVICE", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (youTubeInitializationResult == YouTubeInitializationResult.INTERNAL_ERROR) {
                    Toast toast = Toast.makeText(VideoStreaming.this, "Failed to initialize video view : INTERNAL_ERROR", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (youTubeInitializationResult == YouTubeInitializationResult.INVALID_APPLICATION_SIGNATURE) {
                    Toast toast = Toast.makeText(VideoStreaming.this, "Failed to initialize video view : INVALID_APPLICATION_SIGNATURE", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (youTubeInitializationResult == YouTubeInitializationResult.NETWORK_ERROR) {
                    Toast toast = Toast.makeText(VideoStreaming.this, "Failed to initialize video view : NETWORK_ERROR", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (youTubeInitializationResult == YouTubeInitializationResult.SERVICE_DISABLED) {
                    Toast toast = Toast.makeText(VideoStreaming.this, "Failed to initialize video view : SERVICE_DISABLED", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (youTubeInitializationResult == YouTubeInitializationResult.SERVICE_INVALID) {
                    Toast toast = Toast.makeText(VideoStreaming.this, "Failed to initialize video view : SERVICE_INVALID", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (youTubeInitializationResult == YouTubeInitializationResult.SERVICE_MISSING) {
                    Toast toast = Toast.makeText(VideoStreaming.this, "Failed to initialize video view : SERVICE_MISSING", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (youTubeInitializationResult == YouTubeInitializationResult.SERVICE_VERSION_UPDATE_REQUIRED) {
                    Toast toast = Toast.makeText(VideoStreaming.this, "Failed to initialize video view : SERVICE_VERSION_UPDATE_REQUIRED", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (youTubeInitializationResult == YouTubeInitializationResult.UNKNOWN_ERROR) {
                    Toast toast = Toast.makeText(VideoStreaming.this, "Failed to initialize video view : UNKNOWN_ERROR", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Log.v("Initialize", "SUCCESS");
                }
                Intent intent = new Intent(VideoStreaming.this, SongList.class);
                startActivity(intent);
            }
        };
        youTubePlayerView.initialize("AIzaSyCnZTNDVsDyXoTcHeFlHhI_h7caYjPmhps", initializelistener);

        final ImageButton forward10 = (ImageButton) findViewById(R.id.forward10);
        final ImageButton replay10 = (ImageButton) findViewById(R.id.replay10);
        final ImageButton pauseStartToggle = (ImageButton) findViewById(R.id.pauseStartToggle);
        final ImageButton openInBrowser = (ImageButton) findViewById(R.id.openInBrowser);
        final ImageButton invisibilityToggle = (ImageButton) findViewById(R.id.invisibilityButton);

        forward10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (youTubeVidPlayer != null) {
                    int currentTime = youTubeVidPlayer.getCurrentTimeMillis();
                    youTubeVidPlayer.seekToMillis(currentTime + 10000);
                }
            }
        });

        replay10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (youTubeVidPlayer != null) {
                    int currentTime = youTubeVidPlayer.getCurrentTimeMillis();
                    youTubeVidPlayer.seekToMillis(currentTime - 10000);
                }
            }
        });

        pauseStartToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!currentlyPlaying) {
                    if (youTubeVidPlayer != null) {
                        youTubeVidPlayer.play();
                        pauseStartToggle.setImageResource(R.drawable.icon_pause);
                        currentlyPlaying = true;
                    }
                } else {
                    if (youTubeVidPlayer != null) {
                        youTubeVidPlayer.pause();
                        pauseStartToggle.setImageResource(R.drawable.icon_play);
                        currentlyPlaying = false;
                    }
                }
            }
        });

        openInBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + streamSrc));
                startActivity(intent);
            }
        });

        invisibilityToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.menuLayout);
                relativeLayout.setVisibility(View.GONE);
                notifiManager.notify(NOTIFICATION_ID, notifiBuilder.build());
            }
        });
        setNotification();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNotification();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();
        notifiManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notifiManager.cancel(NOTIFICATION_ID);
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
        if (youTubeVidPlayer != null) {
            youTubeVidPlayer.pause();
        }
        notifiManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notifiManager.cancel(NOTIFICATION_ID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseYoutubePlayer();
        releaseMediaPlayer();
        notifiManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notifiManager.cancel(NOTIFICATION_ID);

    }

    private void releaseYoutubePlayer() {
        if (youTubeVidPlayer != null) {
            youTubeVidPlayer.release();
            youTubeVidPlayer = null;
        }
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }

    private void setNotification(){
        notifiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        remoteViews = new RemoteViews(getPackageName(), R.layout.remote_menu);
        IntentFilter filter = new IntentFilter("open_menu");
        BroadcastReceiver br = new OpenMenu();
        registerReceiver(br, filter);
        Intent menuButton = new Intent("open_menu");
        menuButton.putExtra("id", NOTIFICATION_ID);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, menuButton, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.menuButton, pendingIntent);
        notifiBuilder = new NotificationCompat.Builder(this, "Menu");
        notifiBuilder.setSmallIcon(R.drawable.icon_menu);
        notifiBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notifiBuilder.setCustomContentView(remoteViews);
        notifiBuilder.setContentIntent(pendingIntent);
        notifiManager.notify(NOTIFICATION_ID, notifiBuilder.build());
    }

    private class OpenMenu extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(intent.getExtras().getInt("id"));

            final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.menuLayout);
            relativeLayout.setVisibility(View.VISIBLE);
            CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long l) {
                }

                @Override
                public void onFinish() {
                    relativeLayout.setVisibility(View.GONE);
                    if (youTubeVidPlayer != null) {
                        youTubeVidPlayer.play();
                    }
                    setNotification();

                }
            };
            countDownTimer.start();
        }
    }
}


        /*youtTubePlayerView.setOnTouchListener(new SwipeDown(this) {
            @Override
            public void Execute() {
            final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.vidTools);
                linearLayout.setVisibility(View.VISIBLE);

                CountDownTimer countDownTimer = new CountDownTimer(10000, 10000) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        linearLayout.setVisibility(View.GONE);
                    }
                };
                countDownTimer.start();
            }
        });


        menuTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.VISIBLE);
                menuTool.setVisibility(View.GONE);

                CountDownTimer countDownTimer = new CountDownTimer(10000, 10000) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        linearLayout.setVisibility(View.GONE);
                        menuTool.setVisibility(View.VISIBLE);
                    }
                };
                countDownTimer.start();
            }
        });*/


        /*RelativeLayout youtTubePlayerView = (RelativeLayout) findViewById(R.id.parentLayout);
        youTubePlayerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int request = audioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (request == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    releaseMediaPlayer();
                    mediaPlayer = MediaPlayer.create(VideoStreaming.this, R.raw.sound);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });*/

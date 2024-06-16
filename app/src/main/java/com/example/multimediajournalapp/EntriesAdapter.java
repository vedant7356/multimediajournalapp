package com.example.multimediajournalapp;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class EntriesAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> entries;

    public EntriesAdapter(Context context, List<String> entries) {
        super(context, 0, entries);
        this.context = context;
        this.entries = entries;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String entry = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.entry_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.textViewEntry);
        ImageView imageView = convertView.findViewById(R.id.imageViewIcon);

        String formattedText = formatEntryText(entry);
        textView.setText(formattedText);

        if (entry.endsWith(".3gp")) {
            imageView.setImageResource(R.drawable.baseline_audiotrack_24); // Placeholder for audio icon
        } else if (entry.endsWith(".jpg")) {
            imageView.setImageResource(R.drawable.baseline_photo_camera_24); // Placeholder for photo icon
        } else if (entry.endsWith(".mp4")) {
            imageView.setImageResource(R.drawable.baseline_video_camera_front_24); // Placeholder for video icon
        } else {
            imageView.setImageResource(R.drawable.baseline_device_unknown_24); // Placeholder for unknown type icon
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entry.endsWith(".3gp")) {
                    playAudio(entry);
                } else if (entry.endsWith(".jpg")) {
                    viewPhoto(entry);
                } else if (entry.endsWith(".mp4")) {
                    playVideo(entry);
                }
            }
        });

        return convertView;
    }

    private String formatEntryText(String entry) {
        File file = new File(entry);
        String fileType;
        if (entry.endsWith(".3gp")) {
            fileType = "Audio Recorded";
        } else if (entry.endsWith(".jpg")) {
            fileType = "Photo Captured";
        } else if (entry.endsWith(".mp4")) {
            fileType = "Video Recorded";
        } else {
            fileType = "Unknown Entry";
        }

        String date = DateFormat.format("yyyy-MM-dd hh:mm:ss", new Date(file.lastModified())).toString();
        return fileType + " on " + date;
    }

    private void playAudio(String audioPath) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioPath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void viewPhoto(String photoPath) {
        Intent intent = new Intent(context, PhotoViewActivity.class);
        intent.putExtra("photoPath", photoPath);
        context.startActivity(intent);
    }

    private void playVideo(String videoPath) {
        Intent intent = new Intent(context, VideoViewActivity.class);
        intent.putExtra("videoPath", videoPath);
        context.startActivity(intent);
    }
}

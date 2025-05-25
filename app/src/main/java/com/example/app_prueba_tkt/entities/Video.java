package com.example.app_prueba_tkt.entities;

import java.util.List;

public class Video {
    public int id;
    public String nombreVideo;
    public String archivoVideo;
    public List<FileVideo> videofile;

    public Video(String nombreVideo, String archivoVideo)
    {
        this.nombreVideo = nombreVideo;
        this.archivoVideo = archivoVideo;
    }
    public class FileVideo {
        public String quality;
        public String file_type;
        public String link;

        public FileVideo(String quality, String file_type, String link)
        {
            this.quality=quality;
            this.file_type=file_type;
            this.link=link;
        }
    }
}

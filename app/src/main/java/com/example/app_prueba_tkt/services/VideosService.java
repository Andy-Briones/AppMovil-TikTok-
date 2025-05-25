package com.example.app_prueba_tkt.services;


import com.example.app_prueba_tkt.entities.VideoResponsePexels;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.Call;

public interface VideosService {
    @GET("videos/popular")
    Call<VideoResponsePexels> getPopularVideos(@Header("Authorization") String apiKey);
//    @GET("api/videos/")
//    Call<VideoResponsePexels> getVideos(@Query("key") String apiKey, @Query("q") String query);
    //Key: Csxediy1WLPvsZr06bVVRt64XujcpntZubAEl9UnTKgAgwRJqKy1540A
}

package com.zoomfolks.tidsoptimist_bot.bot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "reddit-meme-api", url = "${bot.meme-api-url}/api/v1/memes")
public interface MemeApiClient {

    @GetMapping("/random")
    RedditResponse getRandomMeme(@RequestParam("subreddits") List<String> subreddits);
}

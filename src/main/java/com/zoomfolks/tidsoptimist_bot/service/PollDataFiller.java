package com.zoomfolks.tidsoptimist_bot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoomfolks.tidsoptimist_bot.bot.client.CompletionGPTClient;
import com.zoomfolks.tidsoptimist_bot.bot.client.models.ChatCompletionRequest;
import com.zoomfolks.tidsoptimist_bot.bot.pojo.AnswerResponse;
import com.zoomfolks.tidsoptimist_bot.bot.pojo.PollData;
import com.zoomfolks.tidsoptimist_bot.bot.pojo.QuestionResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PollDataFiller {

    private static final String GPT_MODEL = "gpt-3.5-turbo";

    private static final String QUESTION_MESSAGE = """
            Write a question to a teammates to if they already checked their PRs for review. Message should be in a
            sarcastic tone and no longer than one sentence. Write it in the following JSON format {"question": "string"}
            """;

    private static final Function<String, String> ANSWERS_MESSAGE_FORMAT = question -> String.format("""
            Write some answers to the following question: "%s"
            The answers should be divided into 4 categories:
                        
            * Yes
            * No
            * In Progress
            * Ignore
                        
            Write an example for each of the categories. Answer for the each category should be no longer than 10 words. "No" and "Ignore" should be written in a sarcastic mood. "Yes" and "In Progress" just in a funny way
            Write it as a JSON object with the exact structure:
            [{ category: "string", answer: "string"}]
            """, question);

    private final CompletionGPTClient client;

    public PollDataFiller(CompletionGPTClient client) {
        this.client = client;
    }

    public PollData generatePollData() {
        var question = generateQuestion().question();
        return new PollData(question, generateAnswers(question));
    }

    @SneakyThrows
    private QuestionResponse generateQuestion() {
        var chatCompletionRequest = getRequest(QUESTION_MESSAGE, 100);
        var completion = client.getCompletion(chatCompletionRequest);

        log.info("Got response from chatGPT: {}", completion);

        return new ObjectMapper().readValue(completion.choices().iterator().next().message().content(), QuestionResponse.class);
    }

    @SneakyThrows
    private List<String> generateAnswers(String question) {
        var chatCompletionRequest = getRequest(ANSWERS_MESSAGE_FORMAT.apply(question), null);
        var completion = client.getCompletion(chatCompletionRequest);

        log.info("Got response from chatGPT: {}", completion);

        var answerResponse = new ObjectMapper().readValue(completion.choices().iterator().next().message().content(), AnswerResponse[].class);

        return Arrays.stream(answerResponse).map(AnswerResponse::answer).collect(Collectors.toList());

    }

    private ChatCompletionRequest getRequest(String message, Integer maxTokens) {
        return new ChatCompletionRequest(
                GPT_MODEL,
                List.of(new ChatCompletionRequest.Message("user", message)),
                maxTokens,
                0.7
        );
    }
}

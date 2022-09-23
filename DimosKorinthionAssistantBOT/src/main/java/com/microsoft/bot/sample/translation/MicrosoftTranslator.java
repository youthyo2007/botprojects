// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.bot.sample.translation;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import com.microsoft.bot.integration.Configuration;
import com.microsoft.bot.sample.translation.model.LanguageDetectorResult;
import com.microsoft.bot.sample.translation.model.TranslatorResponse;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

import org.slf4j.LoggerFactory;

/**
 * A helper class wrapper for the Microsoft Translator API.
 */
public class MicrosoftTranslator {
    private static final String HOST = "https://api.cognitive.microsofttranslator.com";
    private static final String TRANSLATE_PATH = "/translate?api-version=3.0";
    private static final String DETECT_PATH = "/detect?api-version=3.0";
    private static final String URI_PARAMS = "&to=";

    private final String key;
    private final String location;

    /**
     * @param configuration The configuration class with the translator key stored.
     */
    public MicrosoftTranslator(Configuration configuration) {
        String translatorKey = configuration.getProperty("TranslatorKey");

        if (translatorKey == null) {
            throw new IllegalArgumentException("key");
        }

        key = translatorKey;

        String translatorLocation = configuration.getProperty("TranslatorLocation");

        if (translatorLocation == null) {
            throw new IllegalArgumentException("location");
        }

        location = translatorLocation;

    }

    /**
     * Helper method to translate text to a specified language.
     * @param text Text that will be translated.
     * @param targetLocale targetLocale Two character language code, e.g. "en", "es".
     * @return The first translation result
     */
    public CompletableFuture<String> translate(String text, String fromLocale, String targetLocale) {
        return CompletableFuture.supplyAsync(() -> {
            // From Cognitive Services translation documentation:
            // https://docs.microsoft.com/en-us/azure/cognitive-services/Translator/quickstart-translator?tabs=java
            String body = String.format("[{ \"Text\": \"%s\" }]", text);

            StringBuilder uri = new StringBuilder(MicrosoftTranslator.HOST).append(MicrosoftTranslator.TRANSLATE_PATH);
            
            /*if (fromLocale!=null) {
                    uri.append("&from="+fromLocale);
            }*/

            uri.append(MicrosoftTranslator.URI_PARAMS).append(targetLocale);

            
             
        


            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), body);

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(uri.toString())
                .header("Ocp-Apim-Subscription-Key", key)
                .header("Ocp-Apim-Subscription-Region", location)
                .post(requestBody)
                .build();

            try {

                System.out.println("Translate Text:"+text+" to:"+targetLocale);

                Response response = client.newCall(request).execute();

                if (!response.isSuccessful()) {
                    String message = new StringBuilder("The call to the translation service returned HTTP status code ")
                        .append(response.code())
                        .append(".").toString();
                    throw new Exception(message);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                Reader reader = new StringReader(response.body().string());
                TranslatorResponse[] result = objectMapper.readValue(reader, TranslatorResponse[].class);

                return result[0].getTranslations().get(0).getText();

            } catch (Exception e) {
                LoggerFactory.getLogger(MicrosoftTranslator.class).error("findPackages", e);
                throw new CompletionException(e);
            }
        });
    }

    public CompletableFuture<String> detect(String text) {
        return CompletableFuture.supplyAsync(() -> {
            // From Cognitive Services translation documentation:
            // https://docs.microsoft.com/en-us/azure/cognitive-services/Translator/quickstart-translator?tabs=java
            String body = String.format("[{ \"Text\": \"%s\" }]", text);

            String uri = new StringBuilder(MicrosoftTranslator.HOST)
                .append(MicrosoftTranslator.DETECT_PATH).toString();

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), body);

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(uri)
                .header("Ocp-Apim-Subscription-Key", key)
                .header("Ocp-Apim-Subscription-Region", location)
                .post(requestBody)
                .build();

            try {

           

                Response response = client.newCall(request).execute();

                if (!response.isSuccessful()) {
                    String message = new StringBuilder("The call to the detect service returned HTTP status code ")
                        .append(response.code())
                        .append(".").toString();
                    throw new Exception(message);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                Reader reader = new StringReader(response.body().string());

                List<LanguageDetectorResult> detectorResult = objectMapper.readValue(reader, new TypeReference<List<LanguageDetectorResult>>(){});
                //LanguageDetectorResult detectorResult = objectMapper.readValue(reader, LanguageDetectorResult.class);
                return detectorResult.get(0).getLanguage();

            } catch (Exception e) {
                LoggerFactory.getLogger(MicrosoftTranslator.class).error("findPackages", e);
                e.printStackTrace();
                throw new CompletionException(e);
            }
        });
    }


}

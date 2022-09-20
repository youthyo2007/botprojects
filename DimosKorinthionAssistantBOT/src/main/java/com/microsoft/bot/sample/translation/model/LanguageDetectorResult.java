// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.bot.sample.translation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Translation result from Translator API v3.
 */
public class LanguageDetectorResult {
    @JsonProperty("language")
    private String language;

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    };

    @JsonProperty("score")
    private String score;



    public String getScore() {
        return this.score;
    }

    public void setScore(String score) {
        this.score = score;
    };

    @JsonProperty("isTranslationSupported")
    private String isTranslationSupported;

    public String  getIsTranslationSupported() {
        return this.isTranslationSupported;
    }

    public void setIsTranslationSupported(
        String isTranslationSupported) {
        this.isTranslationSupported = isTranslationSupported;
    };


    @JsonProperty("isTransliterationSupported")
    private String isTransliterationSupported;

    public String getIsTransliterationSupported() {
        return this.isTransliterationSupported;
    }

    public void setIsTransliterationSupported(String isTransliterationSupported) {
        this.isTransliterationSupported = isTransliterationSupported;
    };


   
}

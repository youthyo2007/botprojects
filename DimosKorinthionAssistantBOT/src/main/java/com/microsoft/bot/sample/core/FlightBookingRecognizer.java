// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.bot.sample.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microsoft.bot.ai.luis.LuisApplication;
import com.microsoft.bot.ai.luis.LuisRecognizer;
import com.microsoft.bot.ai.luis.LuisRecognizerOptionsV3;
import com.microsoft.bot.builder.Recognizer;
import com.microsoft.bot.builder.RecognizerResult;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.integration.Configuration;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.CompletableFuture;

/**
 * The class in charge of recognizing the booking information.
 */
public class FlightBookingRecognizer implements Recognizer {

    private LuisRecognizer recognizer;

    /**
     * The constructor of the FlightBookingRecognizer class.
     *
     * @param configuration The Configuration object to use.
     */
    public FlightBookingRecognizer(Configuration configuration) {
            /* 
            Boolean luisIsConfigured = StringUtils.isNotBlank(configuration.getProperty("LuisAppId"))
            && StringUtils.isNotBlank(configuration.getProperty("LuisAPIKey"))
            && StringUtils.isNotBlank(configuration.getProperty("LuisAPIHostName"));
            */
        
        
            LuisApplication luisApplication = new LuisApplication("e62c2c02-7738-4aa2-bb57-a30a213e696f",
                "e263dc76fee04758a228deebd23e8d50",
                String.format("https://%s", new String("languageunderstandingresourcegs-authoring.cognitiveservices.azure.com"))
            );


        
        //if (luisIsConfigured) {
            
            
            /* 
            LuisApplication luisApplication = new LuisApplication(
                configuration.getProperty("LuisAppId"),
                configuration.getProperty("LuisAPIKey"),
                String.format("https://%s", configuration.getProperty("LuisAPIHostName"))
            );
            */


            // Set the recognizer options depending on which endpoint version you want to use.
            // More details can be found in
            // https://docs.microsoft.com/en-gb/azure/cognitive-services/luis/luis-migration-api-v3
            LuisRecognizerOptionsV3 recognizerOptions = new LuisRecognizerOptionsV3(luisApplication);
            recognizerOptions.setIncludeInstanceData(true);

            this.recognizer = new LuisRecognizer(recognizerOptions);
        //}
    }

    /**
     * Verify if the recognizer is configured.
     *
     * @return True if it's configured, False if it's not.
     */
    public Boolean isConfigured() {
        return this.recognizer != null;
    }

    /**
     * Return an object with preformatted LUIS results for the bot's dialogs to consume.
     *
     * @param context A {link TurnContext}
     * @return A {link RecognizerResult}
     */
    public CompletableFuture<RecognizerResult> executeLuisQuery(TurnContext context) {
        return this.recognizer.recognize(context);
    }

    /**
     * Gets the From data from the entities which is part of the result.
     *
     * @param result The recognizer result.
     * @return The object node representing the From data.
     */
    public ObjectNode getFromEntities(RecognizerResult result) {
        String fromValue = "", fromAirportValue = "";
        if (result.getEntities().get("$instance").get("From") != null) {
            fromValue = result.getEntities().get("$instance").get("From").get(0).get("text")
                .asText();
        }
        if (!fromValue.isEmpty()
            && result.getEntities().get("From").get(0).get("Airport") != null) {
            fromAirportValue = result.getEntities().get("From").get(0).get("Airport").get(0).get(0)
                .asText();
        }

        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        ObjectNode entitiesNode = mapper.createObjectNode();
        entitiesNode.put("from", fromValue);
        entitiesNode.put("airport", fromAirportValue);
        return entitiesNode;
    }

    /**
     * Gets the To data from the entities which is part of the result.
     *
     * @param result The recognizer result.
     * @return The object node representing the To data.
     */
    public ObjectNode getToEntities(RecognizerResult result) {
        String toValue = "", toAirportValue = "";
        if (result.getEntities().get("$instance").get("To") != null) {
            toValue = result.getEntities().get("$instance").get("To").get(0).get("text").asText();
        }
        if (!toValue.isEmpty() && result.getEntities().get("To").get(0).get("Airport") != null) {
            toAirportValue = result.getEntities().get("To").get(0).get("Airport").get(0).get(0)
                .asText();
        }

        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        ObjectNode entitiesNode = mapper.createObjectNode();
        entitiesNode.put("to", toValue);
        entitiesNode.put("airport", toAirportValue);
        return entitiesNode;
    }


        /**
     * Gets the To data from the entities which is part of the result.
     *
     * @param result The recognizer result.
     * @return The object node representing the To data.
     */
    public ObjectNode getFindPlaceEntities(RecognizerResult result) {
        String placesPlaceType = "", placesNearBy = "", placesProduct = "", placesCuisine ="", placesName="", placesLocation=""; 
        if (result.getEntities().get("$instance").get("Places_PlaceType") != null) {
            placesPlaceType = result.getEntities().get("$instance").get("Places_PlaceType").get(0).asText();
        }

        if (result.getEntities().get("$instance").get("Places_Nearby") != null) {
            placesNearBy = result.getEntities().get("$instance").get("Places_Nearby").get(0).asText();
        }

        if (result.getEntities().get("$instance").get("Places_FindProduct") != null) {
            placesProduct = result.getEntities().get("$instance").get("Places_FindProduct").get(0).asText();
        }

        if (result.getEntities().get("Places_Cuisine") != null) {
            placesCuisine = result.getEntities().get("Places_Cuisine").get(0).asText();
        }

        if (result.getEntities().get("Places_PlaceName") != null) {
            placesName = result.getEntities().get("Places_PlaceName").get(0).asText();
        }

        if (result.getEntities().get("Places_AbsoluteLocation") != null) {
            placesLocation = result.getEntities().get("Places_AbsoluteLocation").get(0).asText();
        }

        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        ObjectNode entitiesNode = mapper.createObjectNode();
        entitiesNode.put("placeType", placesPlaceType);
        entitiesNode.put("placeNearBy", placesNearBy);
        entitiesNode.put("placeProduct", placesProduct);
        entitiesNode.put("placeCuisine", placesCuisine);
        entitiesNode.put("placeName", placesName);
        entitiesNode.put("placeLocation", placesLocation);
        
        
        
        return entitiesNode;
    }


    public ObjectNode getAddressEntities(RecognizerResult result) {
        String placesPlaceType = "", placesPlaceName = "", placesAbosulteLocation = ""; 
        if (result.getEntities().get("$instance").get("Places_PlaceType") != null) {
            placesPlaceType = result.getEntities().get("$instance").get("Places_PlaceType").get(0).get("text").asText();
        }

        if (result.getEntities().get("$instance").get("Places_PlaceName") != null) {
            placesPlaceName = result.getEntities().get("$instance").get("Places_PlaceName").get(0).get("text").asText();
        }

        if (result.getEntities().get("$instance").get("Places_AbsoluteLocation") != null) {
            placesAbosulteLocation = result.getEntities().get("$instance").get("Places_AbsoluteLocation").get(0).get("text").asText();
        }

     

        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        ObjectNode entitiesNode = mapper.createObjectNode();
        entitiesNode.put("Places_PlaceType", placesPlaceType);
        entitiesNode.put("Places_PlaceName", placesPlaceName);
        entitiesNode.put("Places_AbsoluteLocation", placesAbosulteLocation);
        
        return entitiesNode;
    }


    public ObjectNode getPhoneNumberEntities(RecognizerResult result) {
        String placesPlaceType = "", placesPlaceName = "", placesAbosulteLocation = ""; 
        if (result.getEntities().get("$instance").get("Places_PlaceType") != null) {
            placesPlaceType = result.getEntities().get("$instance").get("Places_PlaceType").get(0).get("text").asText();
        }

        if (result.getEntities().get("$instance").get("Places_PlaceName") != null) {
            placesPlaceName = result.getEntities().get("$instance").get("Places_PlaceName").get(0).get("text").asText();
        }

        if (result.getEntities().get("$instance").get("Places_AbsoluteLocation") != null) {
            placesAbosulteLocation = result.getEntities().get("$instance").get("Places_AbsoluteLocation").get(0).get("text").asText();
        }

     

        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        ObjectNode entitiesNode = mapper.createObjectNode();
        entitiesNode.put("Places_PlaceType", placesPlaceType);
        entitiesNode.put("Places_PlaceName", placesPlaceName);
        entitiesNode.put("Places_AbsoluteLocation", placesAbosulteLocation);
        
        return entitiesNode;
    }
    /**
     * This value will be a TIMEX. And we are only interested in a Date so grab the first result and
     * drop the Time part. TIMEX is a format that represents DateTime expressions that include some
     * ambiguity. e.g. missing a Year.
     *
     * @param result A {link RecognizerResult}
     * @return The Timex value without the Time model
     */
    public String getTravelDate(RecognizerResult result) {
        JsonNode datetimeEntity = result.getEntities().get("datetime");
        if (datetimeEntity == null || datetimeEntity.get(0) == null) {
            return null;
        }

        JsonNode timex = datetimeEntity.get(0).get("timex");
        if (timex == null || timex.get(0) == null) {
            return null;
        }

        String datetime = timex.get(0).asText().split("T")[0];
        return datetime;
    }

    /**
     * Runs an utterance through a recognizer and returns a generic recognizer result.
     *
     * @param turnContext Turn context.
     * @return Analysis of utterance.
     */
    @Override
    public CompletableFuture<RecognizerResult> recognize(TurnContext turnContext) {
        return this.recognizer.recognize(turnContext);
    }

}

    
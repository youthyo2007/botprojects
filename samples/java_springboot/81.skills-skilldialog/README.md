# SkillDialog

Bot Framework v4 Skills with Dialogs sample.

This bot has been created using the [Bot Framework](https://dev.botframework.com); it shows how to use a skill dialog from a root bot.

This sample is a Spring Boot app and uses the Azure CLI and azure-webapp Maven plugin to deploy to Azure.

## Prerequisites

- Java 1.8+
- Install [Maven](https://maven.apache.org/)
- An account on [Azure](https://azure.microsoft.com) if you want to deploy to Azure.

## Key concepts in this sample

The solution uses dialogs, within both a parent bot (`dialog-root-bot`) and a skill bot (`dialog-skill-bot`).
It demonstrates how to post activities from the parent bot to the skill bot and return the skill responses to the user.

- `dialog-root-bot`: this project shows how to consume a skill bot using a `SkillDialog`. It includes:
  - A [Main Dialog](dialog-root-bot/src/main/java/com/microsoft/bot/sample/dialogrootbot/dialogs/MainDialog.java) that can call different actions on a skill using a `SkillDialog`:
    - To send events activities.
    - To send message activities.
    - To cancel a `SkillDialog` using `CancelAllDialogs` that automatically sends an `EndOfConversation` activity to remotely let a skill know that it needs to end a conversation.
  - A sample [AdapterWithErrorHandler](dialog-root-bot/src/main/java/com/microsoft/bot/sample/dialogrootbot/AdapterWithErrorHandler.java) adapter that shows how to handle errors, terminate skills and send traces back to the emulator to help debugging the bot.
  - A sample [AllowedSkillsClaimsValidator](dialog-root-bot/src/main/java/com/microsoft/bot/sample/dialogrootbot/authentication/AllowedSkillsClaimsValidator.java) class that shows how to validate that responses sent to the bot are coming from the configured skills.
  - A [Logger Middleware](dialog-root-bot/src/main/java/com/microsoft/bot/sample/dialogrootbot/middleware/LoggerMiddleware.java) that shows how to handle and log activities coming from a skill.
  - A [SkillConfiguration](dialog-root-bot/src/main/resources/application.properties) class that can load skill definitions from the `DefaultConfig` class.
  - An [Application](dialog-root-bot/src/main/java/com/microsoft/bot/sample/dialogrootbot/Application.java) class that can load the skill definitions from the application.properties file.
- `dialog-skill-bot`: this project shows a modified CoreBot that acts as a skill. It receives event and message activities from the parent bot and executes the requested tasks. This project includes:
  - An [ActivityRouterDialog](dialog-skill-bot/src/main/java/com/microsoft/bot/sample/dialogskillbot/dialogs/ActivityRouterDialog.java) that handles Event and Message activities coming from a parent and performs different tasks.
    - Event activities are routed to specific dialogs using the parameters provided in the `Values` property of the activity.
    - Message activities are sent to LUIS if configured and trigger the desired tasks if the intent is recognized.
  - A sample [ActivityHandler](dialog-skill-bot/src/main/java/com/microsoft/bot/sample/dialogskillbot/bots/SkillBot.java) that uses the `run` method on `DialogExtensions`.
  - A sample [SkillAdapterWithErrorHandler](dialog-skill-bot/src/main/java/com/microsoft/bot/sample/dialogskillbot/SkillAdapterWithErrorHandler.java) adapter that shows how to handle errors, terminate the skills, send traces back to the emulator to help debugging the bot and send `EndOfConversation` messages to the parent bot with details of the error.
  - An [Application](dialog-skill-bot/src/main/java/com/microsoft/bot/sample/dialogskillbot/Application.java) class that shows how to register the different skill components.
  - A [sample skill manifest](dialog-skill-bot/src/main/webapp/manifest/echoskillbot-manifest-1.0.json) that describes what the skill can do.

## To try this sample
- Create a bot registration in the azure portal for the `dialog-skill-bot` and update [dialog-skill-bot/src/main/resources/application.properties](dialog-skill-bot/src/main/resources/application.properties) with the `MicrosoftAppId` and `MicrosoftAppPassword` of the new bot registration
- Create a bot registration in the azure portal for the `dialog-root-bot` and update [dialog-root-bot/src/main/resources/application.properties](dialog-root-bot/src/main/resources/application.properties) with the `MicrosoftAppId` and `MicrosoftAppPassword` of the new bot registration
- Update the BotFrameworkSkills section in [dialog-root-bot/src/main/resources/application.properties](dialog-root-bot/src/main/resources/application.properties) with the AppId for the skill you created in the previous step.
- (Optionally) Add the `dialog-root-bot` `MicrosoftAppId` to the `AllowedCallers` comma separated list in [dialog-skill-bot/src/main/resources/application.properties](dialog-skill-bot/src/main/resources/application.properties)

## Testing the bot using Bot Framework Emulator

[Bot Framework Emulator](https://github.com/microsoft/botframework-emulator) is a desktop application that allows bot developers to test and debug their bots on localhost or running remotely through a tunnel.

- Install the latest Bot Framework Emulator from [here](https://github.com/Microsoft/BotFramework-Emulator/releases)

### Connect to the bot using Bot Framework Emulator
- Launch Bot Framework Emulator
- File -> Open Bot
- Enter a Bot URL of `http://localhost:3978/api/messages`, the `MicrosoftAppId` and `MicrosoftAppPassword` for the `dialog-root-bot`

## Deploy the bot to Azure

To learn more about deploying a bot to Azure, see [Deploy your bot to Azure](https://aka.ms/azuredeployment) for a complete list of deployment instructions.

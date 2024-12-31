/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

const {onRequest} = require("firebase-functions/v2/https");
const logger = require("firebase-functions/logger");

const { LanguageServiceClient } = require('@google-cloud/language');
const { onDocumentCreated } = require("firebase-functions/v2/firestore");
const functions = require("firebase-functions");
const admin = require("firebase-admin");

// Initialize the Natural Language API client
const languageClient = new LanguageServiceClient();

// Initialize Firebase admin
admin.initializeApp();

const db = admin.firestore();

exports.analyzeSentiment = functions.https.onRequest(async (req, res) => {
  try {
    // Check if text is provided in the request body
    const text = req.body.text;
    if (!text) {
      return res.status(400).send({ error: "Text is required in the request body." });
    }

    // Prepare the document for sentiment analysis
    logger.info("Preparing document for sentiment analysis", {structuredData: true});
    const document = {
      content: text,
      type: "PLAIN_TEXT",
    };

    // Call the Natural Language API
    logger.info("Calling api", {structuredData: true});
    const [result] = await languageClient.analyzeSentiment({ document });

    // Extract sentiment data
    const sentiment = result.documentSentiment;

    // Send response
    res.status(200).send({
      text: text,
      sentiment: {
        score: sentiment.score,
        magnitude: sentiment.magnitude,
      },
    });
  } catch (error) {
    console.error("Error analyzing sentiment:", error);
    res.status(500).send({ error: "Failed to analyze sentiment." });
  }
});


// Create and deploy your first functions
// https://firebase.google.com/docs/functions/get-started

// exports.helloWorld = onRequest((request, response) => {
//   logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });

exports.onMessageUpdate = onDocumentCreated('rooms/{roomId}/messages/{messageId}', async (event) => {

        try {

            const { roomId, messageId } = event.params;

            const newMessage = event.data.data();
            logger.info("New message generated::"+newMessage.text, {structuredData: true});

                // Prepare the message for sentiment analysis
            logger.info("Preparing message for sentiment analysis", {structuredData: true});
                const document = {
                  content: newMessage.text,
                  type: "PLAIN_TEXT",
                };

                // Call the Natural Language API
                logger.info("Calling api", {structuredData: true});
                const [result] = await languageClient.analyzeSentiment({ document });

                // Extract sentiment data
                const sentiment = result.documentSentiment;

                const roomSnapshot = db.collection('rooms/${roomId}').get();
                const messagesRef = db.collection('rooms/${roomId}/messages');

                const oldScore = roomSnapshot.data()['score']

                const noOfMessages = messagesRef.count();

                const newAverage = (noOfMessages * oldScore) + sentiment.score / noOfMessages + 1;

                await roomRef.update({
                    score: newAverage
                });

        } catch(error) {
            console.error("Error analyzing sentiment:", error);
        }
});
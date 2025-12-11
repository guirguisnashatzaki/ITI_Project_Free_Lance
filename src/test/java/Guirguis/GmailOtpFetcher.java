package Guirguis;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GmailOtpFetcher {
    private static final String APPLICATION_NAME = "Gmail OTP Fetcher";
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/gmail.readonly");
    private static final String CREDENTIALS_FILE_PATH = "credintials.json"; // from Google Cloud
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static Credential getCredentials() throws Exception {
        InputStream in = Files.newInputStream(Paths.get(CREDENTIALS_FILE_PATH));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        var flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new com.google.api.client.util.store.FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private final Gmail service;

    public GmailOtpFetcher() throws Exception {
        Credential credential = getCredentials();
        service = new Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Wait for the latest email matching the query and return the first OTP found (digits).
     * query: Gmail search query e.g. "from:no-reply@indeed.com subject:verification OR subject:code"
     */
    public String waitForOtp(String query, long timeoutMs, long pollIntervalMs) throws Exception {
        long end = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < end) {
            ListMessagesResponse listResponse = service.users().messages().list("me").setQ(query).setMaxResults(5L).execute();
            if (listResponse.getMessages() != null && !listResponse.getMessages().isEmpty()) {
                for (var msgRef : listResponse.getMessages()) {
                    Message msg = service.users().messages().get("me", msgRef.getId()).setFormat("full").execute();
                    String body = getMessageBody(msg);
                    String code = extractOtp(body);
                    if (code != null) return code;
                }
            }
            Thread.sleep(pollIntervalMs);
        }
        throw new RuntimeException("OTP not found within timeout");
    }

    private static String getMessageBody(Message message) {
        if (message.getPayload() == null) return "";
        var parts = message.getPayload().getParts();
        // If single-part
        if (parts == null || parts.isEmpty()) {
            var body = message.getPayload().getBody();
            if (body != null && body.getData() != null) {
                return new String(Base64.getUrlDecoder().decode(body.getData()));
            }
            return "";
        }
        // iterate parts for text/html or text/plain
        for (var part : parts) {
            String mime = part.getMimeType();
            if (mime != null && (mime.equals("text/plain") || mime.equals("text/html"))) {
                if (part.getBody() != null && part.getBody().getData() != null) {
                    return new String(Base64.getUrlDecoder().decode(part.getBody().getData()));
                }
            }
            // nested parts
            if (part.getParts() != null) {
                for (var p2 : part.getParts()) {
                    if (p2.getBody() != null && p2.getBody().getData() != null) {
                        return new String(Base64.getUrlDecoder().decode(p2.getBody().getData()));
                    }
                }
            }
        }
        return "";
    }

    private static String extractOtp(String text) {
//        if (text == null) return null;
//        // adjust regex to the length of OTP (4-8 digits)
//        Pattern p = Pattern.compile("\\b(\\d{4,8})\\b");
//        Matcher m = p.matcher(text);
//        if (m.find()) return m.group(1);
//        return null;
        Pattern pattern = Pattern.compile("<strong>(\\d+)</strong>");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(1);
        }else{
            return null;
        }
    }

    // quick test main
//    public static void main(String[] args) throws Exception {
//        GmailOtpFetcher f = new GmailOtpFetcher();
//        // Example query: from Indeed's no-reply; change as needed
//        String otp = f.waitForOtp("from:(no-reply@indeed.com) OR subject:(verification OR code)", 120000, 3000);
//        System.out.println("Found OTP: " + otp);
//    }
}

//1081066679950-fikmgu3umf0f6noi0v0gddd7i01icdgm.apps.googleusercontent.com
//Clent id

//964367785825-22pme8uf5soqvasrjem3uv8adoeb32an.apps.googleusercontent.com

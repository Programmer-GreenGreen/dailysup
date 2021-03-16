package project.dailysup.config;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FcmConfig {

    private static final String FIREBASE_CONFIG_PATH = "firebase-adminsdk.json";

    @Bean
    public FirebaseApp FirebaseApp() throws IOException {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream()))
                .build();

        return FirebaseApp.initializeApp(options, "DailySup");
    }
}

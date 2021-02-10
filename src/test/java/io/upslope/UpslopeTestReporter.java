package io.upslope;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.upslope.TestHelpers.getRandomString;

public class UpslopeTestReporter implements TestExecutionListener {

    public static final String USERNAME_TXT_PATH = "src/test/resources/username.txt";
    private ArrayList<HashMap<String, Object>> results = new ArrayList<>();

    public void dynamicTestRegistered(TestIdentifier testIdentifier) {
//        System.out.println("--------------------dynamicTestRegistered-------------------------");
//        System.out.println(testIdentifier);
    }

    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        HashMap<String, Object> object = new HashMap<String, Object>() {
            {
                put("testIdentifier", testIdentifier);
                put("testExecutionResult", testExecutionResult);
            }
        };
        results.add(object);
//        System.out.println("--------------------executionFinished-------------------------");
//        System.out.println(testIdentifier);
//        System.out.println(testExecutionResult);
    }

    public void executionSkipped(TestIdentifier testIdentifier, String reason) {
//        System.out.println("--------------------executionSkipped-------------------------");
//        System.out.println(testIdentifier);
//        System.out.println(reason);
    }

    public void executionStarted(TestIdentifier testIdentifier) {
//        System.out.println("--------------------executionStarted-------------------------");
//        System.out.println(testIdentifier);
    }

    public void reportingEntryPublished(TestIdentifier testIdentifier, ReportEntry entry) {
//        System.out.println("--------------------reportingEntryPublished-------------------------");
//        System.out.println(testIdentifier);
//        System.out.println(entry);
    }

    private String readUsername() {
        try {
            Path path = Paths.get(USERNAME_TXT_PATH);
            ;
//            System.out.println(path);
            Stream<String> lines = Files.lines(path);
            String data = lines.collect(Collectors.joining("\n"));
            lines.close();
            return data;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private String writeUsername() throws IOException {
        String username = String.format("user-%s", getRandomString());
        BufferedWriter writer = new BufferedWriter(new FileWriter(USERNAME_TXT_PATH));
        writer.write(username);
        writer.close();
        return username;
    }

    public void testPlanExecutionFinished(TestPlan testPlan) {
//        System.out.println("--------------------testPlanExecutionFinished-------------------------");

        try {
            String username = readUsername();
//            System.out.println(username);

            if (username == null) {
                username = writeUsername();
            }

//            URL url = new URL("http://localhost:4031/upslope-crux/us-central1/junitSubmission");
            URL url = new URL("https://us-central1-upslope-crux.cloudfunctions.net/junitSubmission");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setConnectTimeout(3000);
            con.setReadTimeout(1000);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new Jdk8Module());

            String jsonInputString = objectMapper.writeValueAsString(new PostBody(username, results));
//            System.out.println(jsonInputString);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

//            int code = con.getResponseCode();
//            System.out.println(code);

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
//                System.out.println(response.toString());
            }
        } catch (IOException e) {
        }
    }

    public void testPlanExecutionStarted(TestPlan testPlan) {
//        System.out.println("--------------------testPlanExecutionStarted-------------------------");
//        System.out.println(testPlan);
    }

    static class PostBody {
        private final String username;
        private final List<HashMap<String, Object>> results;

        PostBody(String username, List<HashMap<String, Object>> results) {
            this.username = username;
            this.results = results;
        }

        public String getUsername() {
            return username;
        }

        public List<HashMap<String, Object>> getResults() {
            return results;
        }
    }

}

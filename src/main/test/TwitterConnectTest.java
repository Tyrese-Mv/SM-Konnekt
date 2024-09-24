//import com.github.scribejava.core.oauth.OAuth10aService;
//import javafx.beans.value.ObservableBooleanValue;
//import org.junit.jupiter.api.Test;
//import org.tyresemv.smkonnect.models.TwitterConnect;
//
//import java.io.IOException;
//import java.util.concurrent.ExecutionException;
//
//import static javafx.beans.binding.Bindings.when;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mock;;
//
//public class TwitterConnectTest {
//
//
//    // Initialize TwitterConnect and set up OAuth service correctly
//    @Test
//    public void test_initialize_and_set_service() {
//        TwitterConnect twitterConnect = new TwitterConnect();
//        twitterConnect.setService();
//
//        assertNotNull(twitterConnect.service);
//        assertEquals("https://api.twitter.com/1.1/account/verify_credentials.json", TwitterConnect.PROTECTED_RESOURCE_URL);
//    }
//
//    // Handle IOException during request token retrieval
//    @Test
//    public void test_handle_ioexception_during_request_token_retrieval() throws IOException, ExecutionException, InterruptedException {
//        TwitterConnect twitterConnect = new TwitterConnect();
//        twitterConnect.setService();
//
//        OAuth10aService mockService = mock(OAuth10aService.class);
//        twitterConnect.service = mockService;
//
//        when((ObservableBooleanValue) mockService.getRequestToken()).then(new IOException("IO Exception"));
//
//        assertThrows(IOException.class, twitterConnect::ObtainRequest);
//    }
//}
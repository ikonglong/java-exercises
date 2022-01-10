package book_network_programming.ch08;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DaytimeClientTest {
    @Test
    public void getTime() {
        DaytimeClient client = new DaytimeClient();
        client.getTime();
    }
}
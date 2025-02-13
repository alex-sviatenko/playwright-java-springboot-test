package com.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "test")
public class TestProperties {

    private String browser;
    private boolean headless;
    private int timeout;
    private Slow slow = new Slow();
    private Video video = new Video();
    private Screen screen = new Screen();

    @Data
    public static class Slow {
        private int motion;
    }

    @Data
    public static class Video {
        private boolean enabled;
        private String path;
        private Size size = new Size();

        @Data
        public static class Size {
            private int width;
            private int height;
        }
    }

    @Data
    public static class Screen {
        private Size size = new Size();

        @Data
        public static class Size {
            private int width;
            private int height;
        }
    }
}

package fr.uge.poo.cmdline.ex4;

import java.net.InetSocketAddress;
import java.util.Objects;

public class ApplicationSettings {
    private boolean legacy;
    private boolean bordered;
    private String windowName;
    private int width;
    private int height;
    private int borderWidth;
    private InetSocketAddress serverPort;

    private ApplicationSettings(Builder builder) {
        Objects.requireNonNull(builder);
        this.legacy = builder.legacy;
        this.bordered = builder.bordered;
        this.windowName = builder.windowName;
        this.width = builder.width;
        this.height = builder.height;
        this.borderWidth = builder.borderWidth;
        this.serverPort = builder.serverPort;
    }

    public static Builder builder() {
        return new Builder();
    }

    static public class Builder {

        private boolean legacy = false;
        private boolean bordered = false;
        private String windowName;
        private int width = 500;
        private int height = 500;
        private int borderWidth = 10;
        private InetSocketAddress serverPort;

        public Builder legacy(boolean legacy) {
            this.legacy = legacy;
            return this;
        }

        public Builder bordered(boolean bordered) {
            this.bordered = bordered;
            return this;
        }

        public Builder setBorderWidth(int width) {
            this.bordered = true;
            this.borderWidth = width;
            return this;
        }

        public Builder setWindowName(String s) {
            this.windowName = s;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setServer(String namePort, int port) {
            Objects.requireNonNull(namePort);
            this.serverPort = new InetSocketAddress(namePort, port);
            return this;
        }

        public ApplicationSettings build() {
            if (this.windowName == null) {
                throw new IllegalStateException("no file name");
            }
            if (this.width < 0 || this.height < 0) {
                throw new IllegalStateException("window size is negative");
            }
            if (this.borderWidth < 0) {
                throw new IllegalStateException("border size is negative");
            }
            return new ApplicationSettings(this);
        }
    }
    @Override
    public String toString(){
        return "PaintSettings [ name = " + windowName +
                ", bordered = " + bordered +
                ", legacy = "+ legacy +
                ", width = " + width +
                ", height = " + height +
                ", borderWidth = " + borderWidth +
                ", serverPort = " + serverPort.toString() +
                " ]";
    }
}